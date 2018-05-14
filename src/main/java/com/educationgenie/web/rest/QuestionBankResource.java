package com.educationgenie.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.educationgenie.service.QuestionBankService;
import com.educationgenie.web.rest.errors.BadRequestAlertException;
import com.educationgenie.web.rest.util.HeaderUtil;
import com.educationgenie.web.rest.util.PaginationUtil;
import com.educationgenie.service.dto.QuestionBankDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
 * REST controller for managing QuestionBank.
 */
@RestController
@RequestMapping("/api")
public class QuestionBankResource {

    private final Logger log = LoggerFactory.getLogger(QuestionBankResource.class);

    private static final String ENTITY_NAME = "questionBank";

    private final QuestionBankService questionBankService;

    public QuestionBankResource(QuestionBankService questionBankService) {
        this.questionBankService = questionBankService;
    }

    /**
     * POST  /question-banks : Create a new questionBank.
     *
     * @param questionBankDTO the questionBankDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new questionBankDTO, or with status 400 (Bad Request) if the questionBank has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/question-banks")
    @Timed
    public ResponseEntity<QuestionBankDTO> createQuestionBank(@Valid @RequestBody QuestionBankDTO questionBankDTO) throws URISyntaxException {
        log.debug("REST request to save QuestionBank : {}", questionBankDTO);
        if (questionBankDTO.getId() != null) {
            throw new BadRequestAlertException("A new questionBank cannot already have an ID", ENTITY_NAME, "idexists");
        }        
        QuestionBankDTO result = questionBankService.save(questionBankDTO);
        return ResponseEntity.created(new URI("/api/question-banks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /question-banks : Updates an existing questionBank.
     *
     * @param questionBankDTO the questionBankDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated questionBankDTO,
     * or with status 400 (Bad Request) if the questionBankDTO is not valid,
     * or with status 500 (Internal Server Error) if the questionBankDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/question-banks")
    @Timed
    public ResponseEntity<QuestionBankDTO> updateQuestionBank(@Valid @RequestBody QuestionBankDTO questionBankDTO) throws URISyntaxException {
        log.debug("REST request to update QuestionBank : {}", questionBankDTO);
        if (questionBankDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }        
        QuestionBankDTO result = questionBankService.save(questionBankDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, questionBankDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /question-banks : get all the questionBanks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of questionBanks in body
     */
    @GetMapping("/question-banks")
    @Timed
    public ResponseEntity<List<QuestionBankDTO>> getAllQuestionBanks(Pageable pageable) {
        log.debug("REST request to get a page of QuestionBanks");
        Page<QuestionBankDTO> page = questionBankService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/question-banks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /question-banks/:id : get the "id" questionBank.
     *
     * @param id the id of the questionBankDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the questionBankDTO, or with status 404 (Not Found)
     */
    @GetMapping("/question-banks/{id}")
    @Timed
    public ResponseEntity<QuestionBankDTO> getQuestionBank(@PathVariable Long id) {
        log.debug("REST request to get QuestionBank : {}", id);
        Optional<QuestionBankDTO> questionBankDTO = questionBankService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionBankDTO);
    }

    /**
     * DELETE  /question-banks/:id : delete the "id" questionBank.
     *
     * @param id the id of the questionBankDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/question-banks/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuestionBank(@PathVariable Long id) {
        log.debug("REST request to delete QuestionBank : {}", id);
        questionBankService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/question-banks?query=:query : search for the questionBank corresponding
     * to the query.
     *
     * @param query the query of the questionBank search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/question-banks")
    @Timed
    public ResponseEntity<List<QuestionBankDTO>> searchQuestionBanks(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of QuestionBanks for query {}", query);
        Page<QuestionBankDTO> page = questionBankService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/question-banks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
