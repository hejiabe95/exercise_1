package com.crrc.exercise.repository;

import com.crrc.exercise.domain.Ticket;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Ticket entity.
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("select ticket from Ticket ticket where ticket.user.login = ?#{principal.username}")
    List<Ticket> findByUserIsCurrentUser();
    @Query("select ticket from Ticket ticket where ticket.user.login = ?1")
    List<Ticket> findByUserName(String login);

}
