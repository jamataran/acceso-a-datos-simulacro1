package com.cev.aircev.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cev.aircev.IntegrationTest;
import com.cev.aircev.domain.Piloto;
import com.cev.aircev.repository.PilotoRepository;
import com.cev.aircev.service.dto.PilotoDTO;
import com.cev.aircev.service.mapper.PilotoMapper;
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
 * Integration tests for the {@link PilotoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PilotoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDOS = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDOS = "BBBBBBBBBB";

    private static final String DEFAULT_DNI = "27769712N";
    private static final String UPDATED_DNI = "96234271T";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "yE4}@AU/k.6D{D*r";
    private static final String UPDATED_EMAIL = "9@1+.BdX6DO";

    private static final Boolean DEFAULT_ES_PILOTO = false;
    private static final Boolean UPDATED_ES_PILOTO = true;

    private static final Long DEFAULT_HORAS_DE_VUELO = 1L;
    private static final Long UPDATED_HORAS_DE_VUELO = 2L;

    private static final String ENTITY_API_URL = "/api/pilotos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PilotoRepository pilotoRepository;

    @Autowired
    private PilotoMapper pilotoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPilotoMockMvc;

    private Piloto piloto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Piloto createEntity(EntityManager em) {
        Piloto piloto = new Piloto()
            .nombre(DEFAULT_NOMBRE)
            .apellidos(DEFAULT_APELLIDOS)
            .dni(DEFAULT_DNI)
            .direccion(DEFAULT_DIRECCION)
            .email(DEFAULT_EMAIL)
            .esPiloto(DEFAULT_ES_PILOTO)
            .horasDeVuelo(DEFAULT_HORAS_DE_VUELO);
        return piloto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Piloto createUpdatedEntity(EntityManager em) {
        Piloto piloto = new Piloto()
            .nombre(UPDATED_NOMBRE)
            .apellidos(UPDATED_APELLIDOS)
            .dni(UPDATED_DNI)
            .direccion(UPDATED_DIRECCION)
            .email(UPDATED_EMAIL)
            .esPiloto(UPDATED_ES_PILOTO)
            .horasDeVuelo(UPDATED_HORAS_DE_VUELO);
        return piloto;
    }

    @BeforeEach
    public void initTest() {
        piloto = createEntity(em);
    }

    @Test
    @Transactional
    void createPiloto() throws Exception {
        int databaseSizeBeforeCreate = pilotoRepository.findAll().size();
        // Create the Piloto
        PilotoDTO pilotoDTO = pilotoMapper.toDto(piloto);
        restPilotoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pilotoDTO)))
            .andExpect(status().isCreated());

        // Validate the Piloto in the database
        List<Piloto> pilotoList = pilotoRepository.findAll();
        assertThat(pilotoList).hasSize(databaseSizeBeforeCreate + 1);
        Piloto testPiloto = pilotoList.get(pilotoList.size() - 1);
        assertThat(testPiloto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPiloto.getApellidos()).isEqualTo(DEFAULT_APELLIDOS);
        assertThat(testPiloto.getDni()).isEqualTo(DEFAULT_DNI);
        assertThat(testPiloto.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testPiloto.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPiloto.getEsPiloto()).isEqualTo(DEFAULT_ES_PILOTO);
        assertThat(testPiloto.getHorasDeVuelo()).isEqualTo(DEFAULT_HORAS_DE_VUELO);
    }

    @Test
    @Transactional
    void createPilotoWithExistingId() throws Exception {
        // Create the Piloto with an existing ID
        piloto.setId(1L);
        PilotoDTO pilotoDTO = pilotoMapper.toDto(piloto);

        int databaseSizeBeforeCreate = pilotoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPilotoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pilotoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Piloto in the database
        List<Piloto> pilotoList = pilotoRepository.findAll();
        assertThat(pilotoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = pilotoRepository.findAll().size();
        // set the field null
        piloto.setNombre(null);

        // Create the Piloto, which fails.
        PilotoDTO pilotoDTO = pilotoMapper.toDto(piloto);

        restPilotoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pilotoDTO)))
            .andExpect(status().isBadRequest());

        List<Piloto> pilotoList = pilotoRepository.findAll();
        assertThat(pilotoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApellidosIsRequired() throws Exception {
        int databaseSizeBeforeTest = pilotoRepository.findAll().size();
        // set the field null
        piloto.setApellidos(null);

        // Create the Piloto, which fails.
        PilotoDTO pilotoDTO = pilotoMapper.toDto(piloto);

        restPilotoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pilotoDTO)))
            .andExpect(status().isBadRequest());

        List<Piloto> pilotoList = pilotoRepository.findAll();
        assertThat(pilotoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDniIsRequired() throws Exception {
        int databaseSizeBeforeTest = pilotoRepository.findAll().size();
        // set the field null
        piloto.setDni(null);

        // Create the Piloto, which fails.
        PilotoDTO pilotoDTO = pilotoMapper.toDto(piloto);

        restPilotoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pilotoDTO)))
            .andExpect(status().isBadRequest());

        List<Piloto> pilotoList = pilotoRepository.findAll();
        assertThat(pilotoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = pilotoRepository.findAll().size();
        // set the field null
        piloto.setDireccion(null);

        // Create the Piloto, which fails.
        PilotoDTO pilotoDTO = pilotoMapper.toDto(piloto);

        restPilotoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pilotoDTO)))
            .andExpect(status().isBadRequest());

        List<Piloto> pilotoList = pilotoRepository.findAll();
        assertThat(pilotoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPilotos() throws Exception {
        // Initialize the database
        pilotoRepository.saveAndFlush(piloto);

        // Get all the pilotoList
        restPilotoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(piloto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellidos").value(hasItem(DEFAULT_APELLIDOS)))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].esPiloto").value(hasItem(DEFAULT_ES_PILOTO.booleanValue())))
            .andExpect(jsonPath("$.[*].horasDeVuelo").value(hasItem(DEFAULT_HORAS_DE_VUELO.intValue())));
    }

    @Test
    @Transactional
    void getPiloto() throws Exception {
        // Initialize the database
        pilotoRepository.saveAndFlush(piloto);

        // Get the piloto
        restPilotoMockMvc
            .perform(get(ENTITY_API_URL_ID, piloto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(piloto.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.apellidos").value(DEFAULT_APELLIDOS))
            .andExpect(jsonPath("$.dni").value(DEFAULT_DNI))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.esPiloto").value(DEFAULT_ES_PILOTO.booleanValue()))
            .andExpect(jsonPath("$.horasDeVuelo").value(DEFAULT_HORAS_DE_VUELO.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingPiloto() throws Exception {
        // Get the piloto
        restPilotoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPiloto() throws Exception {
        // Initialize the database
        pilotoRepository.saveAndFlush(piloto);

        int databaseSizeBeforeUpdate = pilotoRepository.findAll().size();

        // Update the piloto
        Piloto updatedPiloto = pilotoRepository.findById(piloto.getId()).get();
        // Disconnect from session so that the updates on updatedPiloto are not directly saved in db
        em.detach(updatedPiloto);
        updatedPiloto
            .nombre(UPDATED_NOMBRE)
            .apellidos(UPDATED_APELLIDOS)
            .dni(UPDATED_DNI)
            .direccion(UPDATED_DIRECCION)
            .email(UPDATED_EMAIL)
            .esPiloto(UPDATED_ES_PILOTO)
            .horasDeVuelo(UPDATED_HORAS_DE_VUELO);
        PilotoDTO pilotoDTO = pilotoMapper.toDto(updatedPiloto);

        restPilotoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pilotoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pilotoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Piloto in the database
        List<Piloto> pilotoList = pilotoRepository.findAll();
        assertThat(pilotoList).hasSize(databaseSizeBeforeUpdate);
        Piloto testPiloto = pilotoList.get(pilotoList.size() - 1);
        assertThat(testPiloto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPiloto.getApellidos()).isEqualTo(UPDATED_APELLIDOS);
        assertThat(testPiloto.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testPiloto.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testPiloto.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPiloto.getEsPiloto()).isEqualTo(UPDATED_ES_PILOTO);
        assertThat(testPiloto.getHorasDeVuelo()).isEqualTo(UPDATED_HORAS_DE_VUELO);
    }

    @Test
    @Transactional
    void putNonExistingPiloto() throws Exception {
        int databaseSizeBeforeUpdate = pilotoRepository.findAll().size();
        piloto.setId(count.incrementAndGet());

        // Create the Piloto
        PilotoDTO pilotoDTO = pilotoMapper.toDto(piloto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPilotoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pilotoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pilotoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Piloto in the database
        List<Piloto> pilotoList = pilotoRepository.findAll();
        assertThat(pilotoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPiloto() throws Exception {
        int databaseSizeBeforeUpdate = pilotoRepository.findAll().size();
        piloto.setId(count.incrementAndGet());

        // Create the Piloto
        PilotoDTO pilotoDTO = pilotoMapper.toDto(piloto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPilotoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pilotoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Piloto in the database
        List<Piloto> pilotoList = pilotoRepository.findAll();
        assertThat(pilotoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPiloto() throws Exception {
        int databaseSizeBeforeUpdate = pilotoRepository.findAll().size();
        piloto.setId(count.incrementAndGet());

        // Create the Piloto
        PilotoDTO pilotoDTO = pilotoMapper.toDto(piloto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPilotoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pilotoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Piloto in the database
        List<Piloto> pilotoList = pilotoRepository.findAll();
        assertThat(pilotoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePilotoWithPatch() throws Exception {
        // Initialize the database
        pilotoRepository.saveAndFlush(piloto);

        int databaseSizeBeforeUpdate = pilotoRepository.findAll().size();

        // Update the piloto using partial update
        Piloto partialUpdatedPiloto = new Piloto();
        partialUpdatedPiloto.setId(piloto.getId());

        partialUpdatedPiloto.direccion(UPDATED_DIRECCION).email(UPDATED_EMAIL).esPiloto(UPDATED_ES_PILOTO);

        restPilotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPiloto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPiloto))
            )
            .andExpect(status().isOk());

        // Validate the Piloto in the database
        List<Piloto> pilotoList = pilotoRepository.findAll();
        assertThat(pilotoList).hasSize(databaseSizeBeforeUpdate);
        Piloto testPiloto = pilotoList.get(pilotoList.size() - 1);
        assertThat(testPiloto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPiloto.getApellidos()).isEqualTo(DEFAULT_APELLIDOS);
        assertThat(testPiloto.getDni()).isEqualTo(DEFAULT_DNI);
        assertThat(testPiloto.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testPiloto.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPiloto.getEsPiloto()).isEqualTo(UPDATED_ES_PILOTO);
        assertThat(testPiloto.getHorasDeVuelo()).isEqualTo(DEFAULT_HORAS_DE_VUELO);
    }

    @Test
    @Transactional
    void fullUpdatePilotoWithPatch() throws Exception {
        // Initialize the database
        pilotoRepository.saveAndFlush(piloto);

        int databaseSizeBeforeUpdate = pilotoRepository.findAll().size();

        // Update the piloto using partial update
        Piloto partialUpdatedPiloto = new Piloto();
        partialUpdatedPiloto.setId(piloto.getId());

        partialUpdatedPiloto
            .nombre(UPDATED_NOMBRE)
            .apellidos(UPDATED_APELLIDOS)
            .dni(UPDATED_DNI)
            .direccion(UPDATED_DIRECCION)
            .email(UPDATED_EMAIL)
            .esPiloto(UPDATED_ES_PILOTO)
            .horasDeVuelo(UPDATED_HORAS_DE_VUELO);

        restPilotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPiloto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPiloto))
            )
            .andExpect(status().isOk());

        // Validate the Piloto in the database
        List<Piloto> pilotoList = pilotoRepository.findAll();
        assertThat(pilotoList).hasSize(databaseSizeBeforeUpdate);
        Piloto testPiloto = pilotoList.get(pilotoList.size() - 1);
        assertThat(testPiloto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPiloto.getApellidos()).isEqualTo(UPDATED_APELLIDOS);
        assertThat(testPiloto.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testPiloto.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testPiloto.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPiloto.getEsPiloto()).isEqualTo(UPDATED_ES_PILOTO);
        assertThat(testPiloto.getHorasDeVuelo()).isEqualTo(UPDATED_HORAS_DE_VUELO);
    }

    @Test
    @Transactional
    void patchNonExistingPiloto() throws Exception {
        int databaseSizeBeforeUpdate = pilotoRepository.findAll().size();
        piloto.setId(count.incrementAndGet());

        // Create the Piloto
        PilotoDTO pilotoDTO = pilotoMapper.toDto(piloto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPilotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pilotoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pilotoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Piloto in the database
        List<Piloto> pilotoList = pilotoRepository.findAll();
        assertThat(pilotoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPiloto() throws Exception {
        int databaseSizeBeforeUpdate = pilotoRepository.findAll().size();
        piloto.setId(count.incrementAndGet());

        // Create the Piloto
        PilotoDTO pilotoDTO = pilotoMapper.toDto(piloto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPilotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pilotoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Piloto in the database
        List<Piloto> pilotoList = pilotoRepository.findAll();
        assertThat(pilotoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPiloto() throws Exception {
        int databaseSizeBeforeUpdate = pilotoRepository.findAll().size();
        piloto.setId(count.incrementAndGet());

        // Create the Piloto
        PilotoDTO pilotoDTO = pilotoMapper.toDto(piloto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPilotoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pilotoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Piloto in the database
        List<Piloto> pilotoList = pilotoRepository.findAll();
        assertThat(pilotoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePiloto() throws Exception {
        // Initialize the database
        pilotoRepository.saveAndFlush(piloto);

        int databaseSizeBeforeDelete = pilotoRepository.findAll().size();

        // Delete the piloto
        restPilotoMockMvc
            .perform(delete(ENTITY_API_URL_ID, piloto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Piloto> pilotoList = pilotoRepository.findAll();
        assertThat(pilotoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
