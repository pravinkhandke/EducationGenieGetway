package com.educationgenie.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.educationgenie.service.UserReviewService;
import com.educationgenie.web.rest.errors.BadRequestAlertException;
import com.educationgenie.web.rest.util.HeaderUtil;
import com.educationgenie.web.rest.util.PaginationUtil;
import com.educationgenie.service.dto.UserReviewDTO;
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
 * REST controller for managing UserReview.
 */
@RestController
@RequestMapping("/api")
public class UserReviewResource {

    private final Logger log = LoggerFactory.getLogger(UserReviewResource.class);

    private static final String ENTITY_NAME = "userReview";

    private final UserReviewService userReviewService;

    public UserReviewResource(UserReviewService userReviewService) {
        this.userReviewService = userReviewService;
    }

    /**
     * POST  /user-reviews : Create a new userReview.
     *
     * @param userReviewDTO the userReviewDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userReviewDTO, or with status 400 (Bad Request) if the userReview has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-reviews")
    @Timed
    public ResponseEntity<UserReviewDTO> createUserReview(@Valid @RequestBody UserReviewDTO userReviewDTO) throws URISyntaxException {
        log.debug("REST request to save UserReview : {}", userReviewDTO);
        if (userReviewDTO.getId() != null) {
            throw new BadRequestAlertException("A new userReview cannot already have an ID", ENTITY_NAME, "idexists");
        }        
        UserReviewDTO result = userReviewService.save(userReviewDTO);
        return ResponseEntity.created(new URI("/api/user-reviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-reviews : Updates an existing userReview.
     *
     * @param userReviewDTO the userReviewDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userReviewDTO,
     * or with status 400 (Bad Request) if the userReviewDTO is not valid,
     * or with status 500 (Internal Server Error) if the userReviewDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-reviews")
    @Timed
    public ResponseEntity<UserReviewDTO> updateUserReview(@Valid @RequestBody UserReviewDTO userReviewDTO) throws URISyntaxException {
        log.debug("REST request to update UserReview : {}", userReviewDTO);
        if (userReviewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }        
        UserReviewDTO result = userReviewService.save(userReviewDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userReviewDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-reviews : get all the userReviews.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userReviews in body
     */
    @GetMapping("/user-reviews")
    @Timed
    public ResponseEntity<List<UserReviewDTO>> getAllUserReviews(Pageable pageable) {
        log.debug("REST request to get a page of UserReviews");
        Page<UserReviewDTO> page = userReviewService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-reviews");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-reviews/:id : get the "id" userReview.
     *
     * @param id the id of the userReviewDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userReviewDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-reviews/{id}")
    @Timed
    public ResponseEntity<UserReviewDTO> getUserReview(@PathVariable Long id) {
        log.debug("REST request to get UserReview : {}", id);
        Optional<UserReviewDTO> userReviewDTO = userReviewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userReviewDTO);
    }

    /**
     * DELETE  /user-reviews/:id : delete the "id" userReview.
     *
     * @param id the id of the userReviewDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-reviews/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserReview(@PathVariable Long id) {
        log.debug("REST request to delete UserReview : {}", id);
        userReviewService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/user-reviews?query=:query : search for the userReview corresponding
     * to the query.
     *
     * @param query the query of the userReview search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/user-reviews")
    @Timed
    public ResponseEntity<List<UserReviewDTO>> searchUserReviews(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UserReviews for query {}", query);
        Page<UserReviewDTO> page = userReviewService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/user-reviews");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
