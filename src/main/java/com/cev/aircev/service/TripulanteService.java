package com.cev.aircev.service;

import com.cev.aircev.domain.Tripulante;
import com.cev.aircev.repository.TripulanteRepository;
import com.cev.aircev.service.dto.TripulanteDTO;
import com.cev.aircev.service.mapper.TripulanteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tripulante}.
 */
@Service
@Transactional
public class TripulanteService {

    private final Logger log = LoggerFactory.getLogger(TripulanteService.class);

    private final TripulanteRepository tripulanteRepository;

    private final TripulanteMapper tripulanteMapper;

    public TripulanteService(TripulanteRepository tripulanteRepository, TripulanteMapper tripulanteMapper) {
        this.tripulanteRepository = tripulanteRepository;
        this.tripulanteMapper = tripulanteMapper;
    }

    /**
     * Save a tripulante.
     *
     * @param tripulanteDTO the entity to save.
     * @return the persisted entity.
     */
    public TripulanteDTO save(TripulanteDTO tripulanteDTO) {
        log.debug("Request to save Tripulante : {}", tripulanteDTO);
        Tripulante tripulante = tripulanteMapper.toEntity(tripulanteDTO);
        tripulante = tripulanteRepository.save(tripulante);
        return tripulanteMapper.toDto(tripulante);
    }

    /**
     * Partially update a tripulante.
     *
     * @param tripulanteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TripulanteDTO> partialUpdate(TripulanteDTO tripulanteDTO) {
        log.debug("Request to partially update Tripulante : {}", tripulanteDTO);

        return tripulanteRepository
            .findById(tripulanteDTO.getId())
            .map(existingTripulante -> {
                tripulanteMapper.partialUpdate(existingTripulante, tripulanteDTO);

                return existingTripulante;
            })
            .map(tripulanteRepository::save)
            .map(tripulanteMapper::toDto);
    }

    /**
     * Get all the tripulantes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TripulanteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tripulantes");
        return tripulanteRepository.findAll(pageable).map(tripulanteMapper::toDto);
    }

    /**
     * Get one tripulante by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TripulanteDTO> findOne(Long id) {
        log.debug("Request to get Tripulante : {}", id);
        return tripulanteRepository.findById(id).map(tripulanteMapper::toDto);
    }

    /**
     * Delete the tripulante by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Tripulante : {}", id);
        tripulanteRepository.deleteById(id);
    }
}
