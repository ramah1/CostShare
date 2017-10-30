package com.costshare.repository;

import com.costshare.domain.CSUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CSUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CSUserRepository extends JpaRepository<CSUser, Long> {

}
