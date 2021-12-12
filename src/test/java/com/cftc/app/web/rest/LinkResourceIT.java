package com.cftc.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cftc.app.IntegrationTest;
import com.cftc.app.domain.Link;
import com.cftc.app.domain.enumeration.TheRole;
import com.cftc.app.repository.LinkRepository;
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
 * Integration tests for the {@link LinkResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LinkResourceIT {

    private static final TheRole DEFAULT_THE_ROLE = TheRole.ADMIN;
    private static final TheRole UPDATED_THE_ROLE = TheRole.ANONYMOUS;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_THE_LINK = "AAAAAAAAAA";
    private static final String UPDATED_THE_LINK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/links";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLinkMockMvc;

    private Link link;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Link createEntity(EntityManager em) {
        Link link = new Link()
            .theRole(DEFAULT_THE_ROLE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .theLink(DEFAULT_THE_LINK);
        return link;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Link createUpdatedEntity(EntityManager em) {
        Link link = new Link()
            .theRole(UPDATED_THE_ROLE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .theLink(UPDATED_THE_LINK);
        return link;
    }

    @BeforeEach
    public void initTest() {
        link = createEntity(em);
    }

    @Test
    @Transactional
    void createLink() throws Exception {
        int databaseSizeBeforeCreate = linkRepository.findAll().size();
        // Create the Link
        restLinkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(link)))
            .andExpect(status().isCreated());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeCreate + 1);
        Link testLink = linkList.get(linkList.size() - 1);
        assertThat(testLink.getTheRole()).isEqualTo(DEFAULT_THE_ROLE);
        assertThat(testLink.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testLink.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testLink.getTheLink()).isEqualTo(DEFAULT_THE_LINK);
    }

    @Test
    @Transactional
    void createLinkWithExistingId() throws Exception {
        // Create the Link with an existing ID
        link.setId(1L);

        int databaseSizeBeforeCreate = linkRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLinkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(link)))
            .andExpect(status().isBadRequest());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTheRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = linkRepository.findAll().size();
        // set the field null
        link.setTheRole(null);

        // Create the Link, which fails.

        restLinkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(link)))
            .andExpect(status().isBadRequest());

        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTheLinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = linkRepository.findAll().size();
        // set the field null
        link.setTheLink(null);

        // Create the Link, which fails.

        restLinkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(link)))
            .andExpect(status().isBadRequest());

        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLinks() throws Exception {
        // Initialize the database
        linkRepository.saveAndFlush(link);

        // Get all the linkList
        restLinkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(link.getId().intValue())))
            .andExpect(jsonPath("$.[*].theRole").value(hasItem(DEFAULT_THE_ROLE.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].theLink").value(hasItem(DEFAULT_THE_LINK)));
    }

    @Test
    @Transactional
    void getLink() throws Exception {
        // Initialize the database
        linkRepository.saveAndFlush(link);

        // Get the link
        restLinkMockMvc
            .perform(get(ENTITY_API_URL_ID, link.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(link.getId().intValue()))
            .andExpect(jsonPath("$.theRole").value(DEFAULT_THE_ROLE.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.theLink").value(DEFAULT_THE_LINK));
    }

    @Test
    @Transactional
    void getNonExistingLink() throws Exception {
        // Get the link
        restLinkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLink() throws Exception {
        // Initialize the database
        linkRepository.saveAndFlush(link);

        int databaseSizeBeforeUpdate = linkRepository.findAll().size();

        // Update the link
        Link updatedLink = linkRepository.findById(link.getId()).get();
        // Disconnect from session so that the updates on updatedLink are not directly saved in db
        em.detach(updatedLink);
        updatedLink.theRole(UPDATED_THE_ROLE).image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE).theLink(UPDATED_THE_LINK);

        restLinkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLink.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLink))
            )
            .andExpect(status().isOk());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeUpdate);
        Link testLink = linkList.get(linkList.size() - 1);
        assertThat(testLink.getTheRole()).isEqualTo(UPDATED_THE_ROLE);
        assertThat(testLink.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testLink.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testLink.getTheLink()).isEqualTo(UPDATED_THE_LINK);
    }

    @Test
    @Transactional
    void putNonExistingLink() throws Exception {
        int databaseSizeBeforeUpdate = linkRepository.findAll().size();
        link.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLinkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, link.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(link))
            )
            .andExpect(status().isBadRequest());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLink() throws Exception {
        int databaseSizeBeforeUpdate = linkRepository.findAll().size();
        link.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(link))
            )
            .andExpect(status().isBadRequest());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLink() throws Exception {
        int databaseSizeBeforeUpdate = linkRepository.findAll().size();
        link.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(link)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLinkWithPatch() throws Exception {
        // Initialize the database
        linkRepository.saveAndFlush(link);

        int databaseSizeBeforeUpdate = linkRepository.findAll().size();

        // Update the link using partial update
        Link partialUpdatedLink = new Link();
        partialUpdatedLink.setId(link.getId());

        partialUpdatedLink.image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restLinkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLink.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLink))
            )
            .andExpect(status().isOk());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeUpdate);
        Link testLink = linkList.get(linkList.size() - 1);
        assertThat(testLink.getTheRole()).isEqualTo(DEFAULT_THE_ROLE);
        assertThat(testLink.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testLink.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testLink.getTheLink()).isEqualTo(DEFAULT_THE_LINK);
    }

    @Test
    @Transactional
    void fullUpdateLinkWithPatch() throws Exception {
        // Initialize the database
        linkRepository.saveAndFlush(link);

        int databaseSizeBeforeUpdate = linkRepository.findAll().size();

        // Update the link using partial update
        Link partialUpdatedLink = new Link();
        partialUpdatedLink.setId(link.getId());

        partialUpdatedLink
            .theRole(UPDATED_THE_ROLE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .theLink(UPDATED_THE_LINK);

        restLinkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLink.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLink))
            )
            .andExpect(status().isOk());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeUpdate);
        Link testLink = linkList.get(linkList.size() - 1);
        assertThat(testLink.getTheRole()).isEqualTo(UPDATED_THE_ROLE);
        assertThat(testLink.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testLink.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testLink.getTheLink()).isEqualTo(UPDATED_THE_LINK);
    }

    @Test
    @Transactional
    void patchNonExistingLink() throws Exception {
        int databaseSizeBeforeUpdate = linkRepository.findAll().size();
        link.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLinkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, link.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(link))
            )
            .andExpect(status().isBadRequest());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLink() throws Exception {
        int databaseSizeBeforeUpdate = linkRepository.findAll().size();
        link.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(link))
            )
            .andExpect(status().isBadRequest());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLink() throws Exception {
        int databaseSizeBeforeUpdate = linkRepository.findAll().size();
        link.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(link)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLink() throws Exception {
        // Initialize the database
        linkRepository.saveAndFlush(link);

        int databaseSizeBeforeDelete = linkRepository.findAll().size();

        // Delete the link
        restLinkMockMvc
            .perform(delete(ENTITY_API_URL_ID, link.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
