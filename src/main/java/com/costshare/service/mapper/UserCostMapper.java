package com.costshare.service.mapper;

import com.costshare.domain.*;
import com.costshare.service.dto.UserCostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserCost and its DTO UserCostDTO.
 */
@Mapper(componentModel = "spring", uses = {CostMapper.class, CSUserMapper.class})
public interface UserCostMapper extends EntityMapper<UserCostDTO, UserCost> {

    @Mapping(source = "baseCost.id", target = "baseCostId")
    @Mapping(source = "baseCost.name", target = "baseCostName")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "userName")
    UserCostDTO toDto(UserCost userCost); 

    @Mapping(source = "baseCostId", target = "baseCost")
    @Mapping(source = "userId", target = "user")
    UserCost toEntity(UserCostDTO userCostDTO);

    default UserCost fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserCost userCost = new UserCost();
        userCost.setId(id);
        return userCost;
    }
}
