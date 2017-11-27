package com.costshare.repository;

import com.costshare.domain.CSUser;
import com.costshare.domain.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CSUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CSUserRepository extends JpaRepository<CSUser, Long> {
    @Query(value = "SELECT * FROM cs_user where cs_user.user_name_id IN (SELECT id FROM jhi_user WHERE jhi_user.login LIKE :login)", nativeQuery = true)
    CSUser findOneByAuthenticatedJHiUser(@Param("login") String login);
}
