package com.costshare.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.costshare.service.CSUserService;
import com.costshare.web.rest.errors.BadRequestAlertException;
import com.costshare.web.rest.util.HeaderUtil;
import com.costshare.service.dto.CSUserDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CSUser.
 */
@RestController
@RequestMapping("/api")
public class CSUserResource {

    private final Logger log = LoggerFactory.getLogger(CSUserResource.class);

    private static final String ENTITY_NAME = "cSUser";

    private final CSUserService cSUserService;

    public CSUserResource(CSUserService cSUserService) {
        this.cSUserService = cSUserService;
    }

    /**
     * POST  /c-s-users : Create a new cSUser.
     *
     * @param cSUserDTO the cSUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cSUserDTO, or with status 400 (Bad Request) if the cSUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-s-users")
    @Timed
    public ResponseEntity<CSUserDTO> createCSUser(@RequestBody CSUserDTO cSUserDTO) throws URISyntaxException {
        log.debug("REST request to save CSUser : {}", cSUserDTO);
        if (cSUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new cSUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CSUserDTO result = cSUserService.save(cSUserDTO);
        return ResponseEntity.created(new URI("/api/c-s-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-s-users : Updates an existing cSUser.
     *
     * @param cSUserDTO the cSUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cSUserDTO,
     * or with status 400 (Bad Request) if the cSUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the cSUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-s-users")
    @Timed
    public ResponseEntity<CSUserDTO> updateCSUser(@RequestBody CSUserDTO cSUserDTO) throws URISyntaxException {
        log.debug("REST request to update CSUser : {}", cSUserDTO);
        if (cSUserDTO.getId() == null) {
            return createCSUser(cSUserDTO);
        }
        CSUserDTO result = cSUserService.save(cSUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cSUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-s-users : get all the cSUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cSUsers in body
     */
    @GetMapping("/c-s-users")
    @Timed
    public List<CSUserDTO> getAllCSUsers() {
        log.debug("REST request to get all CSUsers");
        return cSUserService.findAll();
        }

    /**
     * GET  /c-s-users/:id : get the "id" cSUser.
     *
     * @param id the id of the cSUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cSUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-s-users/{id}")
    @Timed
    public ResponseEntity<CSUserDTO> getCSUser(@PathVariable Long id) {
        log.debug("REST request to get CSUser : {}", id);
        CSUserDTO cSUserDTO = cSUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cSUserDTO));
    }

    /**
     * DELETE  /c-s-users/:id : delete the "id" cSUser.
     *
     * @param id the id of the cSUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-s-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteCSUser(@PathVariable Long id) {
        log.debug("REST request to delete CSUser : {}", id);
        cSUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
