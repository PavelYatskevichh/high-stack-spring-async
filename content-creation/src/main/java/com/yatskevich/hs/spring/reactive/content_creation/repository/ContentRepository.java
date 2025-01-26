package com.yatskevich.hs.spring.reactive.content_creation.repository;

import com.yatskevich.hs.spring.reactive.content_creation.entity.Content;
import com.yatskevich.hs.spring.reactive.content_creation.entity.ContentStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContentRepository extends JpaRepository<Content, UUID> {

    CompletableFuture<List<Content>> findAllByAuthorId(@Param("authorId") UUID authorId);

    CompletableFuture<Optional<Content>> findByIdAndAuthorId(@Param("id") UUID contentId,
                                                             @Param("userId") UUID authorId);

    @Query(value = """
        UPDATE Content
        SET status = :status
        WHERE id = :id
        """)
    @Modifying
    CompletableFuture<Void> updateStatus(@Param("id") UUID id,
                                         @Param("status") ContentStatus status);
}
