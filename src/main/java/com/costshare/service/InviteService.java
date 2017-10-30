package com.costshare.service;

import com.costshare.service.dto.InviteDTO;
import java.util.List;

/**
 * Service Interface for managing Invite.
 */
public interface InviteService {

    /**
     * Save a invite.
     *
     * @param inviteDTO the entity to save
     * @return the persisted entity
     */
    InviteDTO save(InviteDTO inviteDTO);

    /**
     *  Get all the invites.
     *
     *  @return the list of entities
     */
    List<InviteDTO> findAll();

    /**
     *  Get the "id" invite.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    InviteDTO findOne(Long id);

    /**
     *  Delete the "id" invite.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
