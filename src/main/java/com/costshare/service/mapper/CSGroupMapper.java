package com.costshare.service.mapper;

import com.costshare.domain.*;
import com.costshare.service.dto.CSGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CSGroup and its DTO CSGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {CSUserMapper.class})
public interface CSGroupMapper extends EntityMapper<CSGroupDTO, CSGroup> {

    

    @Mapping(target = "costs", ignore = true)
    CSGroup toEntity(CSGroupDTO cSGroupDTO);

    default CSGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        CSGroup cSGroup = new CSGroup();
        cSGroup.setId(id);
        return cSGroup;
    }
}
