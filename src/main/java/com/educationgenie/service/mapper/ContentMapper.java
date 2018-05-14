package com.educationgenie.service.mapper;

import com.educationgenie.domain.*;
import com.educationgenie.service.dto.ContentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Content and its DTO ContentDTO.
 */
@Mapper(componentModel = "spring", uses = {SubjectMapper.class, ChapterMapper.class, TopicMapper.class, GradeMapper.class, UserInfoMapper.class})
public interface ContentMapper extends EntityMapper<ContentDTO, Content> {

    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "chapter.id", target = "chapterId")
    @Mapping(source = "topic.id", target = "topicId")
    @Mapping(source = "userInfo.id", target = "userInfoId")
    ContentDTO toDto(Content content);

    @Mapping(source = "subjectId", target = "subject")
    @Mapping(source = "chapterId", target = "chapter")
    @Mapping(source = "topicId", target = "topic")
    @Mapping(target = "contentLists", ignore = true)
    @Mapping(target = "contentreviews", ignore = true)
    @Mapping(source = "userInfoId", target = "userInfo")
    Content toEntity(ContentDTO contentDTO);

    default Content fromId(Long id) {
        if (id == null) {
            return null;
        }
        Content content = new Content();
        content.setId(id);
        return content;
    }
}
