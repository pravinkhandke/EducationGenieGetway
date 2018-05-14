package com.educationgenie.service;

import com.educationgenie.service.dto.ContentListDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ContentList.
 */
public interface ContentListService {

    /**
     * Save a contentList.
     *
     * @param contentListDTO the entity to save
     * @return the persisted entity
     */
    ContentListDTO save(ContentListDTO contentListDTO);

    /**
     * Get all the contentLists.
     *
     * @return the list of entities
     */
    List<ContentListDTO> findAll();


    /**
     * Get the "id" contentList.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ContentListDTO> findOne(Long id);

    /**
     * Delete the "id" contentList.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the contentList corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ContentListDTO> search(String query);
}
