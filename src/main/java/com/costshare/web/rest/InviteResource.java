package com.costshare.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.costshare.service.InviteService;
import com.costshare.web.rest.errors.BadRequestAlertException;
import com.costshare.web.rest.util.HeaderUtil;
import com.costshare.service.dto.InviteDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Invite.
 */
@RestController
@RequestMapping("/api")
public class InviteResource {

    private final Logger log = LoggerFactory.getLogger(InviteResource.class);

    private static final String ENTITY_NAME = "invite";

    private final InviteService inviteService;

    public InviteResource(InviteService inviteService) {
        this.inviteService = inviteService;
    }

    /**
     * POST  /invites : Create a new invite.
     *
     * @param inviteDTO the inviteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inviteDTO, or with status 400 (Bad Request) if the invite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/invites")
    @Timed
    public ResponseEntity<InviteDTO> createInvite(@Valid @RequestBody InviteDTO inviteDTO) throws URISyntaxException {
        log.debug("REST request to save Invite : {}", inviteDTO);
        if (inviteDTO.getId() != null) {
            throw new BadRequestAlertException("A new invite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InviteDTO result = inviteService.save(inviteDTO);
        return ResponseEntity.created(new URI("/api/invites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /invites : Updates an existing invite.
     *
     * @param inviteDTO the inviteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inviteDTO,
     * or with status 400 (Bad Request) if the inviteDTO is not valid,
     * or with status 500 (Internal Server Error) if the inviteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/invites")
    @Timed
    public ResponseEntity<InviteDTO> updateInvite(@Valid @RequestBody InviteDTO inviteDTO) throws URISyntaxException {
        log.debug("REST request to update Invite : {}", inviteDTO);
        if (inviteDTO.getId() == null) {
            return createInvite(inviteDTO);
        }
        InviteDTO result = inviteService.save(inviteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inviteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /invites : get all the invites.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of invites in body
     */
    @GetMapping("/invites")
    @Timed
    public List<InviteDTO> getAllInvites() {
        log.debug("REST request to get all Invites");
        return inviteService.findAll();
        }

    /**
     * GET  /invites/:id : get the "id" invite.
     *
     * @param id the id of the inviteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inviteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/invites/{id}")
    @Timed
    public ResponseEntity<InviteDTO> getInvite(@PathVariable Long id) {
        log.debug("REST request to get Invite : {}", id);
        InviteDTO inviteDTO = inviteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(inviteDTO));
    }

    /**
     * DELETE  /invites/:id : delete the "id" invite.
     *
     * @param id the id of the inviteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/invites/{id}")
    @Timed
    public ResponseEntity<Void> deleteInvite(@PathVariable Long id) {
        log.debug("REST request to delete Invite : {}", id);
        inviteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }




    @GetMapping("/c-s-user/{id}/invites")
    @Timed
    public  List<InviteDTO> getAllInvitesForCSUser(@PathVariable Long id){
     log.debug("REST request to get all invites for csuser : {}", id);
     return inviteService.findAllByCSUser(id);
    }
}
