package com.costshare.repository;

import com.costshare.domain.Invite;
import com.costshare.service.dto.InviteDTO;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Invite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {


    List<Invite> findAllBySentTo_Id(Long id);
}
