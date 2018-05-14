package com.educationgenie.service.mapper;

import com.educationgenie.domain.*;
import com.educationgenie.service.dto.SubjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Subject and its DTO SubjectDTO.
 */
@Mapper(componentModel = "spring", uses = {GradeMapper.class})
public interface SubjectMapper extends EntityMapper<SubjectDTO, Subject> {

    @Mapping(source = "grade.id", target = "gradeId")
    SubjectDTO toDto(Subject subject);

    @Mapping(source = "gradeId", target = "grade")
    @Mapping(target = "chapters", ignore = true)
    Subject toEntity(SubjectDTO subjectDTO);

    default Subject fromId(Long id) {
        if (id == null) {
            return null;
        }
        Subject subject = new Subject();
        subject.setId(id);
        return subject;
    }
}
