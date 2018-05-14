package com.educationgenie.service.impl;

import com.educationgenie.service.ContentReviewService;
import com.educationgenie.domain.ContentReview;
import com.educationgenie.repository.ContentReviewRepository;
import com.educationgenie.repository.search.ContentReviewSearchRepository;
import com.educationgenie.service.dto.ContentReviewDTO;
import com.educationgenie.service.mapper.ContentReviewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ContentReview.
 */
@Service
@Transactional
public class ContentReviewServiceImpl implements ContentReviewService {

    private final Logger log = LoggerFactory.getLogger(ContentReviewServiceImpl.class);

    private final ContentReviewRepository contentReviewRepository;

    private final ContentReviewMapper contentReviewMapper;

    private final ContentReviewSearchRepository contentReviewSearchRepository;

    public ContentReviewServiceImpl(ContentReviewRepository contentReviewRepository, ContentReviewMapper contentReviewMapper, ContentReviewSearchRepository contentReviewSearchRepository) {
        this.contentReviewRepository = contentReviewRepository;
        this.contentReviewMapper = contentReviewMapper;
        this.contentReviewSearchRepository = contentReviewSearchRepository;
    }

    /**
     * Save a contentReview.
     *
     * @param contentReviewDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ContentReviewDTO save(ContentReviewDTO contentReviewDTO) {
        log.debug("Request to save ContentReview : {}", contentReviewDTO);
        ContentReview contentReview = contentReviewMapper.toEntity(contentReviewDTO);
        contentReview = contentReviewRepository.save(contentReview);
        ContentReviewDTO result = contentReviewMapper.toDto(contentReview);
        contentReviewSearchRepository.save(contentReview);
        return result;
    }

    /**
     * Get all the contentReviews.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContentReviewDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContentReviews");
        return contentReviewRepository.findAll(pageable)
            .map(contentReviewMapper::toDto);
    }


    /**
     * Get one contentReview by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ContentReviewDTO> findOne(Long id) {
        log.debug("Request to get ContentReview : {}", id);
        return contentReviewRepository.findById(id)
            .map(contentReviewMapper::toDto);
    }

    /**
     * Delete the contentReview by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContentReview : {}", id);
        contentReviewRepository.deleteById(id);
        contentReviewSearchRepository.deleteById(id);
    }

    /**
     * Search for the contentReview corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContentReviewDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ContentReviews for query {}", query);
        return contentReviewSearchRepository.search(queryStringQuery(query), pageable)
            .map(contentReviewMapper::toDto);
    }
}
