package com.cev.aircev.service;

import com.cev.aircev.domain.Avion;
import com.cev.aircev.repository.AvionRepository;
import com.cev.aircev.service.dto.AvionDTO;
import com.cev.aircev.service.mapper.AvionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Avion}.
 */
@Service
@Transactional
public class AvionService {

    private final Logger log = LoggerFactory.getLogger(AvionService.class);

    private final AvionRepository avionRepository;

    private final AvionMapper avionMapper;

    public AvionService(AvionRepository avionRepository, AvionMapper avionMapper) {
        this.avionRepository = avionRepository;
        this.avionMapper = avionMapper;
    }

    /**
     * Save a avion.
     *
     * @param avionDTO the entity to save.
     * @return the persisted entity.
     */
    public AvionDTO save(AvionDTO avionDTO) {
        log.debug("Request to save Avion : {}", avionDTO);
        Avion avion = avionMapper.toEntity(avionDTO);
        avion = avionRepository.save(avion);
        return avionMapper.toDto(avion);
    }

    public AvionDTO elMasViejo(){
        final Optional<Avion> firstByOrderByEdadDesc = this.avionRepository.findFirstByOrderByEdadDesc();
        return this.avionMapper.toDto(firstByOrderByEdadDesc.get());
    }

    /**
     * Partially update a avion.
     *
     * @param avionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AvionDTO> partialUpdate(AvionDTO avionDTO) {
        log.debug("Request to partially update Avion : {}", avionDTO);

        return avionRepository
            .findById(avionDTO.getId())
            .map(existingAvion -> {
                avionMapper.partialUpdate(existingAvion, avionDTO);

                return existingAvion;
            })
            .map(avionRepository::save)
            .map(avionMapper::toDto);
    }

    /**
     * Get all the avions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AvionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Avions");
        return avionRepository.findAll(pageable).map(avionMapper::toDto);
    }

    /**
     * Get one avion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AvionDTO> findOne(Long id) {
        log.debug("Request to get Avion : {}", id);
        return avionRepository.findById(id).map(avionMapper::toDto);
    }

    /**
     * Delete the avion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Avion : {}", id);
        avionRepository.deleteById(id);
    }
}
