package com.educationgenie.service.impl;

import com.educationgenie.service.ChapterService;
import com.educationgenie.domain.Chapter;
import com.educationgenie.repository.ChapterRepository;
import com.educationgenie.repository.search.ChapterSearchRepository;
import com.educationgenie.service.dto.ChapterDTO;
import com.educationgenie.service.mapper.ChapterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Chapter.
 */
@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {

    private final Logger log = LoggerFactory.getLogger(ChapterServiceImpl.class);

    private final ChapterRepository chapterRepository;

    private final ChapterMapper chapterMapper;

    private final ChapterSearchRepository chapterSearchRepository;

    public ChapterServiceImpl(ChapterRepository chapterRepository, ChapterMapper chapterMapper, ChapterSearchRepository chapterSearchRepository) {
        this.chapterRepository = chapterRepository;
        this.chapterMapper = chapterMapper;
        this.chapterSearchRepository = chapterSearchRepository;
    }

    /**
     * Save a chapter.
     *
     * @param chapterDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ChapterDTO save(ChapterDTO chapterDTO) {
        log.debug("Request to save Chapter : {}", chapterDTO);
        Chapter chapter = chapterMapper.toEntity(chapterDTO);
        chapter = chapterRepository.save(chapter);
        ChapterDTO result = chapterMapper.toDto(chapter);
        chapterSearchRepository.save(chapter);
        return result;
    }

    /**
     * Get all the chapters.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ChapterDTO> findAll() {
        log.debug("Request to get all Chapters");
        return chapterRepository.findAll().stream()
            .map(chapterMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one chapter by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ChapterDTO> findOne(Long id) {
        log.debug("Request to get Chapter : {}", id);
        return chapterRepository.findById(id)
            .map(chapterMapper::toDto);
    }

    /**
     * Delete the chapter by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Chapter : {}", id);
        chapterRepository.deleteById(id);
        chapterSearchRepository.deleteById(id);
    }

    /**
     * Search for the chapter corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ChapterDTO> search(String query) {
        log.debug("Request to search Chapters for query {}", query);
        return StreamSupport
            .stream(chapterSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(chapterMapper::toDto)
            .collect(Collectors.toList());
    }
}
