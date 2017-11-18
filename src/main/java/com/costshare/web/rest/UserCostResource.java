package com.costshare.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.costshare.service.UserCostService;
import com.costshare.web.rest.errors.BadRequestAlertException;
import com.costshare.web.rest.util.HeaderUtil;
import com.costshare.web.rest.util.PaginationUtil;
import com.costshare.service.dto.UserCostDTO;
import io.swagger.annotations.ApiParam;
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
 * REST controller for managing UserCost.
 */
@RestController
@RequestMapping("/api")
public class UserCostResource {

    private final Logger log = LoggerFactory.getLogger(UserCostResource.class);

    private static final String ENTITY_NAME = "userCost";

    private final UserCostService userCostService;

    public UserCostResource(UserCostService userCostService) {
        this.userCostService = userCostService;
    }

    /**
     * POST  /user-costs : Create a new userCost.
     *
     * @param userCostDTO the userCostDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userCostDTO, or with status 400 (Bad Request) if the userCost has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-costs")
    @Timed
    public ResponseEntity<UserCostDTO> createUserCost(@Valid @RequestBody UserCostDTO userCostDTO) throws URISyntaxException {
        log.debug("REST request to save UserCost : {}", userCostDTO);
        if (userCostDTO.getId() != null) {
            throw new BadRequestAlertException("A new userCost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserCostDTO result = userCostService.save(userCostDTO);
        return ResponseEntity.created(new URI("/api/user-costs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-costs : Updates an existing userCost.
     *
     * @param userCostDTO the userCostDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userCostDTO,
     * or with status 400 (Bad Request) if the userCostDTO is not valid,
     * or with status 500 (Internal Server Error) if the userCostDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-costs")
    @Timed
    public ResponseEntity<UserCostDTO> updateUserCost(@Valid @RequestBody UserCostDTO userCostDTO) throws URISyntaxException {
        log.debug("REST request to update UserCost : {}", userCostDTO);
        if (userCostDTO.getId() == null) {
            return createUserCost(userCostDTO);
        }
        UserCostDTO result = userCostService.save(userCostDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userCostDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-costs : get all the userCosts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userCosts in body
     */
    @GetMapping("/user-costs")
    @Timed
    public ResponseEntity<List<UserCostDTO>> getAllUserCosts(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of UserCosts");
        Page<UserCostDTO> page = userCostService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-costs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-costs/:id : get the "id" userCost.
     *
     * @param id the id of the userCostDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userCostDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-costs/{id}")
    @Timed
    public ResponseEntity<UserCostDTO> getUserCost(@PathVariable Long id) {
        log.debug("REST request to get UserCost : {}", id);
        UserCostDTO userCostDTO = userCostService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userCostDTO));
    }

    /**
     * DELETE  /user-costs/:id : delete the "id" userCost.
     *
     * @param id the id of the userCostDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-costs/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserCost(@PathVariable Long id) {
        log.debug("REST request to delete UserCost : {}", id);
        userCostService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/costs/{id}/user-costs")
    @Timed
    public ResponseEntity<List<UserCostDTO>> getUserCostsByCostID(@PathVariable int id) {
        log.debug("REST request to get a page of UserCosts");
        List<UserCostDTO> list = userCostService.findAllByCostId(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
