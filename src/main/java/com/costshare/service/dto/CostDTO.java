package com.costshare.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Cost entity.
 */
public class CostDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @Size(max = 1000)
    private String description;

    @NotNull
    private Double sum;

    private Long groupId;

    private String groupName;

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

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CostDTO costDTO = (CostDTO) o;
        if(costDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), costDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CostDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", sum='" + getSum() + "'" +
            "}";
    }
}
