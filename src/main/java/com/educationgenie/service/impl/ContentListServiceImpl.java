package com.educationgenie.service.impl;

import com.educationgenie.service.ContentListService;
import com.educationgenie.domain.ContentList;
import com.educationgenie.repository.ContentListRepository;
import com.educationgenie.repository.search.ContentListSearchRepository;
import com.educationgenie.service.dto.ContentListDTO;
import com.educationgenie.service.mapper.ContentListMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ContentList.
 */
@Service
@Transactional
public class ContentListServiceImpl implements ContentListService {

    private final Logger log = LoggerFactory.getLogger(ContentListServiceImpl.class);

    private final ContentListRepository contentListRepository;

    private final ContentListMapper contentListMapper;

    private final ContentListSearchRepository contentListSearchRepository;

    public ContentListServiceImpl(ContentListRepository contentListRepository, ContentListMapper contentListMapper, ContentListSearchRepository contentListSearchRepository) {
        this.contentListRepository = contentListRepository;
        this.contentListMapper = contentListMapper;
        this.contentListSearchRepository = contentListSearchRepository;
    }

    /**
     * Save a contentList.
     *
     * @param contentListDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ContentListDTO save(ContentListDTO contentListDTO) {
        log.debug("Request to save ContentList : {}", contentListDTO);
        ContentList contentList = contentListMapper.toEntity(contentListDTO);
        contentList = contentListRepository.save(contentList);
        ContentListDTO result = contentListMapper.toDto(contentList);
        contentListSearchRepository.save(contentList);
        return result;
    }

    /**
     * Get all the contentLists.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContentListDTO> findAll() {
        log.debug("Request to get all ContentLists");
        return contentListRepository.findAll().stream()
            .map(contentListMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one contentList by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ContentListDTO> findOne(Long id) {
        log.debug("Request to get ContentList : {}", id);
        return contentListRepository.findById(id)
            .map(contentListMapper::toDto);
    }

    /**
     * Delete the contentList by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContentList : {}", id);
        contentListRepository.deleteById(id);
        contentListSearchRepository.deleteById(id);
    }

    /**
     * Search for the contentList corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContentListDTO> search(String query) {
        log.debug("Request to search ContentLists for query {}", query);
        return StreamSupport
            .stream(contentListSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(contentListMapper::toDto)
            .collect(Collectors.toList());
    }
}
