package com.educationgenie.service;

import com.educationgenie.service.dto.ScoreDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Score.
 */
public interface ScoreService {

    /**
     * Save a score.
     *
     * @param scoreDTO the entity to save
     * @return the persisted entity
     */
    ScoreDTO save(ScoreDTO scoreDTO);

    /**
     * Get all the scores.
     *
     * @return the list of entities
     */
    List<ScoreDTO> findAll();


    /**
     * Get the "id" score.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ScoreDTO> findOne(Long id);

    /**
     * Delete the "id" score.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the score corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ScoreDTO> search(String query);
}
