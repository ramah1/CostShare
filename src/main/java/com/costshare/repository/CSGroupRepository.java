package com.costshare.repository;

import com.costshare.domain.CSGroup;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the CSGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CSGroupRepository extends JpaRepository<CSGroup, Long> {
    @Query("select distinct cs_group from CSGroup cs_group left join fetch cs_group.members left join fetch cs_group.admins")
    List<CSGroup> findAllWithEagerRelationships();

    @Query("select cs_group from CSGroup cs_group left join fetch cs_group.members left join fetch cs_group.admins where cs_group.id =:id")
    CSGroup findOneWithEagerRelationships(@Param("id") Long id);

}
