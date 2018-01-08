package com.costshare.service.impl;

import com.costshare.service.CostService;
import com.costshare.domain.Cost;
import com.costshare.repository.CostRepository;
import com.costshare.service.dto.CostDTO;
import com.costshare.service.dto.UserCostDTO;
import com.costshare.service.mapper.CostMapper;
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
 * Service Implementation for managing Cost.
 */
@Service
@Transactional
public class CostServiceImpl implements CostService{

    private final Logger log = LoggerFactory.getLogger(CostServiceImpl.class);

    private final CostRepository costRepository;

    private final CostMapper costMapper;

    public CostServiceImpl(CostRepository costRepository, CostMapper costMapper) {
        this.costRepository = costRepository;
        this.costMapper = costMapper;
    }

    /**
     * Save a cost.
     *
     * @param costDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CostDTO save(CostDTO costDTO) {
        log.debug("Request to save Cost : {}", costDTO);
        Cost cost = costMapper.toEntity(costDTO);
        cost = costRepository.save(cost);
        return costMapper.toDto(cost);
    }

    /**
     *  Get all the costs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CostDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Costs");
        return costRepository.findAll(pageable)
            .map(costMapper::toDto);
    }

    /**
     *  Get one cost by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CostDTO findOne(Long id) {
        log.debug("Request to get Cost : {}", id);
        Cost cost = costRepository.findOne(id);
        return costMapper.toDto(cost);
    }

    @Override
    public List<CostDTO> findAllByUserId(Long id) {
        log.debug("Request to get all Costs by UserId : {}", id);
        return costRepository.findAllByPaidBy(id).stream()
            .map(costMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Delete the  cost by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cost : {}", id);
        costRepository.delete(id);
    }
}
