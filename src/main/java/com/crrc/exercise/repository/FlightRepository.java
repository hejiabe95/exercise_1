package com.crrc.exercise.repository;

import com.crrc.exercise.domain.Flight;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Flight entity.
 * @author hejiabei
 * @date 2018/11/9
 */
@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

}
