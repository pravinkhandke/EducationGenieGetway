package com.educationgenie.service.impl;

import com.educationgenie.service.ScoreService;
import com.educationgenie.domain.Score;
import com.educationgenie.repository.ScoreRepository;
import com.educationgenie.repository.search.ScoreSearchRepository;
import com.educationgenie.service.dto.ScoreDTO;
import com.educationgenie.service.mapper.ScoreMapper;
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
 * Service Implementation for managing Score.
 */
@Service
@Transactional
public class ScoreServiceImpl implements ScoreService {

    private final Logger log = LoggerFactory.getLogger(ScoreServiceImpl.class);

    private final ScoreRepository scoreRepository;

    private final ScoreMapper scoreMapper;

    private final ScoreSearchRepository scoreSearchRepository;

    public ScoreServiceImpl(ScoreRepository scoreRepository, ScoreMapper scoreMapper, ScoreSearchRepository scoreSearchRepository) {
        this.scoreRepository = scoreRepository;
        this.scoreMapper = scoreMapper;
        this.scoreSearchRepository = scoreSearchRepository;
    }

    /**
     * Save a score.
     *
     * @param scoreDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ScoreDTO save(ScoreDTO scoreDTO) {
        log.debug("Request to save Score : {}", scoreDTO);
        Score score = scoreMapper.toEntity(scoreDTO);
        score = scoreRepository.save(score);
        ScoreDTO result = scoreMapper.toDto(score);
        scoreSearchRepository.save(score);
        return result;
    }

    /**
     * Get all the scores.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ScoreDTO> findAll() {
        log.debug("Request to get all Scores");
        return scoreRepository.findAll().stream()
            .map(scoreMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one score by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ScoreDTO> findOne(Long id) {
        log.debug("Request to get Score : {}", id);
        return scoreRepository.findById(id)
            .map(scoreMapper::toDto);
    }

    /**
     * Delete the score by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Score : {}", id);
        scoreRepository.deleteById(id);
        scoreSearchRepository.deleteById(id);
    }

    /**
     * Search for the score corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ScoreDTO> search(String query) {
        log.debug("Request to search Scores for query {}", query);
        return StreamSupport
            .stream(scoreSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(scoreMapper::toDto)
            .collect(Collectors.toList());
    }
}
