package com.educationgenie.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Questions entity.
 */
public class QuestionsDTO implements Serializable {

    private Long id;

    @Size(max = 1000)
    private String options;

    private Boolean answers;

    @NotNull
    @Size(max = 30)
    private String createBy;

    private ZonedDateTime createdTime;

    @Size(max = 30)
    private String updatedBy;

    private ZonedDateTime updatedTime;

    private Long questionBankId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Boolean isAnswers() {
        return answers;
    }

    public void setAnswers(Boolean answers) {
        this.answers = answers;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(ZonedDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Long getQuestionBankId() {
        return questionBankId;
    }

    public void setQuestionBankId(Long questionBankId) {
        this.questionBankId = questionBankId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestionsDTO questionsDTO = (QuestionsDTO) o;
        if (questionsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionsDTO{" +
            "id=" + getId() +
            ", options='" + getOptions() + "'" +
            ", answers='" + isAnswers() + "'" +
            ", createBy='" + getCreateBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", questionBank=" + getQuestionBankId() +
            "}";
    }
}
