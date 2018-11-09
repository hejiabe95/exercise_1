package com.crrc.exercise.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.crrc.exercise.domain.enumeration.SeatLevel;

/**
 * A Ticket.
 */
@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "passenger_name", nullable = false)
    private String passengerName;

    @NotNull
    @Column(name = "order_date", nullable = false)
    private Instant orderDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "seat_level", nullable = false)
    private SeatLevel seatLevel;

    @NotNull
    @Column(name = "ticket_price", nullable = false)
    private Integer ticketPrice;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Flight flight;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public Ticket passengerName(String passengerName) {
        this.passengerName = passengerName;
        return this;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public Ticket orderDate(Instant orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public SeatLevel getSeatLevel() {
        return seatLevel;
    }

    public Ticket seatLevel(SeatLevel seatLevel) {
        this.seatLevel = seatLevel;
        return this;
    }

    public void setSeatLevel(SeatLevel seatLevel) {
        this.seatLevel = seatLevel;
    }

    public Integer getTicketPrice() {
        return ticketPrice;
    }

    public Ticket ticketPrice(Integer ticketPrice) {
        this.ticketPrice = ticketPrice;
        return this;
    }

    public void setTicketPrice(Integer ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Flight getFlight() {
        return flight;
    }

    public Ticket flight(Flight flight) {
        this.flight = flight;
        return this;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public User getUser() {
        return user;
    }

    public Ticket user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        if (ticket.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ticket.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ticket{" +
            "id=" + getId() +
            ", passengerName='" + getPassengerName() + "'" +
            ", orderDate='" + getOrderDate() + "'" +
            ", seatLevel='" + getSeatLevel() + "'" +
            ", ticketPrice=" + getTicketPrice() +
            "}";
    }
}
