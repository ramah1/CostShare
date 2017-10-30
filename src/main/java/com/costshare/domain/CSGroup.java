package com.costshare.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CSGroup.
 */
@Entity
@Table(name = "cs_group")
public class CSGroup implements Serializable {

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

    @OneToMany(mappedBy = "group")
    @JsonIgnore
    private Set<Cost> costs = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "csgroup_members",
               joinColumns = @JoinColumn(name="csgroups_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="members_id", referencedColumnName="id"))
    private Set<CSUser> members = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "csgroup_admins",
               joinColumns = @JoinColumn(name="csgroups_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="admins_id", referencedColumnName="id"))
    private Set<CSUser> admins = new HashSet<>();

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

    public CSGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public CSGroup description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Cost> getCosts() {
        return costs;
    }

    public CSGroup costs(Set<Cost> costs) {
        this.costs = costs;
        return this;
    }

    public CSGroup addCosts(Cost cost) {
        this.costs.add(cost);
        cost.setGroup(this);
        return this;
    }

    public CSGroup removeCosts(Cost cost) {
        this.costs.remove(cost);
        cost.setGroup(null);
        return this;
    }

    public void setCosts(Set<Cost> costs) {
        this.costs = costs;
    }

    public Set<CSUser> getMembers() {
        return members;
    }

    public CSGroup members(Set<CSUser> cSUsers) {
        this.members = cSUsers;
        return this;
    }

    public CSGroup addMembers(CSUser cSUser) {
        this.members.add(cSUser);
        cSUser.getGroups().add(this);
        return this;
    }

    public CSGroup removeMembers(CSUser cSUser) {
        this.members.remove(cSUser);
        cSUser.getGroups().remove(this);
        return this;
    }

    public void setMembers(Set<CSUser> cSUsers) {
        this.members = cSUsers;
    }

    public Set<CSUser> getAdmins() {
        return admins;
    }

    public CSGroup admins(Set<CSUser> cSUsers) {
        this.admins = cSUsers;
        return this;
    }

    public CSGroup addAdmins(CSUser cSUser) {
        this.admins.add(cSUser);
        cSUser.getAdminOfs().add(this);
        return this;
    }

    public CSGroup removeAdmins(CSUser cSUser) {
        this.admins.remove(cSUser);
        cSUser.getAdminOfs().remove(this);
        return this;
    }

    public void setAdmins(Set<CSUser> cSUsers) {
        this.admins = cSUsers;
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
        CSGroup cSGroup = (CSGroup) o;
        if (cSGroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cSGroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CSGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
