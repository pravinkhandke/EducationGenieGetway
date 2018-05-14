package com.educationgenie.service;

import com.educationgenie.service.dto.UserReviewDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing UserReview.
 */
public interface UserReviewService {

    /**
     * Save a userReview.
     *
     * @param userReviewDTO the entity to save
     * @return the persisted entity
     */
    UserReviewDTO save(UserReviewDTO userReviewDTO);

    /**
     * Get all the userReviews.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserReviewDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userReview.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserReviewDTO> findOne(Long id);

    /**
     * Delete the "id" userReview.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the userReview corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserReviewDTO> search(String query, Pageable pageable);
}
