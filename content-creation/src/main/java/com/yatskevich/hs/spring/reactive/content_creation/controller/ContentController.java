package com.yatskevich.hs.spring.reactive.content_creation.controller;

import com.yatskevich.hs.spring.reactive.content_creation.dto.ContentDataDto;
import com.yatskevich.hs.spring.reactive.content_creation.dto.ContentDto;
import com.yatskevich.hs.spring.reactive.content_creation.dto.ContentStatusDto;
import com.yatskevich.hs.spring.reactive.content_creation.dto.ContentTagsDto;
import com.yatskevich.hs.spring.reactive.content_creation.service.ContentService;
import com.yatskevich.hs.spring.reactive.content_creation.service.ContentVersionService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/contents")
public class ContentController { //TODO try to implement ContentCreationFeign

    private final ContentService contentService;
    private final ContentVersionService contentVersionService;

    @GetMapping
    public CompletableFuture<List<ContentDto>> getAll() {
        log.debug("Getting all the contents.");
        return contentService.getAll();
    }

    @GetMapping("/{id}")
    public CompletableFuture<ContentDto> getById(@PathVariable("id") UUID contentId) {
        log.debug("Getting the content {}.", contentId);
        return contentService.getById(contentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<Void> create(@RequestParam UUID authorId,
                                          @RequestBody @Valid ContentDataDto contentDataDto) {
        log.debug("Creating new content {} by author {}.", contentDataDto.getTitle(), authorId);
        return contentService.create(contentDataDto, authorId);
    }

    @PatchMapping("/tags")
    public CompletableFuture<Void> addTags(@RequestParam UUID authorId,
                                           @RequestBody @Valid ContentTagsDto contentTagsDto) {
        log.debug("Adding tags {} to the content {} by author {}.",
            contentTagsDto.getTagIds(), contentTagsDto.getId(), authorId);
        return contentService.addTags(contentTagsDto, authorId);
    }

    @DeleteMapping("/tags")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CompletableFuture<Void> deleteTags(@RequestParam UUID authorId,
                                              @RequestBody @Valid ContentTagsDto contentTagsDto) {
        log.debug("Deleting tags {} from the content {} by author {}.",
            contentTagsDto.getTagIds(), contentTagsDto.getId(), authorId);
        return contentService.deleteTags(contentTagsDto, authorId);
    }

    @PutMapping("/status")
    public CompletableFuture<Void> updateStatus(@RequestBody @Valid ContentStatusDto contentStatusDto) {
        log.debug("Change status to {} of the content {}.",
            contentStatusDto.getStatus(), contentStatusDto.getId());
        return contentVersionService.updateStatus(contentStatusDto);
    }
}
