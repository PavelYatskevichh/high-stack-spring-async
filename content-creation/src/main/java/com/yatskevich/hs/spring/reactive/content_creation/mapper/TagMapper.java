package com.yatskevich.hs.spring.reactive.content_creation.mapper;

import com.yatskevich.hs.spring.reactive.content_creation.dto.TagDto;
import com.yatskevich.hs.spring.reactive.content_creation.entity.Tag;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {

    List<TagDto> toDtoList(List<Tag> tags);
}
