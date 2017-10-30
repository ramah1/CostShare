package com.costshare.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Cost.
 */
@Entity
@Table(name = "cost")
public class Cost implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 1000)
    @Column(name = "description", length = 1000)
    private String description;

    @NotNull
    @Column(name = "sum", nullable = false)
    private Double sum;

    @OneToMany(mappedBy = "baseCost")
    @JsonIgnore
    private Set<UserCost> userCosts = new HashSet<>();

    @ManyToOne
    private CSGroup group;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Cost name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Cost description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getSum() {
        return sum;
    }

    public Cost sum(Double sum) {
        this.sum = sum;
        return this;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Set<UserCost> getUserCosts() {
        return userCosts;
    }

    public Cost userCosts(Set<UserCost> userCosts) {
        this.userCosts = userCosts;
        return this;
    }

    public Cost addUserCosts(UserCost userCost) {
        this.userCosts.add(userCost);
        userCost.setBaseCost(this);
        return this;
    }

    public Cost removeUserCosts(UserCost userCost) {
        this.userCosts.remove(userCost);
        userCost.setBaseCost(null);
        return this;
    }

    public void setUserCosts(Set<UserCost> userCosts) {
        this.userCosts = userCosts;
    }

    public CSGroup getGroup() {
        return group;
    }

    public Cost group(CSGroup cSGroup) {
        this.group = cSGroup;
        return this;
    }

    public void setGroup(CSGroup cSGroup) {
        this.group = cSGroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cost cost = (Cost) o;
        if (cost.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cost.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cost{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", sum='" + getSum() + "'" +
            "}";
    }
}
