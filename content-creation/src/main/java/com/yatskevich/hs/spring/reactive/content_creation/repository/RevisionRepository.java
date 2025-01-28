package com.yatskevich.hs.spring.reactive.content_creation.repository;

import com.yatskevich.hs.spring.reactive.content_creation.entity.Revision;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RevisionRepository extends JpaRepository<Revision, UUID> {

    List<Revision> findAllByContentIdAndContentAuthorId(@Param("contentId") UUID contentId,
                                                        @Param("contentAuthorId") UUID authorId);

    @Query(value = """
        FROM Revision r
        LEFT JOIN r.content c
        WHERE c.id = :contentId
        AND c.authorId = :contentAuthorId
        ORDER BY r.revisionNumber DESC
        LIMIT 1
        """)
    CompletableFuture<Optional<Revision>> findLastByContentIdAndContentAuthorId(@Param("contentId") UUID contentId,
                                                                                @Param("contentAuthorId") UUID authorId);

    CompletableFuture<Void> deleteAllByContentId(@Param("contentId") UUID contentId);
}
