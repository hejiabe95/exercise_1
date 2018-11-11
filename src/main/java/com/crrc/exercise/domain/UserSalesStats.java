package com.crrc.exercise.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A UserSalesStats.
 * @author hejiabei
 * @date 2018/11/9
 */
@Entity
@Table(name = "user_sales_stats")
public class UserSalesStats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "sales_amout", nullable = false)
    private Integer salesAmout;

    @NotNull
    @Column(name = "ticket_amout", nullable = false)
    private Integer ticketAmout;

    @NotNull
    @Column(name = "fcs_amout", nullable = false)
    private Integer fcsAmout;

    @NotNull
    @Column(name = "ecs_amout", nullable = false)
    private Integer ecsAmout;

    @OneToOne    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSalesAmout() {
        return salesAmout;
    }

    public UserSalesStats salesAmout(Integer salesAmout) {
        this.salesAmout = salesAmout;
        return this;
    }

    public void setSalesAmout(Integer salesAmout) {
        this.salesAmout = salesAmout;
    }

    public Integer getTicketAmout() {
        return ticketAmout;
    }

    public UserSalesStats ticketAmout(Integer ticketAmout) {
        this.ticketAmout = ticketAmout;
        return this;
    }

    public void setTicketAmout(Integer ticketAmout) {
        this.ticketAmout = ticketAmout;
    }

    public Integer getFcsAmout() {
        return fcsAmout;
    }

    public UserSalesStats fcsAmout(Integer fcsAmout) {
        this.fcsAmout = fcsAmout;
        return this;
    }

    public void setFcsAmout(Integer fcsAmout) {
        this.fcsAmout = fcsAmout;
    }

    public Integer getEcsAmout() {
        return ecsAmout;
    }

    public UserSalesStats ecsAmout(Integer ecsAmout) {
        this.ecsAmout = ecsAmout;
        return this;
    }

    public void setEcsAmout(Integer ecsAmout) {
        this.ecsAmout = ecsAmout;
    }

    public User getUser() {
        return user;
    }

    public UserSalesStats user(User user) {
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
        UserSalesStats userSalesStats = (UserSalesStats) o;
        if (userSalesStats.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userSalesStats.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserSalesStats{" +
            "id=" + getId() +
            ", salesAmout=" + getSalesAmout() +
            ", ticketAmout=" + getTicketAmout() +
            ", fcsAmout=" + getFcsAmout() +
            ", ecsAmout=" + getEcsAmout() +
            "}";
    }
}
