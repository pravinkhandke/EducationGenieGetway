package com.educationgenie.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ContentList.
 */
@Entity
@Table(name = "content_list")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "contentlist")
public class ContentList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "list_title", nullable = false)
    private String listTitle;

    @ManyToOne
    @JsonIgnoreProperties("contentLists")
    private Content content;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getListTitle() {
        return listTitle;
    }

    public ContentList listTitle(String listTitle) {
        this.listTitle = listTitle;
        return this;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public Content getContent() {
        return content;
    }

    public ContentList content(Content content) {
        this.content = content;
        return this;
    }

    public void setContent(Content content) {
        this.content = content;
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
        ContentList contentList = (ContentList) o;
        if (contentList.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contentList.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContentList{" +
            "id=" + getId() +
            ", listTitle='" + getListTitle() + "'" +
            "}";
    }
}
