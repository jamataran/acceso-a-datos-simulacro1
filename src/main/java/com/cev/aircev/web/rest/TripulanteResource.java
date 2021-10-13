package com.cev.aircev.web.rest;

import com.cev.aircev.repository.TripulanteRepository;
import com.cev.aircev.service.TripulanteService;
import com.cev.aircev.service.dto.TripulanteDTO;
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
 * REST controller for managing {@link com.cev.aircev.domain.Tripulante}.
 */
@RestController
@RequestMapping("/api")
public class TripulanteResource {

    private final Logger log = LoggerFactory.getLogger(TripulanteResource.class);

    private static final String ENTITY_NAME = "tripulante";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TripulanteService tripulanteService;

    private final TripulanteRepository tripulanteRepository;

    public TripulanteResource(TripulanteService tripulanteService, TripulanteRepository tripulanteRepository) {
        this.tripulanteService = tripulanteService;
        this.tripulanteRepository = tripulanteRepository;
    }

    /**
     * {@code POST  /tripulantes} : Create a new tripulante.
     *
     * @param tripulanteDTO the tripulanteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tripulanteDTO, or with status {@code 400 (Bad Request)} if the tripulante has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tripulantes")
    public ResponseEntity<TripulanteDTO> createTripulante(@Valid @RequestBody TripulanteDTO tripulanteDTO) throws URISyntaxException {
        log.debug("REST request to save Tripulante : {}", tripulanteDTO);
        if (tripulanteDTO.getId() != null) {
            throw new BadRequestAlertException("A new tripulante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TripulanteDTO result = tripulanteService.save(tripulanteDTO);
        return ResponseEntity
            .created(new URI("/api/tripulantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tripulantes/:id} : Updates an existing tripulante.
     *
     * @param id the id of the tripulanteDTO to save.
     * @param tripulanteDTO the tripulanteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tripulanteDTO,
     * or with status {@code 400 (Bad Request)} if the tripulanteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tripulanteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tripulantes/{id}")
    public ResponseEntity<TripulanteDTO> updateTripulante(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TripulanteDTO tripulanteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Tripulante : {}, {}", id, tripulanteDTO);
        if (tripulanteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tripulanteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tripulanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TripulanteDTO result = tripulanteService.save(tripulanteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tripulanteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tripulantes/:id} : Partial updates given fields of an existing tripulante, field will ignore if it is null
     *
     * @param id the id of the tripulanteDTO to save.
     * @param tripulanteDTO the tripulanteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tripulanteDTO,
     * or with status {@code 400 (Bad Request)} if the tripulanteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tripulanteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tripulanteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tripulantes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TripulanteDTO> partialUpdateTripulante(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TripulanteDTO tripulanteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tripulante partially : {}, {}", id, tripulanteDTO);
        if (tripulanteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tripulanteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tripulanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TripulanteDTO> result = tripulanteService.partialUpdate(tripulanteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tripulanteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tripulantes} : get all the tripulantes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tripulantes in body.
     */
    @GetMapping("/tripulantes")
    public ResponseEntity<List<TripulanteDTO>> getAllTripulantes(Pageable pageable) {
        log.debug("REST request to get a page of Tripulantes");
        Page<TripulanteDTO> page = tripulanteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tripulantes/:id} : get the "id" tripulante.
     *
     * @param id the id of the tripulanteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tripulanteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tripulantes/{id}")
    public ResponseEntity<TripulanteDTO> getTripulante(@PathVariable Long id) {
        log.debug("REST request to get Tripulante : {}", id);
        Optional<TripulanteDTO> tripulanteDTO = tripulanteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tripulanteDTO);
    }

    /**
     * {@code DELETE  /tripulantes/:id} : delete the "id" tripulante.
     *
     * @param id the id of the tripulanteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tripulantes/{id}")
    public ResponseEntity<Void> deleteTripulante(@PathVariable Long id) {
        log.debug("REST request to delete Tripulante : {}", id);
        tripulanteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
