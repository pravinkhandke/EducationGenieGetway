package com.educationgenie.web.rest;

import com.educationgenie.EducationGenieApp;

import com.educationgenie.domain.Topic;
import com.educationgenie.repository.TopicRepository;
import com.educationgenie.repository.search.TopicSearchRepository;
import com.educationgenie.service.TopicService;
import com.educationgenie.service.dto.TopicDTO;
import com.educationgenie.service.mapper.TopicMapper;
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
 * Test class for the TopicResource REST controller.
 *
 * @see TopicResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EducationGenieApp.class)
public class TopicResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TopicRepository topicRepository;


    @Autowired
    private TopicMapper topicMapper;
    

    @Autowired
    private TopicService topicService;

    /**
     * This repository is mocked in the com.educationgenie.repository.search test package.
     *
     * @see com.educationgenie.repository.search.TopicSearchRepositoryMockConfiguration
     */
    @Autowired
    private TopicSearchRepository mockTopicSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTopicMockMvc;

    private Topic topic;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TopicResource topicResource = new TopicResource(topicService);
        this.restTopicMockMvc = MockMvcBuilders.standaloneSetup(topicResource)
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
    public static Topic createEntity(EntityManager em) {
        Topic topic = new Topic()
            .name(DEFAULT_NAME);
        return topic;
    }

    @Before
    public void initTest() {
        topic = createEntity(em);
    }

    @Test
    @Transactional
    public void createTopic() throws Exception {
        int databaseSizeBeforeCreate = topicRepository.findAll().size();

        // Create the Topic
        TopicDTO topicDTO = topicMapper.toDto(topic);
        restTopicMockMvc.perform(post("/api/topics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topicDTO)))
            .andExpect(status().isCreated());

        // Validate the Topic in the database
        List<Topic> topicList = topicRepository.findAll();
        assertThat(topicList).hasSize(databaseSizeBeforeCreate + 1);
        Topic testTopic = topicList.get(topicList.size() - 1);
        assertThat(testTopic.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Topic in Elasticsearch
        verify(mockTopicSearchRepository, times(1)).save(testTopic);
    }

    @Test
    @Transactional
    public void createTopicWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topicRepository.findAll().size();

        // Create the Topic with an existing ID
        topic.setId(1L);
        TopicDTO topicDTO = topicMapper.toDto(topic);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopicMockMvc.perform(post("/api/topics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topicDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Topic in the database
        List<Topic> topicList = topicRepository.findAll();
        assertThat(topicList).hasSize(databaseSizeBeforeCreate);

        // Validate the Topic in Elasticsearch
        verify(mockTopicSearchRepository, times(0)).save(topic);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicRepository.findAll().size();
        // set the field null
        topic.setName(null);

        // Create the Topic, which fails.
        TopicDTO topicDTO = topicMapper.toDto(topic);

        restTopicMockMvc.perform(post("/api/topics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topicDTO)))
            .andExpect(status().isBadRequest());

        List<Topic> topicList = topicRepository.findAll();
        assertThat(topicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTopics() throws Exception {
        // Initialize the database
        topicRepository.saveAndFlush(topic);

        // Get all the topicList
        restTopicMockMvc.perform(get("/api/topics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topic.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    

    @Test
    @Transactional
    public void getTopic() throws Exception {
        // Initialize the database
        topicRepository.saveAndFlush(topic);

        // Get the topic
        restTopicMockMvc.perform(get("/api/topics/{id}", topic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(topic.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTopic() throws Exception {
        // Get the topic
        restTopicMockMvc.perform(get("/api/topics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTopic() throws Exception {
        // Initialize the database
        topicRepository.saveAndFlush(topic);

        int databaseSizeBeforeUpdate = topicRepository.findAll().size();

        // Update the topic
        Topic updatedTopic = topicRepository.findById(topic.getId()).get();
        // Disconnect from session so that the updates on updatedTopic are not directly saved in db
        em.detach(updatedTopic);
        updatedTopic
            .name(UPDATED_NAME);
        TopicDTO topicDTO = topicMapper.toDto(updatedTopic);

        restTopicMockMvc.perform(put("/api/topics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topicDTO)))
            .andExpect(status().isOk());

        // Validate the Topic in the database
        List<Topic> topicList = topicRepository.findAll();
        assertThat(topicList).hasSize(databaseSizeBeforeUpdate);
        Topic testTopic = topicList.get(topicList.size() - 1);
        assertThat(testTopic.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Topic in Elasticsearch
        verify(mockTopicSearchRepository, times(1)).save(testTopic);
    }

    @Test
    @Transactional
    public void updateNonExistingTopic() throws Exception {
        int databaseSizeBeforeUpdate = topicRepository.findAll().size();

        // Create the Topic
        TopicDTO topicDTO = topicMapper.toDto(topic);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTopicMockMvc.perform(put("/api/topics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topicDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Topic in the database
        List<Topic> topicList = topicRepository.findAll();
        assertThat(topicList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Topic in Elasticsearch
        verify(mockTopicSearchRepository, times(0)).save(topic);
    }

    @Test
    @Transactional
    public void deleteTopic() throws Exception {
        // Initialize the database
        topicRepository.saveAndFlush(topic);

        int databaseSizeBeforeDelete = topicRepository.findAll().size();

        // Get the topic
        restTopicMockMvc.perform(delete("/api/topics/{id}", topic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Topic> topicList = topicRepository.findAll();
        assertThat(topicList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Topic in Elasticsearch
        verify(mockTopicSearchRepository, times(1)).deleteById(topic.getId());
    }

    @Test
    @Transactional
    public void searchTopic() throws Exception {
        // Initialize the database
        topicRepository.saveAndFlush(topic);
        when(mockTopicSearchRepository.search(queryStringQuery("id:" + topic.getId())))
            .thenReturn(Collections.singletonList(topic));
        // Search the topic
        restTopicMockMvc.perform(get("/api/_search/topics?query=id:" + topic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topic.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Topic.class);
        Topic topic1 = new Topic();
        topic1.setId(1L);
        Topic topic2 = new Topic();
        topic2.setId(topic1.getId());
        assertThat(topic1).isEqualTo(topic2);
        topic2.setId(2L);
        assertThat(topic1).isNotEqualTo(topic2);
        topic1.setId(null);
        assertThat(topic1).isNotEqualTo(topic2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopicDTO.class);
        TopicDTO topicDTO1 = new TopicDTO();
        topicDTO1.setId(1L);
        TopicDTO topicDTO2 = new TopicDTO();
        assertThat(topicDTO1).isNotEqualTo(topicDTO2);
        topicDTO2.setId(topicDTO1.getId());
        assertThat(topicDTO1).isEqualTo(topicDTO2);
        topicDTO2.setId(2L);
        assertThat(topicDTO1).isNotEqualTo(topicDTO2);
        topicDTO1.setId(null);
        assertThat(topicDTO1).isNotEqualTo(topicDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(topicMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(topicMapper.fromId(null)).isNull();
    }
}
