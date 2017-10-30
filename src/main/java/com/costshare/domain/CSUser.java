package com.costshare.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CSUser.
 */
@Entity
@Table(name = "cs_user")
public class CSUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(unique = true)
    private User userName;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<UserCost> userCosts = new HashSet<>();

    @OneToMany(mappedBy = "sentTo")
    @JsonIgnore
    private Set<Invite> receivedInvites = new HashSet<>();

    @OneToMany(mappedBy = "sentFrom")
    @JsonIgnore
    private Set<Invite> sentInvites = new HashSet<>();

    @ManyToOne
    private Cost paid;

    @ManyToMany(mappedBy = "members")
    @JsonIgnore
    private Set<CSGroup> groups = new HashSet<>();

    @ManyToMany(mappedBy = "admins")
    @JsonIgnore
    private Set<CSGroup> adminOfs = new HashSet<>();

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

    public CSUser name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUserName() {
        return userName;
    }

    public CSUser userName(User user) {
        this.userName = user;
        return this;
    }

    public void setUserName(User user) {
        this.userName = user;
    }

    public Set<UserCost> getUserCosts() {
        return userCosts;
    }

    public CSUser userCosts(Set<UserCost> userCosts) {
        this.userCosts = userCosts;
        return this;
    }

    public CSUser addUserCosts(UserCost userCost) {
        this.userCosts.add(userCost);
        userCost.setUser(this);
        return this;
    }

    public CSUser removeUserCosts(UserCost userCost) {
        this.userCosts.remove(userCost);
        userCost.setUser(null);
        return this;
    }

    public void setUserCosts(Set<UserCost> userCosts) {
        this.userCosts = userCosts;
    }

    public Set<Invite> getReceivedInvites() {
        return receivedInvites;
    }

    public CSUser receivedInvites(Set<Invite> invites) {
        this.receivedInvites = invites;
        return this;
    }

    public CSUser addReceivedInvites(Invite invite) {
        this.receivedInvites.add(invite);
        invite.setSentTo(this);
        return this;
    }

    public CSUser removeReceivedInvites(Invite invite) {
        this.receivedInvites.remove(invite);
        invite.setSentTo(null);
        return this;
    }

    public void setReceivedInvites(Set<Invite> invites) {
        this.receivedInvites = invites;
    }

    public Set<Invite> getSentInvites() {
        return sentInvites;
    }

    public CSUser sentInvites(Set<Invite> invites) {
        this.sentInvites = invites;
        return this;
    }

    public CSUser addSentInvites(Invite invite) {
        this.sentInvites.add(invite);
        invite.setSentFrom(this);
        return this;
    }

    public CSUser removeSentInvites(Invite invite) {
        this.sentInvites.remove(invite);
        invite.setSentFrom(null);
        return this;
    }

    public void setSentInvites(Set<Invite> invites) {
        this.sentInvites = invites;
    }

    public Cost getPaid() {
        return paid;
    }

    public CSUser paid(Cost cost) {
        this.paid = cost;
        return this;
    }

    public void setPaid(Cost cost) {
        this.paid = cost;
    }

    public Set<CSGroup> getGroups() {
        return groups;
    }

    public CSUser groups(Set<CSGroup> cSGroups) {
        this.groups = cSGroups;
        return this;
    }

    public CSUser addGroups(CSGroup cSGroup) {
        this.groups.add(cSGroup);
        cSGroup.getMembers().add(this);
        return this;
    }

    public CSUser removeGroups(CSGroup cSGroup) {
        this.groups.remove(cSGroup);
        cSGroup.getMembers().remove(this);
        return this;
    }

    public void setGroups(Set<CSGroup> cSGroups) {
        this.groups = cSGroups;
    }

    public Set<CSGroup> getAdminOfs() {
        return adminOfs;
    }

    public CSUser adminOfs(Set<CSGroup> cSGroups) {
        this.adminOfs = cSGroups;
        return this;
    }

    public CSUser addAdminOf(CSGroup cSGroup) {
        this.adminOfs.add(cSGroup);
        cSGroup.getAdmins().add(this);
        return this;
    }

    public CSUser removeAdminOf(CSGroup cSGroup) {
        this.adminOfs.remove(cSGroup);
        cSGroup.getAdmins().remove(this);
        return this;
    }

    public void setAdminOfs(Set<CSGroup> cSGroups) {
        this.adminOfs = cSGroups;
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
        CSUser cSUser = (CSUser) o;
        if (cSUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cSUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CSUser{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
