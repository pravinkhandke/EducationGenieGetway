package com.educationgenie.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Topic entity.
 */
public class TopicDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 30)
    private String name;

    private Long chapterId;

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

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TopicDTO topicDTO = (TopicDTO) o;
        if (topicDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), topicDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TopicDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", chapter=" + getChapterId() +
            "}";
    }
}
