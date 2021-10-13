package com.cev.aircev.web.rest;

import com.cev.aircev.repository.PilotoRepository;
import com.cev.aircev.service.PilotoService;
import com.cev.aircev.service.dto.PilotoDTO;
import com.cev.aircev.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cev.aircev.domain.Piloto}.
 */
@RestController
@RequestMapping("/api")
public class PilotoResource {

    private final Logger log = LoggerFactory.getLogger(PilotoResource.class);

    private static final String ENTITY_NAME = "piloto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PilotoService pilotoService;

    private final PilotoRepository pilotoRepository;

    public PilotoResource(PilotoService pilotoService, PilotoRepository pilotoRepository) {
        this.pilotoService = pilotoService;
        this.pilotoRepository = pilotoRepository;
    }

    /**
     * {@code POST  /pilotos} : Create a new piloto.
     *
     * @param pilotoDTO the pilotoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pilotoDTO, or with status {@code 400 (Bad Request)} if the piloto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pilotos")
    public ResponseEntity<PilotoDTO> createPiloto(@Valid @RequestBody PilotoDTO pilotoDTO) throws URISyntaxException {
        log.debug("REST request to save Piloto : {}", pilotoDTO);
        if (pilotoDTO.getId() != null) {
            throw new BadRequestAlertException("A new piloto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PilotoDTO result = pilotoService.save(pilotoDTO);
        return ResponseEntity
            .created(new URI("/api/pilotos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pilotos/:id} : Updates an existing piloto.
     *
     * @param id the id of the pilotoDTO to save.
     * @param pilotoDTO the pilotoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pilotoDTO,
     * or with status {@code 400 (Bad Request)} if the pilotoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pilotoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pilotos/{id}")
    public ResponseEntity<PilotoDTO> updatePiloto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PilotoDTO pilotoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Piloto : {}, {}", id, pilotoDTO);
        if (pilotoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pilotoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pilotoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PilotoDTO result = pilotoService.save(pilotoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pilotoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pilotos/:id} : Partial updates given fields of an existing piloto, field will ignore if it is null
     *
     * @param id the id of the pilotoDTO to save.
     * @param pilotoDTO the pilotoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pilotoDTO,
     * or with status {@code 400 (Bad Request)} if the pilotoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pilotoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pilotoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pilotos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PilotoDTO> partialUpdatePiloto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PilotoDTO pilotoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Piloto partially : {}, {}", id, pilotoDTO);
        if (pilotoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pilotoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pilotoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PilotoDTO> result = pilotoService.partialUpdate(pilotoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pilotoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /pilotos} : get all the pilotos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pilotos in body.
     */
    @GetMapping("/pilotos")
    public ResponseEntity<List<PilotoDTO>> getAllPilotos(Pageable pageable) {
        log.debug("REST request to get a page of Pilotos");
        Page<PilotoDTO> page = pilotoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pilotos/:id} : get the "id" piloto.
     *
     * @param id the id of the pilotoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pilotoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pilotos/{id}")
    public ResponseEntity<PilotoDTO> getPiloto(@PathVariable Long id) {
        log.debug("REST request to get Piloto : {}", id);
        Optional<PilotoDTO> pilotoDTO = pilotoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pilotoDTO);
    }

    /**
     * {@code DELETE  /pilotos/:id} : delete the "id" piloto.
     *
     * @param id the id of the pilotoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pilotos/{id}")
    public ResponseEntity<Void> deletePiloto(@PathVariable Long id) {
        log.debug("REST request to delete Piloto : {}", id);
        pilotoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
