package com.educationgenie.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ContentReview entity.
 */
public class ContentReviewDTO implements Serializable {

    private Long id;

    private Integer raiting;

    @Size(max = 1000)
    private String review;

    @NotNull
    @Size(max = 30)
    private String createBy;

    private ZonedDateTime createdTime;

    @Size(max = 30)
    private String updatedBy;

    private ZonedDateTime updatedTime;

    private Long contentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRaiting() {
        return raiting;
    }

    public void setRaiting(Integer raiting) {
        this.raiting = raiting;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
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

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContentReviewDTO contentReviewDTO = (ContentReviewDTO) o;
        if (contentReviewDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contentReviewDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContentReviewDTO{" +
            "id=" + getId() +
            ", raiting=" + getRaiting() +
            ", review='" + getReview() + "'" +
            ", createBy='" + getCreateBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", content=" + getContentId() +
            "}";
    }
}
