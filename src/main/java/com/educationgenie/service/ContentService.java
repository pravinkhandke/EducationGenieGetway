package com.educationgenie.service;

import com.educationgenie.service.dto.ContentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Content.
 */
public interface ContentService {

    /**
     * Save a content.
     *
     * @param contentDTO the entity to save
     * @return the persisted entity
     */
    ContentDTO save(ContentDTO contentDTO);

    /**
     * Get all the contents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ContentDTO> findAll(Pageable pageable);

    /**
     * Get all the Content with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<ContentDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" content.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ContentDTO> findOne(Long id);

    /**
     * Delete the "id" content.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the content corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ContentDTO> search(String query, Pageable pageable);
}
