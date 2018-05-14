package com.educationgenie.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Chapter entity.
 */
public class ChapterDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 30)
    private String name;

    private Long subjectId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChapterDTO chapterDTO = (ChapterDTO) o;
        if (chapterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chapterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChapterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", subject=" + getSubjectId() +
            "}";
    }
}
