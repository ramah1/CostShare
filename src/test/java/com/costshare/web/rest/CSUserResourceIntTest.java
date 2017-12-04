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

    private static final String DEFAULT_NAME = "TestUser1";
    private static final String UPDATED_NAME = "TestUserUpdate";

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

    @Before
    public void initTest() {
        cSUser = new CSUser();
        cSUser.setName(DEFAULT_NAME);
    }

    @Test
    public void createCSUser() throws Exception {

        // Create the CSUser
        CSUserDTO cSUserDTO = cSUserMapper.toDto(cSUser);
        restCSUserMockMvc.perform(post("/api/c-s-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cSUserDTO)))
            .andExpect(status().isCreated());

        // Validate the CSUser in the database
        List<CSUser> cSUserList = cSUserRepository.findAll();
        assertThat(cSUserList).hasSize(4);
        CSUser testCSUser = cSUserList.get(3);
        assertThat(testCSUser.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    public void createCSUserWithExistingId() throws Exception {

        // Create the CSUser with an existing ID
        cSUser.setId(1L);
        CSUserDTO cSUserDTO = cSUserMapper.toDto(cSUser);

        // An entity with an existing ID should not be created, so this API call must fail
        restCSUserMockMvc.perform(post("/api/c-s-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cSUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CSUser in the database
        List<CSUser> cSUserList = cSUserRepository.findAll();
        assertThat(cSUserList).hasSize(4);
    }

    @Test
    public void getAllCSUsers() throws Exception {
        // Reset our CSUser
        cSUserRepository.saveAndFlush(cSUser);

        // Get all the CSUserList
        restCSUserMockMvc.perform(get("/api/c-s-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cSUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    public void getCSUser() throws Exception {
        // Reset our CSUser
        cSUserRepository.saveAndFlush(cSUser);

        // Get the CSUser
        restCSUserMockMvc.perform(get("/api/c-s-users/{id}", cSUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cSUser.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    public void getNonExistingCSUser() throws Exception {
        // Get the cSUser
        restCSUserMockMvc.perform(get("/api/c-s-users/{id}", 9999999L))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCSUser() throws Exception {
        // Reset TestUser
        cSUserRepository.saveAndFlush(cSUser);
        // Get amount of Entries in Database before UserIsUpdated
        int databaseSizeBeforeUpdate = cSUserRepository.findAll().size();

        // Update the TestUser
        CSUser userToUpdate = cSUserRepository.findOne(cSUser.getId());
        userToUpdate
            .name(UPDATED_NAME);
        CSUserDTO cSUserDTO = cSUserMapper.toDto(userToUpdate);

        restCSUserMockMvc.perform(put("/api/c-s-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cSUserDTO)))
            .andExpect(status().isOk());

        // Validate the CSUser in the database. Size should not change
        List<CSUser> cSUserList = cSUserRepository.findAll();
        assertThat(cSUserList).hasSize(databaseSizeBeforeUpdate);
        CSUser testCSUser = cSUserList.get(cSUserList.size() - 1);
        assertThat(testCSUser.getName()).isEqualTo(UPDATED_NAME);
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
}
