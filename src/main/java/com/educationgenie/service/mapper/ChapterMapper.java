package com.educationgenie.service.mapper;

import com.educationgenie.domain.*;
import com.educationgenie.service.dto.ChapterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Chapter and its DTO ChapterDTO.
 */
@Mapper(componentModel = "spring", uses = {SubjectMapper.class})
public interface ChapterMapper extends EntityMapper<ChapterDTO, Chapter> {

    @Mapping(source = "subject.id", target = "subjectId")
    ChapterDTO toDto(Chapter chapter);

    @Mapping(source = "subjectId", target = "subject")
    @Mapping(target = "topics", ignore = true)
    Chapter toEntity(ChapterDTO chapterDTO);

    default Chapter fromId(Long id) {
        if (id == null) {
            return null;
        }
        Chapter chapter = new Chapter();
        chapter.setId(id);
        return chapter;
    }
}
