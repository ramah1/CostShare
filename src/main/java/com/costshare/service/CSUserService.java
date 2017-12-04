package com.costshare.service;

import com.costshare.service.dto.CSUserDTO;
import java.util.List;

/**
 * Service Interface for managing CSUser.
 */
public interface CSUserService {

    /**
     * Save a cSUser.
     *
     * @param cSUserDTO the entity to save
     * @return the persisted entity
     */
    CSUserDTO save(CSUserDTO cSUserDTO);

    /**
     *  Get all the cSUsers.
     *
     *  @return the list of entities
     */
    List<CSUserDTO> findAll();

    /**
     *  Get the "id" cSUser.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CSUserDTO findOne(Long id);

    /**
     *  Delete the "id" cSUser.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Get CSUser by accessing it with authenticated user
     *
     * @return the entity
     */
    CSUserDTO findOneByAuthenticatedUser();
}
