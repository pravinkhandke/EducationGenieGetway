package com.educationgenie.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Grade.
 */
@Entity
@Table(name = "grade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "grade")
public class Grade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grade")
    private Integer grade;

    @OneToMany(mappedBy = "grade")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Subject> subjects = new HashSet<>();

    @ManyToMany(mappedBy = "grades")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Content> contents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGrade() {
        return grade;
    }

    public Grade grade(Integer grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public Grade subjects(Set<Subject> subjects) {
        this.subjects = subjects;
        return this;
    }

    public Grade addSubject(Subject subject) {
        this.subjects.add(subject);
        subject.setGrade(this);
        return this;
    }

    public Grade removeSubject(Subject subject) {
        this.subjects.remove(subject);
        subject.setGrade(null);
        return this;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public Set<Content> getContents() {
        return contents;
    }

    public Grade contents(Set<Content> contents) {
        this.contents = contents;
        return this;
    }

    public Grade addContent(Content content) {
        this.contents.add(content);
        content.getGrades().add(this);
        return this;
    }

    public Grade removeContent(Content content) {
        this.contents.remove(content);
        content.getGrades().remove(this);
        return this;
    }

    public void setContents(Set<Content> contents) {
        this.contents = contents;
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
        Grade grade = (Grade) o;
        if (grade.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), grade.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Grade{" +
            "id=" + getId() +
            ", grade=" + getGrade() +
            "}";
    }
}
