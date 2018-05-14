package com.educationgenie.service.mapper;

import com.educationgenie.domain.*;
import com.educationgenie.service.dto.GradeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Grade and its DTO GradeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GradeMapper extends EntityMapper<GradeDTO, Grade> {


    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "contents", ignore = true)
    Grade toEntity(GradeDTO gradeDTO);

    default Grade fromId(Long id) {
        if (id == null) {
            return null;
        }
        Grade grade = new Grade();
        grade.setId(id);
        return grade;
    }
}
