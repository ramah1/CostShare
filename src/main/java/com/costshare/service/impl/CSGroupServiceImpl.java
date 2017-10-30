package com.costshare.service.impl;

import com.costshare.service.CSGroupService;
import com.costshare.domain.CSGroup;
import com.costshare.repository.CSGroupRepository;
import com.costshare.service.dto.CSGroupDTO;
import com.costshare.service.mapper.CSGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CSGroup.
 */
@Service
@Transactional
public class CSGroupServiceImpl implements CSGroupService{

    private final Logger log = LoggerFactory.getLogger(CSGroupServiceImpl.class);

    private final CSGroupRepository cSGroupRepository;

    private final CSGroupMapper cSGroupMapper;

    public CSGroupServiceImpl(CSGroupRepository cSGroupRepository, CSGroupMapper cSGroupMapper) {
        this.cSGroupRepository = cSGroupRepository;
        this.cSGroupMapper = cSGroupMapper;
    }

    /**
     * Save a cSGroup.
     *
     * @param cSGroupDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CSGroupDTO save(CSGroupDTO cSGroupDTO) {
        log.debug("Request to save CSGroup : {}", cSGroupDTO);
        CSGroup cSGroup = cSGroupMapper.toEntity(cSGroupDTO);
        cSGroup = cSGroupRepository.save(cSGroup);
        return cSGroupMapper.toDto(cSGroup);
    }

    /**
     *  Get all the cSGroups.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CSGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CSGroups");
        return cSGroupRepository.findAll(pageable)
            .map(cSGroupMapper::toDto);
    }

    /**
     *  Get one cSGroup by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CSGroupDTO findOne(Long id) {
        log.debug("Request to get CSGroup : {}", id);
        CSGroup cSGroup = cSGroupRepository.findOneWithEagerRelationships(id);
        return cSGroupMapper.toDto(cSGroup);
    }

    /**
     *  Delete the  cSGroup by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CSGroup : {}", id);
        cSGroupRepository.delete(id);
    }
}
