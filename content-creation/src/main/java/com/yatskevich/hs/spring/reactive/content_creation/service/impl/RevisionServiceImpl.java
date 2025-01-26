package com.yatskevich.hs.spring.reactive.content_creation.service.impl;

import com.yatskevich.hs.spring.reactive.content_creation.dto.RevisionDataDto;
import com.yatskevich.hs.spring.reactive.content_creation.entity.Content;
import com.yatskevich.hs.spring.reactive.content_creation.entity.Revision;
import com.yatskevich.hs.spring.reactive.content_creation.repository.RevisionRepository;
import com.yatskevich.hs.spring.reactive.content_creation.service.DeltaService;
import com.yatskevich.hs.spring.reactive.content_creation.service.RevisionService;
import java.util.Comparator;
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
public class RevisionServiceImpl implements RevisionService {

    private final RevisionRepository revisionRepository;
    private final DeltaService deltaService;

    @Override
    public CompletableFuture<List<Revision>> getAllByContentIdAndContentAuthorId(UUID contentId, UUID authorId) {
        return CompletableFuture.supplyAsync(() -> {
                log.debug("Searching for all the revisions for the content {} of the author {} in the database.", contentId, authorId);
                return null;
            })
            .thenCompose(v -> revisionRepository.findAllByContentIdAndContentAuthorId(contentId, authorId));

    }

    @Override
    public CompletableFuture<Void> deleteById(UUID contentId) {
        return CompletableFuture.runAsync(() -> {
            log.debug("Removing all the revisions for the content {} in the database.", contentId);
            revisionRepository.deleteAllByContentId(contentId);
        });
    }

    @Override
    public CompletableFuture<Revision> findLastByContentAndAuthor(UUID contentId, UUID authorId) {
        return CompletableFuture.supplyAsync(() -> {
                log.debug("Searching for the last revision for the content {} in the database.", contentId);
                return null;
            })
            .thenCompose(v -> revisionRepository.findLastByContentIdAndContentAuthorId(contentId, authorId))
            .thenApply(revision -> revision.orElseThrow(() -> {
                    log.error("There are no revisions of the content {} in the database.", contentId);
                    return new RuntimeException("There are no revisions of the content %s in the database.".formatted(contentId));
                })
            );
    }

    @Override
    public CompletableFuture<Void> create(Content content, RevisionDataDto revisionDataDto) {
        return revisionRepository.findAllByContentIdAndContentAuthorId(content.getId(), content.getAuthorId())
            .thenCompose(revisions -> {
                Integer revisionNumber = revisions.stream()
                    .max(Comparator.comparingInt(Revision::getRevisionNumber))
                    .map(lastRevision -> lastRevision.getRevisionNumber() + 1)
                    .orElse(1);

                CompletableFuture<String> titleDeltaFuture =
                    deltaService.getDelta(content.getTitle(), revisionDataDto.getContentTitle());
                CompletableFuture<String> descriptionDeltaFuture =
                    deltaService.getDelta(content.getDescription(), revisionDataDto.getContentDescription());
                CompletableFuture<String> bodyDeltaFuture =
                    deltaService.getDelta(content.getBody(), revisionDataDto.getContentBody());

                return CompletableFuture.allOf(titleDeltaFuture, descriptionDeltaFuture, bodyDeltaFuture)
                    .thenApply(v -> {
                        Revision revision = new Revision();
                        revision.setContent(content);
                        revision.setRevisionNumber(revisionNumber);
                        revision.setDescription(revisionDataDto.getDescription());
                        try {
                            revision.setTitleDelta(titleDeltaFuture.get());
                            revision.setDescriptionDelta(descriptionDeltaFuture.get());
                            revision.setBodyDelta(bodyDeltaFuture.get());
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to compute deltas", e);
                        }
                        return revision;
                    });
            })
            .thenCompose(revision -> CompletableFuture.runAsync(() -> revisionRepository.save(revision)));
    }
}
