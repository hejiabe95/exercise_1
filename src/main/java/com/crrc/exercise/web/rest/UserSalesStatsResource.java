package com.crrc.exercise.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.crrc.exercise.domain.User;
import com.crrc.exercise.domain.UserSalesStats;
import com.crrc.exercise.repository.UserSalesStatsRepository;
import com.crrc.exercise.web.rest.errors.BadRequestAlertException;
import com.crrc.exercise.web.rest.util.HeaderUtil;
import com.crrc.exercise.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserSalesStats.
 */
@RestController
@RequestMapping("/api")
public class UserSalesStatsResource {

    private final Logger log = LoggerFactory.getLogger(UserSalesStatsResource.class);

    private static final String ENTITY_NAME = "userSalesStats";

    private final UserSalesStatsRepository userSalesStatsRepository;

    public UserSalesStatsResource(UserSalesStatsRepository userSalesStatsRepository) {
        this.userSalesStatsRepository = userSalesStatsRepository;
    }

    /**
     * POST  /user-sales-stats : Create a new userSalesStats.
     *
     * @param userSalesStats the userSalesStats to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userSalesStats, or with status 400 (Bad Request) if the userSalesStats has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-sales-stats")
    @Timed
    public ResponseEntity<UserSalesStats> createUserSalesStats(@Valid @RequestBody UserSalesStats userSalesStats) throws URISyntaxException {
        log.debug("REST request to save UserSalesStats : {}", userSalesStats);
        if (userSalesStats.getId() != null) {
            throw new BadRequestAlertException("A new userSalesStats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserSalesStats result = userSalesStatsRepository.save(userSalesStats);
        return ResponseEntity.created(new URI("/api/user-sales-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-sales-stats : Updates an existing userSalesStats.
     *
     * @param userSalesStats the userSalesStats to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userSalesStats,
     * or with status 400 (Bad Request) if the userSalesStats is not valid,
     * or with status 500 (Internal Server Error) if the userSalesStats couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-sales-stats")
    @Timed
    public ResponseEntity<UserSalesStats> updateUserSalesStats(@Valid @RequestBody UserSalesStats userSalesStats) throws URISyntaxException {
        log.debug("REST request to update UserSalesStats : {}", userSalesStats);
        if (userSalesStats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserSalesStats result = userSalesStatsRepository.save(userSalesStats);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userSalesStats.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-sales-stats : get all the userSalesStats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userSalesStats in body
     */
    @GetMapping("/user-sales-stats")
    @Timed
    public ResponseEntity<List<UserSalesStats>> getAllUserSalesStats(Pageable pageable) {
        log.debug("REST request to get a page of UserSalesStats");
        Page<UserSalesStats> page = userSalesStatsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-sales-stats");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /user-sales-stats/:id : get the "id" userSalesStats.
     *
     * @param id the id of the userSalesStats to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userSalesStats, or with status 404 (Not Found)
     */
    @GetMapping("/user-sales-stats/{id}")
    @Timed
    public ResponseEntity<UserSalesStats> getUserSalesStats(@PathVariable Long id) {
        log.debug("REST request to get UserSalesStats : {}", id);
        Optional<UserSalesStats> userSalesStats = userSalesStatsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userSalesStats);
    }

    /**
     * DELETE  /user-sales-stats/:id : delete the "id" userSalesStats.
     *
     * @param id the id of the userSalesStats to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-sales-stats/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserSalesStats(@PathVariable Long id) {
        log.debug("REST request to delete UserSalesStats : {}", id);

        userSalesStatsRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
