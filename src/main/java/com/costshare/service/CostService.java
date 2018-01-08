package com.costshare.service;

import com.costshare.service.dto.CostDTO;
import com.costshare.service.dto.UserCostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Cost.
 */
public interface CostService {

    /**
     * Save a cost.
     *
     * @param costDTO the entity to save
     * @return the persisted entity
     */
    CostDTO save(CostDTO costDTO);

    /**
     *  Get all the costs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CostDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" cost.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CostDTO findOne(Long id);

    /**
     *  Delete the "id" cost.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    List<CostDTO> findAllByUserId(Long id);
}
