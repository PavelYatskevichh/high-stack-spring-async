package com.yatskevich.hs.spring.reactive.content_creation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentDataDto {
    @NotBlank(message = "Content title must not be blank on null.")
    private String title;
    @NotBlank(message = "Content description must not be blank on null.")
    private String description;
    @NotBlank(message = "Content body must not be blank on null.")
    private String body;
}
