package com.educationgenie.service.impl;

import com.educationgenie.service.UserReviewService;
import com.educationgenie.domain.UserReview;
import com.educationgenie.repository.UserReviewRepository;
import com.educationgenie.repository.search.UserReviewSearchRepository;
import com.educationgenie.service.dto.UserReviewDTO;
import com.educationgenie.service.mapper.UserReviewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing UserReview.
 */
@Service
@Transactional
public class UserReviewServiceImpl implements UserReviewService {

    private final Logger log = LoggerFactory.getLogger(UserReviewServiceImpl.class);

    private final UserReviewRepository userReviewRepository;

    private final UserReviewMapper userReviewMapper;

    private final UserReviewSearchRepository userReviewSearchRepository;

    public UserReviewServiceImpl(UserReviewRepository userReviewRepository, UserReviewMapper userReviewMapper, UserReviewSearchRepository userReviewSearchRepository) {
        this.userReviewRepository = userReviewRepository;
        this.userReviewMapper = userReviewMapper;
        this.userReviewSearchRepository = userReviewSearchRepository;
    }

    /**
     * Save a userReview.
     *
     * @param userReviewDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserReviewDTO save(UserReviewDTO userReviewDTO) {
        log.debug("Request to save UserReview : {}", userReviewDTO);
        UserReview userReview = userReviewMapper.toEntity(userReviewDTO);
        userReview = userReviewRepository.save(userReview);
        UserReviewDTO result = userReviewMapper.toDto(userReview);
        userReviewSearchRepository.save(userReview);
        return result;
    }

    /**
     * Get all the userReviews.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserReviewDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserReviews");
        return userReviewRepository.findAll(pageable)
            .map(userReviewMapper::toDto);
    }


    /**
     * Get one userReview by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserReviewDTO> findOne(Long id) {
        log.debug("Request to get UserReview : {}", id);
        return userReviewRepository.findById(id)
            .map(userReviewMapper::toDto);
    }

    /**
     * Delete the userReview by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserReview : {}", id);
        userReviewRepository.deleteById(id);
        userReviewSearchRepository.deleteById(id);
    }

    /**
     * Search for the userReview corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserReviewDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserReviews for query {}", query);
        return userReviewSearchRepository.search(queryStringQuery(query), pageable)
            .map(userReviewMapper::toDto);
    }
}
