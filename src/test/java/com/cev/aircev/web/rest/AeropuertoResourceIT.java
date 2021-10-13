package com.cev.aircev.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cev.aircev.IntegrationTest;
import com.cev.aircev.domain.Aeropuerto;
import com.cev.aircev.repository.AeropuertoRepository;
import com.cev.aircev.service.dto.AeropuertoDTO;
import com.cev.aircev.service.mapper.AeropuertoMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AeropuertoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AeropuertoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CIUDAD = "AAAAAAAAAA";
    private static final String UPDATED_CIUDAD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aeropuertos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AeropuertoRepository aeropuertoRepository;

    @Autowired
    private AeropuertoMapper aeropuertoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAeropuertoMockMvc;

    private Aeropuerto aeropuerto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aeropuerto createEntity(EntityManager em) {
        Aeropuerto aeropuerto = new Aeropuerto().nombre(DEFAULT_NOMBRE).ciudad(DEFAULT_CIUDAD);
        return aeropuerto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aeropuerto createUpdatedEntity(EntityManager em) {
        Aeropuerto aeropuerto = new Aeropuerto().nombre(UPDATED_NOMBRE).ciudad(UPDATED_CIUDAD);
        return aeropuerto;
    }

    @BeforeEach
    public void initTest() {
        aeropuerto = createEntity(em);
    }

    @Test
    @Transactional
    void createAeropuerto() throws Exception {
        int databaseSizeBeforeCreate = aeropuertoRepository.findAll().size();
        // Create the Aeropuerto
        AeropuertoDTO aeropuertoDTO = aeropuertoMapper.toDto(aeropuerto);
        restAeropuertoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aeropuertoDTO)))
            .andExpect(status().isCreated());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeCreate + 1);
        Aeropuerto testAeropuerto = aeropuertoList.get(aeropuertoList.size() - 1);
        assertThat(testAeropuerto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAeropuerto.getCiudad()).isEqualTo(DEFAULT_CIUDAD);
    }

    @Test
    @Transactional
    void createAeropuertoWithExistingId() throws Exception {
        // Create the Aeropuerto with an existing ID
        aeropuerto.setId(1L);
        AeropuertoDTO aeropuertoDTO = aeropuertoMapper.toDto(aeropuerto);

        int databaseSizeBeforeCreate = aeropuertoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAeropuertoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aeropuertoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCiudadIsRequired() throws Exception {
        int databaseSizeBeforeTest = aeropuertoRepository.findAll().size();
        // set the field null
        aeropuerto.setCiudad(null);

        // Create the Aeropuerto, which fails.
        AeropuertoDTO aeropuertoDTO = aeropuertoMapper.toDto(aeropuerto);

        restAeropuertoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aeropuertoDTO)))
            .andExpect(status().isBadRequest());

        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAeropuertos() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        // Get all the aeropuertoList
        restAeropuertoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aeropuerto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].ciudad").value(hasItem(DEFAULT_CIUDAD)));
    }

    @Test
    @Transactional
    void getAeropuerto() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        // Get the aeropuerto
        restAeropuertoMockMvc
            .perform(get(ENTITY_API_URL_ID, aeropuerto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aeropuerto.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.ciudad").value(DEFAULT_CIUDAD));
    }

    @Test
    @Transactional
    void getNonExistingAeropuerto() throws Exception {
        // Get the aeropuerto
        restAeropuertoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAeropuerto() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        int databaseSizeBeforeUpdate = aeropuertoRepository.findAll().size();

        // Update the aeropuerto
        Aeropuerto updatedAeropuerto = aeropuertoRepository.findById(aeropuerto.getId()).get();
        // Disconnect from session so that the updates on updatedAeropuerto are not directly saved in db
        em.detach(updatedAeropuerto);
        updatedAeropuerto.nombre(UPDATED_NOMBRE).ciudad(UPDATED_CIUDAD);
        AeropuertoDTO aeropuertoDTO = aeropuertoMapper.toDto(updatedAeropuerto);

        restAeropuertoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aeropuertoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aeropuertoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeUpdate);
        Aeropuerto testAeropuerto = aeropuertoList.get(aeropuertoList.size() - 1);
        assertThat(testAeropuerto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAeropuerto.getCiudad()).isEqualTo(UPDATED_CIUDAD);
    }

    @Test
    @Transactional
    void putNonExistingAeropuerto() throws Exception {
        int databaseSizeBeforeUpdate = aeropuertoRepository.findAll().size();
        aeropuerto.setId(count.incrementAndGet());

        // Create the Aeropuerto
        AeropuertoDTO aeropuertoDTO = aeropuertoMapper.toDto(aeropuerto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAeropuertoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aeropuertoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aeropuertoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAeropuerto() throws Exception {
        int databaseSizeBeforeUpdate = aeropuertoRepository.findAll().size();
        aeropuerto.setId(count.incrementAndGet());

        // Create the Aeropuerto
        AeropuertoDTO aeropuertoDTO = aeropuertoMapper.toDto(aeropuerto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAeropuertoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aeropuertoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAeropuerto() throws Exception {
        int databaseSizeBeforeUpdate = aeropuertoRepository.findAll().size();
        aeropuerto.setId(count.incrementAndGet());

        // Create the Aeropuerto
        AeropuertoDTO aeropuertoDTO = aeropuertoMapper.toDto(aeropuerto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAeropuertoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aeropuertoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAeropuertoWithPatch() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        int databaseSizeBeforeUpdate = aeropuertoRepository.findAll().size();

        // Update the aeropuerto using partial update
        Aeropuerto partialUpdatedAeropuerto = new Aeropuerto();
        partialUpdatedAeropuerto.setId(aeropuerto.getId());

        restAeropuertoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAeropuerto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAeropuerto))
            )
            .andExpect(status().isOk());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeUpdate);
        Aeropuerto testAeropuerto = aeropuertoList.get(aeropuertoList.size() - 1);
        assertThat(testAeropuerto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAeropuerto.getCiudad()).isEqualTo(DEFAULT_CIUDAD);
    }

    @Test
    @Transactional
    void fullUpdateAeropuertoWithPatch() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        int databaseSizeBeforeUpdate = aeropuertoRepository.findAll().size();

        // Update the aeropuerto using partial update
        Aeropuerto partialUpdatedAeropuerto = new Aeropuerto();
        partialUpdatedAeropuerto.setId(aeropuerto.getId());

        partialUpdatedAeropuerto.nombre(UPDATED_NOMBRE).ciudad(UPDATED_CIUDAD);

        restAeropuertoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAeropuerto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAeropuerto))
            )
            .andExpect(status().isOk());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeUpdate);
        Aeropuerto testAeropuerto = aeropuertoList.get(aeropuertoList.size() - 1);
        assertThat(testAeropuerto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAeropuerto.getCiudad()).isEqualTo(UPDATED_CIUDAD);
    }

    @Test
    @Transactional
    void patchNonExistingAeropuerto() throws Exception {
        int databaseSizeBeforeUpdate = aeropuertoRepository.findAll().size();
        aeropuerto.setId(count.incrementAndGet());

        // Create the Aeropuerto
        AeropuertoDTO aeropuertoDTO = aeropuertoMapper.toDto(aeropuerto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAeropuertoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aeropuertoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aeropuertoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAeropuerto() throws Exception {
        int databaseSizeBeforeUpdate = aeropuertoRepository.findAll().size();
        aeropuerto.setId(count.incrementAndGet());

        // Create the Aeropuerto
        AeropuertoDTO aeropuertoDTO = aeropuertoMapper.toDto(aeropuerto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAeropuertoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aeropuertoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAeropuerto() throws Exception {
        int databaseSizeBeforeUpdate = aeropuertoRepository.findAll().size();
        aeropuerto.setId(count.incrementAndGet());

        // Create the Aeropuerto
        AeropuertoDTO aeropuertoDTO = aeropuertoMapper.toDto(aeropuerto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAeropuertoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(aeropuertoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAeropuerto() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        int databaseSizeBeforeDelete = aeropuertoRepository.findAll().size();

        // Delete the aeropuerto
        restAeropuertoMockMvc
            .perform(delete(ENTITY_API_URL_ID, aeropuerto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
