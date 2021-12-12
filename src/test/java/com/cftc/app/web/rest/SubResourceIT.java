package com.cftc.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cftc.app.IntegrationTest;
import com.cftc.app.domain.Sub;
import com.cftc.app.domain.enumeration.TheRole;
import com.cftc.app.repository.SubRepository;
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
 * Integration tests for the {@link SubResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubResourceIT {

    private static final TheRole DEFAULT_THE_ROLE = TheRole.ADMIN;
    private static final TheRole UPDATED_THE_ROLE = TheRole.ANONYMOUS;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/subs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubRepository subRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubMockMvc;

    private Sub sub;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sub createEntity(EntityManager em) {
        Sub sub = new Sub().theRole(DEFAULT_THE_ROLE).image(DEFAULT_IMAGE).imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return sub;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sub createUpdatedEntity(EntityManager em) {
        Sub sub = new Sub().theRole(UPDATED_THE_ROLE).image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return sub;
    }

    @BeforeEach
    public void initTest() {
        sub = createEntity(em);
    }

    @Test
    @Transactional
    void createSub() throws Exception {
        int databaseSizeBeforeCreate = subRepository.findAll().size();
        // Create the Sub
        restSubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sub)))
            .andExpect(status().isCreated());

        // Validate the Sub in the database
        List<Sub> subList = subRepository.findAll();
        assertThat(subList).hasSize(databaseSizeBeforeCreate + 1);
        Sub testSub = subList.get(subList.size() - 1);
        assertThat(testSub.getTheRole()).isEqualTo(DEFAULT_THE_ROLE);
        assertThat(testSub.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testSub.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createSubWithExistingId() throws Exception {
        // Create the Sub with an existing ID
        sub.setId(1L);

        int databaseSizeBeforeCreate = subRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sub)))
            .andExpect(status().isBadRequest());

        // Validate the Sub in the database
        List<Sub> subList = subRepository.findAll();
        assertThat(subList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTheRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = subRepository.findAll().size();
        // set the field null
        sub.setTheRole(null);

        // Create the Sub, which fails.

        restSubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sub)))
            .andExpect(status().isBadRequest());

        List<Sub> subList = subRepository.findAll();
        assertThat(subList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSubs() throws Exception {
        // Initialize the database
        subRepository.saveAndFlush(sub);

        // Get all the subList
        restSubMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sub.getId().intValue())))
            .andExpect(jsonPath("$.[*].theRole").value(hasItem(DEFAULT_THE_ROLE.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    void getSub() throws Exception {
        // Initialize the database
        subRepository.saveAndFlush(sub);

        // Get the sub
        restSubMockMvc
            .perform(get(ENTITY_API_URL_ID, sub.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sub.getId().intValue()))
            .andExpect(jsonPath("$.theRole").value(DEFAULT_THE_ROLE.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    void getNonExistingSub() throws Exception {
        // Get the sub
        restSubMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSub() throws Exception {
        // Initialize the database
        subRepository.saveAndFlush(sub);

        int databaseSizeBeforeUpdate = subRepository.findAll().size();

        // Update the sub
        Sub updatedSub = subRepository.findById(sub.getId()).get();
        // Disconnect from session so that the updates on updatedSub are not directly saved in db
        em.detach(updatedSub);
        updatedSub.theRole(UPDATED_THE_ROLE).image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restSubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSub.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSub))
            )
            .andExpect(status().isOk());

        // Validate the Sub in the database
        List<Sub> subList = subRepository.findAll();
        assertThat(subList).hasSize(databaseSizeBeforeUpdate);
        Sub testSub = subList.get(subList.size() - 1);
        assertThat(testSub.getTheRole()).isEqualTo(UPDATED_THE_ROLE);
        assertThat(testSub.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testSub.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingSub() throws Exception {
        int databaseSizeBeforeUpdate = subRepository.findAll().size();
        sub.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sub.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sub))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sub in the database
        List<Sub> subList = subRepository.findAll();
        assertThat(subList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSub() throws Exception {
        int databaseSizeBeforeUpdate = subRepository.findAll().size();
        sub.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sub))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sub in the database
        List<Sub> subList = subRepository.findAll();
        assertThat(subList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSub() throws Exception {
        int databaseSizeBeforeUpdate = subRepository.findAll().size();
        sub.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sub)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sub in the database
        List<Sub> subList = subRepository.findAll();
        assertThat(subList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubWithPatch() throws Exception {
        // Initialize the database
        subRepository.saveAndFlush(sub);

        int databaseSizeBeforeUpdate = subRepository.findAll().size();

        // Update the sub using partial update
        Sub partialUpdatedSub = new Sub();
        partialUpdatedSub.setId(sub.getId());

        partialUpdatedSub.theRole(UPDATED_THE_ROLE);

        restSubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSub.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSub))
            )
            .andExpect(status().isOk());

        // Validate the Sub in the database
        List<Sub> subList = subRepository.findAll();
        assertThat(subList).hasSize(databaseSizeBeforeUpdate);
        Sub testSub = subList.get(subList.size() - 1);
        assertThat(testSub.getTheRole()).isEqualTo(UPDATED_THE_ROLE);
        assertThat(testSub.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testSub.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateSubWithPatch() throws Exception {
        // Initialize the database
        subRepository.saveAndFlush(sub);

        int databaseSizeBeforeUpdate = subRepository.findAll().size();

        // Update the sub using partial update
        Sub partialUpdatedSub = new Sub();
        partialUpdatedSub.setId(sub.getId());

        partialUpdatedSub.theRole(UPDATED_THE_ROLE).image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restSubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSub.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSub))
            )
            .andExpect(status().isOk());

        // Validate the Sub in the database
        List<Sub> subList = subRepository.findAll();
        assertThat(subList).hasSize(databaseSizeBeforeUpdate);
        Sub testSub = subList.get(subList.size() - 1);
        assertThat(testSub.getTheRole()).isEqualTo(UPDATED_THE_ROLE);
        assertThat(testSub.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testSub.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingSub() throws Exception {
        int databaseSizeBeforeUpdate = subRepository.findAll().size();
        sub.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sub.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sub))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sub in the database
        List<Sub> subList = subRepository.findAll();
        assertThat(subList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSub() throws Exception {
        int databaseSizeBeforeUpdate = subRepository.findAll().size();
        sub.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sub))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sub in the database
        List<Sub> subList = subRepository.findAll();
        assertThat(subList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSub() throws Exception {
        int databaseSizeBeforeUpdate = subRepository.findAll().size();
        sub.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sub)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sub in the database
        List<Sub> subList = subRepository.findAll();
        assertThat(subList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSub() throws Exception {
        // Initialize the database
        subRepository.saveAndFlush(sub);

        int databaseSizeBeforeDelete = subRepository.findAll().size();

        // Delete the sub
        restSubMockMvc.perform(delete(ENTITY_API_URL_ID, sub.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sub> subList = subRepository.findAll();
        assertThat(subList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
