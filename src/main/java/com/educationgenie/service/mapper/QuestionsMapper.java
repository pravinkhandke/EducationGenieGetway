package com.educationgenie.service.mapper;

import com.educationgenie.domain.*;
import com.educationgenie.service.dto.QuestionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Questions and its DTO QuestionsDTO.
 */
@Mapper(componentModel = "spring", uses = {QuestionBankMapper.class})
public interface QuestionsMapper extends EntityMapper<QuestionsDTO, Questions> {

    @Mapping(source = "questionBank.id", target = "questionBankId")
    QuestionsDTO toDto(Questions questions);

    @Mapping(source = "questionBankId", target = "questionBank")
    Questions toEntity(QuestionsDTO questionsDTO);

    default Questions fromId(Long id) {
        if (id == null) {
            return null;
        }
        Questions questions = new Questions();
        questions.setId(id);
        return questions;
    }
}
