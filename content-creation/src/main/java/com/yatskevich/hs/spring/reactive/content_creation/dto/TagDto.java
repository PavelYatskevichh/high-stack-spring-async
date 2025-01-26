package com.yatskevich.hs.spring.reactive.content_creation.dto;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDto {
    private UUID id;
    private String name;
}
