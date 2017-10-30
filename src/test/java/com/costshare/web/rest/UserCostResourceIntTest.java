package com.costshare.web.rest;

import com.costshare.CostshareApp;

import com.costshare.domain.UserCost;
import com.costshare.repository.UserCostRepository;
import com.costshare.service.UserCostService;
import com.costshare.service.dto.UserCostDTO;
import com.costshare.service.mapper.UserCostMapper;
import com.costshare.web.rest.errors.ExceptionTranslator;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserCostResource REST controller.
 *
 * @see UserCostResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CostshareApp.class)
public class UserCostResourceIntTest {

    private static final Double DEFAULT_MULTIPLIER = 1D;
    private static final Double UPDATED_MULTIPLIER = 2D;

    @Autowired
    private UserCostRepository userCostRepository;

    @Autowired
    private UserCostMapper userCostMapper;

    @Autowired
    private UserCostService userCostService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserCostMockMvc;

    private UserCost userCost;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserCostResource userCostResource = new UserCostResource(userCostService);
        this.restUserCostMockMvc = MockMvcBuilders.standaloneSetup(userCostResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserCost createEntity(EntityManager em) {
        UserCost userCost = new UserCost()
            .multiplier(DEFAULT_MULTIPLIER);
        return userCost;
    }

    @Before
    public void initTest() {
        userCost = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserCost() throws Exception {
        int databaseSizeBeforeCreate = userCostRepository.findAll().size();

        // Create the UserCost
        UserCostDTO userCostDTO = userCostMapper.toDto(userCost);
        restUserCostMockMvc.perform(post("/api/user-costs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userCostDTO)))
            .andExpect(status().isCreated());

        // Validate the UserCost in the database
        List<UserCost> userCostList = userCostRepository.findAll();
        assertThat(userCostList).hasSize(databaseSizeBeforeCreate + 1);
        UserCost testUserCost = userCostList.get(userCostList.size() - 1);
        assertThat(testUserCost.getMultiplier()).isEqualTo(DEFAULT_MULTIPLIER);
    }

    @Test
    @Transactional
    public void createUserCostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userCostRepository.findAll().size();

        // Create the UserCost with an existing ID
        userCost.setId(1L);
        UserCostDTO userCostDTO = userCostMapper.toDto(userCost);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserCostMockMvc.perform(post("/api/user-costs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userCostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserCost in the database
        List<UserCost> userCostList = userCostRepository.findAll();
        assertThat(userCostList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMultiplierIsRequired() throws Exception {
        int databaseSizeBeforeTest = userCostRepository.findAll().size();
        // set the field null
        userCost.setMultiplier(null);

        // Create the UserCost, which fails.
        UserCostDTO userCostDTO = userCostMapper.toDto(userCost);

        restUserCostMockMvc.perform(post("/api/user-costs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userCostDTO)))
            .andExpect(status().isBadRequest());

        List<UserCost> userCostList = userCostRepository.findAll();
        assertThat(userCostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserCosts() throws Exception {
        // Initialize the database
        userCostRepository.saveAndFlush(userCost);

        // Get all the userCostList
        restUserCostMockMvc.perform(get("/api/user-costs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userCost.getId().intValue())))
            .andExpect(jsonPath("$.[*].multiplier").value(hasItem(DEFAULT_MULTIPLIER.doubleValue())));
    }

    @Test
    @Transactional
    public void getUserCost() throws Exception {
        // Initialize the database
        userCostRepository.saveAndFlush(userCost);

        // Get the userCost
        restUserCostMockMvc.perform(get("/api/user-costs/{id}", userCost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userCost.getId().intValue()))
            .andExpect(jsonPath("$.multiplier").value(DEFAULT_MULTIPLIER.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserCost() throws Exception {
        // Get the userCost
        restUserCostMockMvc.perform(get("/api/user-costs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserCost() throws Exception {
        // Initialize the database
        userCostRepository.saveAndFlush(userCost);
        int databaseSizeBeforeUpdate = userCostRepository.findAll().size();

        // Update the userCost
        UserCost updatedUserCost = userCostRepository.findOne(userCost.getId());
        updatedUserCost
            .multiplier(UPDATED_MULTIPLIER);
        UserCostDTO userCostDTO = userCostMapper.toDto(updatedUserCost);

        restUserCostMockMvc.perform(put("/api/user-costs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userCostDTO)))
            .andExpect(status().isOk());

        // Validate the UserCost in the database
        List<UserCost> userCostList = userCostRepository.findAll();
        assertThat(userCostList).hasSize(databaseSizeBeforeUpdate);
        UserCost testUserCost = userCostList.get(userCostList.size() - 1);
        assertThat(testUserCost.getMultiplier()).isEqualTo(UPDATED_MULTIPLIER);
    }

    @Test
    @Transactional
    public void updateNonExistingUserCost() throws Exception {
        int databaseSizeBeforeUpdate = userCostRepository.findAll().size();

        // Create the UserCost
        UserCostDTO userCostDTO = userCostMapper.toDto(userCost);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserCostMockMvc.perform(put("/api/user-costs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userCostDTO)))
            .andExpect(status().isCreated());

        // Validate the UserCost in the database
        List<UserCost> userCostList = userCostRepository.findAll();
        assertThat(userCostList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserCost() throws Exception {
        // Initialize the database
        userCostRepository.saveAndFlush(userCost);
        int databaseSizeBeforeDelete = userCostRepository.findAll().size();

        // Get the userCost
        restUserCostMockMvc.perform(delete("/api/user-costs/{id}", userCost.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserCost> userCostList = userCostRepository.findAll();
        assertThat(userCostList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserCost.class);
        UserCost userCost1 = new UserCost();
        userCost1.setId(1L);
        UserCost userCost2 = new UserCost();
        userCost2.setId(userCost1.getId());
        assertThat(userCost1).isEqualTo(userCost2);
        userCost2.setId(2L);
        assertThat(userCost1).isNotEqualTo(userCost2);
        userCost1.setId(null);
        assertThat(userCost1).isNotEqualTo(userCost2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserCostDTO.class);
        UserCostDTO userCostDTO1 = new UserCostDTO();
        userCostDTO1.setId(1L);
        UserCostDTO userCostDTO2 = new UserCostDTO();
        assertThat(userCostDTO1).isNotEqualTo(userCostDTO2);
        userCostDTO2.setId(userCostDTO1.getId());
        assertThat(userCostDTO1).isEqualTo(userCostDTO2);
        userCostDTO2.setId(2L);
        assertThat(userCostDTO1).isNotEqualTo(userCostDTO2);
        userCostDTO1.setId(null);
        assertThat(userCostDTO1).isNotEqualTo(userCostDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userCostMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userCostMapper.fromId(null)).isNull();
    }
}
