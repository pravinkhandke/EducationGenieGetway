package com.educationgenie.service;

import com.educationgenie.service.dto.ChapterDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Chapter.
 */
public interface ChapterService {

    /**
     * Save a chapter.
     *
     * @param chapterDTO the entity to save
     * @return the persisted entity
     */
    ChapterDTO save(ChapterDTO chapterDTO);

    /**
     * Get all the chapters.
     *
     * @return the list of entities
     */
    List<ChapterDTO> findAll();


    /**
     * Get the "id" chapter.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ChapterDTO> findOne(Long id);

    /**
     * Delete the "id" chapter.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the chapter corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ChapterDTO> search(String query);
}
