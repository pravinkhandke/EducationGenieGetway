package com.educationgenie.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ContentList entity.
 */
public class ContentListDTO implements Serializable {

    private Long id;

    @NotNull
    private String listTitle;

    private Long contentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContentListDTO contentListDTO = (ContentListDTO) o;
        if (contentListDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contentListDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContentListDTO{" +
            "id=" + getId() +
            ", listTitle='" + getListTitle() + "'" +
            ", content=" + getContentId() +
            "}";
    }
}
