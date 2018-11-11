package com.crrc.exercise.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Flight.
 * @author hejiabei
 * @date 2018/11/9
 */
@Entity
@Table(name = "flight")
public class Flight implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "flight_code", nullable = false)
    private String flightCode;

    @NotNull
    @Column(name = "departure_date", nullable = false)
    private Instant departureDate;

    @NotNull
    @Min(value = 0, message = "no fcs rest!!")
    @Column(name = "fcs_remain", nullable = false)
    private Integer fcsRemain;

    @NotNull
    @Min(value = 0, message = "no ecs rest!!")
    @Column(name = "ecs_remain", nullable = false)
    private Integer ecsRemain;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public Flight flightCode(String flightCode) {
        this.flightCode = flightCode;
        return this;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public Instant getDepartureDate() {
        return departureDate;
    }

    public Flight departureDate(Instant departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    public void setDepartureDate(Instant departureDate) {
        this.departureDate = departureDate;
    }

    public Integer getFcsRemain() {
        return fcsRemain;
    }

    public Flight fcsRemain(Integer fcsRemain) {
        this.fcsRemain = fcsRemain;
        return this;
    }

    public void setFcsRemain(Integer fcsRemain) {
        this.fcsRemain = fcsRemain;
    }

    public Integer getEcsRemain() {
        return ecsRemain;
    }

    public Flight ecsRemain(Integer ecsRemain) {
        this.ecsRemain = ecsRemain;
        return this;
    }

    public void setEcsRemain(Integer ecsRemain) {
        this.ecsRemain = ecsRemain;
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
        Flight flight = (Flight) o;
        if (flight.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), flight.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Flight{" +
            "id=" + getId() +
            ", flightCode='" + getFlightCode() + "'" +
            ", departureDate='" + getDepartureDate() + "'" +
            ", fcsRemain=" + getFcsRemain() +
            ", ecsRemain=" + getEcsRemain() +
            "}";
    }
}
