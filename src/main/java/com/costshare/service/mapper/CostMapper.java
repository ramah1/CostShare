package com.costshare.service.mapper;

import com.costshare.domain.*;
import com.costshare.service.dto.CostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cost and its DTO CostDTO.
 */
@Mapper(componentModel = "spring", uses = {CSUserMapper.class, CSGroupMapper.class})
public interface CostMapper extends EntityMapper<CostDTO, Cost> {

    @Mapping(source = "paidBy.id", target = "paidById")
    @Mapping(source = "paidBy.name", target = "paidByName")
    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "group.name", target = "groupName")
    CostDTO toDto(Cost cost);

    @Mapping(target = "userCosts", ignore = true)
    @Mapping(source = "paidById", target = "paidBy")
    @Mapping(source = "groupId", target = "group")
    Cost toEntity(CostDTO costDTO);

    default Cost fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cost cost = new Cost();
        cost.setId(id);
        return cost;
    }
}
