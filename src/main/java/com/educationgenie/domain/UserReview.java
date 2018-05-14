package com.educationgenie.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A UserReview.
 */
@Entity
@Table(name = "user_review")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "userreview")
public class UserReview implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "raiting")
    private Integer raiting;

    @Size(max = 1000)
    @Column(name = "review", length = 1000)
    private String review;

    @NotNull
    @Size(max = 30)
    @Column(name = "create_by", length = 30, nullable = false)
    private String createBy;

    @Column(name = "created_time")
    private ZonedDateTime createdTime;

    @Size(max = 30)
    @Column(name = "updated_by", length = 30)
    private String updatedBy;

    @Column(name = "updated_time")
    private ZonedDateTime updatedTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRaiting() {
        return raiting;
    }

    public UserReview raiting(Integer raiting) {
        this.raiting = raiting;
        return this;
    }

    public void setRaiting(Integer raiting) {
        this.raiting = raiting;
    }

    public String getReview() {
        return review;
    }

    public UserReview review(String review) {
        this.review = review;
        return this;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getCreateBy() {
        return createBy;
    }

    public UserReview createBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public UserReview createdTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public UserReview updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedTime() {
        return updatedTime;
    }

    public UserReview updatedTime(ZonedDateTime updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    public void setUpdatedTime(ZonedDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserReview userReview = (UserReview) o;
        if (userReview.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userReview.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserReview{" +
            "id=" + getId() +
            ", raiting=" + getRaiting() +
            ", review='" + getReview() + "'" +
            ", createBy='" + getCreateBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
