package com.costshare.service;

import com.costshare.service.dto.CSGroupDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CSGroup.
 */
public interface CSGroupService {

    /**
     * Save a cSGroup.
     *
     * @param cSGroupDTO the entity to save
     * @return the persisted entity
     */
    CSGroupDTO save(CSGroupDTO cSGroupDTO);

    /**
     *  Get all the cSGroups.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CSGroupDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" cSGroup.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CSGroupDTO findOne(Long id);

    /**
     *  Delete the "id" cSGroup.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
