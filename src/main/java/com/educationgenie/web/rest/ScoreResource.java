package com.educationgenie.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.educationgenie.service.ScoreService;
import com.educationgenie.web.rest.errors.BadRequestAlertException;
import com.educationgenie.web.rest.util.HeaderUtil;
import com.educationgenie.service.dto.ScoreDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Score.
 */
@RestController
@RequestMapping("/api")
public class ScoreResource {

    private final Logger log = LoggerFactory.getLogger(ScoreResource.class);

    private static final String ENTITY_NAME = "score";

    private final ScoreService scoreService;

    public ScoreResource(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    /**
     * POST  /scores : Create a new score.
     *
     * @param scoreDTO the scoreDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new scoreDTO, or with status 400 (Bad Request) if the score has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/scores")
    @Timed
    public ResponseEntity<ScoreDTO> createScore(@RequestBody ScoreDTO scoreDTO) throws URISyntaxException {
        log.debug("REST request to save Score : {}", scoreDTO);
        if (scoreDTO.getId() != null) {
            throw new BadRequestAlertException("A new score cannot already have an ID", ENTITY_NAME, "idexists");
        }        
        ScoreDTO result = scoreService.save(scoreDTO);
        return ResponseEntity.created(new URI("/api/scores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /scores : Updates an existing score.
     *
     * @param scoreDTO the scoreDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated scoreDTO,
     * or with status 400 (Bad Request) if the scoreDTO is not valid,
     * or with status 500 (Internal Server Error) if the scoreDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/scores")
    @Timed
    public ResponseEntity<ScoreDTO> updateScore(@RequestBody ScoreDTO scoreDTO) throws URISyntaxException {
        log.debug("REST request to update Score : {}", scoreDTO);
        if (scoreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }        
        ScoreDTO result = scoreService.save(scoreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, scoreDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /scores : get all the scores.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of scores in body
     */
    @GetMapping("/scores")
    @Timed
    public List<ScoreDTO> getAllScores() {
        log.debug("REST request to get all Scores");
        return scoreService.findAll();
    }

    /**
     * GET  /scores/:id : get the "id" score.
     *
     * @param id the id of the scoreDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the scoreDTO, or with status 404 (Not Found)
     */
    @GetMapping("/scores/{id}")
    @Timed
    public ResponseEntity<ScoreDTO> getScore(@PathVariable Long id) {
        log.debug("REST request to get Score : {}", id);
        Optional<ScoreDTO> scoreDTO = scoreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scoreDTO);
    }

    /**
     * DELETE  /scores/:id : delete the "id" score.
     *
     * @param id the id of the scoreDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/scores/{id}")
    @Timed
    public ResponseEntity<Void> deleteScore(@PathVariable Long id) {
        log.debug("REST request to delete Score : {}", id);
        scoreService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/scores?query=:query : search for the score corresponding
     * to the query.
     *
     * @param query the query of the score search
     * @return the result of the search
     */
    @GetMapping("/_search/scores")
    @Timed
    public List<ScoreDTO> searchScores(@RequestParam String query) {
        log.debug("REST request to search Scores for query {}", query);
        return scoreService.search(query);
    }

}
