package com.educationgenie.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Grade entity.
 */
public class GradeDTO implements Serializable {

    private Long id;

    private Integer grade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GradeDTO gradeDTO = (GradeDTO) o;
        if (gradeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gradeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GradeDTO{" +
            "id=" + getId() +
            ", grade=" + getGrade() +
            "}";
    }
}
