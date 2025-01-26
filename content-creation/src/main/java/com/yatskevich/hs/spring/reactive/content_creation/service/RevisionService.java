package com.yatskevich.hs.spring.reactive.content_creation.service;

import com.yatskevich.hs.spring.reactive.content_creation.dto.RevisionDataDto;
import com.yatskevich.hs.spring.reactive.content_creation.entity.Content;
import com.yatskevich.hs.spring.reactive.content_creation.entity.Revision;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface RevisionService {

    CompletableFuture<List<Revision>> getAllByContentIdAndContentAuthorId(UUID contentId, UUID authorId);

    CompletableFuture<Void> deleteById(UUID contentId);

    CompletableFuture<Revision> findLastByContentAndAuthor(UUID contentId, UUID authorId);

    CompletableFuture<Void> create(Content content, RevisionDataDto revisionDataDto);
}
