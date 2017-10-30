package com.costshare.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Invite entity.
 */
public class InviteDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 1000)
    private String comment;

    private Long groupId;

    private String groupName;

    private Long sentToId;

    private String sentToName;

    private Long sentFromId;

    private String sentFromName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long cSGroupId) {
        this.groupId = cSGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String cSGroupName) {
        this.groupName = cSGroupName;
    }

    public Long getSentToId() {
        return sentToId;
    }

    public void setSentToId(Long cSUserId) {
        this.sentToId = cSUserId;
    }

    public String getSentToName() {
        return sentToName;
    }

    public void setSentToName(String cSUserName) {
        this.sentToName = cSUserName;
    }

    public Long getSentFromId() {
        return sentFromId;
    }

    public void setSentFromId(Long cSUserId) {
        this.sentFromId = cSUserId;
    }

    public String getSentFromName() {
        return sentFromName;
    }

    public void setSentFromName(String cSUserName) {
        this.sentFromName = cSUserName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InviteDTO inviteDTO = (InviteDTO) o;
        if(inviteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inviteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InviteDTO{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
