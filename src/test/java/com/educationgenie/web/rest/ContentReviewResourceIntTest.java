package com.educationgenie.web.rest;

import com.educationgenie.EducationGenieApp;

import com.educationgenie.domain.ContentReview;
import com.educationgenie.repository.ContentReviewRepository;
import com.educationgenie.repository.search.ContentReviewSearchRepository;
import com.educationgenie.service.ContentReviewService;
import com.educationgenie.service.dto.ContentReviewDTO;
import com.educationgenie.service.mapper.ContentReviewMapper;
import com.educationgenie.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import static com.educationgenie.web.rest.TestUtil.sameInstant;
import static com.educationgenie.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ContentReviewResource REST controller.
 *
 * @see ContentReviewResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EducationGenieApp.class)
public class ContentReviewResourceIntTest {

    private static final Integer DEFAULT_RAITING = 1;
    private static final Integer UPDATED_RAITING = 2;

    private static final String DEFAULT_REVIEW = "AAAAAAAAAA";
    private static final String UPDATED_REVIEW = "BBBBBBBBBB";

    private static final String DEFAULT_CREATE_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ContentReviewRepository contentReviewRepository;


    @Autowired
    private ContentReviewMapper contentReviewMapper;
    

    @Autowired
    private ContentReviewService contentReviewService;

    /**
     * This repository is mocked in the com.educationgenie.repository.search test package.
     *
     * @see com.educationgenie.repository.search.ContentReviewSearchRepositoryMockConfiguration
     */
    @Autowired
    private ContentReviewSearchRepository mockContentReviewSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContentReviewMockMvc;

    private ContentReview contentReview;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContentReviewResource contentReviewResource = new ContentReviewResource(contentReviewService);
        this.restContentReviewMockMvc = MockMvcBuilders.standaloneSetup(contentReviewResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContentReview createEntity(EntityManager em) {
        ContentReview contentReview = new ContentReview()
            .raiting(DEFAULT_RAITING)
            .review(DEFAULT_REVIEW)
            .createBy(DEFAULT_CREATE_BY)
            .createdTime(DEFAULT_CREATED_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedTime(DEFAULT_UPDATED_TIME);
        return contentReview;
    }

    @Before
    public void initTest() {
        contentReview = createEntity(em);
    }

    @Test
    @Transactional
    public void createContentReview() throws Exception {
        int databaseSizeBeforeCreate = contentReviewRepository.findAll().size();

        // Create the ContentReview
        ContentReviewDTO contentReviewDTO = contentReviewMapper.toDto(contentReview);
        restContentReviewMockMvc.perform(post("/api/content-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentReviewDTO)))
            .andExpect(status().isCreated());

        // Validate the ContentReview in the database
        List<ContentReview> contentReviewList = contentReviewRepository.findAll();
        assertThat(contentReviewList).hasSize(databaseSizeBeforeCreate + 1);
        ContentReview testContentReview = contentReviewList.get(contentReviewList.size() - 1);
        assertThat(testContentReview.getRaiting()).isEqualTo(DEFAULT_RAITING);
        assertThat(testContentReview.getReview()).isEqualTo(DEFAULT_REVIEW);
        assertThat(testContentReview.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testContentReview.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testContentReview.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testContentReview.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);

        // Validate the ContentReview in Elasticsearch
        verify(mockContentReviewSearchRepository, times(1)).save(testContentReview);
    }

    @Test
    @Transactional
    public void createContentReviewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contentReviewRepository.findAll().size();

        // Create the ContentReview with an existing ID
        contentReview.setId(1L);
        ContentReviewDTO contentReviewDTO = contentReviewMapper.toDto(contentReview);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentReviewMockMvc.perform(post("/api/content-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentReviewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContentReview in the database
        List<ContentReview> contentReviewList = contentReviewRepository.findAll();
        assertThat(contentReviewList).hasSize(databaseSizeBeforeCreate);

        // Validate the ContentReview in Elasticsearch
        verify(mockContentReviewSearchRepository, times(0)).save(contentReview);
    }

    @Test
    @Transactional
    public void checkCreateByIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentReviewRepository.findAll().size();
        // set the field null
        contentReview.setCreateBy(null);

        // Create the ContentReview, which fails.
        ContentReviewDTO contentReviewDTO = contentReviewMapper.toDto(contentReview);

        restContentReviewMockMvc.perform(post("/api/content-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentReviewDTO)))
            .andExpect(status().isBadRequest());

        List<ContentReview> contentReviewList = contentReviewRepository.findAll();
        assertThat(contentReviewList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContentReviews() throws Exception {
        // Initialize the database
        contentReviewRepository.saveAndFlush(contentReview);

        // Get all the contentReviewList
        restContentReviewMockMvc.perform(get("/api/content-reviews?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contentReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].raiting").value(hasItem(DEFAULT_RAITING)))
            .andExpect(jsonPath("$.[*].review").value(hasItem(DEFAULT_REVIEW.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(sameInstant(DEFAULT_CREATED_TIME))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(sameInstant(DEFAULT_UPDATED_TIME))));
    }
    

    @Test
    @Transactional
    public void getContentReview() throws Exception {
        // Initialize the database
        contentReviewRepository.saveAndFlush(contentReview);

        // Get the contentReview
        restContentReviewMockMvc.perform(get("/api/content-reviews/{id}", contentReview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contentReview.getId().intValue()))
            .andExpect(jsonPath("$.raiting").value(DEFAULT_RAITING))
            .andExpect(jsonPath("$.review").value(DEFAULT_REVIEW.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.toString()))
            .andExpect(jsonPath("$.createdTime").value(sameInstant(DEFAULT_CREATED_TIME)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedTime").value(sameInstant(DEFAULT_UPDATED_TIME)));
    }
    @Test
    @Transactional
    public void getNonExistingContentReview() throws Exception {
        // Get the contentReview
        restContentReviewMockMvc.perform(get("/api/content-reviews/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContentReview() throws Exception {
        // Initialize the database
        contentReviewRepository.saveAndFlush(contentReview);

        int databaseSizeBeforeUpdate = contentReviewRepository.findAll().size();

        // Update the contentReview
        ContentReview updatedContentReview = contentReviewRepository.findById(contentReview.getId()).get();
        // Disconnect from session so that the updates on updatedContentReview are not directly saved in db
        em.detach(updatedContentReview);
        updatedContentReview
            .raiting(UPDATED_RAITING)
            .review(UPDATED_REVIEW)
            .createBy(UPDATED_CREATE_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        ContentReviewDTO contentReviewDTO = contentReviewMapper.toDto(updatedContentReview);

        restContentReviewMockMvc.perform(put("/api/content-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentReviewDTO)))
            .andExpect(status().isOk());

        // Validate the ContentReview in the database
        List<ContentReview> contentReviewList = contentReviewRepository.findAll();
        assertThat(contentReviewList).hasSize(databaseSizeBeforeUpdate);
        ContentReview testContentReview = contentReviewList.get(contentReviewList.size() - 1);
        assertThat(testContentReview.getRaiting()).isEqualTo(UPDATED_RAITING);
        assertThat(testContentReview.getReview()).isEqualTo(UPDATED_REVIEW);
        assertThat(testContentReview.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testContentReview.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testContentReview.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testContentReview.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);

        // Validate the ContentReview in Elasticsearch
        verify(mockContentReviewSearchRepository, times(1)).save(testContentReview);
    }

    @Test
    @Transactional
    public void updateNonExistingContentReview() throws Exception {
        int databaseSizeBeforeUpdate = contentReviewRepository.findAll().size();

        // Create the ContentReview
        ContentReviewDTO contentReviewDTO = contentReviewMapper.toDto(contentReview);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContentReviewMockMvc.perform(put("/api/content-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentReviewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContentReview in the database
        List<ContentReview> contentReviewList = contentReviewRepository.findAll();
        assertThat(contentReviewList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ContentReview in Elasticsearch
        verify(mockContentReviewSearchRepository, times(0)).save(contentReview);
    }

    @Test
    @Transactional
    public void deleteContentReview() throws Exception {
        // Initialize the database
        contentReviewRepository.saveAndFlush(contentReview);

        int databaseSizeBeforeDelete = contentReviewRepository.findAll().size();

        // Get the contentReview
        restContentReviewMockMvc.perform(delete("/api/content-reviews/{id}", contentReview.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContentReview> contentReviewList = contentReviewRepository.findAll();
        assertThat(contentReviewList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ContentReview in Elasticsearch
        verify(mockContentReviewSearchRepository, times(1)).deleteById(contentReview.getId());
    }

    @Test
    @Transactional
    public void searchContentReview() throws Exception {
        // Initialize the database
        contentReviewRepository.saveAndFlush(contentReview);
        when(mockContentReviewSearchRepository.search(queryStringQuery("id:" + contentReview.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(contentReview), PageRequest.of(0, 1), 1));
        // Search the contentReview
        restContentReviewMockMvc.perform(get("/api/_search/content-reviews?query=id:" + contentReview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contentReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].raiting").value(hasItem(DEFAULT_RAITING)))
            .andExpect(jsonPath("$.[*].review").value(hasItem(DEFAULT_REVIEW.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(sameInstant(DEFAULT_CREATED_TIME))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(sameInstant(DEFAULT_UPDATED_TIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentReview.class);
        ContentReview contentReview1 = new ContentReview();
        contentReview1.setId(1L);
        ContentReview contentReview2 = new ContentReview();
        contentReview2.setId(contentReview1.getId());
        assertThat(contentReview1).isEqualTo(contentReview2);
        contentReview2.setId(2L);
        assertThat(contentReview1).isNotEqualTo(contentReview2);
        contentReview1.setId(null);
        assertThat(contentReview1).isNotEqualTo(contentReview2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentReviewDTO.class);
        ContentReviewDTO contentReviewDTO1 = new ContentReviewDTO();
        contentReviewDTO1.setId(1L);
        ContentReviewDTO contentReviewDTO2 = new ContentReviewDTO();
        assertThat(contentReviewDTO1).isNotEqualTo(contentReviewDTO2);
        contentReviewDTO2.setId(contentReviewDTO1.getId());
        assertThat(contentReviewDTO1).isEqualTo(contentReviewDTO2);
        contentReviewDTO2.setId(2L);
        assertThat(contentReviewDTO1).isNotEqualTo(contentReviewDTO2);
        contentReviewDTO1.setId(null);
        assertThat(contentReviewDTO1).isNotEqualTo(contentReviewDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contentReviewMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contentReviewMapper.fromId(null)).isNull();
    }
}
