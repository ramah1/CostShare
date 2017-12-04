package com.costshare.service.impl;

import com.costshare.service.UserCostService;
import com.costshare.domain.UserCost;
import com.costshare.repository.UserCostRepository;
import com.costshare.service.dto.UserCostDTO;
import com.costshare.service.mapper.UserCostMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing UserCost.
 */
@Service
@Transactional
public class UserCostServiceImpl implements UserCostService{

    private final Logger log = LoggerFactory.getLogger(UserCostServiceImpl.class);

    private final UserCostRepository userCostRepository;

    private final UserCostMapper userCostMapper;

    public UserCostServiceImpl(UserCostRepository userCostRepository, UserCostMapper userCostMapper) {
        this.userCostRepository = userCostRepository;
        this.userCostMapper = userCostMapper;
    }

    /**
     * Save a userCost.
     *
     * @param userCostDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserCostDTO save(UserCostDTO userCostDTO) {
        log.debug("Request to save UserCost : {}", userCostDTO);
        UserCost userCost = userCostMapper.toEntity(userCostDTO);
        userCost = userCostRepository.save(userCost);
        return userCostMapper.toDto(userCost);
    }

    /**
     *  Get all the userCosts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserCostDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserCosts");
        return userCostRepository.findAll(pageable)
            .map(userCostMapper::toDto);
    }

    /**
     *  Get one userCost by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserCostDTO findOne(Long id) {
        log.debug("Request to get UserCost : {}", id);
        UserCost userCost = userCostRepository.findOne(id);
        return userCostMapper.toDto(userCost);
    }

    /**
     *  Delete the  userCost by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserCost : {}", id);
        userCostRepository.delete(id);
    }

    @Override
    public List<UserCostDTO> findAllByCostId(Long id) {
        log.debug("Request to get all UserCost by CostId : {}", id);
        return userCostRepository.findAllByBaseCostId(id).stream()
            .map(userCostMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
