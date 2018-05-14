package com.educationgenie.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.educationgenie.service.ContentReviewService;
import com.educationgenie.web.rest.errors.BadRequestAlertException;
import com.educationgenie.web.rest.util.HeaderUtil;
import com.educationgenie.web.rest.util.PaginationUtil;
import com.educationgenie.service.dto.ContentReviewDTO;
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
 * REST controller for managing ContentReview.
 */
@RestController
@RequestMapping("/api")
public class ContentReviewResource {

    private final Logger log = LoggerFactory.getLogger(ContentReviewResource.class);

    private static final String ENTITY_NAME = "contentReview";

    private final ContentReviewService contentReviewService;

    public ContentReviewResource(ContentReviewService contentReviewService) {
        this.contentReviewService = contentReviewService;
    }

    /**
     * POST  /content-reviews : Create a new contentReview.
     *
     * @param contentReviewDTO the contentReviewDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contentReviewDTO, or with status 400 (Bad Request) if the contentReview has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/content-reviews")
    @Timed
    public ResponseEntity<ContentReviewDTO> createContentReview(@Valid @RequestBody ContentReviewDTO contentReviewDTO) throws URISyntaxException {
        log.debug("REST request to save ContentReview : {}", contentReviewDTO);
        if (contentReviewDTO.getId() != null) {
            throw new BadRequestAlertException("A new contentReview cannot already have an ID", ENTITY_NAME, "idexists");
        }        
        ContentReviewDTO result = contentReviewService.save(contentReviewDTO);
        return ResponseEntity.created(new URI("/api/content-reviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /content-reviews : Updates an existing contentReview.
     *
     * @param contentReviewDTO the contentReviewDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contentReviewDTO,
     * or with status 400 (Bad Request) if the contentReviewDTO is not valid,
     * or with status 500 (Internal Server Error) if the contentReviewDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/content-reviews")
    @Timed
    public ResponseEntity<ContentReviewDTO> updateContentReview(@Valid @RequestBody ContentReviewDTO contentReviewDTO) throws URISyntaxException {
        log.debug("REST request to update ContentReview : {}", contentReviewDTO);
        if (contentReviewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }        
        ContentReviewDTO result = contentReviewService.save(contentReviewDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contentReviewDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /content-reviews : get all the contentReviews.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contentReviews in body
     */
    @GetMapping("/content-reviews")
    @Timed
    public ResponseEntity<List<ContentReviewDTO>> getAllContentReviews(Pageable pageable) {
        log.debug("REST request to get a page of ContentReviews");
        Page<ContentReviewDTO> page = contentReviewService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/content-reviews");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /content-reviews/:id : get the "id" contentReview.
     *
     * @param id the id of the contentReviewDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contentReviewDTO, or with status 404 (Not Found)
     */
    @GetMapping("/content-reviews/{id}")
    @Timed
    public ResponseEntity<ContentReviewDTO> getContentReview(@PathVariable Long id) {
        log.debug("REST request to get ContentReview : {}", id);
        Optional<ContentReviewDTO> contentReviewDTO = contentReviewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contentReviewDTO);
    }

    /**
     * DELETE  /content-reviews/:id : delete the "id" contentReview.
     *
     * @param id the id of the contentReviewDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/content-reviews/{id}")
    @Timed
    public ResponseEntity<Void> deleteContentReview(@PathVariable Long id) {
        log.debug("REST request to delete ContentReview : {}", id);
        contentReviewService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/content-reviews?query=:query : search for the contentReview corresponding
     * to the query.
     *
     * @param query the query of the contentReview search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/content-reviews")
    @Timed
    public ResponseEntity<List<ContentReviewDTO>> searchContentReviews(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ContentReviews for query {}", query);
        Page<ContentReviewDTO> page = contentReviewService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/content-reviews");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
