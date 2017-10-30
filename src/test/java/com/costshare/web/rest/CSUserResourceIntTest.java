package com.costshare.web.rest;

import com.costshare.CostshareApp;

import com.costshare.domain.CSUser;
import com.costshare.repository.CSUserRepository;
import com.costshare.service.CSUserService;
import com.costshare.service.dto.CSUserDTO;
import com.costshare.service.mapper.CSUserMapper;
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
 * Test class for the CSUserResource REST controller.
 *
 * @see CSUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CostshareApp.class)
public class CSUserResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CSUserRepository cSUserRepository;

    @Autowired
    private CSUserMapper cSUserMapper;

    @Autowired
    private CSUserService cSUserService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCSUserMockMvc;

    private CSUser cSUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CSUserResource cSUserResource = new CSUserResource(cSUserService);
        this.restCSUserMockMvc = MockMvcBuilders.standaloneSetup(cSUserResource)
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
    public static CSUser createEntity(EntityManager em) {
        CSUser cSUser = new CSUser()
            .name(DEFAULT_NAME);
        return cSUser;
    }

    @Before
    public void initTest() {
        cSUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createCSUser() throws Exception {
        int databaseSizeBeforeCreate = cSUserRepository.findAll().size();

        // Create the CSUser
        CSUserDTO cSUserDTO = cSUserMapper.toDto(cSUser);
        restCSUserMockMvc.perform(post("/api/c-s-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cSUserDTO)))
            .andExpect(status().isCreated());

        // Validate the CSUser in the database
        List<CSUser> cSUserList = cSUserRepository.findAll();
        assertThat(cSUserList).hasSize(databaseSizeBeforeCreate + 1);
        CSUser testCSUser = cSUserList.get(cSUserList.size() - 1);
        assertThat(testCSUser.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCSUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cSUserRepository.findAll().size();

        // Create the CSUser with an existing ID
        cSUser.setId(1L);
        CSUserDTO cSUserDTO = cSUserMapper.toDto(cSUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCSUserMockMvc.perform(post("/api/c-s-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cSUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CSUser in the database
        List<CSUser> cSUserList = cSUserRepository.findAll();
        assertThat(cSUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCSUsers() throws Exception {
        // Initialize the database
        cSUserRepository.saveAndFlush(cSUser);

        // Get all the cSUserList
        restCSUserMockMvc.perform(get("/api/c-s-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cSUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCSUser() throws Exception {
        // Initialize the database
        cSUserRepository.saveAndFlush(cSUser);

        // Get the cSUser
        restCSUserMockMvc.perform(get("/api/c-s-users/{id}", cSUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cSUser.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCSUser() throws Exception {
        // Get the cSUser
        restCSUserMockMvc.perform(get("/api/c-s-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCSUser() throws Exception {
        // Initialize the database
        cSUserRepository.saveAndFlush(cSUser);
        int databaseSizeBeforeUpdate = cSUserRepository.findAll().size();

        // Update the cSUser
        CSUser updatedCSUser = cSUserRepository.findOne(cSUser.getId());
        updatedCSUser
            .name(UPDATED_NAME);
        CSUserDTO cSUserDTO = cSUserMapper.toDto(updatedCSUser);

        restCSUserMockMvc.perform(put("/api/c-s-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cSUserDTO)))
            .andExpect(status().isOk());

        // Validate the CSUser in the database
        List<CSUser> cSUserList = cSUserRepository.findAll();
        assertThat(cSUserList).hasSize(databaseSizeBeforeUpdate);
        CSUser testCSUser = cSUserList.get(cSUserList.size() - 1);
        assertThat(testCSUser.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCSUser() throws Exception {
        int databaseSizeBeforeUpdate = cSUserRepository.findAll().size();

        // Create the CSUser
        CSUserDTO cSUserDTO = cSUserMapper.toDto(cSUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCSUserMockMvc.perform(put("/api/c-s-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cSUserDTO)))
            .andExpect(status().isCreated());

        // Validate the CSUser in the database
        List<CSUser> cSUserList = cSUserRepository.findAll();
        assertThat(cSUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCSUser() throws Exception {
        // Initialize the database
        cSUserRepository.saveAndFlush(cSUser);
        int databaseSizeBeforeDelete = cSUserRepository.findAll().size();

        // Get the cSUser
        restCSUserMockMvc.perform(delete("/api/c-s-users/{id}", cSUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CSUser> cSUserList = cSUserRepository.findAll();
        assertThat(cSUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CSUser.class);
        CSUser cSUser1 = new CSUser();
        cSUser1.setId(1L);
        CSUser cSUser2 = new CSUser();
        cSUser2.setId(cSUser1.getId());
        assertThat(cSUser1).isEqualTo(cSUser2);
        cSUser2.setId(2L);
        assertThat(cSUser1).isNotEqualTo(cSUser2);
        cSUser1.setId(null);
        assertThat(cSUser1).isNotEqualTo(cSUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CSUserDTO.class);
        CSUserDTO cSUserDTO1 = new CSUserDTO();
        cSUserDTO1.setId(1L);
        CSUserDTO cSUserDTO2 = new CSUserDTO();
        assertThat(cSUserDTO1).isNotEqualTo(cSUserDTO2);
        cSUserDTO2.setId(cSUserDTO1.getId());
        assertThat(cSUserDTO1).isEqualTo(cSUserDTO2);
        cSUserDTO2.setId(2L);
        assertThat(cSUserDTO1).isNotEqualTo(cSUserDTO2);
        cSUserDTO1.setId(null);
        assertThat(cSUserDTO1).isNotEqualTo(cSUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cSUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cSUserMapper.fromId(null)).isNull();
    }
}
