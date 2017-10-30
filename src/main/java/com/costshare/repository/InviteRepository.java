package com.costshare.repository;

import com.costshare.domain.Invite;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Invite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {

}
