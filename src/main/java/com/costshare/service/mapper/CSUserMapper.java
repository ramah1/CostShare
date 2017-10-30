package com.costshare.service.mapper;

import com.costshare.domain.*;
import com.costshare.service.dto.CSUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CSUser and its DTO CSUserDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CSUserMapper extends EntityMapper<CSUserDTO, CSUser> {

    @Mapping(source = "userName.id", target = "userNameId")
    @Mapping(source = "userName.login", target = "userNameLogin")
    CSUserDTO toDto(CSUser cSUser); 

    @Mapping(source = "userNameId", target = "userName")
    @Mapping(target = "userCosts", ignore = true)
    @Mapping(target = "receivedInvites", ignore = true)
    @Mapping(target = "sentInvites", ignore = true)
    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "adminOfs", ignore = true)
    CSUser toEntity(CSUserDTO cSUserDTO);

    default CSUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        CSUser cSUser = new CSUser();
        cSUser.setId(id);
        return cSUser;
    }
}
