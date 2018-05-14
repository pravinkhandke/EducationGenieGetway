package com.educationgenie.service;

import com.educationgenie.service.dto.UserInfoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing UserInfo.
 */
public interface UserInfoService {

    /**
     * Save a userInfo.
     *
     * @param userInfoDTO the entity to save
     * @return the persisted entity
     */
    UserInfoDTO save(UserInfoDTO userInfoDTO);

    /**
     * Get all the userInfos.
     *
     * @return the list of entities
     */
    List<UserInfoDTO> findAll();


    /**
     * Get the "id" userInfo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserInfoDTO> findOne(Long id);

    /**
     * Delete the "id" userInfo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the userInfo corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<UserInfoDTO> search(String query);
}
