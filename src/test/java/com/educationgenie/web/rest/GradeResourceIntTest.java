package com.educationgenie.web.rest;

import com.educationgenie.EducationGenieApp;

import com.educationgenie.domain.Grade;
import com.educationgenie.repository.GradeRepository;
import com.educationgenie.repository.search.GradeSearchRepository;
import com.educationgenie.service.GradeService;
import com.educationgenie.service.dto.GradeDTO;
import com.educationgenie.service.mapper.GradeMapper;
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
 * Test class for the GradeResource REST controller.
 *
 * @see GradeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EducationGenieApp.class)
public class GradeResourceIntTest {

    private static final Integer DEFAULT_GRADE = 1;
    private static final Integer UPDATED_GRADE = 2;

    @Autowired
    private GradeRepository gradeRepository;


    @Autowired
    private GradeMapper gradeMapper;
    

    @Autowired
    private GradeService gradeService;

    /**
     * This repository is mocked in the com.educationgenie.repository.search test package.
     *
     * @see com.educationgenie.repository.search.GradeSearchRepositoryMockConfiguration
     */
    @Autowired
    private GradeSearchRepository mockGradeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGradeMockMvc;

    private Grade grade;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GradeResource gradeResource = new GradeResource(gradeService);
        this.restGradeMockMvc = MockMvcBuilders.standaloneSetup(gradeResource)
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
    public static Grade createEntity(EntityManager em) {
        Grade grade = new Grade()
            .grade(DEFAULT_GRADE);
        return grade;
    }

    @Before
    public void initTest() {
        grade = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrade() throws Exception {
        int databaseSizeBeforeCreate = gradeRepository.findAll().size();

        // Create the Grade
        GradeDTO gradeDTO = gradeMapper.toDto(grade);
        restGradeMockMvc.perform(post("/api/grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gradeDTO)))
            .andExpect(status().isCreated());

        // Validate the Grade in the database
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeCreate + 1);
        Grade testGrade = gradeList.get(gradeList.size() - 1);
        assertThat(testGrade.getGrade()).isEqualTo(DEFAULT_GRADE);

        // Validate the Grade in Elasticsearch
        verify(mockGradeSearchRepository, times(1)).save(testGrade);
    }

    @Test
    @Transactional
    public void createGradeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gradeRepository.findAll().size();

        // Create the Grade with an existing ID
        grade.setId(1L);
        GradeDTO gradeDTO = gradeMapper.toDto(grade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGradeMockMvc.perform(post("/api/grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gradeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Grade in the database
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Grade in Elasticsearch
        verify(mockGradeSearchRepository, times(0)).save(grade);
    }

    @Test
    @Transactional
    public void getAllGrades() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get all the gradeList
        restGradeMockMvc.perform(get("/api/grades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grade.getId().intValue())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)));
    }
    

    @Test
    @Transactional
    public void getGrade() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get the grade
        restGradeMockMvc.perform(get("/api/grades/{id}", grade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(grade.getId().intValue()))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE));
    }
    @Test
    @Transactional
    public void getNonExistingGrade() throws Exception {
        // Get the grade
        restGradeMockMvc.perform(get("/api/grades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrade() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        int databaseSizeBeforeUpdate = gradeRepository.findAll().size();

        // Update the grade
        Grade updatedGrade = gradeRepository.findById(grade.getId()).get();
        // Disconnect from session so that the updates on updatedGrade are not directly saved in db
        em.detach(updatedGrade);
        updatedGrade
            .grade(UPDATED_GRADE);
        GradeDTO gradeDTO = gradeMapper.toDto(updatedGrade);

        restGradeMockMvc.perform(put("/api/grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gradeDTO)))
            .andExpect(status().isOk());

        // Validate the Grade in the database
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeUpdate);
        Grade testGrade = gradeList.get(gradeList.size() - 1);
        assertThat(testGrade.getGrade()).isEqualTo(UPDATED_GRADE);

        // Validate the Grade in Elasticsearch
        verify(mockGradeSearchRepository, times(1)).save(testGrade);
    }

    @Test
    @Transactional
    public void updateNonExistingGrade() throws Exception {
        int databaseSizeBeforeUpdate = gradeRepository.findAll().size();

        // Create the Grade
        GradeDTO gradeDTO = gradeMapper.toDto(grade);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGradeMockMvc.perform(put("/api/grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gradeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Grade in the database
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Grade in Elasticsearch
        verify(mockGradeSearchRepository, times(0)).save(grade);
    }

    @Test
    @Transactional
    public void deleteGrade() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        int databaseSizeBeforeDelete = gradeRepository.findAll().size();

        // Get the grade
        restGradeMockMvc.perform(delete("/api/grades/{id}", grade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Grade in Elasticsearch
        verify(mockGradeSearchRepository, times(1)).deleteById(grade.getId());
    }

    @Test
    @Transactional
    public void searchGrade() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);
        when(mockGradeSearchRepository.search(queryStringQuery("id:" + grade.getId())))
            .thenReturn(Collections.singletonList(grade));
        // Search the grade
        restGradeMockMvc.perform(get("/api/_search/grades?query=id:" + grade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grade.getId().intValue())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Grade.class);
        Grade grade1 = new Grade();
        grade1.setId(1L);
        Grade grade2 = new Grade();
        grade2.setId(grade1.getId());
        assertThat(grade1).isEqualTo(grade2);
        grade2.setId(2L);
        assertThat(grade1).isNotEqualTo(grade2);
        grade1.setId(null);
        assertThat(grade1).isNotEqualTo(grade2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GradeDTO.class);
        GradeDTO gradeDTO1 = new GradeDTO();
        gradeDTO1.setId(1L);
        GradeDTO gradeDTO2 = new GradeDTO();
        assertThat(gradeDTO1).isNotEqualTo(gradeDTO2);
        gradeDTO2.setId(gradeDTO1.getId());
        assertThat(gradeDTO1).isEqualTo(gradeDTO2);
        gradeDTO2.setId(2L);
        assertThat(gradeDTO1).isNotEqualTo(gradeDTO2);
        gradeDTO1.setId(null);
        assertThat(gradeDTO1).isNotEqualTo(gradeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(gradeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(gradeMapper.fromId(null)).isNull();
    }
}
