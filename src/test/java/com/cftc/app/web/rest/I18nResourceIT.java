package com.cftc.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cftc.app.IntegrationTest;
import com.cftc.app.domain.I18n;
import com.cftc.app.repository.I18nRepository;
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
 * Integration tests for the {@link I18nResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class I18nResourceIT {

    private static final String DEFAULT_FR = "AAAAAAAAAA";
    private static final String UPDATED_FR = "BBBBBBBBBB";

    private static final String DEFAULT_EN = "AAAAAAAAAA";
    private static final String UPDATED_EN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/i-18-ns";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private I18nRepository i18nRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restI18nMockMvc;

    private I18n i18n;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static I18n createEntity(EntityManager em) {
        I18n i18n = new I18n().fr(DEFAULT_FR).en(DEFAULT_EN);
        return i18n;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static I18n createUpdatedEntity(EntityManager em) {
        I18n i18n = new I18n().fr(UPDATED_FR).en(UPDATED_EN);
        return i18n;
    }

    @BeforeEach
    public void initTest() {
        i18n = createEntity(em);
    }

    @Test
    @Transactional
    void createI18n() throws Exception {
        int databaseSizeBeforeCreate = i18nRepository.findAll().size();
        // Create the I18n
        restI18nMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(i18n)))
            .andExpect(status().isCreated());

        // Validate the I18n in the database
        List<I18n> i18nList = i18nRepository.findAll();
        assertThat(i18nList).hasSize(databaseSizeBeforeCreate + 1);
        I18n testI18n = i18nList.get(i18nList.size() - 1);
        assertThat(testI18n.getFr()).isEqualTo(DEFAULT_FR);
        assertThat(testI18n.getEn()).isEqualTo(DEFAULT_EN);
    }

    @Test
    @Transactional
    void createI18nWithExistingId() throws Exception {
        // Create the I18n with an existing ID
        i18n.setId(1L);

        int databaseSizeBeforeCreate = i18nRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restI18nMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(i18n)))
            .andExpect(status().isBadRequest());

        // Validate the I18n in the database
        List<I18n> i18nList = i18nRepository.findAll();
        assertThat(i18nList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllI18ns() throws Exception {
        // Initialize the database
        i18nRepository.saveAndFlush(i18n);

        // Get all the i18nList
        restI18nMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(i18n.getId().intValue())))
            .andExpect(jsonPath("$.[*].fr").value(hasItem(DEFAULT_FR)))
            .andExpect(jsonPath("$.[*].en").value(hasItem(DEFAULT_EN)));
    }

    @Test
    @Transactional
    void getI18n() throws Exception {
        // Initialize the database
        i18nRepository.saveAndFlush(i18n);

        // Get the i18n
        restI18nMockMvc
            .perform(get(ENTITY_API_URL_ID, i18n.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(i18n.getId().intValue()))
            .andExpect(jsonPath("$.fr").value(DEFAULT_FR))
            .andExpect(jsonPath("$.en").value(DEFAULT_EN));
    }

    @Test
    @Transactional
    void getNonExistingI18n() throws Exception {
        // Get the i18n
        restI18nMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewI18n() throws Exception {
        // Initialize the database
        i18nRepository.saveAndFlush(i18n);

        int databaseSizeBeforeUpdate = i18nRepository.findAll().size();

        // Update the i18n
        I18n updatedI18n = i18nRepository.findById(i18n.getId()).get();
        // Disconnect from session so that the updates on updatedI18n are not directly saved in db
        em.detach(updatedI18n);
        updatedI18n.fr(UPDATED_FR).en(UPDATED_EN);

        restI18nMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedI18n.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedI18n))
            )
            .andExpect(status().isOk());

        // Validate the I18n in the database
        List<I18n> i18nList = i18nRepository.findAll();
        assertThat(i18nList).hasSize(databaseSizeBeforeUpdate);
        I18n testI18n = i18nList.get(i18nList.size() - 1);
        assertThat(testI18n.getFr()).isEqualTo(UPDATED_FR);
        assertThat(testI18n.getEn()).isEqualTo(UPDATED_EN);
    }

    @Test
    @Transactional
    void putNonExistingI18n() throws Exception {
        int databaseSizeBeforeUpdate = i18nRepository.findAll().size();
        i18n.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restI18nMockMvc
            .perform(
                put(ENTITY_API_URL_ID, i18n.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(i18n))
            )
            .andExpect(status().isBadRequest());

        // Validate the I18n in the database
        List<I18n> i18nList = i18nRepository.findAll();
        assertThat(i18nList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchI18n() throws Exception {
        int databaseSizeBeforeUpdate = i18nRepository.findAll().size();
        i18n.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restI18nMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(i18n))
            )
            .andExpect(status().isBadRequest());

        // Validate the I18n in the database
        List<I18n> i18nList = i18nRepository.findAll();
        assertThat(i18nList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamI18n() throws Exception {
        int databaseSizeBeforeUpdate = i18nRepository.findAll().size();
        i18n.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restI18nMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(i18n)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the I18n in the database
        List<I18n> i18nList = i18nRepository.findAll();
        assertThat(i18nList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateI18nWithPatch() throws Exception {
        // Initialize the database
        i18nRepository.saveAndFlush(i18n);

        int databaseSizeBeforeUpdate = i18nRepository.findAll().size();

        // Update the i18n using partial update
        I18n partialUpdatedI18n = new I18n();
        partialUpdatedI18n.setId(i18n.getId());

        partialUpdatedI18n.fr(UPDATED_FR).en(UPDATED_EN);

        restI18nMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedI18n.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedI18n))
            )
            .andExpect(status().isOk());

        // Validate the I18n in the database
        List<I18n> i18nList = i18nRepository.findAll();
        assertThat(i18nList).hasSize(databaseSizeBeforeUpdate);
        I18n testI18n = i18nList.get(i18nList.size() - 1);
        assertThat(testI18n.getFr()).isEqualTo(UPDATED_FR);
        assertThat(testI18n.getEn()).isEqualTo(UPDATED_EN);
    }

    @Test
    @Transactional
    void fullUpdateI18nWithPatch() throws Exception {
        // Initialize the database
        i18nRepository.saveAndFlush(i18n);

        int databaseSizeBeforeUpdate = i18nRepository.findAll().size();

        // Update the i18n using partial update
        I18n partialUpdatedI18n = new I18n();
        partialUpdatedI18n.setId(i18n.getId());

        partialUpdatedI18n.fr(UPDATED_FR).en(UPDATED_EN);

        restI18nMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedI18n.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedI18n))
            )
            .andExpect(status().isOk());

        // Validate the I18n in the database
        List<I18n> i18nList = i18nRepository.findAll();
        assertThat(i18nList).hasSize(databaseSizeBeforeUpdate);
        I18n testI18n = i18nList.get(i18nList.size() - 1);
        assertThat(testI18n.getFr()).isEqualTo(UPDATED_FR);
        assertThat(testI18n.getEn()).isEqualTo(UPDATED_EN);
    }

    @Test
    @Transactional
    void patchNonExistingI18n() throws Exception {
        int databaseSizeBeforeUpdate = i18nRepository.findAll().size();
        i18n.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restI18nMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, i18n.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(i18n))
            )
            .andExpect(status().isBadRequest());

        // Validate the I18n in the database
        List<I18n> i18nList = i18nRepository.findAll();
        assertThat(i18nList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchI18n() throws Exception {
        int databaseSizeBeforeUpdate = i18nRepository.findAll().size();
        i18n.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restI18nMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(i18n))
            )
            .andExpect(status().isBadRequest());

        // Validate the I18n in the database
        List<I18n> i18nList = i18nRepository.findAll();
        assertThat(i18nList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamI18n() throws Exception {
        int databaseSizeBeforeUpdate = i18nRepository.findAll().size();
        i18n.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restI18nMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(i18n)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the I18n in the database
        List<I18n> i18nList = i18nRepository.findAll();
        assertThat(i18nList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteI18n() throws Exception {
        // Initialize the database
        i18nRepository.saveAndFlush(i18n);

        int databaseSizeBeforeDelete = i18nRepository.findAll().size();

        // Delete the i18n
        restI18nMockMvc
            .perform(delete(ENTITY_API_URL_ID, i18n.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<I18n> i18nList = i18nRepository.findAll();
        assertThat(i18nList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
