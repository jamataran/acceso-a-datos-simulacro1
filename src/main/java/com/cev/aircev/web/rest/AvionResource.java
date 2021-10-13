package com.cev.aircev.web.rest;

import com.cev.aircev.repository.AvionRepository;
import com.cev.aircev.service.AvionService;
import com.cev.aircev.service.dto.AvionDTO;
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
 * REST controller for managing {@link com.cev.aircev.domain.Avion}.
 */
@RestController
@RequestMapping("/api")
public class AvionResource {

    private final Logger log = LoggerFactory.getLogger(AvionResource.class);

    private static final String ENTITY_NAME = "avion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvionService avionService;

    private final AvionRepository avionRepository;

    public AvionResource(AvionService avionService, AvionRepository avionRepository) {
        this.avionService = avionService;
        this.avionRepository = avionRepository;
    }

    /**
     * {@code POST  /avions} : Create a new avion.
     *
     * @param avionDTO the avionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avionDTO, or with status {@code 400 (Bad Request)} if the avion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/avions")
    public ResponseEntity<AvionDTO> createAvion(@Valid @RequestBody AvionDTO avionDTO) throws URISyntaxException {
        log.debug("REST request to save Avion : {}", avionDTO);
        if (avionDTO.getId() != null) {
            throw new BadRequestAlertException("A new avion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvionDTO result = avionService.save(avionDTO);
        return ResponseEntity
            .created(new URI("/api/avions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /avions/:id} : Updates an existing avion.
     *
     * @param id the id of the avionDTO to save.
     * @param avionDTO the avionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avionDTO,
     * or with status {@code 400 (Bad Request)} if the avionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/avions/{id}")
    public ResponseEntity<AvionDTO> updateAvion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AvionDTO avionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Avion : {}, {}", id, avionDTO);
        if (avionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, avionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!avionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AvionDTO result = avionService.save(avionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, avionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /avions/:id} : Partial updates given fields of an existing avion, field will ignore if it is null
     *
     * @param id the id of the avionDTO to save.
     * @param avionDTO the avionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avionDTO,
     * or with status {@code 400 (Bad Request)} if the avionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the avionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the avionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/avions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AvionDTO> partialUpdateAvion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AvionDTO avionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Avion partially : {}, {}", id, avionDTO);
        if (avionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, avionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!avionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AvionDTO> result = avionService.partialUpdate(avionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, avionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /avions} : get all the avions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avions in body.
     */
    @GetMapping("/avions")
    public ResponseEntity<List<AvionDTO>> getAllAvions(Pageable pageable) {
        log.debug("REST request to get a page of Avions");
        Page<AvionDTO> page = avionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /avions/:id} : get the "id" avion.
     *
     * @param id the id of the avionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/avions/{id}")
    public ResponseEntity<AvionDTO> getAvion(@PathVariable Long id) {
        log.debug("REST request to get Avion : {}", id);
        Optional<AvionDTO> avionDTO = avionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(avionDTO);
    }

    /**
     * {@code GET  /avion-viejo} : Obtiene el avion mas viejo
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/avions/mas-viejo")
    public ResponseEntity<AvionDTO> getMasViejo() {
        return ResponseUtil.wrapOrNotFound(avionService.elMasViejo());
    }

    /**
     * {@code DELETE  /avions/:id} : delete the "id" avion.
     *
     * @param id the id of the avionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/avions/{id}")
    public ResponseEntity<Void> deleteAvion(@PathVariable Long id) {
        log.debug("REST request to delete Avion : {}", id);
        avionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
