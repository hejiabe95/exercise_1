package com.crrc.exercise.repository;

import com.crrc.exercise.domain.Ticket;
import com.crrc.exercise.domain.UserSalesStats;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the UserSalesStats entity.
 */
@Repository
public interface UserSalesStatsRepository extends JpaRepository<UserSalesStats, Long> {

    @Query("select userSalesStats from UserSalesStats userSalesStats where userSalesStats.user.login = ?#{principal.username}")
    UserSalesStats findByUserIsCurrentUser();
    @Query("select userSalesStats from UserSalesStats userSalesStats where userSalesStats.user.login = ?1")
    UserSalesStats findByUserName(String userLoginName);

}
