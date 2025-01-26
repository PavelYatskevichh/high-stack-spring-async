package com.yatskevich.hs.spring.reactive.content_creation.mapper;

import com.yatskevich.hs.spring.reactive.content_creation.dto.ContentDto;
import com.yatskevich.hs.spring.reactive.content_creation.entity.Content;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
    uses = {TagMapper.class})
public interface ContentMapper {

    ContentDto toDto(Content content);

    List<ContentDto> toDtoList(List<Content> contents);
}
