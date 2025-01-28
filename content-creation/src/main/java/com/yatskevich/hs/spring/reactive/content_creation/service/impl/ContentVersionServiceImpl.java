package com.yatskevich.hs.spring.reactive.content_creation.service.impl;

import com.yatskevich.hs.spring.reactive.content_creation.dto.ContentStatusDto;
import com.yatskevich.hs.spring.reactive.content_creation.dto.RevisionDataDto;
import com.yatskevich.hs.spring.reactive.content_creation.dto.RevisionDto;
import com.yatskevich.hs.spring.reactive.content_creation.entity.Content;
import com.yatskevich.hs.spring.reactive.content_creation.entity.ContentStatus;
import com.yatskevich.hs.spring.reactive.content_creation.entity.Revision;
import com.yatskevich.hs.spring.reactive.content_creation.repository.ContentRepository;
import com.yatskevich.hs.spring.reactive.content_creation.service.ContentService;
import com.yatskevich.hs.spring.reactive.content_creation.service.ContentVersionService;
import com.yatskevich.hs.spring.reactive.content_creation.service.DeltaService;
import com.yatskevich.hs.spring.reactive.content_creation.service.RevisionService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ContentVersionServiceImpl implements ContentVersionService {

    private final ContentService contentService;
    private final ContentRepository contentRepository;
    private final RevisionService revisionService;
    private final DeltaService deltaService;

    @Override
    public CompletableFuture<List<RevisionDto>> getAllByContentAndAuthor(UUID contentId, UUID authorId) {
        log.debug("Searching for all the revisions for the content {} of the author {} in the database.",
            contentId, authorId);

        CompletableFuture<List<Revision>> revisionsFuture = revisionService.getAllByContentIdAndContentAuthorId(
            contentId, authorId);
        CompletableFuture<Content> contentFuture = contentService.findByIdAndAuthorIdOrElseThrowAsync(contentId, authorId);

        return revisionsFuture.thenCombine(contentFuture, (revisions, content) -> {
            List<CompletableFuture<RevisionDto>> revisionDtoFutures = new ArrayList<>();

            for (Revision revision : revisions) {
                CompletableFuture<String> titleFuture =
                    deltaService.getText2FromDelta(content.getTitle(), revision.getTitleDelta());
                CompletableFuture<String> descriptionFuture =
                    deltaService.getText2FromDelta(content.getDescription(), revision.getDescriptionDelta());
                CompletableFuture<String> bodyFuture =
                    deltaService.getText2FromDelta(content.getBody(), revision.getBodyDelta());

                CompletableFuture<RevisionDto> revisionDtoFuture = CompletableFuture.allOf(
                    titleFuture, descriptionFuture, bodyFuture
                ).thenApply(v -> {
                    try {
                        RevisionDto revisionDto = new RevisionDto();
                        revisionDto.setContentId(revision.getContent().getId());
                        revisionDto.setRevisionNumber(revision.getRevisionNumber());
                        revisionDto.setDescription(revision.getDescription());
                        revisionDto.setContentTitle(titleFuture.get());
                        revisionDto.setContentDescription(descriptionFuture.get());
                        revisionDto.setContentBody(bodyFuture.get());
                        revisionDto.setCreatedAt(revision.getCreatedAt());
                        return revisionDto;
                    } catch (Exception e) {
                        throw new RuntimeException("Error processing revision", e);
                    }
                });
                revisionDtoFutures.add(revisionDtoFuture);
            }

            return revisionDtoFutures.stream()
                .map(CompletableFuture::join)
                .toList();
        });
    }

    @Override
    public void createRevision(RevisionDataDto revisionDataDto, UUID authorId) {
        UUID contentId = revisionDataDto.getContentId();
        Content content = contentService.findByIdAndAuthorIdOrElseThrow(contentId, authorId);
        revisionService.create(content, revisionDataDto);
    }

    @Override
    public CompletableFuture<Void> updateStatus(ContentStatusDto contentStatusDto) {
        UUID contentId = contentStatusDto.getId();
        ContentStatus status = ContentStatus.valueOf(contentStatusDto.getStatus().toUpperCase());

        if (status.equals(ContentStatus.SUBMITTED)) {
            log.debug("Searching for the content {} in the database.", contentId);

            return CompletableFuture.supplyAsync(() ->
                    contentRepository.findById(contentId).orElseThrow(() -> {
                        log.error("The content {} is not found in the database.", contentId);
                        return new RuntimeException("The content %s is not found in the database.".formatted(contentId));
                    })
                )
                .thenCompose(content -> applyLastRevisionToContent(content)
                    .thenCompose(v -> {
                        content.setStatus(ContentStatus.SUBMITTED);
                        return CompletableFuture.runAsync(() -> contentRepository.save(content));
                    })
                );
        } else {
            log.debug("Changing the status of the content {} to {} in the database.", contentId, status);
            return contentRepository.updateStatus(contentId, status);
        }
    }

    private CompletableFuture<Void> applyLastRevisionToContent(Content content) {
        UUID contentId = content.getId();

        return revisionService.findLastByContentAndAuthor(contentId, content.getAuthorId())
            .thenCompose(lastRevision -> {
                log.debug("Applying last revision {} for the content {}.", lastRevision.getId(), contentId);

                CompletableFuture<String> titleFuture =
                    deltaService.getText2FromDelta(content.getTitle(), lastRevision.getTitleDelta());
                CompletableFuture<String> descriptionFuture =
                    deltaService.getText2FromDelta(content.getDescription(), lastRevision.getDescriptionDelta());
                CompletableFuture<String> bodyFuture =
                    deltaService.getText2FromDelta(content.getBody(), lastRevision.getBodyDelta());

                return CompletableFuture.allOf(titleFuture, descriptionFuture, bodyFuture)
                    .thenRun(() -> {
                        try {
                            content.setTitle(titleFuture.get());
                            content.setDescription(descriptionFuture.get());
                            content.setBody(bodyFuture.get());
                        } catch (Exception e) {
                            throw new RuntimeException("Error applying deltas to content", e);
                        }
                    });
            })
            .thenCompose(v -> revisionService.deleteById(contentId));
    }
}
