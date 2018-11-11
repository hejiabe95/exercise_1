package com.crrc.exercise.service;

import com.crrc.exercise.domain.Flight;
import com.crrc.exercise.domain.Ticket;
import com.crrc.exercise.domain.enumeration.SeatLevel;
import com.crrc.exercise.repository.FlightRepository;
import com.crrc.exercise.repository.TicketRepository;
import com.crrc.exercise.web.rest.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URISyntaxException;


/**
 * Service class for managing Ticket.
 * @author hejiabei
 * @date 2018/11/9
 */
@Service
@Transactional
public class TicketSevice {

    private static final String ENTITY_NAME = "ticket";
    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;

    public TicketSevice(TicketRepository ticketRepository, FlightRepository flightRepository) {
        this.ticketRepository = ticketRepository;
        this.flightRepository = flightRepository;
    }

    public Ticket createTicket(Ticket ticket) throws URISyntaxException
    {
        Flight flight = ticket.getFlight();
        SeatLevel seatLevel = ticket.getSeatLevel();
        Integer ecsRemain = flight.getEcsRemain();
        ecsRemain = ecsRemain-1;
        Integer fcsRemain = flight.getFcsRemain();
        fcsRemain = fcsRemain-1;
        switch (seatLevel)
        {
            case ECS:
                if(ecsRemain<0)
                {
                    throw new BadRequestAlertException("No economic-class!", ENTITY_NAME, "ecszero");
                }
                else {
                    flight.setEcsRemain(ecsRemain); break;
                }

            case FCS:
                if(fcsRemain<0)
                {
                    throw new BadRequestAlertException("No frist-class!", ENTITY_NAME, "fcszero");
                }
                else {
                    flight.setFcsRemain(fcsRemain); break;
                }
                default:
        }
        flightRepository.save(flight);
        ticket.setFlight(flight);
        return ticketRepository.save(ticket);
    }
}
