package com.educationgenie.service.impl;

import com.educationgenie.service.UserInfoService;
import com.educationgenie.domain.UserInfo;
import com.educationgenie.repository.UserInfoRepository;
import com.educationgenie.repository.search.UserInfoSearchRepository;
import com.educationgenie.service.dto.UserInfoDTO;
import com.educationgenie.service.mapper.UserInfoMapper;
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
 * Service Implementation for managing UserInfo.
 */
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    private final Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    private final UserInfoRepository userInfoRepository;

    private final UserInfoMapper userInfoMapper;

    private final UserInfoSearchRepository userInfoSearchRepository;

    public UserInfoServiceImpl(UserInfoRepository userInfoRepository, UserInfoMapper userInfoMapper, UserInfoSearchRepository userInfoSearchRepository) {
        this.userInfoRepository = userInfoRepository;
        this.userInfoMapper = userInfoMapper;
        this.userInfoSearchRepository = userInfoSearchRepository;
    }

    /**
     * Save a userInfo.
     *
     * @param userInfoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserInfoDTO save(UserInfoDTO userInfoDTO) {
        log.debug("Request to save UserInfo : {}", userInfoDTO);
        UserInfo userInfo = userInfoMapper.toEntity(userInfoDTO);
        userInfo = userInfoRepository.save(userInfo);
        UserInfoDTO result = userInfoMapper.toDto(userInfo);
        userInfoSearchRepository.save(userInfo);
        return result;
    }

    /**
     * Get all the userInfos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserInfoDTO> findAll() {
        log.debug("Request to get all UserInfos");
        return userInfoRepository.findAll().stream()
            .map(userInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one userInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserInfoDTO> findOne(Long id) {
        log.debug("Request to get UserInfo : {}", id);
        return userInfoRepository.findById(id)
            .map(userInfoMapper::toDto);
    }

    /**
     * Delete the userInfo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserInfo : {}", id);
        userInfoRepository.deleteById(id);
        userInfoSearchRepository.deleteById(id);
    }

    /**
     * Search for the userInfo corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserInfoDTO> search(String query) {
        log.debug("Request to search UserInfos for query {}", query);
        return StreamSupport
            .stream(userInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(userInfoMapper::toDto)
            .collect(Collectors.toList());
    }
}
