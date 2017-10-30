package com.costshare.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Invite.
 */
@Entity
@Table(name = "invite")
public class Invite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 1000)
    @Column(name = "jhi_comment", length = 1000, nullable = false)
    private String comment;

    @Column(name = "accepted")
    private Boolean accepted;

    @ManyToOne
    private CSGroup group;

    @ManyToOne
    private CSUser sentTo;

    @ManyToOne
    private CSUser sentFrom;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public Invite comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean isAccepted() {
        return accepted;
    }

    public Invite accepted(Boolean accepted) {
        this.accepted = accepted;
        return this;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public CSGroup getGroup() {
        return group;
    }

    public Invite group(CSGroup cSGroup) {
        this.group = cSGroup;
        return this;
    }

    public void setGroup(CSGroup cSGroup) {
        this.group = cSGroup;
    }

    public CSUser getSentTo() {
        return sentTo;
    }

    public Invite sentTo(CSUser cSUser) {
        this.sentTo = cSUser;
        return this;
    }

    public void setSentTo(CSUser cSUser) {
        this.sentTo = cSUser;
    }

    public CSUser getSentFrom() {
        return sentFrom;
    }

    public Invite sentFrom(CSUser cSUser) {
        this.sentFrom = cSUser;
        return this;
    }

    public void setSentFrom(CSUser cSUser) {
        this.sentFrom = cSUser;
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
        Invite invite = (Invite) o;
        if (invite.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invite.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Invite{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", accepted='" + isAccepted() + "'" +
            "}";
    }
}
