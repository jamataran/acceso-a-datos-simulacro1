package com.cev.aircev.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cev.aircev.domain.Vuelo;
import com.cev.aircev.repository.VueloRepository;
import com.cev.aircev.service.dto.VueloDTO;
import com.cev.aircev.service.mapper.VueloMapper;

/**
 * Service Implementation for managing {@link Vuelo}.
 */
@Service
@Transactional
public class VueloService {

    private final Logger log = LoggerFactory.getLogger(VueloService.class);

    private final VueloRepository vueloRepository;

    private final VueloMapper vueloMapper;

    public VueloService(VueloRepository vueloRepository, VueloMapper vueloMapper) {
        this.vueloRepository = vueloRepository;
        this.vueloMapper = vueloMapper;
    }

    /**
     * Save a vuelo.
     *
     * @param vueloDTO the entity to save.
     * @return the persisted entity.
     */
    public VueloDTO save(VueloDTO vueloDTO) {
        log.debug("Request to save Vuelo : {}", vueloDTO);
        Vuelo vuelo = vueloMapper.toEntity(vueloDTO);
        vuelo = vueloRepository.save(vuelo);
        return vueloMapper.toDto(vuelo);
    }

    /**
     * Partially update a vuelo.
     *
     * @param vueloDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VueloDTO> partialUpdate(VueloDTO vueloDTO) {
        log.debug("Request to partially update Vuelo : {}", vueloDTO);

        return vueloRepository
            .findById(vueloDTO.getId())
            .map(existingVuelo -> {
                vueloMapper.partialUpdate(existingVuelo, vueloDTO);

                return existingVuelo;
            })
            .map(vueloRepository::save)
            .map(vueloMapper::toDto);
    }

    /**
     * Get all the vuelos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VueloDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Vuelos");
        return vueloRepository.findAll(pageable).map(vueloMapper::toDto);
    }
    /**
     * Get all the vuelos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VueloDTO> buscarPorPiloto(String dni, Pageable pageable) {
        log.debug("Request to get all Vuelos");
        return vueloRepository.findAllByPilotoDni(dni, pageable).map(vueloMapper::toDto);
    }

    /**
     * Get all the vuelos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public long cuentaVuelosTripulante(String dni) {
        log.debug("Request to get all Vuelos");
        return vueloRepository.countVuelosByTripulacionsDni(dni);
    }

    /**
     * Get all the vuelos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<VueloDTO> findAllWithEagerRelationships(Pageable pageable) {
        return vueloRepository.findAllWithEagerRelationships(pageable).map(vueloMapper::toDto);
    }

    /**
     * Get one vuelo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VueloDTO> findOne(Long id) {
        log.debug("Request to get Vuelo : {}", id);
        return vueloRepository.findOneWithEagerRelationships(id).map(vueloMapper::toDto);
    }

    /**
     * Delete the vuelo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Vuelo : {}", id);
        vueloRepository.deleteById(id);
    }
}
