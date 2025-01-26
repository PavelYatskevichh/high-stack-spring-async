package com.yatskevich.hs.spring.reactive.content_creation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RevisionDataDto {
    @NotNull(message = "Content ID must not be null.")
    private UUID contentId;
    @NotBlank(message = "Revision description must not be blank on null.")
    private String description;
    @NotBlank(message = "Content title must not be blank on null.")
    private String contentTitle;
    @NotBlank(message = "Content description must not be blank on null.")
    private String contentDescription;
    @NotBlank(message = "Content body must not be blank on null.")
    private String contentBody;
}
