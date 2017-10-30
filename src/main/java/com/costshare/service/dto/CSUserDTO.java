package com.costshare.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CSUser entity.
 */
public class CSUserDTO implements Serializable {

    private Long id;

    private String name;

    private Long userNameId;

    private String userNameLogin;

    private Long paidId;

    private String paidName;

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

    public Long getUserNameId() {
        return userNameId;
    }

    public void setUserNameId(Long userId) {
        this.userNameId = userId;
    }

    public String getUserNameLogin() {
        return userNameLogin;
    }

    public void setUserNameLogin(String userLogin) {
        this.userNameLogin = userLogin;
    }

    public Long getPaidId() {
        return paidId;
    }

    public void setPaidId(Long costId) {
        this.paidId = costId;
    }

    public String getPaidName() {
        return paidName;
    }

    public void setPaidName(String costName) {
        this.paidName = costName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CSUserDTO cSUserDTO = (CSUserDTO) o;
        if(cSUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cSUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CSUserDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
