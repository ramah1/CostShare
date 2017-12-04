package com.costshare.web.rest;

import com.costshare.CostshareApp;

import com.costshare.domain.Cost;
import com.costshare.repository.CostRepository;
import com.costshare.service.CostService;
import com.costshare.service.dto.CostDTO;
import com.costshare.service.mapper.CostMapper;
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
 * Test class for the CostResource REST controller.
 *
 * @see CostResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CostshareApp.class)
public class CostResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_SUM = 1D;
    private static final Double UPDATED_SUM = 2D;

    @Autowired
    private CostRepository costRepository;

    @Autowired
    private CostMapper costMapper;

    @Autowired
    private CostService costService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCostMockMvc;

    private Cost cost;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CostResource costResource = new CostResource(costService);
        this.restCostMockMvc = MockMvcBuilders.standaloneSetup(costResource)
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
    public static Cost createEntity(EntityManager em) {
        Cost cost = new Cost()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .sum(DEFAULT_SUM);
        return cost;
    }

    @Before
    public void initTest() {
        cost = createEntity(em);
    }

    @Test
    @Transactional
    public void createCost() throws Exception {
        int databaseSizeBeforeCreate = costRepository.findAll().size();

        // Create the Cost
        CostDTO costDTO = costMapper.toDto(cost);
        restCostMockMvc.perform(post("/api/costs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costDTO)))
            .andExpect(status().isCreated());

        // Validate the Cost in the database
        List<Cost> costList = costRepository.findAll();
        assertThat(costList).hasSize(databaseSizeBeforeCreate + 1);
        Cost testCost = costList.get(costList.size() - 1);
        assertThat(testCost.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCost.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCost.getSum()).isEqualTo(DEFAULT_SUM);
    }

    @Test
    @Transactional
    public void createCostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = costRepository.findAll().size();

        // Create the Cost with an existing ID
        cost.setId(1L);
        CostDTO costDTO = costMapper.toDto(cost);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCostMockMvc.perform(post("/api/costs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cost in the database
        List<Cost> costList = costRepository.findAll();
        assertThat(costList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = costRepository.findAll().size();
        // set the field null
        cost.setName(null);

        // Create the Cost, which fails.
        CostDTO costDTO = costMapper.toDto(cost);

        restCostMockMvc.perform(post("/api/costs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costDTO)))
            .andExpect(status().isBadRequest());

        List<Cost> costList = costRepository.findAll();
        assertThat(costList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSumIsRequired() throws Exception {
        // set the field null
        cost.setSum(null);

        // Create the Cost, which fails.
        CostDTO costDTO = costMapper.toDto(cost);

        restCostMockMvc.perform(post("/api/costs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costDTO)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void checkSumIsNegative() throws Exception {
        // set the sum -1
        cost.setSum(-1D);

        // Create the Cost, which fails.
        CostDTO costDTO = costMapper.toDto(cost);

        restCostMockMvc.perform(post("/api/costs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costDTO)))
            .andExpect(status().isOk());
    }
    @Test
    public void checkSumOnBoundary() throws Exception {
        // set the sum 0
        cost.setSum(0D);

        // Create the Cost, which fails.
        CostDTO costDTO = costMapper.toDto(cost);

        restCostMockMvc.perform(post("/api/costs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costDTO)))
            .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void getAllCosts() throws Exception {
        // Initialize the database
        costRepository.saveAndFlush(cost);

        // Get all the costList
        restCostMockMvc.perform(get("/api/costs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cost.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].sum").value(hasItem(DEFAULT_SUM.doubleValue())));
    }

    @Test
    @Transactional
    public void getCost() throws Exception {
        // Initialize the database
        costRepository.saveAndFlush(cost);

        // Get the cost
        restCostMockMvc.perform(get("/api/costs/{id}", cost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cost.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.sum").value(DEFAULT_SUM.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCost() throws Exception {
        // Get the cost
        restCostMockMvc.perform(get("/api/costs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCost() throws Exception {
        // Initialize the database
        costRepository.saveAndFlush(cost);
        int databaseSizeBeforeUpdate = costRepository.findAll().size();

        // Update the cost
        Cost updatedCost = costRepository.findOne(cost.getId());
        updatedCost
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .sum(UPDATED_SUM);
        CostDTO costDTO = costMapper.toDto(updatedCost);

        restCostMockMvc.perform(put("/api/costs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costDTO)))
            .andExpect(status().isOk());

        // Validate the Cost in the database
        List<Cost> costList = costRepository.findAll();
        assertThat(costList).hasSize(databaseSizeBeforeUpdate);
        Cost testCost = costList.get(costList.size() - 1);
        assertThat(testCost.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCost.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCost.getSum()).isEqualTo(UPDATED_SUM);
    }

    @Test
    @Transactional
    public void updateNonExistingCost() throws Exception {
        int databaseSizeBeforeUpdate = costRepository.findAll().size();

        // Create the Cost
        CostDTO costDTO = costMapper.toDto(cost);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCostMockMvc.perform(put("/api/costs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costDTO)))
            .andExpect(status().isCreated());

        // Validate the Cost in the database
        List<Cost> costList = costRepository.findAll();
        assertThat(costList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCost() throws Exception {
        // Initialize the database
        costRepository.saveAndFlush(cost);
        int databaseSizeBeforeDelete = costRepository.findAll().size();

        // Get the cost
        restCostMockMvc.perform(delete("/api/costs/{id}", cost.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cost> costList = costRepository.findAll();
        assertThat(costList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cost.class);
        Cost cost1 = new Cost();
        cost1.setId(1L);
        Cost cost2 = new Cost();
        cost2.setId(cost1.getId());
        assertThat(cost1).isEqualTo(cost2);
        cost2.setId(2L);
        assertThat(cost1).isNotEqualTo(cost2);
        cost1.setId(null);
        assertThat(cost1).isNotEqualTo(cost2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CostDTO.class);
        CostDTO costDTO1 = new CostDTO();
        costDTO1.setId(1L);
        CostDTO costDTO2 = new CostDTO();
        assertThat(costDTO1).isNotEqualTo(costDTO2);
        costDTO2.setId(costDTO1.getId());
        assertThat(costDTO1).isEqualTo(costDTO2);
        costDTO2.setId(2L);
        assertThat(costDTO1).isNotEqualTo(costDTO2);
        costDTO1.setId(null);
        assertThat(costDTO1).isNotEqualTo(costDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(costMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(costMapper.fromId(null)).isNull();
    }
}
