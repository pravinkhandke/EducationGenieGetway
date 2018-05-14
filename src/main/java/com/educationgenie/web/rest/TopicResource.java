package com.educationgenie.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.educationgenie.service.TopicService;
import com.educationgenie.web.rest.errors.BadRequestAlertException;
import com.educationgenie.web.rest.util.HeaderUtil;
import com.educationgenie.service.dto.TopicDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Topic.
 */
@RestController
@RequestMapping("/api")
public class TopicResource {

    private final Logger log = LoggerFactory.getLogger(TopicResource.class);

    private static final String ENTITY_NAME = "topic";

    private final TopicService topicService;

    public TopicResource(TopicService topicService) {
        this.topicService = topicService;
    }

    /**
     * POST  /topics : Create a new topic.
     *
     * @param topicDTO the topicDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new topicDTO, or with status 400 (Bad Request) if the topic has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/topics")
    @Timed
    public ResponseEntity<TopicDTO> createTopic(@Valid @RequestBody TopicDTO topicDTO) throws URISyntaxException {
        log.debug("REST request to save Topic : {}", topicDTO);
        if (topicDTO.getId() != null) {
            throw new BadRequestAlertException("A new topic cannot already have an ID", ENTITY_NAME, "idexists");
        }        
        TopicDTO result = topicService.save(topicDTO);
        return ResponseEntity.created(new URI("/api/topics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /topics : Updates an existing topic.
     *
     * @param topicDTO the topicDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated topicDTO,
     * or with status 400 (Bad Request) if the topicDTO is not valid,
     * or with status 500 (Internal Server Error) if the topicDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/topics")
    @Timed
    public ResponseEntity<TopicDTO> updateTopic(@Valid @RequestBody TopicDTO topicDTO) throws URISyntaxException {
        log.debug("REST request to update Topic : {}", topicDTO);
        if (topicDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }        
        TopicDTO result = topicService.save(topicDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, topicDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /topics : get all the topics.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of topics in body
     */
    @GetMapping("/topics")
    @Timed
    public List<TopicDTO> getAllTopics() {
        log.debug("REST request to get all Topics");
        return topicService.findAll();
    }

    /**
     * GET  /topics/:id : get the "id" topic.
     *
     * @param id the id of the topicDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the topicDTO, or with status 404 (Not Found)
     */
    @GetMapping("/topics/{id}")
    @Timed
    public ResponseEntity<TopicDTO> getTopic(@PathVariable Long id) {
        log.debug("REST request to get Topic : {}", id);
        Optional<TopicDTO> topicDTO = topicService.findOne(id);
        return ResponseUtil.wrapOrNotFound(topicDTO);
    }

    /**
     * DELETE  /topics/:id : delete the "id" topic.
     *
     * @param id the id of the topicDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/topics/{id}")
    @Timed
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        log.debug("REST request to delete Topic : {}", id);
        topicService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/topics?query=:query : search for the topic corresponding
     * to the query.
     *
     * @param query the query of the topic search
     * @return the result of the search
     */
    @GetMapping("/_search/topics")
    @Timed
    public List<TopicDTO> searchTopics(@RequestParam String query) {
        log.debug("REST request to search Topics for query {}", query);
        return topicService.search(query);
    }

}
