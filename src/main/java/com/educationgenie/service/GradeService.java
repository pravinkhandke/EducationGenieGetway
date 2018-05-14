package com.educationgenie.service;

import com.educationgenie.service.dto.GradeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Grade.
 */
public interface GradeService {

    /**
     * Save a grade.
     *
     * @param gradeDTO the entity to save
     * @return the persisted entity
     */
    GradeDTO save(GradeDTO gradeDTO);

    /**
     * Get all the grades.
     *
     * @return the list of entities
     */
    List<GradeDTO> findAll();


    /**
     * Get the "id" grade.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<GradeDTO> findOne(Long id);

    /**
     * Delete the "id" grade.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the grade corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<GradeDTO> search(String query);
}
