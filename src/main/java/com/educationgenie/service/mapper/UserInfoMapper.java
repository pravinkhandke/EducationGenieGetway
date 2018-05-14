package com.educationgenie.service.mapper;

import com.educationgenie.domain.*;
import com.educationgenie.service.dto.UserInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserInfo and its DTO UserInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {UserReviewMapper.class, GradeMapper.class})
public interface UserInfoMapper extends EntityMapper<UserInfoDTO, UserInfo> {

    @Mapping(source = "userReview.id", target = "userReviewId")
    @Mapping(source = "grade.id", target = "gradeId")
    UserInfoDTO toDto(UserInfo userInfo);

    @Mapping(source = "userReviewId", target = "userReview")
    @Mapping(source = "gradeId", target = "grade")
    @Mapping(target = "contents", ignore = true)
    @Mapping(target = "scores", ignore = true)
    @Mapping(target = "questionBanks", ignore = true)
    UserInfo toEntity(UserInfoDTO userInfoDTO);

    default UserInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        return userInfo;
    }
}
