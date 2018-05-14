package com.educationgenie.service.mapper;

import com.educationgenie.domain.*;
import com.educationgenie.service.dto.QuestionBankDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity QuestionBank and its DTO QuestionBankDTO.
 */
@Mapper(componentModel = "spring", uses = {UserInfoMapper.class, ScoreMapper.class, SubjectMapper.class, GradeMapper.class, ChapterMapper.class, TopicMapper.class})
public interface QuestionBankMapper extends EntityMapper<QuestionBankDTO, QuestionBank> {

    @Mapping(source = "userInfo.id", target = "userInfoId")
    @Mapping(source = "score.id", target = "scoreId")
    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "grade.id", target = "gradeId")
    @Mapping(source = "chapter.id", target = "chapterId")
    @Mapping(source = "topic.id", target = "topicId")
    QuestionBankDTO toDto(QuestionBank questionBank);

    @Mapping(source = "userInfoId", target = "userInfo")
    @Mapping(source = "scoreId", target = "score")
    @Mapping(source = "subjectId", target = "subject")
    @Mapping(source = "gradeId", target = "grade")
    @Mapping(source = "chapterId", target = "chapter")
    @Mapping(source = "topicId", target = "topic")
    @Mapping(target = "questions", ignore = true)
    QuestionBank toEntity(QuestionBankDTO questionBankDTO);

    default QuestionBank fromId(Long id) {
        if (id == null) {
            return null;
        }
        QuestionBank questionBank = new QuestionBank();
        questionBank.setId(id);
        return questionBank;
    }
}
