package com.costshare.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.costshare.service.CSGroupService;
import com.costshare.web.rest.errors.BadRequestAlertException;
import com.costshare.web.rest.util.HeaderUtil;
import com.costshare.web.rest.util.PaginationUtil;
import com.costshare.service.dto.CSGroupDTO;
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
 * REST controller for managing CSGroup.
 */
@RestController
@RequestMapping("/api")
public class CSGroupResource {

    private final Logger log = LoggerFactory.getLogger(CSGroupResource.class);

    private static final String ENTITY_NAME = "cSGroup";

    private final CSGroupService cSGroupService;

    public CSGroupResource(CSGroupService cSGroupService) {
        this.cSGroupService = cSGroupService;
    }

    /**
     * POST  /c-s-groups : Create a new cSGroup.
     *
     * @param cSGroupDTO the cSGroupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cSGroupDTO, or with status 400 (Bad Request) if the cSGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-s-groups")
    @Timed
    public ResponseEntity<CSGroupDTO> createCSGroup(@Valid @RequestBody CSGroupDTO cSGroupDTO) throws URISyntaxException {
        log.debug("REST request to save CSGroup : {}", cSGroupDTO);
        if (cSGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new cSGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CSGroupDTO result = cSGroupService.save(cSGroupDTO);
        return ResponseEntity.created(new URI("/api/c-s-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-s-groups : Updates an existing cSGroup.
     *
     * @param cSGroupDTO the cSGroupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cSGroupDTO,
     * or with status 400 (Bad Request) if the cSGroupDTO is not valid,
     * or with status 500 (Internal Server Error) if the cSGroupDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-s-groups")
    @Timed
    public ResponseEntity<CSGroupDTO> updateCSGroup(@Valid @RequestBody CSGroupDTO cSGroupDTO) throws URISyntaxException {
        log.debug("REST request to update CSGroup : {}", cSGroupDTO);
        if (cSGroupDTO.getId() == null) {
            return createCSGroup(cSGroupDTO);
        }
        CSGroupDTO result = cSGroupService.save(cSGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cSGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-s-groups : get all the cSGroups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cSGroups in body
     */
    @GetMapping("/c-s-groups")
    @Timed
    public ResponseEntity<List<CSGroupDTO>> getAllCSGroups(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CSGroups");
        Page<CSGroupDTO> page = cSGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-s-groups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-s-groups/:id : get the "id" cSGroup.
     *
     * @param id the id of the cSGroupDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cSGroupDTO, or with status 404 (Not Found)
     */
    @GetMapping("/c-s-groups/{id}")
    @Timed
    public ResponseEntity<CSGroupDTO> getCSGroup(@PathVariable Long id) {
        log.debug("REST request to get CSGroup : {}", id);
        CSGroupDTO cSGroupDTO = cSGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cSGroupDTO));
    }

    /**
     * DELETE  /c-s-groups/:id : delete the "id" cSGroup.
     *
     * @param id the id of the cSGroupDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-s-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteCSGroup(@PathVariable Long id) {
        log.debug("REST request to delete CSGroup : {}", id);
        cSGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
