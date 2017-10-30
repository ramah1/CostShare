package com.costshare.service.impl;

import com.costshare.service.CSUserService;
import com.costshare.domain.CSUser;
import com.costshare.repository.CSUserRepository;
import com.costshare.service.dto.CSUserDTO;
import com.costshare.service.mapper.CSUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CSUser.
 */
@Service
@Transactional
public class CSUserServiceImpl implements CSUserService{

    private final Logger log = LoggerFactory.getLogger(CSUserServiceImpl.class);

    private final CSUserRepository cSUserRepository;

    private final CSUserMapper cSUserMapper;

    public CSUserServiceImpl(CSUserRepository cSUserRepository, CSUserMapper cSUserMapper) {
        this.cSUserRepository = cSUserRepository;
        this.cSUserMapper = cSUserMapper;
    }

    /**
     * Save a cSUser.
     *
     * @param cSUserDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CSUserDTO save(CSUserDTO cSUserDTO) {
        log.debug("Request to save CSUser : {}", cSUserDTO);
        CSUser cSUser = cSUserMapper.toEntity(cSUserDTO);
        cSUser = cSUserRepository.save(cSUser);
        return cSUserMapper.toDto(cSUser);
    }

    /**
     *  Get all the cSUsers.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CSUserDTO> findAll() {
        log.debug("Request to get all CSUsers");
        return cSUserRepository.findAll().stream()
            .map(cSUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one cSUser by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CSUserDTO findOne(Long id) {
        log.debug("Request to get CSUser : {}", id);
        CSUser cSUser = cSUserRepository.findOne(id);
        return cSUserMapper.toDto(cSUser);
    }

    /**
     *  Delete the  cSUser by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CSUser : {}", id);
        cSUserRepository.delete(id);
    }
}
