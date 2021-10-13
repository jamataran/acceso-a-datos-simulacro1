package com.cev.aircev.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cev.aircev.IntegrationTest;
import com.cev.aircev.domain.Vuelo;
import com.cev.aircev.repository.VueloRepository;
import com.cev.aircev.service.VueloService;
import com.cev.aircev.service.dto.VueloDTO;
import com.cev.aircev.service.mapper.VueloMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VueloResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class VueloResourceIT {

    private static final String ENTITY_API_URL = "/api/vuelos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VueloRepository vueloRepository;

    @Mock
    private VueloRepository vueloRepositoryMock;

    @Autowired
    private VueloMapper vueloMapper;

    @Mock
    private VueloService vueloServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVueloMockMvc;

    private Vuelo vuelo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vuelo createEntity(EntityManager em) {
        Vuelo vuelo = new Vuelo();
        return vuelo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vuelo createUpdatedEntity(EntityManager em) {
        Vuelo vuelo = new Vuelo();
        return vuelo;
    }

    @BeforeEach
    public void initTest() {
        vuelo = createEntity(em);
    }

    @Test
    @Transactional
    void createVuelo() throws Exception {
        int databaseSizeBeforeCreate = vueloRepository.findAll().size();
        // Create the Vuelo
        VueloDTO vueloDTO = vueloMapper.toDto(vuelo);
        restVueloMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vueloDTO)))
            .andExpect(status().isCreated());

        // Validate the Vuelo in the database
        List<Vuelo> vueloList = vueloRepository.findAll();
        assertThat(vueloList).hasSize(databaseSizeBeforeCreate + 1);
        Vuelo testVuelo = vueloList.get(vueloList.size() - 1);
    }

    @Test
    @Transactional
    void createVueloWithExistingId() throws Exception {
        // Create the Vuelo with an existing ID
        vuelo.setId(1L);
        VueloDTO vueloDTO = vueloMapper.toDto(vuelo);

        int databaseSizeBeforeCreate = vueloRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVueloMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vueloDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vuelo in the database
        List<Vuelo> vueloList = vueloRepository.findAll();
        assertThat(vueloList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVuelos() throws Exception {
        // Initialize the database
        vueloRepository.saveAndFlush(vuelo);

        // Get all the vueloList
        restVueloMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vuelo.getId().intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVuelosWithEagerRelationshipsIsEnabled() throws Exception {
        when(vueloServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVueloMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(vueloServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVuelosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(vueloServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVueloMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(vueloServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getVuelo() throws Exception {
        // Initialize the database
        vueloRepository.saveAndFlush(vuelo);

        // Get the vuelo
        restVueloMockMvc
            .perform(get(ENTITY_API_URL_ID, vuelo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vuelo.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingVuelo() throws Exception {
        // Get the vuelo
        restVueloMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVuelo() throws Exception {
        // Initialize the database
        vueloRepository.saveAndFlush(vuelo);

        int databaseSizeBeforeUpdate = vueloRepository.findAll().size();

        // Update the vuelo
        Vuelo updatedVuelo = vueloRepository.findById(vuelo.getId()).get();
        // Disconnect from session so that the updates on updatedVuelo are not directly saved in db
        em.detach(updatedVuelo);
        VueloDTO vueloDTO = vueloMapper.toDto(updatedVuelo);

        restVueloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vueloDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vueloDTO))
            )
            .andExpect(status().isOk());

        // Validate the Vuelo in the database
        List<Vuelo> vueloList = vueloRepository.findAll();
        assertThat(vueloList).hasSize(databaseSizeBeforeUpdate);
        Vuelo testVuelo = vueloList.get(vueloList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingVuelo() throws Exception {
        int databaseSizeBeforeUpdate = vueloRepository.findAll().size();
        vuelo.setId(count.incrementAndGet());

        // Create the Vuelo
        VueloDTO vueloDTO = vueloMapper.toDto(vuelo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVueloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vueloDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vueloDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vuelo in the database
        List<Vuelo> vueloList = vueloRepository.findAll();
        assertThat(vueloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVuelo() throws Exception {
        int databaseSizeBeforeUpdate = vueloRepository.findAll().size();
        vuelo.setId(count.incrementAndGet());

        // Create the Vuelo
        VueloDTO vueloDTO = vueloMapper.toDto(vuelo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVueloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vueloDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vuelo in the database
        List<Vuelo> vueloList = vueloRepository.findAll();
        assertThat(vueloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVuelo() throws Exception {
        int databaseSizeBeforeUpdate = vueloRepository.findAll().size();
        vuelo.setId(count.incrementAndGet());

        // Create the Vuelo
        VueloDTO vueloDTO = vueloMapper.toDto(vuelo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVueloMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vueloDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vuelo in the database
        List<Vuelo> vueloList = vueloRepository.findAll();
        assertThat(vueloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVueloWithPatch() throws Exception {
        // Initialize the database
        vueloRepository.saveAndFlush(vuelo);

        int databaseSizeBeforeUpdate = vueloRepository.findAll().size();

        // Update the vuelo using partial update
        Vuelo partialUpdatedVuelo = new Vuelo();
        partialUpdatedVuelo.setId(vuelo.getId());

        restVueloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVuelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVuelo))
            )
            .andExpect(status().isOk());

        // Validate the Vuelo in the database
        List<Vuelo> vueloList = vueloRepository.findAll();
        assertThat(vueloList).hasSize(databaseSizeBeforeUpdate);
        Vuelo testVuelo = vueloList.get(vueloList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateVueloWithPatch() throws Exception {
        // Initialize the database
        vueloRepository.saveAndFlush(vuelo);

        int databaseSizeBeforeUpdate = vueloRepository.findAll().size();

        // Update the vuelo using partial update
        Vuelo partialUpdatedVuelo = new Vuelo();
        partialUpdatedVuelo.setId(vuelo.getId());

        restVueloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVuelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVuelo))
            )
            .andExpect(status().isOk());

        // Validate the Vuelo in the database
        List<Vuelo> vueloList = vueloRepository.findAll();
        assertThat(vueloList).hasSize(databaseSizeBeforeUpdate);
        Vuelo testVuelo = vueloList.get(vueloList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingVuelo() throws Exception {
        int databaseSizeBeforeUpdate = vueloRepository.findAll().size();
        vuelo.setId(count.incrementAndGet());

        // Create the Vuelo
        VueloDTO vueloDTO = vueloMapper.toDto(vuelo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVueloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vueloDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vueloDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vuelo in the database
        List<Vuelo> vueloList = vueloRepository.findAll();
        assertThat(vueloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVuelo() throws Exception {
        int databaseSizeBeforeUpdate = vueloRepository.findAll().size();
        vuelo.setId(count.incrementAndGet());

        // Create the Vuelo
        VueloDTO vueloDTO = vueloMapper.toDto(vuelo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVueloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vueloDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vuelo in the database
        List<Vuelo> vueloList = vueloRepository.findAll();
        assertThat(vueloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVuelo() throws Exception {
        int databaseSizeBeforeUpdate = vueloRepository.findAll().size();
        vuelo.setId(count.incrementAndGet());

        // Create the Vuelo
        VueloDTO vueloDTO = vueloMapper.toDto(vuelo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVueloMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(vueloDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vuelo in the database
        List<Vuelo> vueloList = vueloRepository.findAll();
        assertThat(vueloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVuelo() throws Exception {
        // Initialize the database
        vueloRepository.saveAndFlush(vuelo);

        int databaseSizeBeforeDelete = vueloRepository.findAll().size();

        // Delete the vuelo
        restVueloMockMvc
            .perform(delete(ENTITY_API_URL_ID, vuelo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vuelo> vueloList = vueloRepository.findAll();
        assertThat(vueloList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
