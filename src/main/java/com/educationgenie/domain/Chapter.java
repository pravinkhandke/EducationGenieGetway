package com.educationgenie.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * A Chapter.
 */
@Entity
@Table(name = "chapter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "chapter")
public class Chapter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("chapters")
    private Subject subject;

    @OneToMany(mappedBy = "chapter")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Topic> topics = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Chapter name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subject getSubject() {
        return subject;
    }

    public Chapter subject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    public Chapter topics(Set<Topic> topics) {
        this.topics = topics;
        return this;
    }

    public Chapter addTopic(Topic topic) {
        this.topics.add(topic);
        topic.setChapter(this);
        return this;
    }

    public Chapter removeTopic(Topic topic) {
        this.topics.remove(topic);
        topic.setChapter(null);
        return this;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
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
        Chapter chapter = (Chapter) o;
        if (chapter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chapter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Chapter{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
