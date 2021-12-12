package com.cftc.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cftc.app.IntegrationTest;
import com.cftc.app.domain.Main;
import com.cftc.app.domain.enumeration.TheRole;
import com.cftc.app.repository.MainRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link MainResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MainResourceIT {

    private static final TheRole DEFAULT_THE_ROLE = TheRole.ADMIN;
    private static final TheRole UPDATED_THE_ROLE = TheRole.ANONYMOUS;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/mains";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MainRepository mainRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMainMockMvc;

    private Main main;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Main createEntity(EntityManager em) {
        Main main = new Main().theRole(DEFAULT_THE_ROLE).image(DEFAULT_IMAGE).imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return main;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Main createUpdatedEntity(EntityManager em) {
        Main main = new Main().theRole(UPDATED_THE_ROLE).image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return main;
    }

    @BeforeEach
    public void initTest() {
        main = createEntity(em);
    }

    @Test
    @Transactional
    void createMain() throws Exception {
        int databaseSizeBeforeCreate = mainRepository.findAll().size();
        // Create the Main
        restMainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(main)))
            .andExpect(status().isCreated());

        // Validate the Main in the database
        List<Main> mainList = mainRepository.findAll();
        assertThat(mainList).hasSize(databaseSizeBeforeCreate + 1);
        Main testMain = mainList.get(mainList.size() - 1);
        assertThat(testMain.getTheRole()).isEqualTo(DEFAULT_THE_ROLE);
        assertThat(testMain.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testMain.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createMainWithExistingId() throws Exception {
        // Create the Main with an existing ID
        main.setId(1L);

        int databaseSizeBeforeCreate = mainRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(main)))
            .andExpect(status().isBadRequest());

        // Validate the Main in the database
        List<Main> mainList = mainRepository.findAll();
        assertThat(mainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTheRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = mainRepository.findAll().size();
        // set the field null
        main.setTheRole(null);

        // Create the Main, which fails.

        restMainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(main)))
            .andExpect(status().isBadRequest());

        List<Main> mainList = mainRepository.findAll();
        assertThat(mainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMains() throws Exception {
        // Initialize the database
        mainRepository.saveAndFlush(main);

        // Get all the mainList
        restMainMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(main.getId().intValue())))
            .andExpect(jsonPath("$.[*].theRole").value(hasItem(DEFAULT_THE_ROLE.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    void getMain() throws Exception {
        // Initialize the database
        mainRepository.saveAndFlush(main);

        // Get the main
        restMainMockMvc
            .perform(get(ENTITY_API_URL_ID, main.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(main.getId().intValue()))
            .andExpect(jsonPath("$.theRole").value(DEFAULT_THE_ROLE.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    void getNonExistingMain() throws Exception {
        // Get the main
        restMainMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMain() throws Exception {
        // Initialize the database
        mainRepository.saveAndFlush(main);

        int databaseSizeBeforeUpdate = mainRepository.findAll().size();

        // Update the main
        Main updatedMain = mainRepository.findById(main.getId()).get();
        // Disconnect from session so that the updates on updatedMain are not directly saved in db
        em.detach(updatedMain);
        updatedMain.theRole(UPDATED_THE_ROLE).image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restMainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMain.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMain))
            )
            .andExpect(status().isOk());

        // Validate the Main in the database
        List<Main> mainList = mainRepository.findAll();
        assertThat(mainList).hasSize(databaseSizeBeforeUpdate);
        Main testMain = mainList.get(mainList.size() - 1);
        assertThat(testMain.getTheRole()).isEqualTo(UPDATED_THE_ROLE);
        assertThat(testMain.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testMain.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingMain() throws Exception {
        int databaseSizeBeforeUpdate = mainRepository.findAll().size();
        main.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, main.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(main))
            )
            .andExpect(status().isBadRequest());

        // Validate the Main in the database
        List<Main> mainList = mainRepository.findAll();
        assertThat(mainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMain() throws Exception {
        int databaseSizeBeforeUpdate = mainRepository.findAll().size();
        main.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(main))
            )
            .andExpect(status().isBadRequest());

        // Validate the Main in the database
        List<Main> mainList = mainRepository.findAll();
        assertThat(mainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMain() throws Exception {
        int databaseSizeBeforeUpdate = mainRepository.findAll().size();
        main.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMainMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(main)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Main in the database
        List<Main> mainList = mainRepository.findAll();
        assertThat(mainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMainWithPatch() throws Exception {
        // Initialize the database
        mainRepository.saveAndFlush(main);

        int databaseSizeBeforeUpdate = mainRepository.findAll().size();

        // Update the main using partial update
        Main partialUpdatedMain = new Main();
        partialUpdatedMain.setId(main.getId());

        restMainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMain))
            )
            .andExpect(status().isOk());

        // Validate the Main in the database
        List<Main> mainList = mainRepository.findAll();
        assertThat(mainList).hasSize(databaseSizeBeforeUpdate);
        Main testMain = mainList.get(mainList.size() - 1);
        assertThat(testMain.getTheRole()).isEqualTo(DEFAULT_THE_ROLE);
        assertThat(testMain.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testMain.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateMainWithPatch() throws Exception {
        // Initialize the database
        mainRepository.saveAndFlush(main);

        int databaseSizeBeforeUpdate = mainRepository.findAll().size();

        // Update the main using partial update
        Main partialUpdatedMain = new Main();
        partialUpdatedMain.setId(main.getId());

        partialUpdatedMain.theRole(UPDATED_THE_ROLE).image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restMainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMain))
            )
            .andExpect(status().isOk());

        // Validate the Main in the database
        List<Main> mainList = mainRepository.findAll();
        assertThat(mainList).hasSize(databaseSizeBeforeUpdate);
        Main testMain = mainList.get(mainList.size() - 1);
        assertThat(testMain.getTheRole()).isEqualTo(UPDATED_THE_ROLE);
        assertThat(testMain.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testMain.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingMain() throws Exception {
        int databaseSizeBeforeUpdate = mainRepository.findAll().size();
        main.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, main.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(main))
            )
            .andExpect(status().isBadRequest());

        // Validate the Main in the database
        List<Main> mainList = mainRepository.findAll();
        assertThat(mainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMain() throws Exception {
        int databaseSizeBeforeUpdate = mainRepository.findAll().size();
        main.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(main))
            )
            .andExpect(status().isBadRequest());

        // Validate the Main in the database
        List<Main> mainList = mainRepository.findAll();
        assertThat(mainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMain() throws Exception {
        int databaseSizeBeforeUpdate = mainRepository.findAll().size();
        main.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMainMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(main)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Main in the database
        List<Main> mainList = mainRepository.findAll();
        assertThat(mainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMain() throws Exception {
        // Initialize the database
        mainRepository.saveAndFlush(main);

        int databaseSizeBeforeDelete = mainRepository.findAll().size();

        // Delete the main
        restMainMockMvc
            .perform(delete(ENTITY_API_URL_ID, main.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Main> mainList = mainRepository.findAll();
        assertThat(mainList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
