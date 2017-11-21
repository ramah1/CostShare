package com.costshare.service;

import com.costshare.service.dto.UserCostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing UserCost.
 */
public interface UserCostService {

    /**
     * Save a userCost.
     *
     * @param userCostDTO the entity to save
     * @return the persisted entity
     */
    UserCostDTO save(UserCostDTO userCostDTO);

    /**
     *  Get all the userCosts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UserCostDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" userCost.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UserCostDTO findOne(Long id);

    /**
     *  Delete the "id" userCost.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    List<UserCostDTO> findAllByCostId(Long id);
}
