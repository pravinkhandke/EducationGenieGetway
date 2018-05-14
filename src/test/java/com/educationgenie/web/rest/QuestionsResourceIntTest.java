package com.educationgenie.web.rest;

import com.educationgenie.EducationGenieApp;

import com.educationgenie.domain.Questions;
import com.educationgenie.repository.QuestionsRepository;
import com.educationgenie.repository.search.QuestionsSearchRepository;
import com.educationgenie.service.QuestionsService;
import com.educationgenie.service.dto.QuestionsDTO;
import com.educationgenie.service.mapper.QuestionsMapper;
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
 * Test class for the QuestionsResource REST controller.
 *
 * @see QuestionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EducationGenieApp.class)
public class QuestionsResourceIntTest {

    private static final String DEFAULT_OPTIONS = "AAAAAAAAAA";
    private static final String UPDATED_OPTIONS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ANSWERS = false;
    private static final Boolean UPDATED_ANSWERS = true;

    private static final String DEFAULT_CREATE_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private QuestionsRepository questionsRepository;


    @Autowired
    private QuestionsMapper questionsMapper;
    

    @Autowired
    private QuestionsService questionsService;

    /**
     * This repository is mocked in the com.educationgenie.repository.search test package.
     *
     * @see com.educationgenie.repository.search.QuestionsSearchRepositoryMockConfiguration
     */
    @Autowired
    private QuestionsSearchRepository mockQuestionsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuestionsMockMvc;

    private Questions questions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuestionsResource questionsResource = new QuestionsResource(questionsService);
        this.restQuestionsMockMvc = MockMvcBuilders.standaloneSetup(questionsResource)
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
    public static Questions createEntity(EntityManager em) {
        Questions questions = new Questions()
            .options(DEFAULT_OPTIONS)
            .answers(DEFAULT_ANSWERS)
            .createBy(DEFAULT_CREATE_BY)
            .createdTime(DEFAULT_CREATED_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedTime(DEFAULT_UPDATED_TIME);
        return questions;
    }

    @Before
    public void initTest() {
        questions = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestions() throws Exception {
        int databaseSizeBeforeCreate = questionsRepository.findAll().size();

        // Create the Questions
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);
        restQuestionsMockMvc.perform(post("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isCreated());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeCreate + 1);
        Questions testQuestions = questionsList.get(questionsList.size() - 1);
        assertThat(testQuestions.getOptions()).isEqualTo(DEFAULT_OPTIONS);
        assertThat(testQuestions.isAnswers()).isEqualTo(DEFAULT_ANSWERS);
        assertThat(testQuestions.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testQuestions.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testQuestions.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testQuestions.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);

        // Validate the Questions in Elasticsearch
        verify(mockQuestionsSearchRepository, times(1)).save(testQuestions);
    }

    @Test
    @Transactional
    public void createQuestionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionsRepository.findAll().size();

        // Create the Questions with an existing ID
        questions.setId(1L);
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionsMockMvc.perform(post("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Questions in Elasticsearch
        verify(mockQuestionsSearchRepository, times(0)).save(questions);
    }

    @Test
    @Transactional
    public void checkCreateByIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsRepository.findAll().size();
        // set the field null
        questions.setCreateBy(null);

        // Create the Questions, which fails.
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        restQuestionsMockMvc.perform(post("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isBadRequest());

        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList
        restQuestionsMockMvc.perform(get("/api/questions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questions.getId().intValue())))
            .andExpect(jsonPath("$.[*].options").value(hasItem(DEFAULT_OPTIONS.toString())))
            .andExpect(jsonPath("$.[*].answers").value(hasItem(DEFAULT_ANSWERS.booleanValue())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(sameInstant(DEFAULT_CREATED_TIME))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(sameInstant(DEFAULT_UPDATED_TIME))));
    }
    

    @Test
    @Transactional
    public void getQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get the questions
        restQuestionsMockMvc.perform(get("/api/questions/{id}", questions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questions.getId().intValue()))
            .andExpect(jsonPath("$.options").value(DEFAULT_OPTIONS.toString()))
            .andExpect(jsonPath("$.answers").value(DEFAULT_ANSWERS.booleanValue()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.toString()))
            .andExpect(jsonPath("$.createdTime").value(sameInstant(DEFAULT_CREATED_TIME)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedTime").value(sameInstant(DEFAULT_UPDATED_TIME)));
    }
    @Test
    @Transactional
    public void getNonExistingQuestions() throws Exception {
        // Get the questions
        restQuestionsMockMvc.perform(get("/api/questions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();

        // Update the questions
        Questions updatedQuestions = questionsRepository.findById(questions.getId()).get();
        // Disconnect from session so that the updates on updatedQuestions are not directly saved in db
        em.detach(updatedQuestions);
        updatedQuestions
            .options(UPDATED_OPTIONS)
            .answers(UPDATED_ANSWERS)
            .createBy(UPDATED_CREATE_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        QuestionsDTO questionsDTO = questionsMapper.toDto(updatedQuestions);

        restQuestionsMockMvc.perform(put("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isOk());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
        Questions testQuestions = questionsList.get(questionsList.size() - 1);
        assertThat(testQuestions.getOptions()).isEqualTo(UPDATED_OPTIONS);
        assertThat(testQuestions.isAnswers()).isEqualTo(UPDATED_ANSWERS);
        assertThat(testQuestions.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testQuestions.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testQuestions.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testQuestions.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);

        // Validate the Questions in Elasticsearch
        verify(mockQuestionsSearchRepository, times(1)).save(testQuestions);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestions() throws Exception {
        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();

        // Create the Questions
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQuestionsMockMvc.perform(put("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Questions in Elasticsearch
        verify(mockQuestionsSearchRepository, times(0)).save(questions);
    }

    @Test
    @Transactional
    public void deleteQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        int databaseSizeBeforeDelete = questionsRepository.findAll().size();

        // Get the questions
        restQuestionsMockMvc.perform(delete("/api/questions/{id}", questions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Questions in Elasticsearch
        verify(mockQuestionsSearchRepository, times(1)).deleteById(questions.getId());
    }

    @Test
    @Transactional
    public void searchQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);
        when(mockQuestionsSearchRepository.search(queryStringQuery("id:" + questions.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(questions), PageRequest.of(0, 1), 1));
        // Search the questions
        restQuestionsMockMvc.perform(get("/api/_search/questions?query=id:" + questions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questions.getId().intValue())))
            .andExpect(jsonPath("$.[*].options").value(hasItem(DEFAULT_OPTIONS.toString())))
            .andExpect(jsonPath("$.[*].answers").value(hasItem(DEFAULT_ANSWERS.booleanValue())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(sameInstant(DEFAULT_CREATED_TIME))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(sameInstant(DEFAULT_UPDATED_TIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Questions.class);
        Questions questions1 = new Questions();
        questions1.setId(1L);
        Questions questions2 = new Questions();
        questions2.setId(questions1.getId());
        assertThat(questions1).isEqualTo(questions2);
        questions2.setId(2L);
        assertThat(questions1).isNotEqualTo(questions2);
        questions1.setId(null);
        assertThat(questions1).isNotEqualTo(questions2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionsDTO.class);
        QuestionsDTO questionsDTO1 = new QuestionsDTO();
        questionsDTO1.setId(1L);
        QuestionsDTO questionsDTO2 = new QuestionsDTO();
        assertThat(questionsDTO1).isNotEqualTo(questionsDTO2);
        questionsDTO2.setId(questionsDTO1.getId());
        assertThat(questionsDTO1).isEqualTo(questionsDTO2);
        questionsDTO2.setId(2L);
        assertThat(questionsDTO1).isNotEqualTo(questionsDTO2);
        questionsDTO1.setId(null);
        assertThat(questionsDTO1).isNotEqualTo(questionsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(questionsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(questionsMapper.fromId(null)).isNull();
    }
}
