package com.cftc.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cftc.app.IntegrationTest;
import com.cftc.app.domain.LinkData;
import com.cftc.app.domain.enumeration.TheRole;
import com.cftc.app.repository.LinkDataRepository;
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
 * Integration tests for the {@link LinkDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LinkDataResourceIT {

    private static final TheRole DEFAULT_THE_ROLE = TheRole.ADMIN;
    private static final TheRole UPDATED_THE_ROLE = TheRole.ANONYMOUS;

    private static final byte[] DEFAULT_IMAGE_CARD = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_CARD = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CARD_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CARD_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGE_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_2 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_2_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGE_3 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_3 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_3_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_3_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/link-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LinkDataRepository linkDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLinkDataMockMvc;

    private LinkData linkData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LinkData createEntity(EntityManager em) {
        LinkData linkData = new LinkData()
            .theRole(DEFAULT_THE_ROLE)
            .imageCard(DEFAULT_IMAGE_CARD)
            .imageCardContentType(DEFAULT_IMAGE_CARD_CONTENT_TYPE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .image2(DEFAULT_IMAGE_2)
            .image2ContentType(DEFAULT_IMAGE_2_CONTENT_TYPE)
            .image3(DEFAULT_IMAGE_3)
            .image3ContentType(DEFAULT_IMAGE_3_CONTENT_TYPE);
        return linkData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LinkData createUpdatedEntity(EntityManager em) {
        LinkData linkData = new LinkData()
            .theRole(UPDATED_THE_ROLE)
            .imageCard(UPDATED_IMAGE_CARD)
            .imageCardContentType(UPDATED_IMAGE_CARD_CONTENT_TYPE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .image2(UPDATED_IMAGE_2)
            .image2ContentType(UPDATED_IMAGE_2_CONTENT_TYPE)
            .image3(UPDATED_IMAGE_3)
            .image3ContentType(UPDATED_IMAGE_3_CONTENT_TYPE);
        return linkData;
    }

    @BeforeEach
    public void initTest() {
        linkData = createEntity(em);
    }

    @Test
    @Transactional
    void createLinkData() throws Exception {
        int databaseSizeBeforeCreate = linkDataRepository.findAll().size();
        // Create the LinkData
        restLinkDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(linkData)))
            .andExpect(status().isCreated());

        // Validate the LinkData in the database
        List<LinkData> linkDataList = linkDataRepository.findAll();
        assertThat(linkDataList).hasSize(databaseSizeBeforeCreate + 1);
        LinkData testLinkData = linkDataList.get(linkDataList.size() - 1);
        assertThat(testLinkData.getTheRole()).isEqualTo(DEFAULT_THE_ROLE);
        assertThat(testLinkData.getImageCard()).isEqualTo(DEFAULT_IMAGE_CARD);
        assertThat(testLinkData.getImageCardContentType()).isEqualTo(DEFAULT_IMAGE_CARD_CONTENT_TYPE);
        assertThat(testLinkData.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testLinkData.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testLinkData.getImage2()).isEqualTo(DEFAULT_IMAGE_2);
        assertThat(testLinkData.getImage2ContentType()).isEqualTo(DEFAULT_IMAGE_2_CONTENT_TYPE);
        assertThat(testLinkData.getImage3()).isEqualTo(DEFAULT_IMAGE_3);
        assertThat(testLinkData.getImage3ContentType()).isEqualTo(DEFAULT_IMAGE_3_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createLinkDataWithExistingId() throws Exception {
        // Create the LinkData with an existing ID
        linkData.setId(1L);

        int databaseSizeBeforeCreate = linkDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLinkDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(linkData)))
            .andExpect(status().isBadRequest());

        // Validate the LinkData in the database
        List<LinkData> linkDataList = linkDataRepository.findAll();
        assertThat(linkDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTheRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = linkDataRepository.findAll().size();
        // set the field null
        linkData.setTheRole(null);

        // Create the LinkData, which fails.

        restLinkDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(linkData)))
            .andExpect(status().isBadRequest());

        List<LinkData> linkDataList = linkDataRepository.findAll();
        assertThat(linkDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLinkData() throws Exception {
        // Initialize the database
        linkDataRepository.saveAndFlush(linkData);

        // Get all the linkDataList
        restLinkDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(linkData.getId().intValue())))
            .andExpect(jsonPath("$.[*].theRole").value(hasItem(DEFAULT_THE_ROLE.toString())))
            .andExpect(jsonPath("$.[*].imageCardContentType").value(hasItem(DEFAULT_IMAGE_CARD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageCard").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_CARD))))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].image2ContentType").value(hasItem(DEFAULT_IMAGE_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_2))))
            .andExpect(jsonPath("$.[*].image3ContentType").value(hasItem(DEFAULT_IMAGE_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image3").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_3))));
    }

    @Test
    @Transactional
    void getLinkData() throws Exception {
        // Initialize the database
        linkDataRepository.saveAndFlush(linkData);

        // Get the linkData
        restLinkDataMockMvc
            .perform(get(ENTITY_API_URL_ID, linkData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(linkData.getId().intValue()))
            .andExpect(jsonPath("$.theRole").value(DEFAULT_THE_ROLE.toString()))
            .andExpect(jsonPath("$.imageCardContentType").value(DEFAULT_IMAGE_CARD_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageCard").value(Base64Utils.encodeToString(DEFAULT_IMAGE_CARD)))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.image2ContentType").value(DEFAULT_IMAGE_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.image2").value(Base64Utils.encodeToString(DEFAULT_IMAGE_2)))
            .andExpect(jsonPath("$.image3ContentType").value(DEFAULT_IMAGE_3_CONTENT_TYPE))
            .andExpect(jsonPath("$.image3").value(Base64Utils.encodeToString(DEFAULT_IMAGE_3)));
    }

    @Test
    @Transactional
    void getNonExistingLinkData() throws Exception {
        // Get the linkData
        restLinkDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLinkData() throws Exception {
        // Initialize the database
        linkDataRepository.saveAndFlush(linkData);

        int databaseSizeBeforeUpdate = linkDataRepository.findAll().size();

        // Update the linkData
        LinkData updatedLinkData = linkDataRepository.findById(linkData.getId()).get();
        // Disconnect from session so that the updates on updatedLinkData are not directly saved in db
        em.detach(updatedLinkData);
        updatedLinkData
            .theRole(UPDATED_THE_ROLE)
            .imageCard(UPDATED_IMAGE_CARD)
            .imageCardContentType(UPDATED_IMAGE_CARD_CONTENT_TYPE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .image2(UPDATED_IMAGE_2)
            .image2ContentType(UPDATED_IMAGE_2_CONTENT_TYPE)
            .image3(UPDATED_IMAGE_3)
            .image3ContentType(UPDATED_IMAGE_3_CONTENT_TYPE);

        restLinkDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLinkData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLinkData))
            )
            .andExpect(status().isOk());

        // Validate the LinkData in the database
        List<LinkData> linkDataList = linkDataRepository.findAll();
        assertThat(linkDataList).hasSize(databaseSizeBeforeUpdate);
        LinkData testLinkData = linkDataList.get(linkDataList.size() - 1);
        assertThat(testLinkData.getTheRole()).isEqualTo(UPDATED_THE_ROLE);
        assertThat(testLinkData.getImageCard()).isEqualTo(UPDATED_IMAGE_CARD);
        assertThat(testLinkData.getImageCardContentType()).isEqualTo(UPDATED_IMAGE_CARD_CONTENT_TYPE);
        assertThat(testLinkData.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testLinkData.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testLinkData.getImage2()).isEqualTo(UPDATED_IMAGE_2);
        assertThat(testLinkData.getImage2ContentType()).isEqualTo(UPDATED_IMAGE_2_CONTENT_TYPE);
        assertThat(testLinkData.getImage3()).isEqualTo(UPDATED_IMAGE_3);
        assertThat(testLinkData.getImage3ContentType()).isEqualTo(UPDATED_IMAGE_3_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingLinkData() throws Exception {
        int databaseSizeBeforeUpdate = linkDataRepository.findAll().size();
        linkData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLinkDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, linkData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(linkData))
            )
            .andExpect(status().isBadRequest());

        // Validate the LinkData in the database
        List<LinkData> linkDataList = linkDataRepository.findAll();
        assertThat(linkDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLinkData() throws Exception {
        int databaseSizeBeforeUpdate = linkDataRepository.findAll().size();
        linkData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(linkData))
            )
            .andExpect(status().isBadRequest());

        // Validate the LinkData in the database
        List<LinkData> linkDataList = linkDataRepository.findAll();
        assertThat(linkDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLinkData() throws Exception {
        int databaseSizeBeforeUpdate = linkDataRepository.findAll().size();
        linkData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(linkData)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LinkData in the database
        List<LinkData> linkDataList = linkDataRepository.findAll();
        assertThat(linkDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLinkDataWithPatch() throws Exception {
        // Initialize the database
        linkDataRepository.saveAndFlush(linkData);

        int databaseSizeBeforeUpdate = linkDataRepository.findAll().size();

        // Update the linkData using partial update
        LinkData partialUpdatedLinkData = new LinkData();
        partialUpdatedLinkData.setId(linkData.getId());

        partialUpdatedLinkData
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .image2(UPDATED_IMAGE_2)
            .image2ContentType(UPDATED_IMAGE_2_CONTENT_TYPE);

        restLinkDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLinkData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLinkData))
            )
            .andExpect(status().isOk());

        // Validate the LinkData in the database
        List<LinkData> linkDataList = linkDataRepository.findAll();
        assertThat(linkDataList).hasSize(databaseSizeBeforeUpdate);
        LinkData testLinkData = linkDataList.get(linkDataList.size() - 1);
        assertThat(testLinkData.getTheRole()).isEqualTo(DEFAULT_THE_ROLE);
        assertThat(testLinkData.getImageCard()).isEqualTo(DEFAULT_IMAGE_CARD);
        assertThat(testLinkData.getImageCardContentType()).isEqualTo(DEFAULT_IMAGE_CARD_CONTENT_TYPE);
        assertThat(testLinkData.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testLinkData.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testLinkData.getImage2()).isEqualTo(UPDATED_IMAGE_2);
        assertThat(testLinkData.getImage2ContentType()).isEqualTo(UPDATED_IMAGE_2_CONTENT_TYPE);
        assertThat(testLinkData.getImage3()).isEqualTo(DEFAULT_IMAGE_3);
        assertThat(testLinkData.getImage3ContentType()).isEqualTo(DEFAULT_IMAGE_3_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateLinkDataWithPatch() throws Exception {
        // Initialize the database
        linkDataRepository.saveAndFlush(linkData);

        int databaseSizeBeforeUpdate = linkDataRepository.findAll().size();

        // Update the linkData using partial update
        LinkData partialUpdatedLinkData = new LinkData();
        partialUpdatedLinkData.setId(linkData.getId());

        partialUpdatedLinkData
            .theRole(UPDATED_THE_ROLE)
            .imageCard(UPDATED_IMAGE_CARD)
            .imageCardContentType(UPDATED_IMAGE_CARD_CONTENT_TYPE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .image2(UPDATED_IMAGE_2)
            .image2ContentType(UPDATED_IMAGE_2_CONTENT_TYPE)
            .image3(UPDATED_IMAGE_3)
            .image3ContentType(UPDATED_IMAGE_3_CONTENT_TYPE);

        restLinkDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLinkData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLinkData))
            )
            .andExpect(status().isOk());

        // Validate the LinkData in the database
        List<LinkData> linkDataList = linkDataRepository.findAll();
        assertThat(linkDataList).hasSize(databaseSizeBeforeUpdate);
        LinkData testLinkData = linkDataList.get(linkDataList.size() - 1);
        assertThat(testLinkData.getTheRole()).isEqualTo(UPDATED_THE_ROLE);
        assertThat(testLinkData.getImageCard()).isEqualTo(UPDATED_IMAGE_CARD);
        assertThat(testLinkData.getImageCardContentType()).isEqualTo(UPDATED_IMAGE_CARD_CONTENT_TYPE);
        assertThat(testLinkData.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testLinkData.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testLinkData.getImage2()).isEqualTo(UPDATED_IMAGE_2);
        assertThat(testLinkData.getImage2ContentType()).isEqualTo(UPDATED_IMAGE_2_CONTENT_TYPE);
        assertThat(testLinkData.getImage3()).isEqualTo(UPDATED_IMAGE_3);
        assertThat(testLinkData.getImage3ContentType()).isEqualTo(UPDATED_IMAGE_3_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingLinkData() throws Exception {
        int databaseSizeBeforeUpdate = linkDataRepository.findAll().size();
        linkData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLinkDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, linkData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(linkData))
            )
            .andExpect(status().isBadRequest());

        // Validate the LinkData in the database
        List<LinkData> linkDataList = linkDataRepository.findAll();
        assertThat(linkDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLinkData() throws Exception {
        int databaseSizeBeforeUpdate = linkDataRepository.findAll().size();
        linkData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(linkData))
            )
            .andExpect(status().isBadRequest());

        // Validate the LinkData in the database
        List<LinkData> linkDataList = linkDataRepository.findAll();
        assertThat(linkDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLinkData() throws Exception {
        int databaseSizeBeforeUpdate = linkDataRepository.findAll().size();
        linkData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkDataMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(linkData)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LinkData in the database
        List<LinkData> linkDataList = linkDataRepository.findAll();
        assertThat(linkDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLinkData() throws Exception {
        // Initialize the database
        linkDataRepository.saveAndFlush(linkData);

        int databaseSizeBeforeDelete = linkDataRepository.findAll().size();

        // Delete the linkData
        restLinkDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, linkData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LinkData> linkDataList = linkDataRepository.findAll();
        assertThat(linkDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
