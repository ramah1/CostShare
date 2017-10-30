package com.costshare.web.rest;

import com.costshare.CostshareApp;

import com.costshare.domain.Invite;
import com.costshare.repository.InviteRepository;
import com.costshare.service.InviteService;
import com.costshare.service.dto.InviteDTO;
import com.costshare.service.mapper.InviteMapper;
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
 * Test class for the InviteResource REST controller.
 *
 * @see InviteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CostshareApp.class)
public class InviteResourceIntTest {

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private InviteRepository inviteRepository;

    @Autowired
    private InviteMapper inviteMapper;

    @Autowired
    private InviteService inviteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInviteMockMvc;

    private Invite invite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InviteResource inviteResource = new InviteResource(inviteService);
        this.restInviteMockMvc = MockMvcBuilders.standaloneSetup(inviteResource)
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
    public static Invite createEntity(EntityManager em) {
        Invite invite = new Invite()
            .comment(DEFAULT_COMMENT);
        return invite;
    }

    @Before
    public void initTest() {
        invite = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvite() throws Exception {
        int databaseSizeBeforeCreate = inviteRepository.findAll().size();

        // Create the Invite
        InviteDTO inviteDTO = inviteMapper.toDto(invite);
        restInviteMockMvc.perform(post("/api/invites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inviteDTO)))
            .andExpect(status().isCreated());

        // Validate the Invite in the database
        List<Invite> inviteList = inviteRepository.findAll();
        assertThat(inviteList).hasSize(databaseSizeBeforeCreate + 1);
        Invite testInvite = inviteList.get(inviteList.size() - 1);
        assertThat(testInvite.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createInviteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inviteRepository.findAll().size();

        // Create the Invite with an existing ID
        invite.setId(1L);
        InviteDTO inviteDTO = inviteMapper.toDto(invite);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInviteMockMvc.perform(post("/api/invites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inviteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Invite in the database
        List<Invite> inviteList = inviteRepository.findAll();
        assertThat(inviteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCommentIsRequired() throws Exception {
        int databaseSizeBeforeTest = inviteRepository.findAll().size();
        // set the field null
        invite.setComment(null);

        // Create the Invite, which fails.
        InviteDTO inviteDTO = inviteMapper.toDto(invite);

        restInviteMockMvc.perform(post("/api/invites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inviteDTO)))
            .andExpect(status().isBadRequest());

        List<Invite> inviteList = inviteRepository.findAll();
        assertThat(inviteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvites() throws Exception {
        // Initialize the database
        inviteRepository.saveAndFlush(invite);

        // Get all the inviteList
        restInviteMockMvc.perform(get("/api/invites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invite.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getInvite() throws Exception {
        // Initialize the database
        inviteRepository.saveAndFlush(invite);

        // Get the invite
        restInviteMockMvc.perform(get("/api/invites/{id}", invite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invite.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvite() throws Exception {
        // Get the invite
        restInviteMockMvc.perform(get("/api/invites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvite() throws Exception {
        // Initialize the database
        inviteRepository.saveAndFlush(invite);
        int databaseSizeBeforeUpdate = inviteRepository.findAll().size();

        // Update the invite
        Invite updatedInvite = inviteRepository.findOne(invite.getId());
        updatedInvite
            .comment(UPDATED_COMMENT);
        InviteDTO inviteDTO = inviteMapper.toDto(updatedInvite);

        restInviteMockMvc.perform(put("/api/invites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inviteDTO)))
            .andExpect(status().isOk());

        // Validate the Invite in the database
        List<Invite> inviteList = inviteRepository.findAll();
        assertThat(inviteList).hasSize(databaseSizeBeforeUpdate);
        Invite testInvite = inviteList.get(inviteList.size() - 1);
        assertThat(testInvite.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingInvite() throws Exception {
        int databaseSizeBeforeUpdate = inviteRepository.findAll().size();

        // Create the Invite
        InviteDTO inviteDTO = inviteMapper.toDto(invite);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInviteMockMvc.perform(put("/api/invites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inviteDTO)))
            .andExpect(status().isCreated());

        // Validate the Invite in the database
        List<Invite> inviteList = inviteRepository.findAll();
        assertThat(inviteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInvite() throws Exception {
        // Initialize the database
        inviteRepository.saveAndFlush(invite);
        int databaseSizeBeforeDelete = inviteRepository.findAll().size();

        // Get the invite
        restInviteMockMvc.perform(delete("/api/invites/{id}", invite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Invite> inviteList = inviteRepository.findAll();
        assertThat(inviteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Invite.class);
        Invite invite1 = new Invite();
        invite1.setId(1L);
        Invite invite2 = new Invite();
        invite2.setId(invite1.getId());
        assertThat(invite1).isEqualTo(invite2);
        invite2.setId(2L);
        assertThat(invite1).isNotEqualTo(invite2);
        invite1.setId(null);
        assertThat(invite1).isNotEqualTo(invite2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InviteDTO.class);
        InviteDTO inviteDTO1 = new InviteDTO();
        inviteDTO1.setId(1L);
        InviteDTO inviteDTO2 = new InviteDTO();
        assertThat(inviteDTO1).isNotEqualTo(inviteDTO2);
        inviteDTO2.setId(inviteDTO1.getId());
        assertThat(inviteDTO1).isEqualTo(inviteDTO2);
        inviteDTO2.setId(2L);
        assertThat(inviteDTO1).isNotEqualTo(inviteDTO2);
        inviteDTO1.setId(null);
        assertThat(inviteDTO1).isNotEqualTo(inviteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(inviteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(inviteMapper.fromId(null)).isNull();
    }
}
