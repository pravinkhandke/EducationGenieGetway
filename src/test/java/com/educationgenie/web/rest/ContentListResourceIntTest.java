package com.educationgenie.web.rest;

import com.educationgenie.EducationGenieApp;

import com.educationgenie.domain.ContentList;
import com.educationgenie.repository.ContentListRepository;
import com.educationgenie.repository.search.ContentListSearchRepository;
import com.educationgenie.service.ContentListService;
import com.educationgenie.service.dto.ContentListDTO;
import com.educationgenie.service.mapper.ContentListMapper;
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
 * Test class for the ContentListResource REST controller.
 *
 * @see ContentListResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EducationGenieApp.class)
public class ContentListResourceIntTest {

    private static final String DEFAULT_LIST_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_LIST_TITLE = "BBBBBBBBBB";

    @Autowired
    private ContentListRepository contentListRepository;


    @Autowired
    private ContentListMapper contentListMapper;
    

    @Autowired
    private ContentListService contentListService;

    /**
     * This repository is mocked in the com.educationgenie.repository.search test package.
     *
     * @see com.educationgenie.repository.search.ContentListSearchRepositoryMockConfiguration
     */
    @Autowired
    private ContentListSearchRepository mockContentListSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContentListMockMvc;

    private ContentList contentList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContentListResource contentListResource = new ContentListResource(contentListService);
        this.restContentListMockMvc = MockMvcBuilders.standaloneSetup(contentListResource)
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
    public static ContentList createEntity(EntityManager em) {
        ContentList contentList = new ContentList()
            .listTitle(DEFAULT_LIST_TITLE);
        return contentList;
    }

    @Before
    public void initTest() {
        contentList = createEntity(em);
    }

    @Test
    @Transactional
    public void createContentList() throws Exception {
        int databaseSizeBeforeCreate = contentListRepository.findAll().size();

        // Create the ContentList
        ContentListDTO contentListDTO = contentListMapper.toDto(contentList);
        restContentListMockMvc.perform(post("/api/content-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentListDTO)))
            .andExpect(status().isCreated());

        // Validate the ContentList in the database
        List<ContentList> contentListList = contentListRepository.findAll();
        assertThat(contentListList).hasSize(databaseSizeBeforeCreate + 1);
        ContentList testContentList = contentListList.get(contentListList.size() - 1);
        assertThat(testContentList.getListTitle()).isEqualTo(DEFAULT_LIST_TITLE);

        // Validate the ContentList in Elasticsearch
        verify(mockContentListSearchRepository, times(1)).save(testContentList);
    }

    @Test
    @Transactional
    public void createContentListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contentListRepository.findAll().size();

        // Create the ContentList with an existing ID
        contentList.setId(1L);
        ContentListDTO contentListDTO = contentListMapper.toDto(contentList);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentListMockMvc.perform(post("/api/content-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentListDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContentList in the database
        List<ContentList> contentListList = contentListRepository.findAll();
        assertThat(contentListList).hasSize(databaseSizeBeforeCreate);

        // Validate the ContentList in Elasticsearch
        verify(mockContentListSearchRepository, times(0)).save(contentList);
    }

    @Test
    @Transactional
    public void checkListTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentListRepository.findAll().size();
        // set the field null
        contentList.setListTitle(null);

        // Create the ContentList, which fails.
        ContentListDTO contentListDTO = contentListMapper.toDto(contentList);

        restContentListMockMvc.perform(post("/api/content-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentListDTO)))
            .andExpect(status().isBadRequest());

        List<ContentList> contentListList = contentListRepository.findAll();
        assertThat(contentListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContentLists() throws Exception {
        // Initialize the database
        contentListRepository.saveAndFlush(contentList);

        // Get all the contentListList
        restContentListMockMvc.perform(get("/api/content-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contentList.getId().intValue())))
            .andExpect(jsonPath("$.[*].listTitle").value(hasItem(DEFAULT_LIST_TITLE.toString())));
    }
    

    @Test
    @Transactional
    public void getContentList() throws Exception {
        // Initialize the database
        contentListRepository.saveAndFlush(contentList);

        // Get the contentList
        restContentListMockMvc.perform(get("/api/content-lists/{id}", contentList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contentList.getId().intValue()))
            .andExpect(jsonPath("$.listTitle").value(DEFAULT_LIST_TITLE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingContentList() throws Exception {
        // Get the contentList
        restContentListMockMvc.perform(get("/api/content-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContentList() throws Exception {
        // Initialize the database
        contentListRepository.saveAndFlush(contentList);

        int databaseSizeBeforeUpdate = contentListRepository.findAll().size();

        // Update the contentList
        ContentList updatedContentList = contentListRepository.findById(contentList.getId()).get();
        // Disconnect from session so that the updates on updatedContentList are not directly saved in db
        em.detach(updatedContentList);
        updatedContentList
            .listTitle(UPDATED_LIST_TITLE);
        ContentListDTO contentListDTO = contentListMapper.toDto(updatedContentList);

        restContentListMockMvc.perform(put("/api/content-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentListDTO)))
            .andExpect(status().isOk());

        // Validate the ContentList in the database
        List<ContentList> contentListList = contentListRepository.findAll();
        assertThat(contentListList).hasSize(databaseSizeBeforeUpdate);
        ContentList testContentList = contentListList.get(contentListList.size() - 1);
        assertThat(testContentList.getListTitle()).isEqualTo(UPDATED_LIST_TITLE);

        // Validate the ContentList in Elasticsearch
        verify(mockContentListSearchRepository, times(1)).save(testContentList);
    }

    @Test
    @Transactional
    public void updateNonExistingContentList() throws Exception {
        int databaseSizeBeforeUpdate = contentListRepository.findAll().size();

        // Create the ContentList
        ContentListDTO contentListDTO = contentListMapper.toDto(contentList);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContentListMockMvc.perform(put("/api/content-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentListDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContentList in the database
        List<ContentList> contentListList = contentListRepository.findAll();
        assertThat(contentListList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ContentList in Elasticsearch
        verify(mockContentListSearchRepository, times(0)).save(contentList);
    }

    @Test
    @Transactional
    public void deleteContentList() throws Exception {
        // Initialize the database
        contentListRepository.saveAndFlush(contentList);

        int databaseSizeBeforeDelete = contentListRepository.findAll().size();

        // Get the contentList
        restContentListMockMvc.perform(delete("/api/content-lists/{id}", contentList.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContentList> contentListList = contentListRepository.findAll();
        assertThat(contentListList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ContentList in Elasticsearch
        verify(mockContentListSearchRepository, times(1)).deleteById(contentList.getId());
    }

    @Test
    @Transactional
    public void searchContentList() throws Exception {
        // Initialize the database
        contentListRepository.saveAndFlush(contentList);
        when(mockContentListSearchRepository.search(queryStringQuery("id:" + contentList.getId())))
            .thenReturn(Collections.singletonList(contentList));
        // Search the contentList
        restContentListMockMvc.perform(get("/api/_search/content-lists?query=id:" + contentList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contentList.getId().intValue())))
            .andExpect(jsonPath("$.[*].listTitle").value(hasItem(DEFAULT_LIST_TITLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentList.class);
        ContentList contentList1 = new ContentList();
        contentList1.setId(1L);
        ContentList contentList2 = new ContentList();
        contentList2.setId(contentList1.getId());
        assertThat(contentList1).isEqualTo(contentList2);
        contentList2.setId(2L);
        assertThat(contentList1).isNotEqualTo(contentList2);
        contentList1.setId(null);
        assertThat(contentList1).isNotEqualTo(contentList2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentListDTO.class);
        ContentListDTO contentListDTO1 = new ContentListDTO();
        contentListDTO1.setId(1L);
        ContentListDTO contentListDTO2 = new ContentListDTO();
        assertThat(contentListDTO1).isNotEqualTo(contentListDTO2);
        contentListDTO2.setId(contentListDTO1.getId());
        assertThat(contentListDTO1).isEqualTo(contentListDTO2);
        contentListDTO2.setId(2L);
        assertThat(contentListDTO1).isNotEqualTo(contentListDTO2);
        contentListDTO1.setId(null);
        assertThat(contentListDTO1).isNotEqualTo(contentListDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contentListMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contentListMapper.fromId(null)).isNull();
    }
}
