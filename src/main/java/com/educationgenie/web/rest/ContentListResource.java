package com.educationgenie.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.educationgenie.service.ContentListService;
import com.educationgenie.web.rest.errors.BadRequestAlertException;
import com.educationgenie.web.rest.util.HeaderUtil;
import com.educationgenie.service.dto.ContentListDTO;
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
 * REST controller for managing ContentList.
 */
@RestController
@RequestMapping("/api")
public class ContentListResource {

    private final Logger log = LoggerFactory.getLogger(ContentListResource.class);

    private static final String ENTITY_NAME = "contentList";

    private final ContentListService contentListService;

    public ContentListResource(ContentListService contentListService) {
        this.contentListService = contentListService;
    }

    /**
     * POST  /content-lists : Create a new contentList.
     *
     * @param contentListDTO the contentListDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contentListDTO, or with status 400 (Bad Request) if the contentList has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/content-lists")
    @Timed
    public ResponseEntity<ContentListDTO> createContentList(@Valid @RequestBody ContentListDTO contentListDTO) throws URISyntaxException {
        log.debug("REST request to save ContentList : {}", contentListDTO);
        if (contentListDTO.getId() != null) {
            throw new BadRequestAlertException("A new contentList cannot already have an ID", ENTITY_NAME, "idexists");
        }        
        ContentListDTO result = contentListService.save(contentListDTO);
        return ResponseEntity.created(new URI("/api/content-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /content-lists : Updates an existing contentList.
     *
     * @param contentListDTO the contentListDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contentListDTO,
     * or with status 400 (Bad Request) if the contentListDTO is not valid,
     * or with status 500 (Internal Server Error) if the contentListDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/content-lists")
    @Timed
    public ResponseEntity<ContentListDTO> updateContentList(@Valid @RequestBody ContentListDTO contentListDTO) throws URISyntaxException {
        log.debug("REST request to update ContentList : {}", contentListDTO);
        if (contentListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }        
        ContentListDTO result = contentListService.save(contentListDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contentListDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /content-lists : get all the contentLists.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contentLists in body
     */
    @GetMapping("/content-lists")
    @Timed
    public List<ContentListDTO> getAllContentLists() {
        log.debug("REST request to get all ContentLists");
        return contentListService.findAll();
    }

    /**
     * GET  /content-lists/:id : get the "id" contentList.
     *
     * @param id the id of the contentListDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contentListDTO, or with status 404 (Not Found)
     */
    @GetMapping("/content-lists/{id}")
    @Timed
    public ResponseEntity<ContentListDTO> getContentList(@PathVariable Long id) {
        log.debug("REST request to get ContentList : {}", id);
        Optional<ContentListDTO> contentListDTO = contentListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contentListDTO);
    }

    /**
     * DELETE  /content-lists/:id : delete the "id" contentList.
     *
     * @param id the id of the contentListDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/content-lists/{id}")
    @Timed
    public ResponseEntity<Void> deleteContentList(@PathVariable Long id) {
        log.debug("REST request to delete ContentList : {}", id);
        contentListService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/content-lists?query=:query : search for the contentList corresponding
     * to the query.
     *
     * @param query the query of the contentList search
     * @return the result of the search
     */
    @GetMapping("/_search/content-lists")
    @Timed
    public List<ContentListDTO> searchContentLists(@RequestParam String query) {
        log.debug("REST request to search ContentLists for query {}", query);
        return contentListService.search(query);
    }

}
