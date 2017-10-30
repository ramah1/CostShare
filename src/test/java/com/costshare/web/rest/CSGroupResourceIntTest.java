package com.costshare.web.rest;

import com.costshare.CostshareApp;

import com.costshare.domain.CSGroup;
import com.costshare.repository.CSGroupRepository;
import com.costshare.service.CSGroupService;
import com.costshare.service.dto.CSGroupDTO;
import com.costshare.service.mapper.CSGroupMapper;
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
 * Test class for the CSGroupResource REST controller.
 *
 * @see CSGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CostshareApp.class)
public class CSGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CSGroupRepository cSGroupRepository;

    @Autowired
    private CSGroupMapper cSGroupMapper;

    @Autowired
    private CSGroupService cSGroupService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCSGroupMockMvc;

    private CSGroup cSGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CSGroupResource cSGroupResource = new CSGroupResource(cSGroupService);
        this.restCSGroupMockMvc = MockMvcBuilders.standaloneSetup(cSGroupResource)
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
    public static CSGroup createEntity(EntityManager em) {
        CSGroup cSGroup = new CSGroup()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return cSGroup;
    }

    @Before
    public void initTest() {
        cSGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createCSGroup() throws Exception {
        int databaseSizeBeforeCreate = cSGroupRepository.findAll().size();

        // Create the CSGroup
        CSGroupDTO cSGroupDTO = cSGroupMapper.toDto(cSGroup);
        restCSGroupMockMvc.perform(post("/api/c-s-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cSGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the CSGroup in the database
        List<CSGroup> cSGroupList = cSGroupRepository.findAll();
        assertThat(cSGroupList).hasSize(databaseSizeBeforeCreate + 1);
        CSGroup testCSGroup = cSGroupList.get(cSGroupList.size() - 1);
        assertThat(testCSGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCSGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCSGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cSGroupRepository.findAll().size();

        // Create the CSGroup with an existing ID
        cSGroup.setId(1L);
        CSGroupDTO cSGroupDTO = cSGroupMapper.toDto(cSGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCSGroupMockMvc.perform(post("/api/c-s-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cSGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CSGroup in the database
        List<CSGroup> cSGroupList = cSGroupRepository.findAll();
        assertThat(cSGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cSGroupRepository.findAll().size();
        // set the field null
        cSGroup.setName(null);

        // Create the CSGroup, which fails.
        CSGroupDTO cSGroupDTO = cSGroupMapper.toDto(cSGroup);

        restCSGroupMockMvc.perform(post("/api/c-s-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cSGroupDTO)))
            .andExpect(status().isBadRequest());

        List<CSGroup> cSGroupList = cSGroupRepository.findAll();
        assertThat(cSGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCSGroups() throws Exception {
        // Initialize the database
        cSGroupRepository.saveAndFlush(cSGroup);

        // Get all the cSGroupList
        restCSGroupMockMvc.perform(get("/api/c-s-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cSGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCSGroup() throws Exception {
        // Initialize the database
        cSGroupRepository.saveAndFlush(cSGroup);

        // Get the cSGroup
        restCSGroupMockMvc.perform(get("/api/c-s-groups/{id}", cSGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cSGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCSGroup() throws Exception {
        // Get the cSGroup
        restCSGroupMockMvc.perform(get("/api/c-s-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCSGroup() throws Exception {
        // Initialize the database
        cSGroupRepository.saveAndFlush(cSGroup);
        int databaseSizeBeforeUpdate = cSGroupRepository.findAll().size();

        // Update the cSGroup
        CSGroup updatedCSGroup = cSGroupRepository.findOne(cSGroup.getId());
        updatedCSGroup
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        CSGroupDTO cSGroupDTO = cSGroupMapper.toDto(updatedCSGroup);

        restCSGroupMockMvc.perform(put("/api/c-s-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cSGroupDTO)))
            .andExpect(status().isOk());

        // Validate the CSGroup in the database
        List<CSGroup> cSGroupList = cSGroupRepository.findAll();
        assertThat(cSGroupList).hasSize(databaseSizeBeforeUpdate);
        CSGroup testCSGroup = cSGroupList.get(cSGroupList.size() - 1);
        assertThat(testCSGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCSGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCSGroup() throws Exception {
        int databaseSizeBeforeUpdate = cSGroupRepository.findAll().size();

        // Create the CSGroup
        CSGroupDTO cSGroupDTO = cSGroupMapper.toDto(cSGroup);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCSGroupMockMvc.perform(put("/api/c-s-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cSGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the CSGroup in the database
        List<CSGroup> cSGroupList = cSGroupRepository.findAll();
        assertThat(cSGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCSGroup() throws Exception {
        // Initialize the database
        cSGroupRepository.saveAndFlush(cSGroup);
        int databaseSizeBeforeDelete = cSGroupRepository.findAll().size();

        // Get the cSGroup
        restCSGroupMockMvc.perform(delete("/api/c-s-groups/{id}", cSGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CSGroup> cSGroupList = cSGroupRepository.findAll();
        assertThat(cSGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CSGroup.class);
        CSGroup cSGroup1 = new CSGroup();
        cSGroup1.setId(1L);
        CSGroup cSGroup2 = new CSGroup();
        cSGroup2.setId(cSGroup1.getId());
        assertThat(cSGroup1).isEqualTo(cSGroup2);
        cSGroup2.setId(2L);
        assertThat(cSGroup1).isNotEqualTo(cSGroup2);
        cSGroup1.setId(null);
        assertThat(cSGroup1).isNotEqualTo(cSGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CSGroupDTO.class);
        CSGroupDTO cSGroupDTO1 = new CSGroupDTO();
        cSGroupDTO1.setId(1L);
        CSGroupDTO cSGroupDTO2 = new CSGroupDTO();
        assertThat(cSGroupDTO1).isNotEqualTo(cSGroupDTO2);
        cSGroupDTO2.setId(cSGroupDTO1.getId());
        assertThat(cSGroupDTO1).isEqualTo(cSGroupDTO2);
        cSGroupDTO2.setId(2L);
        assertThat(cSGroupDTO1).isNotEqualTo(cSGroupDTO2);
        cSGroupDTO1.setId(null);
        assertThat(cSGroupDTO1).isNotEqualTo(cSGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cSGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cSGroupMapper.fromId(null)).isNull();
    }
}
