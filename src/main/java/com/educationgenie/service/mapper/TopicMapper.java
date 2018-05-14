package com.educationgenie.service.mapper;

import com.educationgenie.domain.*;
import com.educationgenie.service.dto.TopicDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Topic and its DTO TopicDTO.
 */
@Mapper(componentModel = "spring", uses = {ChapterMapper.class})
public interface TopicMapper extends EntityMapper<TopicDTO, Topic> {

    @Mapping(source = "chapter.id", target = "chapterId")
    TopicDTO toDto(Topic topic);

    @Mapping(source = "chapterId", target = "chapter")
    Topic toEntity(TopicDTO topicDTO);

    default Topic fromId(Long id) {
        if (id == null) {
            return null;
        }
        Topic topic = new Topic();
        topic.setId(id);
        return topic;
    }
}
