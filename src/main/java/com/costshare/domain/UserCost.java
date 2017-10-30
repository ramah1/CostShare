package com.costshare.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UserCost.
 */
@Entity
@Table(name = "user_cost")
public class UserCost implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "multiplier", nullable = false)
    private Double multiplier;

    @ManyToOne
    private Cost baseCost;

    @ManyToOne
    private CSUser user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public UserCost multiplier(Double multiplier) {
        this.multiplier = multiplier;
        return this;
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

    public Cost getBaseCost() {
        return baseCost;
    }

    public UserCost baseCost(Cost cost) {
        this.baseCost = cost;
        return this;
    }

    public void setBaseCost(Cost cost) {
        this.baseCost = cost;
    }

    public CSUser getUser() {
        return user;
    }

    public UserCost user(CSUser cSUser) {
        this.user = cSUser;
        return this;
    }

    public void setUser(CSUser cSUser) {
        this.user = cSUser;
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
        UserCost userCost = (UserCost) o;
        if (userCost.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userCost.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserCost{" +
            "id=" + getId() +
            ", multiplier='" + getMultiplier() + "'" +
            "}";
    }
}
