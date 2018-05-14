package com.educationgenie.service;

import com.educationgenie.service.dto.QuestionBankDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing QuestionBank.
 */
public interface QuestionBankService {

    /**
     * Save a questionBank.
     *
     * @param questionBankDTO the entity to save
     * @return the persisted entity
     */
    QuestionBankDTO save(QuestionBankDTO questionBankDTO);

    /**
     * Get all the questionBanks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<QuestionBankDTO> findAll(Pageable pageable);


    /**
     * Get the "id" questionBank.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<QuestionBankDTO> findOne(Long id);

    /**
     * Delete the "id" questionBank.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the questionBank corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<QuestionBankDTO> search(String query, Pageable pageable);
}
