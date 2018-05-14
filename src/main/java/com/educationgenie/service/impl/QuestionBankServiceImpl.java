package com.educationgenie.service.impl;

import com.educationgenie.service.QuestionBankService;
import com.educationgenie.domain.QuestionBank;
import com.educationgenie.repository.QuestionBankRepository;
import com.educationgenie.repository.search.QuestionBankSearchRepository;
import com.educationgenie.service.dto.QuestionBankDTO;
import com.educationgenie.service.mapper.QuestionBankMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing QuestionBank.
 */
@Service
@Transactional
public class QuestionBankServiceImpl implements QuestionBankService {

    private final Logger log = LoggerFactory.getLogger(QuestionBankServiceImpl.class);

    private final QuestionBankRepository questionBankRepository;

    private final QuestionBankMapper questionBankMapper;

    private final QuestionBankSearchRepository questionBankSearchRepository;

    public QuestionBankServiceImpl(QuestionBankRepository questionBankRepository, QuestionBankMapper questionBankMapper, QuestionBankSearchRepository questionBankSearchRepository) {
        this.questionBankRepository = questionBankRepository;
        this.questionBankMapper = questionBankMapper;
        this.questionBankSearchRepository = questionBankSearchRepository;
    }

    /**
     * Save a questionBank.
     *
     * @param questionBankDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public QuestionBankDTO save(QuestionBankDTO questionBankDTO) {
        log.debug("Request to save QuestionBank : {}", questionBankDTO);
        QuestionBank questionBank = questionBankMapper.toEntity(questionBankDTO);
        questionBank = questionBankRepository.save(questionBank);
        QuestionBankDTO result = questionBankMapper.toDto(questionBank);
        questionBankSearchRepository.save(questionBank);
        return result;
    }

    /**
     * Get all the questionBanks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QuestionBankDTO> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionBanks");
        return questionBankRepository.findAll(pageable)
            .map(questionBankMapper::toDto);
    }


    /**
     * Get one questionBank by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionBankDTO> findOne(Long id) {
        log.debug("Request to get QuestionBank : {}", id);
        return questionBankRepository.findById(id)
            .map(questionBankMapper::toDto);
    }

    /**
     * Delete the questionBank by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuestionBank : {}", id);
        questionBankRepository.deleteById(id);
        questionBankSearchRepository.deleteById(id);
    }

    /**
     * Search for the questionBank corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QuestionBankDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of QuestionBanks for query {}", query);
        return questionBankSearchRepository.search(queryStringQuery(query), pageable)
            .map(questionBankMapper::toDto);
    }
}
