package com.educationgenie.service;

import com.educationgenie.service.dto.ContentReviewDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ContentReview.
 */
public interface ContentReviewService {

    /**
     * Save a contentReview.
     *
     * @param contentReviewDTO the entity to save
     * @return the persisted entity
     */
    ContentReviewDTO save(ContentReviewDTO contentReviewDTO);

    /**
     * Get all the contentReviews.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ContentReviewDTO> findAll(Pageable pageable);


    /**
     * Get the "id" contentReview.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ContentReviewDTO> findOne(Long id);

    /**
     * Delete the "id" contentReview.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the contentReview corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ContentReviewDTO> search(String query, Pageable pageable);
}
