package com.crrc.exercise.repository;

import com.crrc.exercise.domain.UserSalesStats;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserSalesStats entity.
 * @author hejiabei
 * @date 2018/11/9
 */
@Repository
public interface UserSalesStatsRepository extends JpaRepository<UserSalesStats, Long> {

}
