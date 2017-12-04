package com.costshare.service.impl;

import com.costshare.service.InviteService;
import com.costshare.domain.Invite;
import com.costshare.repository.InviteRepository;
import com.costshare.service.dto.InviteDTO;
import com.costshare.service.mapper.InviteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Invite.
 */
@Service
@Transactional
public class InviteServiceImpl implements InviteService{

    private final Logger log = LoggerFactory.getLogger(InviteServiceImpl.class);

    private final InviteRepository inviteRepository;

    private final InviteMapper inviteMapper;

    public InviteServiceImpl(InviteRepository inviteRepository, InviteMapper inviteMapper) {
        this.inviteRepository = inviteRepository;
        this.inviteMapper = inviteMapper;
    }

    /**
     * Save a invite.
     *
     * @param inviteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InviteDTO save(InviteDTO inviteDTO) {
        log.debug("Request to save Invite : {}", inviteDTO);
        Invite invite = inviteMapper.toEntity(inviteDTO);
        invite = inviteRepository.save(invite);
        return inviteMapper.toDto(invite);
    }

    /**
     *  Get all the invites.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<InviteDTO> findAll() {
        log.debug("Request to get all Invites");
        return inviteRepository.findAll().stream()
            .map(inviteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one invite by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public InviteDTO findOne(Long id) {
        log.debug("Request to get Invite : {}", id);
        Invite invite = inviteRepository.findOne(id);
        return inviteMapper.toDto(invite);
    }

    /**
     *  Delete the  invite by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Invite : {}", id);
        inviteRepository.delete(id);
    }

    @Override
    public List<InviteDTO> findAllByCSUser(Long id) {
        log.debug("Request to find all invites by csuser");
        return inviteRepository.findAllBySentTo_Id(id)
            .stream()
            .map(inviteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
