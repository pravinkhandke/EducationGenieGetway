package com.educationgenie.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.educationgenie.domain.enumeration.State;

/**
 * A DTO for the QuestionBank entity.
 */
public class QuestionBankDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 30)
    private String title;

    private Integer duration;

    @NotNull
    private State state;

    @NotNull
    @Size(max = 30)
    private String createBy;

    private ZonedDateTime createdTime;

    @Size(max = 30)
    private String updatedBy;

    private ZonedDateTime updatedTime;

    private Long userInfoId;

    private Long scoreId;

    private Long subjectId;

    private Long gradeId;

    private Long chapterId;

    private Long topicId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
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

    public Long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

    public Long getScoreId() {
        return scoreId;
    }

    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestionBankDTO questionBankDTO = (QuestionBankDTO) o;
        if (questionBankDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionBankDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionBankDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", duration=" + getDuration() +
            ", state='" + getState() + "'" +
            ", createBy='" + getCreateBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", userInfo=" + getUserInfoId() +
            ", score=" + getScoreId() +
            ", subject=" + getSubjectId() +
            ", grade=" + getGradeId() +
            ", chapter=" + getChapterId() +
            ", topic=" + getTopicId() +
            "}";
    }
}
