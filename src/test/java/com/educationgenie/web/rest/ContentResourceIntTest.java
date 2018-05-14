package com.educationgenie.web.rest;

import com.educationgenie.EducationGenieApp;

import com.educationgenie.domain.Content;
import com.educationgenie.repository.ContentRepository;
import com.educationgenie.repository.search.ContentSearchRepository;
import com.educationgenie.service.ContentService;
import com.educationgenie.service.dto.ContentDTO;
import com.educationgenie.service.mapper.ContentMapper;
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

import com.educationgenie.domain.enumeration.Language;
import com.educationgenie.domain.enumeration.State;
/**
 * Test class for the ContentResource REST controller.
 *
 * @see ContentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EducationGenieApp.class)
public class ContentResourceIntTest {

    private static final String DEFAULT_CONTENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_MEDIA_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MEDIA_TYPE = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_LANGUAGE = Language.HINDI;

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
    private ContentRepository contentRepository;
    @Mock
    private ContentRepository contentRepositoryMock;

    @Autowired
    private ContentMapper contentMapper;
    
    @Mock
    private ContentService contentServiceMock;

    @Autowired
    private ContentService contentService;

    /**
     * This repository is mocked in the com.educationgenie.repository.search test package.
     *
     * @see com.educationgenie.repository.search.ContentSearchRepositoryMockConfiguration
     */
    @Autowired
    private ContentSearchRepository mockContentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContentMockMvc;

    private Content content;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContentResource contentResource = new ContentResource(contentService);
        this.restContentMockMvc = MockMvcBuilders.standaloneSetup(contentResource)
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
    public static Content createEntity(EntityManager em) {
        Content content = new Content()
            .contentType(DEFAULT_CONTENT_TYPE)
            .url(DEFAULT_URL)
            .mediaType(DEFAULT_MEDIA_TYPE)
            .language(DEFAULT_LANGUAGE)
            .state(DEFAULT_STATE)
            .createBy(DEFAULT_CREATE_BY)
            .createdTime(DEFAULT_CREATED_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedTime(DEFAULT_UPDATED_TIME);
        return content;
    }

    @Before
    public void initTest() {
        content = createEntity(em);
    }

    @Test
    @Transactional
    public void createContent() throws Exception {
        int databaseSizeBeforeCreate = contentRepository.findAll().size();

        // Create the Content
        ContentDTO contentDTO = contentMapper.toDto(content);
        restContentMockMvc.perform(post("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isCreated());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeCreate + 1);
        Content testContent = contentList.get(contentList.size() - 1);
        assertThat(testContent.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testContent.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testContent.getMediaType()).isEqualTo(DEFAULT_MEDIA_TYPE);
        assertThat(testContent.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testContent.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testContent.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testContent.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testContent.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testContent.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);

        // Validate the Content in Elasticsearch
        verify(mockContentSearchRepository, times(1)).save(testContent);
    }

    @Test
    @Transactional
    public void createContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contentRepository.findAll().size();

        // Create the Content with an existing ID
        content.setId(1L);
        ContentDTO contentDTO = contentMapper.toDto(content);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentMockMvc.perform(post("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeCreate);

        // Validate the Content in Elasticsearch
        verify(mockContentSearchRepository, times(0)).save(content);
    }

    @Test
    @Transactional
    public void checkContentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentRepository.findAll().size();
        // set the field null
        content.setContentType(null);

        // Create the Content, which fails.
        ContentDTO contentDTO = contentMapper.toDto(content);

        restContentMockMvc.perform(post("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isBadRequest());

        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentRepository.findAll().size();
        // set the field null
        content.setUrl(null);

        // Create the Content, which fails.
        ContentDTO contentDTO = contentMapper.toDto(content);

        restContentMockMvc.perform(post("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isBadRequest());

        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMediaTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentRepository.findAll().size();
        // set the field null
        content.setMediaType(null);

        // Create the Content, which fails.
        ContentDTO contentDTO = contentMapper.toDto(content);

        restContentMockMvc.perform(post("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isBadRequest());

        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentRepository.findAll().size();
        // set the field null
        content.setState(null);

        // Create the Content, which fails.
        ContentDTO contentDTO = contentMapper.toDto(content);

        restContentMockMvc.perform(post("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isBadRequest());

        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateByIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentRepository.findAll().size();
        // set the field null
        content.setCreateBy(null);

        // Create the Content, which fails.
        ContentDTO contentDTO = contentMapper.toDto(content);

        restContentMockMvc.perform(post("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isBadRequest());

        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContents() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        // Get all the contentList
        restContentMockMvc.perform(get("/api/contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(content.getId().intValue())))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].mediaType").value(hasItem(DEFAULT_MEDIA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(sameInstant(DEFAULT_CREATED_TIME))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(sameInstant(DEFAULT_UPDATED_TIME))));
    }
    
    public void getAllContentsWithEagerRelationshipsIsEnabled() throws Exception {
        ContentResource contentResource = new ContentResource(contentServiceMock);
        when(contentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restContentMockMvc = MockMvcBuilders.standaloneSetup(contentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restContentMockMvc.perform(get("/api/contents?eagerload=true"))
        .andExpect(status().isOk());

        verify(contentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllContentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ContentResource contentResource = new ContentResource(contentServiceMock);
            when(contentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restContentMockMvc = MockMvcBuilders.standaloneSetup(contentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restContentMockMvc.perform(get("/api/contents?eagerload=true"))
        .andExpect(status().isOk());

            verify(contentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getContent() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        // Get the content
        restContentMockMvc.perform(get("/api/contents/{id}", content.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(content.getId().intValue()))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.mediaType").value(DEFAULT_MEDIA_TYPE.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.toString()))
            .andExpect(jsonPath("$.createdTime").value(sameInstant(DEFAULT_CREATED_TIME)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedTime").value(sameInstant(DEFAULT_UPDATED_TIME)));
    }
    @Test
    @Transactional
    public void getNonExistingContent() throws Exception {
        // Get the content
        restContentMockMvc.perform(get("/api/contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContent() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        int databaseSizeBeforeUpdate = contentRepository.findAll().size();

        // Update the content
        Content updatedContent = contentRepository.findById(content.getId()).get();
        // Disconnect from session so that the updates on updatedContent are not directly saved in db
        em.detach(updatedContent);
        updatedContent
            .contentType(UPDATED_CONTENT_TYPE)
            .url(UPDATED_URL)
            .mediaType(UPDATED_MEDIA_TYPE)
            .language(UPDATED_LANGUAGE)
            .state(UPDATED_STATE)
            .createBy(UPDATED_CREATE_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        ContentDTO contentDTO = contentMapper.toDto(updatedContent);

        restContentMockMvc.perform(put("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isOk());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeUpdate);
        Content testContent = contentList.get(contentList.size() - 1);
        assertThat(testContent.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testContent.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testContent.getMediaType()).isEqualTo(UPDATED_MEDIA_TYPE);
        assertThat(testContent.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testContent.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testContent.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testContent.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testContent.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testContent.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);

        // Validate the Content in Elasticsearch
        verify(mockContentSearchRepository, times(1)).save(testContent);
    }

    @Test
    @Transactional
    public void updateNonExistingContent() throws Exception {
        int databaseSizeBeforeUpdate = contentRepository.findAll().size();

        // Create the Content
        ContentDTO contentDTO = contentMapper.toDto(content);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContentMockMvc.perform(put("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Content in Elasticsearch
        verify(mockContentSearchRepository, times(0)).save(content);
    }

    @Test
    @Transactional
    public void deleteContent() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        int databaseSizeBeforeDelete = contentRepository.findAll().size();

        // Get the content
        restContentMockMvc.perform(delete("/api/contents/{id}", content.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Content in Elasticsearch
        verify(mockContentSearchRepository, times(1)).deleteById(content.getId());
    }

    @Test
    @Transactional
    public void searchContent() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);
        when(mockContentSearchRepository.search(queryStringQuery("id:" + content.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(content), PageRequest.of(0, 1), 1));
        // Search the content
        restContentMockMvc.perform(get("/api/_search/contents?query=id:" + content.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(content.getId().intValue())))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].mediaType").value(hasItem(DEFAULT_MEDIA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(sameInstant(DEFAULT_CREATED_TIME))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(sameInstant(DEFAULT_UPDATED_TIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Content.class);
        Content content1 = new Content();
        content1.setId(1L);
        Content content2 = new Content();
        content2.setId(content1.getId());
        assertThat(content1).isEqualTo(content2);
        content2.setId(2L);
        assertThat(content1).isNotEqualTo(content2);
        content1.setId(null);
        assertThat(content1).isNotEqualTo(content2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentDTO.class);
        ContentDTO contentDTO1 = new ContentDTO();
        contentDTO1.setId(1L);
        ContentDTO contentDTO2 = new ContentDTO();
        assertThat(contentDTO1).isNotEqualTo(contentDTO2);
        contentDTO2.setId(contentDTO1.getId());
        assertThat(contentDTO1).isEqualTo(contentDTO2);
        contentDTO2.setId(2L);
        assertThat(contentDTO1).isNotEqualTo(contentDTO2);
        contentDTO1.setId(null);
        assertThat(contentDTO1).isNotEqualTo(contentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contentMapper.fromId(null)).isNull();
    }
}
