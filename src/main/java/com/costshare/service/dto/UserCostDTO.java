package com.costshare.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the UserCost entity.
 */
public class UserCostDTO implements Serializable {

    private Long id;

    @NotNull
    private Double multiplier;

    private Long baseCostId;

    private String baseCostName;

    private Long userId;

    private String userName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

    public Long getBaseCostId() {
        return baseCostId;
    }

    public void setBaseCostId(Long costId) {
        this.baseCostId = costId;
    }

    public String getBaseCostName() {
        return baseCostName;
    }

    public void setBaseCostName(String costName) {
        this.baseCostName = costName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long cSUserId) {
        this.userId = cSUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String cSUserName) {
        this.userName = cSUserName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserCostDTO userCostDTO = (UserCostDTO) o;
        if(userCostDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userCostDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserCostDTO{" +
            "id=" + getId() +
            ", multiplier='" + getMultiplier() + "'" +
            "}";
    }
}
