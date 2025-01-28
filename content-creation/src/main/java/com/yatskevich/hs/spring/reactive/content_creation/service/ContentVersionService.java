package com.yatskevich.hs.spring.reactive.content_creation.service;

import com.yatskevich.hs.spring.reactive.content_creation.dto.ContentStatusDto;
import com.yatskevich.hs.spring.reactive.content_creation.dto.RevisionDataDto;
import com.yatskevich.hs.spring.reactive.content_creation.dto.RevisionDto;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ContentVersionService {

    CompletableFuture<List<RevisionDto>> getAllByContentAndAuthor(UUID contentId, UUID authorId);

    void createRevision(RevisionDataDto revisionDataDto, UUID authorId);

    CompletableFuture<Void> updateStatus(ContentStatusDto contentStatusDto);
}
