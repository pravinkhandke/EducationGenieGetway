package com.educationgenie.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.educationgenie.domain.enumeration.Language;
import com.educationgenie.domain.enumeration.State;

/**
 * A DTO for the Content entity.
 */
public class ContentDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 30)
    private String contentType;

    @NotNull
    @Size(max = 255)
    private String url;

    @NotNull
    @Size(max = 30)
    private String mediaType;

    private Language language;

    @NotNull
    private State state;

    @NotNull
    @Size(max = 30)
    private String createBy;

    private ZonedDateTime createdTime;

    @Size(max = 30)
    private String updatedBy;

    private ZonedDateTime updatedTime;

    private Long subjectId;

    private Long chapterId;

    private Long topicId;

    private Set<GradeDTO> grades = new HashSet<>();

    private Long userInfoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
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

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
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

    public Set<GradeDTO> getGrades() {
        return grades;
    }

    public void setGrades(Set<GradeDTO> grades) {
        this.grades = grades;
    }

    public Long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContentDTO contentDTO = (ContentDTO) o;
        if (contentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContentDTO{" +
            "id=" + getId() +
            ", contentType='" + getContentType() + "'" +
            ", url='" + getUrl() + "'" +
            ", mediaType='" + getMediaType() + "'" +
            ", language='" + getLanguage() + "'" +
            ", state='" + getState() + "'" +
            ", createBy='" + getCreateBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", subject=" + getSubjectId() +
            ", chapter=" + getChapterId() +
            ", topic=" + getTopicId() +
            ", userInfo=" + getUserInfoId() +
            "}";
    }
}
