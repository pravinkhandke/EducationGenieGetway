package com.educationgenie.service.mapper;

import com.educationgenie.domain.*;
import com.educationgenie.service.dto.ScoreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Score and its DTO ScoreDTO.
 */
@Mapper(componentModel = "spring", uses = {UserInfoMapper.class})
public interface ScoreMapper extends EntityMapper<ScoreDTO, Score> {

    @Mapping(source = "userInfo.id", target = "userInfoId")
    ScoreDTO toDto(Score score);

    @Mapping(source = "userInfoId", target = "userInfo")
    Score toEntity(ScoreDTO scoreDTO);

    default Score fromId(Long id) {
        if (id == null) {
            return null;
        }
        Score score = new Score();
        score.setId(id);
        return score;
    }
}
