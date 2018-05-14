package com.educationgenie.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Subject entity.
 */
public class SubjectDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 30)
    private String name;

    private Long gradeId;

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

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SubjectDTO subjectDTO = (SubjectDTO) o;
        if (subjectDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subjectDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubjectDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", grade=" + getGradeId() +
            "}";
    }
}
