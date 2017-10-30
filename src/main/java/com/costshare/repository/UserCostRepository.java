package com.costshare.repository;

import com.costshare.domain.UserCost;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserCost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserCostRepository extends JpaRepository<UserCost, Long> {

}
