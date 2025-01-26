package com.yatskevich.hs.spring.reactive.content_creation.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "content_creation", name = "revisions")
public class Revision {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "content_item_id", nullable = false)
    private Content content;

    @Column(name = "revision_number", nullable = false)
    private Integer revisionNumber;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "title_delta", nullable = false)
    private String titleDelta;

    @Column(name = "description_delta", nullable = false)
    private String descriptionDelta;

    @Column(name = "body_delta", nullable = false)
    private String bodyDelta;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
