package com.costshare.repository;

import com.costshare.domain.Cost;
import com.costshare.domain.UserCost;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Cost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CostRepository extends JpaRepository<Cost, Long> {
    public List<Cost> findAllByPaidBy(Long id);
}
