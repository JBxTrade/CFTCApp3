package com.cftc.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cftc.app.IntegrationTest;
import com.cftc.app.domain.UnSub;
import com.cftc.app.domain.enumeration.TheRole;
import com.cftc.app.repository.UnSubRepository;
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
 * Integration tests for the {@link UnSubResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UnSubResourceIT {

    private static final TheRole DEFAULT_THE_ROLE = TheRole.ADMIN;
    private static final TheRole UPDATED_THE_ROLE = TheRole.ANONYMOUS;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/un-subs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UnSubRepository unSubRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUnSubMockMvc;

    private UnSub unSub;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnSub createEntity(EntityManager em) {
        UnSub unSub = new UnSub().theRole(DEFAULT_THE_ROLE).image(DEFAULT_IMAGE).imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return unSub;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnSub createUpdatedEntity(EntityManager em) {
        UnSub unSub = new UnSub().theRole(UPDATED_THE_ROLE).image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return unSub;
    }

    @BeforeEach
    public void initTest() {
        unSub = createEntity(em);
    }

    @Test
    @Transactional
    void createUnSub() throws Exception {
        int databaseSizeBeforeCreate = unSubRepository.findAll().size();
        // Create the UnSub
        restUnSubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unSub)))
            .andExpect(status().isCreated());

        // Validate the UnSub in the database
        List<UnSub> unSubList = unSubRepository.findAll();
        assertThat(unSubList).hasSize(databaseSizeBeforeCreate + 1);
        UnSub testUnSub = unSubList.get(unSubList.size() - 1);
        assertThat(testUnSub.getTheRole()).isEqualTo(DEFAULT_THE_ROLE);
        assertThat(testUnSub.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testUnSub.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createUnSubWithExistingId() throws Exception {
        // Create the UnSub with an existing ID
        unSub.setId(1L);

        int databaseSizeBeforeCreate = unSubRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnSubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unSub)))
            .andExpect(status().isBadRequest());

        // Validate the UnSub in the database
        List<UnSub> unSubList = unSubRepository.findAll();
        assertThat(unSubList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTheRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = unSubRepository.findAll().size();
        // set the field null
        unSub.setTheRole(null);

        // Create the UnSub, which fails.

        restUnSubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unSub)))
            .andExpect(status().isBadRequest());

        List<UnSub> unSubList = unSubRepository.findAll();
        assertThat(unSubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUnSubs() throws Exception {
        // Initialize the database
        unSubRepository.saveAndFlush(unSub);

        // Get all the unSubList
        restUnSubMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unSub.getId().intValue())))
            .andExpect(jsonPath("$.[*].theRole").value(hasItem(DEFAULT_THE_ROLE.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    void getUnSub() throws Exception {
        // Initialize the database
        unSubRepository.saveAndFlush(unSub);

        // Get the unSub
        restUnSubMockMvc
            .perform(get(ENTITY_API_URL_ID, unSub.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(unSub.getId().intValue()))
            .andExpect(jsonPath("$.theRole").value(DEFAULT_THE_ROLE.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    void getNonExistingUnSub() throws Exception {
        // Get the unSub
        restUnSubMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUnSub() throws Exception {
        // Initialize the database
        unSubRepository.saveAndFlush(unSub);

        int databaseSizeBeforeUpdate = unSubRepository.findAll().size();

        // Update the unSub
        UnSub updatedUnSub = unSubRepository.findById(unSub.getId()).get();
        // Disconnect from session so that the updates on updatedUnSub are not directly saved in db
        em.detach(updatedUnSub);
        updatedUnSub.theRole(UPDATED_THE_ROLE).image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restUnSubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUnSub.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUnSub))
            )
            .andExpect(status().isOk());

        // Validate the UnSub in the database
        List<UnSub> unSubList = unSubRepository.findAll();
        assertThat(unSubList).hasSize(databaseSizeBeforeUpdate);
        UnSub testUnSub = unSubList.get(unSubList.size() - 1);
        assertThat(testUnSub.getTheRole()).isEqualTo(UPDATED_THE_ROLE);
        assertThat(testUnSub.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testUnSub.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingUnSub() throws Exception {
        int databaseSizeBeforeUpdate = unSubRepository.findAll().size();
        unSub.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnSubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, unSub.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unSub))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnSub in the database
        List<UnSub> unSubList = unSubRepository.findAll();
        assertThat(unSubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUnSub() throws Exception {
        int databaseSizeBeforeUpdate = unSubRepository.findAll().size();
        unSub.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnSubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unSub))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnSub in the database
        List<UnSub> unSubList = unSubRepository.findAll();
        assertThat(unSubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUnSub() throws Exception {
        int databaseSizeBeforeUpdate = unSubRepository.findAll().size();
        unSub.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnSubMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unSub)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UnSub in the database
        List<UnSub> unSubList = unSubRepository.findAll();
        assertThat(unSubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUnSubWithPatch() throws Exception {
        // Initialize the database
        unSubRepository.saveAndFlush(unSub);

        int databaseSizeBeforeUpdate = unSubRepository.findAll().size();

        // Update the unSub using partial update
        UnSub partialUpdatedUnSub = new UnSub();
        partialUpdatedUnSub.setId(unSub.getId());

        partialUpdatedUnSub.image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restUnSubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnSub.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnSub))
            )
            .andExpect(status().isOk());

        // Validate the UnSub in the database
        List<UnSub> unSubList = unSubRepository.findAll();
        assertThat(unSubList).hasSize(databaseSizeBeforeUpdate);
        UnSub testUnSub = unSubList.get(unSubList.size() - 1);
        assertThat(testUnSub.getTheRole()).isEqualTo(DEFAULT_THE_ROLE);
        assertThat(testUnSub.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testUnSub.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateUnSubWithPatch() throws Exception {
        // Initialize the database
        unSubRepository.saveAndFlush(unSub);

        int databaseSizeBeforeUpdate = unSubRepository.findAll().size();

        // Update the unSub using partial update
        UnSub partialUpdatedUnSub = new UnSub();
        partialUpdatedUnSub.setId(unSub.getId());

        partialUpdatedUnSub.theRole(UPDATED_THE_ROLE).image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restUnSubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnSub.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnSub))
            )
            .andExpect(status().isOk());

        // Validate the UnSub in the database
        List<UnSub> unSubList = unSubRepository.findAll();
        assertThat(unSubList).hasSize(databaseSizeBeforeUpdate);
        UnSub testUnSub = unSubList.get(unSubList.size() - 1);
        assertThat(testUnSub.getTheRole()).isEqualTo(UPDATED_THE_ROLE);
        assertThat(testUnSub.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testUnSub.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingUnSub() throws Exception {
        int databaseSizeBeforeUpdate = unSubRepository.findAll().size();
        unSub.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnSubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, unSub.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unSub))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnSub in the database
        List<UnSub> unSubList = unSubRepository.findAll();
        assertThat(unSubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUnSub() throws Exception {
        int databaseSizeBeforeUpdate = unSubRepository.findAll().size();
        unSub.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnSubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unSub))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnSub in the database
        List<UnSub> unSubList = unSubRepository.findAll();
        assertThat(unSubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUnSub() throws Exception {
        int databaseSizeBeforeUpdate = unSubRepository.findAll().size();
        unSub.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnSubMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(unSub)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UnSub in the database
        List<UnSub> unSubList = unSubRepository.findAll();
        assertThat(unSubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUnSub() throws Exception {
        // Initialize the database
        unSubRepository.saveAndFlush(unSub);

        int databaseSizeBeforeDelete = unSubRepository.findAll().size();

        // Delete the unSub
        restUnSubMockMvc
            .perform(delete(ENTITY_API_URL_ID, unSub.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UnSub> unSubList = unSubRepository.findAll();
        assertThat(unSubList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
