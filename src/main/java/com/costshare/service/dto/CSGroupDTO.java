package com.costshare.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CSGroup entity.
 */
public class CSGroupDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @Size(max = 1000)
    private String description;

    private Set<CSUserDTO> members = new HashSet<>();

    private Set<CSUserDTO> admins = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CSUserDTO> getMembers() {
        return members;
    }

    public void setMembers(Set<CSUserDTO> cSUsers) {
        this.members = cSUsers;
    }

    public Set<CSUserDTO> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<CSUserDTO> cSUsers) {
        this.admins = cSUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CSGroupDTO cSGroupDTO = (CSGroupDTO) o;
        if(cSGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cSGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CSGroupDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
