package com.educationgenie.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.educationgenie.domain.enumeration.State;

/**
 * A QuestionBank.
 */
@Entity
@Table(name = "question_bank")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "questionbank")
public class QuestionBank implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "title", length = 30, nullable = false)
    private String title;

    @Column(name = "duration")
    private Integer duration;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

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
    @JsonIgnoreProperties("questionBanks")
    private UserInfo userInfo;

    @OneToOne
    @JoinColumn(unique = true)
    private Score score;

    @OneToOne
    @JoinColumn(unique = true)
    private Subject subject;

    @OneToOne
    @JoinColumn(unique = true)
    private Grade grade;

    @OneToOne
    @JoinColumn(unique = true)
    private Chapter chapter;

    @OneToOne
    @JoinColumn(unique = true)
    private Topic topic;

    @OneToMany(mappedBy = "questionBank")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Questions> questions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public QuestionBank title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDuration() {
        return duration;
    }

    public QuestionBank duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public State getState() {
        return state;
    }

    public QuestionBank state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getCreateBy() {
        return createBy;
    }

    public QuestionBank createBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public QuestionBank createdTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public QuestionBank updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedTime() {
        return updatedTime;
    }

    public QuestionBank updatedTime(ZonedDateTime updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    public void setUpdatedTime(ZonedDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public QuestionBank userInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
        return this;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Score getScore() {
        return score;
    }

    public QuestionBank score(Score score) {
        this.score = score;
        return this;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public Subject getSubject() {
        return subject;
    }

    public QuestionBank subject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Grade getGrade() {
        return grade;
    }

    public QuestionBank grade(Grade grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public QuestionBank chapter(Chapter chapter) {
        this.chapter = chapter;
        return this;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public Topic getTopic() {
        return topic;
    }

    public QuestionBank topic(Topic topic) {
        this.topic = topic;
        return this;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Set<Questions> getQuestions() {
        return questions;
    }

    public QuestionBank questions(Set<Questions> questions) {
        this.questions = questions;
        return this;
    }

    public QuestionBank addQuestions(Questions questions) {
        this.questions.add(questions);
        questions.setQuestionBank(this);
        return this;
    }

    public QuestionBank removeQuestions(Questions questions) {
        this.questions.remove(questions);
        questions.setQuestionBank(null);
        return this;
    }

    public void setQuestions(Set<Questions> questions) {
        this.questions = questions;
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
        QuestionBank questionBank = (QuestionBank) o;
        if (questionBank.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionBank.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionBank{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", duration=" + getDuration() +
            ", state='" + getState() + "'" +
            ", createBy='" + getCreateBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
