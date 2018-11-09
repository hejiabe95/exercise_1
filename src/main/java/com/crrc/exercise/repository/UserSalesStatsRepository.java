package com.crrc.exercise.repository;

import com.crrc.exercise.domain.UserSalesStats;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserSalesStats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserSalesStatsRepository extends JpaRepository<UserSalesStats, Long> {

}
