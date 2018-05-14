package com.educationgenie.web.rest;

import com.educationgenie.EducationGenieApp;

import com.educationgenie.domain.UserReview;
import com.educationgenie.repository.UserReviewRepository;
import com.educationgenie.repository.search.UserReviewSearchRepository;
import com.educationgenie.service.UserReviewService;
import com.educationgenie.service.dto.UserReviewDTO;
import com.educationgenie.service.mapper.UserReviewMapper;
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
 * Test class for the UserReviewResource REST controller.
 *
 * @see UserReviewResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EducationGenieApp.class)
public class UserReviewResourceIntTest {

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
    private UserReviewRepository userReviewRepository;


    @Autowired
    private UserReviewMapper userReviewMapper;
    

    @Autowired
    private UserReviewService userReviewService;

    /**
     * This repository is mocked in the com.educationgenie.repository.search test package.
     *
     * @see com.educationgenie.repository.search.UserReviewSearchRepositoryMockConfiguration
     */
    @Autowired
    private UserReviewSearchRepository mockUserReviewSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserReviewMockMvc;

    private UserReview userReview;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserReviewResource userReviewResource = new UserReviewResource(userReviewService);
        this.restUserReviewMockMvc = MockMvcBuilders.standaloneSetup(userReviewResource)
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
    public static UserReview createEntity(EntityManager em) {
        UserReview userReview = new UserReview()
            .raiting(DEFAULT_RAITING)
            .review(DEFAULT_REVIEW)
            .createBy(DEFAULT_CREATE_BY)
            .createdTime(DEFAULT_CREATED_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedTime(DEFAULT_UPDATED_TIME);
        return userReview;
    }

    @Before
    public void initTest() {
        userReview = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserReview() throws Exception {
        int databaseSizeBeforeCreate = userReviewRepository.findAll().size();

        // Create the UserReview
        UserReviewDTO userReviewDTO = userReviewMapper.toDto(userReview);
        restUserReviewMockMvc.perform(post("/api/user-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userReviewDTO)))
            .andExpect(status().isCreated());

        // Validate the UserReview in the database
        List<UserReview> userReviewList = userReviewRepository.findAll();
        assertThat(userReviewList).hasSize(databaseSizeBeforeCreate + 1);
        UserReview testUserReview = userReviewList.get(userReviewList.size() - 1);
        assertThat(testUserReview.getRaiting()).isEqualTo(DEFAULT_RAITING);
        assertThat(testUserReview.getReview()).isEqualTo(DEFAULT_REVIEW);
        assertThat(testUserReview.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testUserReview.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testUserReview.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testUserReview.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);

        // Validate the UserReview in Elasticsearch
        verify(mockUserReviewSearchRepository, times(1)).save(testUserReview);
    }

    @Test
    @Transactional
    public void createUserReviewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userReviewRepository.findAll().size();

        // Create the UserReview with an existing ID
        userReview.setId(1L);
        UserReviewDTO userReviewDTO = userReviewMapper.toDto(userReview);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserReviewMockMvc.perform(post("/api/user-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userReviewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserReview in the database
        List<UserReview> userReviewList = userReviewRepository.findAll();
        assertThat(userReviewList).hasSize(databaseSizeBeforeCreate);

        // Validate the UserReview in Elasticsearch
        verify(mockUserReviewSearchRepository, times(0)).save(userReview);
    }

    @Test
    @Transactional
    public void checkCreateByIsRequired() throws Exception {
        int databaseSizeBeforeTest = userReviewRepository.findAll().size();
        // set the field null
        userReview.setCreateBy(null);

        // Create the UserReview, which fails.
        UserReviewDTO userReviewDTO = userReviewMapper.toDto(userReview);

        restUserReviewMockMvc.perform(post("/api/user-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userReviewDTO)))
            .andExpect(status().isBadRequest());

        List<UserReview> userReviewList = userReviewRepository.findAll();
        assertThat(userReviewList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserReviews() throws Exception {
        // Initialize the database
        userReviewRepository.saveAndFlush(userReview);

        // Get all the userReviewList
        restUserReviewMockMvc.perform(get("/api/user-reviews?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].raiting").value(hasItem(DEFAULT_RAITING)))
            .andExpect(jsonPath("$.[*].review").value(hasItem(DEFAULT_REVIEW.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(sameInstant(DEFAULT_CREATED_TIME))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(sameInstant(DEFAULT_UPDATED_TIME))));
    }
    

    @Test
    @Transactional
    public void getUserReview() throws Exception {
        // Initialize the database
        userReviewRepository.saveAndFlush(userReview);

        // Get the userReview
        restUserReviewMockMvc.perform(get("/api/user-reviews/{id}", userReview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userReview.getId().intValue()))
            .andExpect(jsonPath("$.raiting").value(DEFAULT_RAITING))
            .andExpect(jsonPath("$.review").value(DEFAULT_REVIEW.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.toString()))
            .andExpect(jsonPath("$.createdTime").value(sameInstant(DEFAULT_CREATED_TIME)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedTime").value(sameInstant(DEFAULT_UPDATED_TIME)));
    }
    @Test
    @Transactional
    public void getNonExistingUserReview() throws Exception {
        // Get the userReview
        restUserReviewMockMvc.perform(get("/api/user-reviews/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserReview() throws Exception {
        // Initialize the database
        userReviewRepository.saveAndFlush(userReview);

        int databaseSizeBeforeUpdate = userReviewRepository.findAll().size();

        // Update the userReview
        UserReview updatedUserReview = userReviewRepository.findById(userReview.getId()).get();
        // Disconnect from session so that the updates on updatedUserReview are not directly saved in db
        em.detach(updatedUserReview);
        updatedUserReview
            .raiting(UPDATED_RAITING)
            .review(UPDATED_REVIEW)
            .createBy(UPDATED_CREATE_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        UserReviewDTO userReviewDTO = userReviewMapper.toDto(updatedUserReview);

        restUserReviewMockMvc.perform(put("/api/user-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userReviewDTO)))
            .andExpect(status().isOk());

        // Validate the UserReview in the database
        List<UserReview> userReviewList = userReviewRepository.findAll();
        assertThat(userReviewList).hasSize(databaseSizeBeforeUpdate);
        UserReview testUserReview = userReviewList.get(userReviewList.size() - 1);
        assertThat(testUserReview.getRaiting()).isEqualTo(UPDATED_RAITING);
        assertThat(testUserReview.getReview()).isEqualTo(UPDATED_REVIEW);
        assertThat(testUserReview.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testUserReview.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testUserReview.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testUserReview.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);

        // Validate the UserReview in Elasticsearch
        verify(mockUserReviewSearchRepository, times(1)).save(testUserReview);
    }

    @Test
    @Transactional
    public void updateNonExistingUserReview() throws Exception {
        int databaseSizeBeforeUpdate = userReviewRepository.findAll().size();

        // Create the UserReview
        UserReviewDTO userReviewDTO = userReviewMapper.toDto(userReview);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserReviewMockMvc.perform(put("/api/user-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userReviewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserReview in the database
        List<UserReview> userReviewList = userReviewRepository.findAll();
        assertThat(userReviewList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UserReview in Elasticsearch
        verify(mockUserReviewSearchRepository, times(0)).save(userReview);
    }

    @Test
    @Transactional
    public void deleteUserReview() throws Exception {
        // Initialize the database
        userReviewRepository.saveAndFlush(userReview);

        int databaseSizeBeforeDelete = userReviewRepository.findAll().size();

        // Get the userReview
        restUserReviewMockMvc.perform(delete("/api/user-reviews/{id}", userReview.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserReview> userReviewList = userReviewRepository.findAll();
        assertThat(userReviewList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UserReview in Elasticsearch
        verify(mockUserReviewSearchRepository, times(1)).deleteById(userReview.getId());
    }

    @Test
    @Transactional
    public void searchUserReview() throws Exception {
        // Initialize the database
        userReviewRepository.saveAndFlush(userReview);
        when(mockUserReviewSearchRepository.search(queryStringQuery("id:" + userReview.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(userReview), PageRequest.of(0, 1), 1));
        // Search the userReview
        restUserReviewMockMvc.perform(get("/api/_search/user-reviews?query=id:" + userReview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userReview.getId().intValue())))
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
        TestUtil.equalsVerifier(UserReview.class);
        UserReview userReview1 = new UserReview();
        userReview1.setId(1L);
        UserReview userReview2 = new UserReview();
        userReview2.setId(userReview1.getId());
        assertThat(userReview1).isEqualTo(userReview2);
        userReview2.setId(2L);
        assertThat(userReview1).isNotEqualTo(userReview2);
        userReview1.setId(null);
        assertThat(userReview1).isNotEqualTo(userReview2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserReviewDTO.class);
        UserReviewDTO userReviewDTO1 = new UserReviewDTO();
        userReviewDTO1.setId(1L);
        UserReviewDTO userReviewDTO2 = new UserReviewDTO();
        assertThat(userReviewDTO1).isNotEqualTo(userReviewDTO2);
        userReviewDTO2.setId(userReviewDTO1.getId());
        assertThat(userReviewDTO1).isEqualTo(userReviewDTO2);
        userReviewDTO2.setId(2L);
        assertThat(userReviewDTO1).isNotEqualTo(userReviewDTO2);
        userReviewDTO1.setId(null);
        assertThat(userReviewDTO1).isNotEqualTo(userReviewDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userReviewMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userReviewMapper.fromId(null)).isNull();
    }
}
