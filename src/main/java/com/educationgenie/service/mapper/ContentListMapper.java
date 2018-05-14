package com.educationgenie.service.mapper;

import com.educationgenie.domain.*;
import com.educationgenie.service.dto.ContentListDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ContentList and its DTO ContentListDTO.
 */
@Mapper(componentModel = "spring", uses = {ContentMapper.class})
public interface ContentListMapper extends EntityMapper<ContentListDTO, ContentList> {

    @Mapping(source = "content.id", target = "contentId")
    ContentListDTO toDto(ContentList contentList);

    @Mapping(source = "contentId", target = "content")
    ContentList toEntity(ContentListDTO contentListDTO);

    default ContentList fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContentList contentList = new ContentList();
        contentList.setId(id);
        return contentList;
    }
}
