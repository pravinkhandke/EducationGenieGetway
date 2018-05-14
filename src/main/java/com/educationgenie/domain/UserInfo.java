package com.educationgenie.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserInfo.
 */
@Entity
@Table(name = "user_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "userinfo")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "user_type", length = 30, nullable = false)
    private String userType;

    @OneToOne
    @JoinColumn(unique = true)
    private UserReview userReview;

    @OneToOne
    @JoinColumn(unique = true)
    private Grade grade;

    @OneToMany(mappedBy = "userInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Content> contents = new HashSet<>();

    @OneToMany(mappedBy = "userInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Score> scores = new HashSet<>();

    @OneToMany(mappedBy = "userInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<QuestionBank> questionBanks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public UserInfo userType(String userType) {
        this.userType = userType;
        return this;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public UserReview getUserReview() {
        return userReview;
    }

    public UserInfo userReview(UserReview userReview) {
        this.userReview = userReview;
        return this;
    }

    public void setUserReview(UserReview userReview) {
        this.userReview = userReview;
    }

    public Grade getGrade() {
        return grade;
    }

    public UserInfo grade(Grade grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Set<Content> getContents() {
        return contents;
    }

    public UserInfo contents(Set<Content> contents) {
        this.contents = contents;
        return this;
    }

    public UserInfo addContent(Content content) {
        this.contents.add(content);
        content.setUserInfo(this);
        return this;
    }

    public UserInfo removeContent(Content content) {
        this.contents.remove(content);
        content.setUserInfo(null);
        return this;
    }

    public void setContents(Set<Content> contents) {
        this.contents = contents;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public UserInfo scores(Set<Score> scores) {
        this.scores = scores;
        return this;
    }

    public UserInfo addScore(Score score) {
        this.scores.add(score);
        score.setUserInfo(this);
        return this;
    }

    public UserInfo removeScore(Score score) {
        this.scores.remove(score);
        score.setUserInfo(null);
        return this;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    public Set<QuestionBank> getQuestionBanks() {
        return questionBanks;
    }

    public UserInfo questionBanks(Set<QuestionBank> questionBanks) {
        this.questionBanks = questionBanks;
        return this;
    }

    public UserInfo addQuestionBank(QuestionBank questionBank) {
        this.questionBanks.add(questionBank);
        questionBank.setUserInfo(this);
        return this;
    }

    public UserInfo removeQuestionBank(QuestionBank questionBank) {
        this.questionBanks.remove(questionBank);
        questionBank.setUserInfo(null);
        return this;
    }

    public void setQuestionBanks(Set<QuestionBank> questionBanks) {
        this.questionBanks = questionBanks;
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
        UserInfo userInfo = (UserInfo) o;
        if (userInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserInfo{" +
            "id=" + getId() +
            ", userType='" + getUserType() + "'" +
            "}";
    }
}
