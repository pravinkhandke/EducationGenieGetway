package com.educationgenie.service.mapper;

import com.educationgenie.domain.*;
import com.educationgenie.service.dto.ContentReviewDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ContentReview and its DTO ContentReviewDTO.
 */
@Mapper(componentModel = "spring", uses = {ContentMapper.class})
public interface ContentReviewMapper extends EntityMapper<ContentReviewDTO, ContentReview> {

    @Mapping(source = "content.id", target = "contentId")
    ContentReviewDTO toDto(ContentReview contentReview);

    @Mapping(source = "contentId", target = "content")
    ContentReview toEntity(ContentReviewDTO contentReviewDTO);

    default ContentReview fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContentReview contentReview = new ContentReview();
        contentReview.setId(id);
        return contentReview;
    }
}
