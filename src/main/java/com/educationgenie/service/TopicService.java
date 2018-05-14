package com.educationgenie.service;

import com.educationgenie.service.dto.TopicDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Topic.
 */
public interface TopicService {

    /**
     * Save a topic.
     *
     * @param topicDTO the entity to save
     * @return the persisted entity
     */
    TopicDTO save(TopicDTO topicDTO);

    /**
     * Get all the topics.
     *
     * @return the list of entities
     */
    List<TopicDTO> findAll();


    /**
     * Get the "id" topic.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TopicDTO> findOne(Long id);

    /**
     * Delete the "id" topic.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the topic corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<TopicDTO> search(String query);
}
