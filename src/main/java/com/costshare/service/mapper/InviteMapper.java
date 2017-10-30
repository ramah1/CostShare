package com.costshare.service.mapper;

import com.costshare.domain.*;
import com.costshare.service.dto.InviteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Invite and its DTO InviteDTO.
 */
@Mapper(componentModel = "spring", uses = {CSGroupMapper.class, CSUserMapper.class})
public interface InviteMapper extends EntityMapper<InviteDTO, Invite> {

    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "group.name", target = "groupName")
    @Mapping(source = "sentTo.id", target = "sentToId")
    @Mapping(source = "sentTo.name", target = "sentToName")
    @Mapping(source = "sentFrom.id", target = "sentFromId")
    @Mapping(source = "sentFrom.name", target = "sentFromName")
    InviteDTO toDto(Invite invite); 

    @Mapping(source = "groupId", target = "group")
    @Mapping(source = "sentToId", target = "sentTo")
    @Mapping(source = "sentFromId", target = "sentFrom")
    Invite toEntity(InviteDTO inviteDTO);

    default Invite fromId(Long id) {
        if (id == null) {
            return null;
        }
        Invite invite = new Invite();
        invite.setId(id);
        return invite;
    }
}
