package com.yatskevich.hs.spring.reactive.content_creation.service;

import com.yatskevich.hs.spring.reactive.content_creation.dto.ContentDataDto;
import com.yatskevich.hs.spring.reactive.content_creation.dto.ContentDto;
import com.yatskevich.hs.spring.reactive.content_creation.dto.ContentTagsDto;
import com.yatskevich.hs.spring.reactive.content_creation.entity.Content;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ContentService {

    CompletableFuture<List<ContentDto>> getAll();

    CompletableFuture<ContentDto> getById(UUID contentId);

    CompletableFuture<Content> findByIdAndAuthorIdOrElseThrowAsync(UUID contentId, UUID authorId);

    Content findByIdAndAuthorIdOrElseThrow(UUID contentId, UUID authorId);

    CompletableFuture<Void> create(ContentDataDto contentDataDto, UUID authorId);

    CompletableFuture<Void> addTags(ContentTagsDto contentTagsDto, UUID authorId);

    CompletableFuture<Void> deleteTags(ContentTagsDto contentTagsDto, UUID authorId);
}
