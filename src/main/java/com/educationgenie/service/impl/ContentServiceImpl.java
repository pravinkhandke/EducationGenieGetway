package com.educationgenie.service.impl;

import com.educationgenie.service.ContentService;
import com.educationgenie.domain.Content;
import com.educationgenie.repository.ContentRepository;
import com.educationgenie.repository.search.ContentSearchRepository;
import com.educationgenie.service.dto.ContentDTO;
import com.educationgenie.service.mapper.ContentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Content.
 */
@Service
@Transactional
public class ContentServiceImpl implements ContentService {

    private final Logger log = LoggerFactory.getLogger(ContentServiceImpl.class);

    private final ContentRepository contentRepository;

    private final ContentMapper contentMapper;

    private final ContentSearchRepository contentSearchRepository;

    public ContentServiceImpl(ContentRepository contentRepository, ContentMapper contentMapper, ContentSearchRepository contentSearchRepository) {
        this.contentRepository = contentRepository;
        this.contentMapper = contentMapper;
        this.contentSearchRepository = contentSearchRepository;
    }

    /**
     * Save a content.
     *
     * @param contentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ContentDTO save(ContentDTO contentDTO) {
        log.debug("Request to save Content : {}", contentDTO);
        Content content = contentMapper.toEntity(contentDTO);
        content = contentRepository.save(content);
        ContentDTO result = contentMapper.toDto(content);
        contentSearchRepository.save(content);
        return result;
    }

    /**
     * Get all the contents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contents");
        return contentRepository.findAll(pageable)
            .map(contentMapper::toDto);
    }

    /**
     * Get all the Content with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ContentDTO> findAllWithEagerRelationships(Pageable pageable) {
        return contentRepository.findAllWithEagerRelationships(pageable).map(contentMapper::toDto);
    }
    

    /**
     * Get one content by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ContentDTO> findOne(Long id) {
        log.debug("Request to get Content : {}", id);
        return contentRepository.findOneWithEagerRelationships(id)
            .map(contentMapper::toDto);
    }

    /**
     * Delete the content by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Content : {}", id);
        contentRepository.deleteById(id);
        contentSearchRepository.deleteById(id);
    }

    /**
     * Search for the content corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Contents for query {}", query);
        return contentSearchRepository.search(queryStringQuery(query), pageable)
            .map(contentMapper::toDto);
    }
}
