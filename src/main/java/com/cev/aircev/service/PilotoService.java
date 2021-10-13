package com.cev.aircev.service;

import com.cev.aircev.domain.Piloto;
import com.cev.aircev.repository.PilotoRepository;
import com.cev.aircev.service.dto.PilotoDTO;
import com.cev.aircev.service.mapper.PilotoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Piloto}.
 */
@Service
@Transactional
public class PilotoService {

    private final Logger log = LoggerFactory.getLogger(PilotoService.class);

    private final PilotoRepository pilotoRepository;

    private final PilotoMapper pilotoMapper;

    public PilotoService(PilotoRepository pilotoRepository, PilotoMapper pilotoMapper) {
        this.pilotoRepository = pilotoRepository;
        this.pilotoMapper = pilotoMapper;
    }

    /**
     * Save a piloto.
     *
     * @param pilotoDTO the entity to save.
     * @return the persisted entity.
     */
    public PilotoDTO save(PilotoDTO pilotoDTO) {
        log.debug("Request to save Piloto : {}", pilotoDTO);
        Piloto piloto = pilotoMapper.toEntity(pilotoDTO);
        piloto = pilotoRepository.save(piloto);
        return pilotoMapper.toDto(piloto);
    }

    /**
     * Partially update a piloto.
     *
     * @param pilotoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PilotoDTO> partialUpdate(PilotoDTO pilotoDTO) {
        log.debug("Request to partially update Piloto : {}", pilotoDTO);

        return pilotoRepository
            .findById(pilotoDTO.getId())
            .map(existingPiloto -> {
                pilotoMapper.partialUpdate(existingPiloto, pilotoDTO);

                return existingPiloto;
            })
            .map(pilotoRepository::save)
            .map(pilotoMapper::toDto);
    }

    /**
     * Get all the pilotos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PilotoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pilotos");
        return pilotoRepository.findAll(pageable).map(pilotoMapper::toDto);
    }

    /**
     * Get one piloto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PilotoDTO> findOne(Long id) {
        log.debug("Request to get Piloto : {}", id);
        return pilotoRepository.findById(id).map(pilotoMapper::toDto);
    }

    /**
     * Delete the piloto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Piloto : {}", id);
        pilotoRepository.deleteById(id);
    }
}
