package com.educationgenie.service.mapper;

import com.educationgenie.domain.*;
import com.educationgenie.service.dto.UserReviewDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserReview and its DTO UserReviewDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserReviewMapper extends EntityMapper<UserReviewDTO, UserReview> {



    default UserReview fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserReview userReview = new UserReview();
        userReview.setId(id);
        return userReview;
    }
}
