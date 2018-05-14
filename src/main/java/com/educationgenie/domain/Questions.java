package com.educationgenie.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Questions.
 */
@Entity
@Table(name = "questions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "questions")
public class Questions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 1000)
    @Column(name = "options", length = 1000)
    private String options;

    @Column(name = "answers")
    private Boolean answers;

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

    @ManyToOne
    @JsonIgnoreProperties("questions")
    private QuestionBank questionBank;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptions() {
        return options;
    }

    public Questions options(String options) {
        this.options = options;
        return this;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Boolean isAnswers() {
        return answers;
    }

    public Questions answers(Boolean answers) {
        this.answers = answers;
        return this;
    }

    public void setAnswers(Boolean answers) {
        this.answers = answers;
    }

    public String getCreateBy() {
        return createBy;
    }

    public Questions createBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public Questions createdTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Questions updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedTime() {
        return updatedTime;
    }

    public Questions updatedTime(ZonedDateTime updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    public void setUpdatedTime(ZonedDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public QuestionBank getQuestionBank() {
        return questionBank;
    }

    public Questions questionBank(QuestionBank questionBank) {
        this.questionBank = questionBank;
        return this;
    }

    public void setQuestionBank(QuestionBank questionBank) {
        this.questionBank = questionBank;
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
        Questions questions = (Questions) o;
        if (questions.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questions.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Questions{" +
            "id=" + getId() +
            ", options='" + getOptions() + "'" +
            ", answers='" + isAnswers() + "'" +
            ", createBy='" + getCreateBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
