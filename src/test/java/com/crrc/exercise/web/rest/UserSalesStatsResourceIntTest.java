package com.crrc.exercise.web.rest;

import com.crrc.exercise.ExerciseApp;

import com.crrc.exercise.domain.UserSalesStats;
import com.crrc.exercise.repository.UserSalesStatsRepository;
import com.crrc.exercise.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.crrc.exercise.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserSalesStatsResource REST controller.
 *
 * @see UserSalesStatsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExerciseApp.class)
public class UserSalesStatsResourceIntTest {

    private static final Integer DEFAULT_SALES_AMOUT = 1;
    private static final Integer UPDATED_SALES_AMOUT = 2;

    private static final Integer DEFAULT_TICKET_AMOUT = 1;
    private static final Integer UPDATED_TICKET_AMOUT = 2;

    private static final Integer DEFAULT_FCS_AMOUT = 1;
    private static final Integer UPDATED_FCS_AMOUT = 2;

    private static final Integer DEFAULT_ECS_AMOUT = 1;
    private static final Integer UPDATED_ECS_AMOUT = 2;

    @Autowired
    private UserSalesStatsRepository userSalesStatsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserSalesStatsMockMvc;

    private UserSalesStats userSalesStats;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserSalesStatsResource userSalesStatsResource = new UserSalesStatsResource(userSalesStatsRepository);
        this.restUserSalesStatsMockMvc = MockMvcBuilders.standaloneSetup(userSalesStatsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSalesStats createEntity(EntityManager em) {
        UserSalesStats userSalesStats = new UserSalesStats()
            .salesAmout(DEFAULT_SALES_AMOUT)
            .ticketAmout(DEFAULT_TICKET_AMOUT)
            .fcsAmout(DEFAULT_FCS_AMOUT)
            .ecsAmout(DEFAULT_ECS_AMOUT);
        return userSalesStats;
    }

    @Before
    public void initTest() {
        userSalesStats = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserSalesStats() throws Exception {
        int databaseSizeBeforeCreate = userSalesStatsRepository.findAll().size();

        // Create the UserSalesStats
        restUserSalesStatsMockMvc.perform(post("/api/user-sales-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userSalesStats)))
            .andExpect(status().isCreated());

        // Validate the UserSalesStats in the database
        List<UserSalesStats> userSalesStatsList = userSalesStatsRepository.findAll();
        assertThat(userSalesStatsList).hasSize(databaseSizeBeforeCreate + 1);
        UserSalesStats testUserSalesStats = userSalesStatsList.get(userSalesStatsList.size() - 1);
        assertThat(testUserSalesStats.getSalesAmout()).isEqualTo(DEFAULT_SALES_AMOUT);
        assertThat(testUserSalesStats.getTicketAmout()).isEqualTo(DEFAULT_TICKET_AMOUT);
        assertThat(testUserSalesStats.getFcsAmout()).isEqualTo(DEFAULT_FCS_AMOUT);
        assertThat(testUserSalesStats.getEcsAmout()).isEqualTo(DEFAULT_ECS_AMOUT);
    }

    @Test
    @Transactional
    public void createUserSalesStatsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userSalesStatsRepository.findAll().size();

        // Create the UserSalesStats with an existing ID
        userSalesStats.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserSalesStatsMockMvc.perform(post("/api/user-sales-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userSalesStats)))
            .andExpect(status().isBadRequest());

        // Validate the UserSalesStats in the database
        List<UserSalesStats> userSalesStatsList = userSalesStatsRepository.findAll();
        assertThat(userSalesStatsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSalesAmoutIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSalesStatsRepository.findAll().size();
        // set the field null
        userSalesStats.setSalesAmout(null);

        // Create the UserSalesStats, which fails.

        restUserSalesStatsMockMvc.perform(post("/api/user-sales-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userSalesStats)))
            .andExpect(status().isBadRequest());

        List<UserSalesStats> userSalesStatsList = userSalesStatsRepository.findAll();
        assertThat(userSalesStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTicketAmoutIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSalesStatsRepository.findAll().size();
        // set the field null
        userSalesStats.setTicketAmout(null);

        // Create the UserSalesStats, which fails.

        restUserSalesStatsMockMvc.perform(post("/api/user-sales-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userSalesStats)))
            .andExpect(status().isBadRequest());

        List<UserSalesStats> userSalesStatsList = userSalesStatsRepository.findAll();
        assertThat(userSalesStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFcsAmoutIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSalesStatsRepository.findAll().size();
        // set the field null
        userSalesStats.setFcsAmout(null);

        // Create the UserSalesStats, which fails.

        restUserSalesStatsMockMvc.perform(post("/api/user-sales-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userSalesStats)))
            .andExpect(status().isBadRequest());

        List<UserSalesStats> userSalesStatsList = userSalesStatsRepository.findAll();
        assertThat(userSalesStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEcsAmoutIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSalesStatsRepository.findAll().size();
        // set the field null
        userSalesStats.setEcsAmout(null);

        // Create the UserSalesStats, which fails.

        restUserSalesStatsMockMvc.perform(post("/api/user-sales-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userSalesStats)))
            .andExpect(status().isBadRequest());

        List<UserSalesStats> userSalesStatsList = userSalesStatsRepository.findAll();
        assertThat(userSalesStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserSalesStats() throws Exception {
        // Initialize the database
        userSalesStatsRepository.saveAndFlush(userSalesStats);

        // Get all the userSalesStatsList
        restUserSalesStatsMockMvc.perform(get("/api/user-sales-stats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userSalesStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].salesAmout").value(hasItem(DEFAULT_SALES_AMOUT)))
            .andExpect(jsonPath("$.[*].ticketAmout").value(hasItem(DEFAULT_TICKET_AMOUT)))
            .andExpect(jsonPath("$.[*].fcsAmout").value(hasItem(DEFAULT_FCS_AMOUT)))
            .andExpect(jsonPath("$.[*].ecsAmout").value(hasItem(DEFAULT_ECS_AMOUT)));
    }
    
    @Test
    @Transactional
    public void getUserSalesStats() throws Exception {
        // Initialize the database
        userSalesStatsRepository.saveAndFlush(userSalesStats);

        // Get the userSalesStats
        restUserSalesStatsMockMvc.perform(get("/api/user-sales-stats/{id}", userSalesStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userSalesStats.getId().intValue()))
            .andExpect(jsonPath("$.salesAmout").value(DEFAULT_SALES_AMOUT))
            .andExpect(jsonPath("$.ticketAmout").value(DEFAULT_TICKET_AMOUT))
            .andExpect(jsonPath("$.fcsAmout").value(DEFAULT_FCS_AMOUT))
            .andExpect(jsonPath("$.ecsAmout").value(DEFAULT_ECS_AMOUT));
    }

    @Test
    @Transactional
    public void getNonExistingUserSalesStats() throws Exception {
        // Get the userSalesStats
        restUserSalesStatsMockMvc.perform(get("/api/user-sales-stats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserSalesStats() throws Exception {
        // Initialize the database
        userSalesStatsRepository.saveAndFlush(userSalesStats);

        int databaseSizeBeforeUpdate = userSalesStatsRepository.findAll().size();

        // Update the userSalesStats
        UserSalesStats updatedUserSalesStats = userSalesStatsRepository.findById(userSalesStats.getId()).get();
        // Disconnect from session so that the updates on updatedUserSalesStats are not directly saved in db
        em.detach(updatedUserSalesStats);
        updatedUserSalesStats
            .salesAmout(UPDATED_SALES_AMOUT)
            .ticketAmout(UPDATED_TICKET_AMOUT)
            .fcsAmout(UPDATED_FCS_AMOUT)
            .ecsAmout(UPDATED_ECS_AMOUT);

        restUserSalesStatsMockMvc.perform(put("/api/user-sales-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserSalesStats)))
            .andExpect(status().isOk());

        // Validate the UserSalesStats in the database
        List<UserSalesStats> userSalesStatsList = userSalesStatsRepository.findAll();
        assertThat(userSalesStatsList).hasSize(databaseSizeBeforeUpdate);
        UserSalesStats testUserSalesStats = userSalesStatsList.get(userSalesStatsList.size() - 1);
        assertThat(testUserSalesStats.getSalesAmout()).isEqualTo(UPDATED_SALES_AMOUT);
        assertThat(testUserSalesStats.getTicketAmout()).isEqualTo(UPDATED_TICKET_AMOUT);
        assertThat(testUserSalesStats.getFcsAmout()).isEqualTo(UPDATED_FCS_AMOUT);
        assertThat(testUserSalesStats.getEcsAmout()).isEqualTo(UPDATED_ECS_AMOUT);
    }

    @Test
    @Transactional
    public void updateNonExistingUserSalesStats() throws Exception {
        int databaseSizeBeforeUpdate = userSalesStatsRepository.findAll().size();

        // Create the UserSalesStats

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserSalesStatsMockMvc.perform(put("/api/user-sales-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userSalesStats)))
            .andExpect(status().isBadRequest());

        // Validate the UserSalesStats in the database
        List<UserSalesStats> userSalesStatsList = userSalesStatsRepository.findAll();
        assertThat(userSalesStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserSalesStats() throws Exception {
        // Initialize the database
        userSalesStatsRepository.saveAndFlush(userSalesStats);

        int databaseSizeBeforeDelete = userSalesStatsRepository.findAll().size();

        // Get the userSalesStats
        restUserSalesStatsMockMvc.perform(delete("/api/user-sales-stats/{id}", userSalesStats.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserSalesStats> userSalesStatsList = userSalesStatsRepository.findAll();
        assertThat(userSalesStatsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserSalesStats.class);
        UserSalesStats userSalesStats1 = new UserSalesStats();
        userSalesStats1.setId(1L);
        UserSalesStats userSalesStats2 = new UserSalesStats();
        userSalesStats2.setId(userSalesStats1.getId());
        assertThat(userSalesStats1).isEqualTo(userSalesStats2);
        userSalesStats2.setId(2L);
        assertThat(userSalesStats1).isNotEqualTo(userSalesStats2);
        userSalesStats1.setId(null);
        assertThat(userSalesStats1).isNotEqualTo(userSalesStats2);
    }
}
