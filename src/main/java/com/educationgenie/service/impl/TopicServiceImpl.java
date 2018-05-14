package com.educationgenie.service.impl;

import com.educationgenie.service.TopicService;
import com.educationgenie.domain.Topic;
import com.educationgenie.repository.TopicRepository;
import com.educationgenie.repository.search.TopicSearchRepository;
import com.educationgenie.service.dto.TopicDTO;
import com.educationgenie.service.mapper.TopicMapper;
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
 * Service Implementation for managing Topic.
 */
@Service
@Transactional
public class TopicServiceImpl implements TopicService {

    private final Logger log = LoggerFactory.getLogger(TopicServiceImpl.class);

    private final TopicRepository topicRepository;

    private final TopicMapper topicMapper;

    private final TopicSearchRepository topicSearchRepository;

    public TopicServiceImpl(TopicRepository topicRepository, TopicMapper topicMapper, TopicSearchRepository topicSearchRepository) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
        this.topicSearchRepository = topicSearchRepository;
    }

    /**
     * Save a topic.
     *
     * @param topicDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TopicDTO save(TopicDTO topicDTO) {
        log.debug("Request to save Topic : {}", topicDTO);
        Topic topic = topicMapper.toEntity(topicDTO);
        topic = topicRepository.save(topic);
        TopicDTO result = topicMapper.toDto(topic);
        topicSearchRepository.save(topic);
        return result;
    }

    /**
     * Get all the topics.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TopicDTO> findAll() {
        log.debug("Request to get all Topics");
        return topicRepository.findAll().stream()
            .map(topicMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one topic by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TopicDTO> findOne(Long id) {
        log.debug("Request to get Topic : {}", id);
        return topicRepository.findById(id)
            .map(topicMapper::toDto);
    }

    /**
     * Delete the topic by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Topic : {}", id);
        topicRepository.deleteById(id);
        topicSearchRepository.deleteById(id);
    }

    /**
     * Search for the topic corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TopicDTO> search(String query) {
        log.debug("Request to search Topics for query {}", query);
        return StreamSupport
            .stream(topicSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(topicMapper::toDto)
            .collect(Collectors.toList());
    }
}
