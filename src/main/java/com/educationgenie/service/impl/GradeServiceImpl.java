package com.educationgenie.service.impl;

import com.educationgenie.service.GradeService;
import com.educationgenie.domain.Grade;
import com.educationgenie.repository.GradeRepository;
import com.educationgenie.repository.search.GradeSearchRepository;
import com.educationgenie.service.dto.GradeDTO;
import com.educationgenie.service.mapper.GradeMapper;
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
 * Service Implementation for managing Grade.
 */
@Service
@Transactional
public class GradeServiceImpl implements GradeService {

    private final Logger log = LoggerFactory.getLogger(GradeServiceImpl.class);

    private final GradeRepository gradeRepository;

    private final GradeMapper gradeMapper;

    private final GradeSearchRepository gradeSearchRepository;

    public GradeServiceImpl(GradeRepository gradeRepository, GradeMapper gradeMapper, GradeSearchRepository gradeSearchRepository) {
        this.gradeRepository = gradeRepository;
        this.gradeMapper = gradeMapper;
        this.gradeSearchRepository = gradeSearchRepository;
    }

    /**
     * Save a grade.
     *
     * @param gradeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GradeDTO save(GradeDTO gradeDTO) {
        log.debug("Request to save Grade : {}", gradeDTO);
        Grade grade = gradeMapper.toEntity(gradeDTO);
        grade = gradeRepository.save(grade);
        GradeDTO result = gradeMapper.toDto(grade);
        gradeSearchRepository.save(grade);
        return result;
    }

    /**
     * Get all the grades.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<GradeDTO> findAll() {
        log.debug("Request to get all Grades");
        return gradeRepository.findAll().stream()
            .map(gradeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one grade by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GradeDTO> findOne(Long id) {
        log.debug("Request to get Grade : {}", id);
        return gradeRepository.findById(id)
            .map(gradeMapper::toDto);
    }

    /**
     * Delete the grade by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Grade : {}", id);
        gradeRepository.deleteById(id);
        gradeSearchRepository.deleteById(id);
    }

    /**
     * Search for the grade corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<GradeDTO> search(String query) {
        log.debug("Request to search Grades for query {}", query);
        return StreamSupport
            .stream(gradeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(gradeMapper::toDto)
            .collect(Collectors.toList());
    }
}
