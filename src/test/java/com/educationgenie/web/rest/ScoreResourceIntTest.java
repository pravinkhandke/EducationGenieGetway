package com.educationgenie.web.rest;

import com.educationgenie.EducationGenieApp;

import com.educationgenie.domain.Score;
import com.educationgenie.repository.ScoreRepository;
import com.educationgenie.repository.search.ScoreSearchRepository;
import com.educationgenie.service.ScoreService;
import com.educationgenie.service.dto.ScoreDTO;
import com.educationgenie.service.mapper.ScoreMapper;
import com.educationgenie.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import static com.educationgenie.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ScoreResource REST controller.
 *
 * @see ScoreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EducationGenieApp.class)
public class ScoreResourceIntTest {

    private static final Integer DEFAULT_SCORE = 1;
    private static final Integer UPDATED_SCORE = 2;

    @Autowired
    private ScoreRepository scoreRepository;


    @Autowired
    private ScoreMapper scoreMapper;
    

    @Autowired
    private ScoreService scoreService;

    /**
     * This repository is mocked in the com.educationgenie.repository.search test package.
     *
     * @see com.educationgenie.repository.search.ScoreSearchRepositoryMockConfiguration
     */
    @Autowired
    private ScoreSearchRepository mockScoreSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restScoreMockMvc;

    private Score score;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ScoreResource scoreResource = new ScoreResource(scoreService);
        this.restScoreMockMvc = MockMvcBuilders.standaloneSetup(scoreResource)
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
    public static Score createEntity(EntityManager em) {
        Score score = new Score()
            .score(DEFAULT_SCORE);
        return score;
    }

    @Before
    public void initTest() {
        score = createEntity(em);
    }

    @Test
    @Transactional
    public void createScore() throws Exception {
        int databaseSizeBeforeCreate = scoreRepository.findAll().size();

        // Create the Score
        ScoreDTO scoreDTO = scoreMapper.toDto(score);
        restScoreMockMvc.perform(post("/api/scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scoreDTO)))
            .andExpect(status().isCreated());

        // Validate the Score in the database
        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeCreate + 1);
        Score testScore = scoreList.get(scoreList.size() - 1);
        assertThat(testScore.getScore()).isEqualTo(DEFAULT_SCORE);

        // Validate the Score in Elasticsearch
        verify(mockScoreSearchRepository, times(1)).save(testScore);
    }

    @Test
    @Transactional
    public void createScoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scoreRepository.findAll().size();

        // Create the Score with an existing ID
        score.setId(1L);
        ScoreDTO scoreDTO = scoreMapper.toDto(score);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScoreMockMvc.perform(post("/api/scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scoreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Score in the database
        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeCreate);

        // Validate the Score in Elasticsearch
        verify(mockScoreSearchRepository, times(0)).save(score);
    }

    @Test
    @Transactional
    public void getAllScores() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get all the scoreList
        restScoreMockMvc.perform(get("/api/scores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(score.getId().intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)));
    }
    

    @Test
    @Transactional
    public void getScore() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get the score
        restScoreMockMvc.perform(get("/api/scores/{id}", score.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(score.getId().intValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE));
    }
    @Test
    @Transactional
    public void getNonExistingScore() throws Exception {
        // Get the score
        restScoreMockMvc.perform(get("/api/scores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScore() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        int databaseSizeBeforeUpdate = scoreRepository.findAll().size();

        // Update the score
        Score updatedScore = scoreRepository.findById(score.getId()).get();
        // Disconnect from session so that the updates on updatedScore are not directly saved in db
        em.detach(updatedScore);
        updatedScore
            .score(UPDATED_SCORE);
        ScoreDTO scoreDTO = scoreMapper.toDto(updatedScore);

        restScoreMockMvc.perform(put("/api/scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scoreDTO)))
            .andExpect(status().isOk());

        // Validate the Score in the database
        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeUpdate);
        Score testScore = scoreList.get(scoreList.size() - 1);
        assertThat(testScore.getScore()).isEqualTo(UPDATED_SCORE);

        // Validate the Score in Elasticsearch
        verify(mockScoreSearchRepository, times(1)).save(testScore);
    }

    @Test
    @Transactional
    public void updateNonExistingScore() throws Exception {
        int databaseSizeBeforeUpdate = scoreRepository.findAll().size();

        // Create the Score
        ScoreDTO scoreDTO = scoreMapper.toDto(score);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restScoreMockMvc.perform(put("/api/scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scoreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Score in the database
        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Score in Elasticsearch
        verify(mockScoreSearchRepository, times(0)).save(score);
    }

    @Test
    @Transactional
    public void deleteScore() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        int databaseSizeBeforeDelete = scoreRepository.findAll().size();

        // Get the score
        restScoreMockMvc.perform(delete("/api/scores/{id}", score.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Score in Elasticsearch
        verify(mockScoreSearchRepository, times(1)).deleteById(score.getId());
    }

    @Test
    @Transactional
    public void searchScore() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);
        when(mockScoreSearchRepository.search(queryStringQuery("id:" + score.getId())))
            .thenReturn(Collections.singletonList(score));
        // Search the score
        restScoreMockMvc.perform(get("/api/_search/scores?query=id:" + score.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(score.getId().intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Score.class);
        Score score1 = new Score();
        score1.setId(1L);
        Score score2 = new Score();
        score2.setId(score1.getId());
        assertThat(score1).isEqualTo(score2);
        score2.setId(2L);
        assertThat(score1).isNotEqualTo(score2);
        score1.setId(null);
        assertThat(score1).isNotEqualTo(score2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScoreDTO.class);
        ScoreDTO scoreDTO1 = new ScoreDTO();
        scoreDTO1.setId(1L);
        ScoreDTO scoreDTO2 = new ScoreDTO();
        assertThat(scoreDTO1).isNotEqualTo(scoreDTO2);
        scoreDTO2.setId(scoreDTO1.getId());
        assertThat(scoreDTO1).isEqualTo(scoreDTO2);
        scoreDTO2.setId(2L);
        assertThat(scoreDTO1).isNotEqualTo(scoreDTO2);
        scoreDTO1.setId(null);
        assertThat(scoreDTO1).isNotEqualTo(scoreDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(scoreMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(scoreMapper.fromId(null)).isNull();
    }
}
