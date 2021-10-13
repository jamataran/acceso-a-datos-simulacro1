package com.cev.aircev.web.rest;

import com.cev.aircev.repository.AeropuertoRepository;
import com.cev.aircev.service.AeropuertoService;
import com.cev.aircev.service.dto.AeropuertoDTO;
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
 * REST controller for managing {@link com.cev.aircev.domain.Aeropuerto}.
 */
@RestController
@RequestMapping("/api")
public class AeropuertoResource {

    private final Logger log = LoggerFactory.getLogger(AeropuertoResource.class);

    private static final String ENTITY_NAME = "aeropuerto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AeropuertoService aeropuertoService;

    private final AeropuertoRepository aeropuertoRepository;

    public AeropuertoResource(AeropuertoService aeropuertoService, AeropuertoRepository aeropuertoRepository) {
        this.aeropuertoService = aeropuertoService;
        this.aeropuertoRepository = aeropuertoRepository;
    }

    /**
     * {@code POST  /aeropuertos} : Create a new aeropuerto.
     *
     * @param aeropuertoDTO the aeropuertoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aeropuertoDTO, or with status {@code 400 (Bad Request)} if the aeropuerto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aeropuertos")
    public ResponseEntity<AeropuertoDTO> createAeropuerto(@Valid @RequestBody AeropuertoDTO aeropuertoDTO) throws URISyntaxException {
        log.debug("REST request to save Aeropuerto : {}", aeropuertoDTO);
        if (aeropuertoDTO.getId() != null) {
            throw new BadRequestAlertException("A new aeropuerto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AeropuertoDTO result = aeropuertoService.save(aeropuertoDTO);
        return ResponseEntity
            .created(new URI("/api/aeropuertos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aeropuertos/:id} : Updates an existing aeropuerto.
     *
     * @param id the id of the aeropuertoDTO to save.
     * @param aeropuertoDTO the aeropuertoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aeropuertoDTO,
     * or with status {@code 400 (Bad Request)} if the aeropuertoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aeropuertoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aeropuertos/{id}")
    public ResponseEntity<AeropuertoDTO> updateAeropuerto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AeropuertoDTO aeropuertoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Aeropuerto : {}, {}", id, aeropuertoDTO);
        if (aeropuertoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aeropuertoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aeropuertoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AeropuertoDTO result = aeropuertoService.save(aeropuertoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aeropuertoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /aeropuertos/:id} : Partial updates given fields of an existing aeropuerto, field will ignore if it is null
     *
     * @param id the id of the aeropuertoDTO to save.
     * @param aeropuertoDTO the aeropuertoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aeropuertoDTO,
     * or with status {@code 400 (Bad Request)} if the aeropuertoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the aeropuertoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the aeropuertoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/aeropuertos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AeropuertoDTO> partialUpdateAeropuerto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AeropuertoDTO aeropuertoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Aeropuerto partially : {}, {}", id, aeropuertoDTO);
        if (aeropuertoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aeropuertoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aeropuertoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AeropuertoDTO> result = aeropuertoService.partialUpdate(aeropuertoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aeropuertoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /aeropuertos} : get all the aeropuertos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aeropuertos in body.
     */
    @GetMapping("/aeropuertos")
    public ResponseEntity<List<AeropuertoDTO>> getAllAeropuertos(Pageable pageable) {
        log.debug("REST request to get a page of Aeropuertos");
        Page<AeropuertoDTO> page = aeropuertoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /aeropuertos/:id} : get the "id" aeropuerto.
     *
     * @param id the id of the aeropuertoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aeropuertoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aeropuertos/{id}")
    public ResponseEntity<AeropuertoDTO> getAeropuerto(@PathVariable Long id) {
        log.debug("REST request to get Aeropuerto : {}", id);
        Optional<AeropuertoDTO> aeropuertoDTO = aeropuertoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aeropuertoDTO);
    }

    /**
     * {@code DELETE  /aeropuertos/:id} : delete the "id" aeropuerto.
     *
     * @param id the id of the aeropuertoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aeropuertos/{id}")
    public ResponseEntity<Void> deleteAeropuerto(@PathVariable Long id) {
        log.debug("REST request to delete Aeropuerto : {}", id);
        aeropuertoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
