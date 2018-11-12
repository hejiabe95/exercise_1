package com.crrc.exercise.service;

import com.crrc.exercise.domain.Flight;
import com.crrc.exercise.domain.Ticket;
import com.crrc.exercise.domain.User;
import com.crrc.exercise.domain.UserSalesStats;
import com.crrc.exercise.domain.enumeration.SeatLevel;
import com.crrc.exercise.repository.FlightRepository;
import com.crrc.exercise.repository.TicketRepository;
import com.crrc.exercise.repository.UserSalesStatsRepository;
import com.crrc.exercise.web.rest.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Optional;


/**
 * Service class for managing Ticket.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TicketSevice {

    private static final String ENTITY_NAME = "ticket";
    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;
    private final UserSalesStatsRepository userSalesStatsRepository;

    public TicketSevice(TicketRepository ticketRepository, FlightRepository flightRepository, UserSalesStatsRepository userSalesStatsRepository) {
        this.ticketRepository = ticketRepository;
        this.flightRepository = flightRepository;
        this.userSalesStatsRepository = userSalesStatsRepository;
    }

    /**
     * Create a ticket, record to stats, and refresh the remain seat status for flight.
     *
     * @param ticket ticket to create
     * @return this new ticket
     */
    public Ticket createTicket(Ticket ticket) throws URISyntaxException
    {
        User user = ticket.getUser();
        UserSalesStats userSalesStats = userSalesStatsRepository.findByUserName(user.getLogin());
        if(userSalesStats==null)
        {
            //if it is the frist time, inti it.
            userSalesStats = new UserSalesStats();
            userSalesStats.inti(user);
        }

        Integer ticketPrice = ticket.getTicketPrice();



        Integer salesAmout = userSalesStats.getSalesAmout();
        Integer ticketAmout = userSalesStats.getTicketAmout();
        Integer ecsAmout = userSalesStats.getEcsAmout();
        Integer fcsAmout = userSalesStats.getFcsAmout();


        Flight flight = ticket.getFlight();
        SeatLevel seatLevel = ticket.getSeatLevel();
        Integer ecsRemain = flight.getEcsRemain();
        Integer fcsRemain = flight.getFcsRemain();

        switch (seatLevel)
        {
            case ECS:
                if(ecsRemain == 0)
                {
                    throw new BadRequestAlertException("No economic-class!", ENTITY_NAME, "ecszero");
                }
                else {
                    flight.setEcsRemain(ecsRemain-1); userSalesStats.setEcsAmout(ecsAmout+1);break;
                }

            case FCS:
                if(fcsRemain == 0)
                {
                    throw new BadRequestAlertException("No frist-class!", ENTITY_NAME, "fcszero");
                }
                else {
                    flight.setFcsRemain(fcsRemain-1); userSalesStats.setFcsAmout(fcsAmout+1);break;
                }
                default:
        }

        userSalesStats.setSalesAmout(salesAmout+ticketPrice);
        userSalesStats.setTicketAmout(ticketAmout+1);
        userSalesStatsRepository.save(userSalesStats);



        flightRepository.save(flight);
        ticket.setFlight(flight);

        return ticketRepository.save(ticket);
    }


    /**
     * Delete a ticket, record to stats, and refresh the remain seat status for flight.
     *
     * @param id ticket id to delete
     * @return boolean means no error, false means the ticket is invalid.
     */
    public boolean deleteTicket(Long id)
    {
        Ticket ticket = ticketRepository.findById(id).get();
        User user = ticket.getUser();
        UserSalesStats userSalesStats = userSalesStatsRepository.findByUserName(user.getLogin());

        if(userSalesStats==null)
        {
            //if it is the recorded ticket in stats, deleted directly.
            userSalesStats = new UserSalesStats();
            userSalesStats.inti(user);
            userSalesStatsRepository.save(userSalesStats);
            ticketRepository.deleteById(id);
            return false;
        }

        Integer ticketPrice = ticket.getTicketPrice();


        Integer salesAmout = userSalesStats.getSalesAmout();
        Integer ticketAmout = userSalesStats.getTicketAmout();
        Integer ecsAmout = userSalesStats.getEcsAmout();
        Integer fcsAmout = userSalesStats.getFcsAmout();


        Flight flight = ticket.getFlight();
        SeatLevel seatLevel = ticket.getSeatLevel();
        Integer ecsRemain = flight.getEcsRemain();
        Integer fcsRemain = flight.getFcsRemain();

        switch (seatLevel)
        {
            case ECS:
                    flight.setEcsRemain(ecsRemain+1); userSalesStats.setEcsAmout(ecsAmout-1);break;
            case FCS:
                    flight.setFcsRemain(fcsRemain+1); userSalesStats.setFcsAmout(fcsAmout-1);break;
            default:
        }

        userSalesStats.setSalesAmout(salesAmout-ticketPrice);
        userSalesStats.setTicketAmout(ticketAmout-1);

        if(!userSalesStats.selfCheck())
        {
            ticketRepository.deleteById(id);
            return false;
        }

        userSalesStatsRepository.save(userSalesStats);


        flightRepository.save(flight);
        ticket.setFlight(flight);


        ticketRepository.deleteById(id);
        return true;
    }

}
