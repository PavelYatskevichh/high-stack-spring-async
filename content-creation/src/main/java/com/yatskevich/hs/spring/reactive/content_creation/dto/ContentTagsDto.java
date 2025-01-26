package com.yatskevich.hs.spring.reactive.content_creation.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentTagsDto {
    @NotNull(message = "Content ID must not be null.")
    private UUID id;
    @NotEmpty
    private List<UUID> tagIds;
}
