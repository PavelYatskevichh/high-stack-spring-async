package com.yatskevich.hs.spring.reactive.content_creation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RevisionDto {
    private UUID contentId;
    private Integer revisionNumber;
    private String description;
    private String contentTitle;
    private String contentDescription;
    private String contentBody;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdAt;
}
