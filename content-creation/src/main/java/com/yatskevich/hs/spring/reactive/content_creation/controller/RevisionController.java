package com.yatskevich.hs.spring.reactive.content_creation.controller;

import com.yatskevich.hs.spring.reactive.content_creation.dto.RevisionDataDto;
import com.yatskevich.hs.spring.reactive.content_creation.dto.RevisionDto;
import com.yatskevich.hs.spring.reactive.content_creation.service.ContentVersionService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/revisions")
public class RevisionController {

    private final ContentVersionService contentVersionService;

    @GetMapping
    public CompletableFuture<List<RevisionDto>> getAllForContent(@RequestParam UUID contentId,
                                                                 @RequestParam UUID authorId) {
        log.debug("Getting all the revisions of the content {} of the author {}.", contentId, authorId);
        return contentVersionService.getAllByContentAndAuthor(contentId, authorId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<Void> create(@RequestParam UUID authorId,
                                          @RequestBody @Valid RevisionDataDto revisionDataDto) {
        log.debug("Creating new revision for the content {} by author {}.", revisionDataDto.getContentId(), authorId);
        return contentVersionService.createRevision(revisionDataDto, authorId);
    }
}
