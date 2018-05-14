package com.educationgenie.web.rest;

import com.educationgenie.EducationGenieApp;

import com.educationgenie.domain.QuestionBank;
import com.educationgenie.repository.QuestionBankRepository;
import com.educationgenie.repository.search.QuestionBankSearchRepository;
import com.educationgenie.service.QuestionBankService;
import com.educationgenie.service.dto.QuestionBankDTO;
import com.educationgenie.service.mapper.QuestionBankMapper;
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

import com.educationgenie.domain.enumeration.State;
/**
 * Test class for the QuestionBankResource REST controller.
 *
 * @see QuestionBankResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EducationGenieApp.class)
public class QuestionBankResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final State DEFAULT_STATE = State.ACTIVE;
    private static final State UPDATED_STATE = State.INACTIVE;

    private static final String DEFAULT_CREATE_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private QuestionBankRepository questionBankRepository;


    @Autowired
    private QuestionBankMapper questionBankMapper;
    

    @Autowired
    private QuestionBankService questionBankService;

    /**
     * This repository is mocked in the com.educationgenie.repository.search test package.
     *
     * @see com.educationgenie.repository.search.QuestionBankSearchRepositoryMockConfiguration
     */
    @Autowired
    private QuestionBankSearchRepository mockQuestionBankSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuestionBankMockMvc;

    private QuestionBank questionBank;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuestionBankResource questionBankResource = new QuestionBankResource(questionBankService);
        this.restQuestionBankMockMvc = MockMvcBuilders.standaloneSetup(questionBankResource)
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
    public static QuestionBank createEntity(EntityManager em) {
        QuestionBank questionBank = new QuestionBank()
            .title(DEFAULT_TITLE)
            .duration(DEFAULT_DURATION)
            .state(DEFAULT_STATE)
            .createBy(DEFAULT_CREATE_BY)
            .createdTime(DEFAULT_CREATED_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedTime(DEFAULT_UPDATED_TIME);
        return questionBank;
    }

    @Before
    public void initTest() {
        questionBank = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionBank() throws Exception {
        int databaseSizeBeforeCreate = questionBankRepository.findAll().size();

        // Create the QuestionBank
        QuestionBankDTO questionBankDTO = questionBankMapper.toDto(questionBank);
        restQuestionBankMockMvc.perform(post("/api/question-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionBankDTO)))
            .andExpect(status().isCreated());

        // Validate the QuestionBank in the database
        List<QuestionBank> questionBankList = questionBankRepository.findAll();
        assertThat(questionBankList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionBank testQuestionBank = questionBankList.get(questionBankList.size() - 1);
        assertThat(testQuestionBank.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testQuestionBank.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testQuestionBank.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testQuestionBank.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testQuestionBank.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testQuestionBank.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testQuestionBank.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);

        // Validate the QuestionBank in Elasticsearch
        verify(mockQuestionBankSearchRepository, times(1)).save(testQuestionBank);
    }

    @Test
    @Transactional
    public void createQuestionBankWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionBankRepository.findAll().size();

        // Create the QuestionBank with an existing ID
        questionBank.setId(1L);
        QuestionBankDTO questionBankDTO = questionBankMapper.toDto(questionBank);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionBankMockMvc.perform(post("/api/question-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionBankDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionBank in the database
        List<QuestionBank> questionBankList = questionBankRepository.findAll();
        assertThat(questionBankList).hasSize(databaseSizeBeforeCreate);

        // Validate the QuestionBank in Elasticsearch
        verify(mockQuestionBankSearchRepository, times(0)).save(questionBank);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionBankRepository.findAll().size();
        // set the field null
        questionBank.setTitle(null);

        // Create the QuestionBank, which fails.
        QuestionBankDTO questionBankDTO = questionBankMapper.toDto(questionBank);

        restQuestionBankMockMvc.perform(post("/api/question-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionBankDTO)))
            .andExpect(status().isBadRequest());

        List<QuestionBank> questionBankList = questionBankRepository.findAll();
        assertThat(questionBankList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionBankRepository.findAll().size();
        // set the field null
        questionBank.setState(null);

        // Create the QuestionBank, which fails.
        QuestionBankDTO questionBankDTO = questionBankMapper.toDto(questionBank);

        restQuestionBankMockMvc.perform(post("/api/question-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionBankDTO)))
            .andExpect(status().isBadRequest());

        List<QuestionBank> questionBankList = questionBankRepository.findAll();
        assertThat(questionBankList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateByIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionBankRepository.findAll().size();
        // set the field null
        questionBank.setCreateBy(null);

        // Create the QuestionBank, which fails.
        QuestionBankDTO questionBankDTO = questionBankMapper.toDto(questionBank);

        restQuestionBankMockMvc.perform(post("/api/question-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionBankDTO)))
            .andExpect(status().isBadRequest());

        List<QuestionBank> questionBankList = questionBankRepository.findAll();
        assertThat(questionBankList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuestionBanks() throws Exception {
        // Initialize the database
        questionBankRepository.saveAndFlush(questionBank);

        // Get all the questionBankList
        restQuestionBankMockMvc.perform(get("/api/question-banks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionBank.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(sameInstant(DEFAULT_CREATED_TIME))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(sameInstant(DEFAULT_UPDATED_TIME))));
    }
    

    @Test
    @Transactional
    public void getQuestionBank() throws Exception {
        // Initialize the database
        questionBankRepository.saveAndFlush(questionBank);

        // Get the questionBank
        restQuestionBankMockMvc.perform(get("/api/question-banks/{id}", questionBank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questionBank.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.toString()))
            .andExpect(jsonPath("$.createdTime").value(sameInstant(DEFAULT_CREATED_TIME)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedTime").value(sameInstant(DEFAULT_UPDATED_TIME)));
    }
    @Test
    @Transactional
    public void getNonExistingQuestionBank() throws Exception {
        // Get the questionBank
        restQuestionBankMockMvc.perform(get("/api/question-banks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionBank() throws Exception {
        // Initialize the database
        questionBankRepository.saveAndFlush(questionBank);

        int databaseSizeBeforeUpdate = questionBankRepository.findAll().size();

        // Update the questionBank
        QuestionBank updatedQuestionBank = questionBankRepository.findById(questionBank.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionBank are not directly saved in db
        em.detach(updatedQuestionBank);
        updatedQuestionBank
            .title(UPDATED_TITLE)
            .duration(UPDATED_DURATION)
            .state(UPDATED_STATE)
            .createBy(UPDATED_CREATE_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        QuestionBankDTO questionBankDTO = questionBankMapper.toDto(updatedQuestionBank);

        restQuestionBankMockMvc.perform(put("/api/question-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionBankDTO)))
            .andExpect(status().isOk());

        // Validate the QuestionBank in the database
        List<QuestionBank> questionBankList = questionBankRepository.findAll();
        assertThat(questionBankList).hasSize(databaseSizeBeforeUpdate);
        QuestionBank testQuestionBank = questionBankList.get(questionBankList.size() - 1);
        assertThat(testQuestionBank.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testQuestionBank.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testQuestionBank.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testQuestionBank.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testQuestionBank.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testQuestionBank.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testQuestionBank.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);

        // Validate the QuestionBank in Elasticsearch
        verify(mockQuestionBankSearchRepository, times(1)).save(testQuestionBank);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionBank() throws Exception {
        int databaseSizeBeforeUpdate = questionBankRepository.findAll().size();

        // Create the QuestionBank
        QuestionBankDTO questionBankDTO = questionBankMapper.toDto(questionBank);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQuestionBankMockMvc.perform(put("/api/question-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionBankDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionBank in the database
        List<QuestionBank> questionBankList = questionBankRepository.findAll();
        assertThat(questionBankList).hasSize(databaseSizeBeforeUpdate);

        // Validate the QuestionBank in Elasticsearch
        verify(mockQuestionBankSearchRepository, times(0)).save(questionBank);
    }

    @Test
    @Transactional
    public void deleteQuestionBank() throws Exception {
        // Initialize the database
        questionBankRepository.saveAndFlush(questionBank);

        int databaseSizeBeforeDelete = questionBankRepository.findAll().size();

        // Get the questionBank
        restQuestionBankMockMvc.perform(delete("/api/question-banks/{id}", questionBank.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<QuestionBank> questionBankList = questionBankRepository.findAll();
        assertThat(questionBankList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the QuestionBank in Elasticsearch
        verify(mockQuestionBankSearchRepository, times(1)).deleteById(questionBank.getId());
    }

    @Test
    @Transactional
    public void searchQuestionBank() throws Exception {
        // Initialize the database
        questionBankRepository.saveAndFlush(questionBank);
        when(mockQuestionBankSearchRepository.search(queryStringQuery("id:" + questionBank.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(questionBank), PageRequest.of(0, 1), 1));
        // Search the questionBank
        restQuestionBankMockMvc.perform(get("/api/_search/question-banks?query=id:" + questionBank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionBank.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(sameInstant(DEFAULT_CREATED_TIME))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(sameInstant(DEFAULT_UPDATED_TIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionBank.class);
        QuestionBank questionBank1 = new QuestionBank();
        questionBank1.setId(1L);
        QuestionBank questionBank2 = new QuestionBank();
        questionBank2.setId(questionBank1.getId());
        assertThat(questionBank1).isEqualTo(questionBank2);
        questionBank2.setId(2L);
        assertThat(questionBank1).isNotEqualTo(questionBank2);
        questionBank1.setId(null);
        assertThat(questionBank1).isNotEqualTo(questionBank2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionBankDTO.class);
        QuestionBankDTO questionBankDTO1 = new QuestionBankDTO();
        questionBankDTO1.setId(1L);
        QuestionBankDTO questionBankDTO2 = new QuestionBankDTO();
        assertThat(questionBankDTO1).isNotEqualTo(questionBankDTO2);
        questionBankDTO2.setId(questionBankDTO1.getId());
        assertThat(questionBankDTO1).isEqualTo(questionBankDTO2);
        questionBankDTO2.setId(2L);
        assertThat(questionBankDTO1).isNotEqualTo(questionBankDTO2);
        questionBankDTO1.setId(null);
        assertThat(questionBankDTO1).isNotEqualTo(questionBankDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(questionBankMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(questionBankMapper.fromId(null)).isNull();
    }
}
