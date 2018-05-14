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

import com.educationgenie.domain.enumeration.Language;

import com.educationgenie.domain.enumeration.State;

/**
 * A Content.
 */
@Entity
@Table(name = "content")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "content")
public class Content implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "content_type", length = 30, nullable = false)
    private String contentType;

    @NotNull
    @Size(max = 255)
    @Column(name = "url", length = 255, nullable = false)
    private String url;

    @NotNull
    @Size(max = 30)
    @Column(name = "media_type", length = 30, nullable = false)
    private String mediaType;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

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

    @OneToOne
    @JoinColumn(unique = true)
    private Subject subject;

    @OneToOne
    @JoinColumn(unique = true)
    private Chapter chapter;

    @OneToOne
    @JoinColumn(unique = true)
    private Topic topic;

    @OneToMany(mappedBy = "content")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContentList> contentLists = new HashSet<>();

    @OneToMany(mappedBy = "content")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContentReview> contentreviews = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "content_grade",
               joinColumns = @JoinColumn(name = "contents_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "grades_id", referencedColumnName = "id"))
    private Set<Grade> grades = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("contents")
    private UserInfo userInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentType() {
        return contentType;
    }

    public Content contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public Content url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMediaType() {
        return mediaType;
    }

    public Content mediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Language getLanguage() {
        return language;
    }

    public Content language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public State getState() {
        return state;
    }

    public Content state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getCreateBy() {
        return createBy;
    }

    public Content createBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public Content createdTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Content updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedTime() {
        return updatedTime;
    }

    public Content updatedTime(ZonedDateTime updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    public void setUpdatedTime(ZonedDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Subject getSubject() {
        return subject;
    }

    public Content subject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public Content chapter(Chapter chapter) {
        this.chapter = chapter;
        return this;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public Topic getTopic() {
        return topic;
    }

    public Content topic(Topic topic) {
        this.topic = topic;
        return this;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Set<ContentList> getContentLists() {
        return contentLists;
    }

    public Content contentLists(Set<ContentList> contentLists) {
        this.contentLists = contentLists;
        return this;
    }

    public Content addContentList(ContentList contentList) {
        this.contentLists.add(contentList);
        contentList.setContent(this);
        return this;
    }

    public Content removeContentList(ContentList contentList) {
        this.contentLists.remove(contentList);
        contentList.setContent(null);
        return this;
    }

    public void setContentLists(Set<ContentList> contentLists) {
        this.contentLists = contentLists;
    }

    public Set<ContentReview> getContentreviews() {
        return contentreviews;
    }

    public Content contentreviews(Set<ContentReview> contentReviews) {
        this.contentreviews = contentReviews;
        return this;
    }

    public Content addContentreview(ContentReview contentReview) {
        this.contentreviews.add(contentReview);
        contentReview.setContent(this);
        return this;
    }

    public Content removeContentreview(ContentReview contentReview) {
        this.contentreviews.remove(contentReview);
        contentReview.setContent(null);
        return this;
    }

    public void setContentreviews(Set<ContentReview> contentReviews) {
        this.contentreviews = contentReviews;
    }

    public Set<Grade> getGrades() {
        return grades;
    }

    public Content grades(Set<Grade> grades) {
        this.grades = grades;
        return this;
    }

    public Content addGrade(Grade grade) {
        this.grades.add(grade);
        grade.getContents().add(this);
        return this;
    }

    public Content removeGrade(Grade grade) {
        this.grades.remove(grade);
        grade.getContents().remove(this);
        return this;
    }

    public void setGrades(Set<Grade> grades) {
        this.grades = grades;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public Content userInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
        return this;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
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
        Content content = (Content) o;
        if (content.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), content.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Content{" +
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
            "}";
    }
}
