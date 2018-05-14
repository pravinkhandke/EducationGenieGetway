package com.educationgenie.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Score entity.
 */
public class ScoreDTO implements Serializable {

    private Long id;

    private Integer score;

    private Long userInfoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScoreDTO scoreDTO = (ScoreDTO) o;
        if (scoreDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), scoreDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ScoreDTO{" +
            "id=" + getId() +
            ", score=" + getScore() +
            ", userInfo=" + getUserInfoId() +
            "}";
    }
}
