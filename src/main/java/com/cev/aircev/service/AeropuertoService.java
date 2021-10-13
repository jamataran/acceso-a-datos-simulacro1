package com.cev.aircev.service;

import com.cev.aircev.domain.Aeropuerto;
import com.cev.aircev.repository.AeropuertoRepository;
import com.cev.aircev.service.dto.AeropuertoDTO;
import com.cev.aircev.service.mapper.AeropuertoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Aeropuerto}.
 */
@Service
@Transactional
public class AeropuertoService {

    private final Logger log = LoggerFactory.getLogger(AeropuertoService.class);

    private final AeropuertoRepository aeropuertoRepository;

    private final AeropuertoMapper aeropuertoMapper;

    public AeropuertoService(AeropuertoRepository aeropuertoRepository, AeropuertoMapper aeropuertoMapper) {
        this.aeropuertoRepository = aeropuertoRepository;
        this.aeropuertoMapper = aeropuertoMapper;
    }

    /**
     * Save a aeropuerto.
     *
     * @param aeropuertoDTO the entity to save.
     * @return the persisted entity.
     */
    public AeropuertoDTO save(AeropuertoDTO aeropuertoDTO) {
        log.debug("Request to save Aeropuerto : {}", aeropuertoDTO);
        Aeropuerto aeropuerto = aeropuertoMapper.toEntity(aeropuertoDTO);
        aeropuerto = aeropuertoRepository.save(aeropuerto);
        return aeropuertoMapper.toDto(aeropuerto);
    }

    /**
     * Partially update a aeropuerto.
     *
     * @param aeropuertoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AeropuertoDTO> partialUpdate(AeropuertoDTO aeropuertoDTO) {
        log.debug("Request to partially update Aeropuerto : {}", aeropuertoDTO);

        return aeropuertoRepository
            .findById(aeropuertoDTO.getId())
            .map(existingAeropuerto -> {
                aeropuertoMapper.partialUpdate(existingAeropuerto, aeropuertoDTO);

                return existingAeropuerto;
            })
            .map(aeropuertoRepository::save)
            .map(aeropuertoMapper::toDto);
    }

    /**
     * Get all the aeropuertos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AeropuertoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Aeropuertos");
        return aeropuertoRepository.findAll(pageable).map(aeropuertoMapper::toDto);
    }

    /**
     * Get one aeropuerto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AeropuertoDTO> findOne(Long id) {
        log.debug("Request to get Aeropuerto : {}", id);
        return aeropuertoRepository.findById(id).map(aeropuertoMapper::toDto);
    }

    /**
     * Delete the aeropuerto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Aeropuerto : {}", id);
        aeropuertoRepository.deleteById(id);
    }
}
