package com.yatskevich.hs.spring.reactive.content_creation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentDto {
    private UUID id;
    private String title;
    private String description;
    private String body;
    private UUID authorId;
    @Pattern(regexp = "(?i)(draft)|(submitted)|(approved)|(rejected)",
        message = "Provided content status does not exist.")
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime updatedAt;
    private List<TagDto> tags;
}
