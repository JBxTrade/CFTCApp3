package com.cftc.app.web.rest;

import com.cftc.app.domain.LinkData;
import com.cftc.app.repository.LinkDataRepository;
import com.cftc.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cftc.app.domain.LinkData}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LinkDataResource {

    private final Logger log = LoggerFactory.getLogger(LinkDataResource.class);

    private static final String ENTITY_NAME = "linkData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LinkDataRepository linkDataRepository;

    public LinkDataResource(LinkDataRepository linkDataRepository) {
        this.linkDataRepository = linkDataRepository;
    }

    /**
     * {@code POST  /link-data} : Create a new linkData.
     *
     * @param linkData the linkData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new linkData, or with status {@code 400 (Bad Request)} if the linkData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/link-data")
    public ResponseEntity<LinkData> createLinkData(@Valid @RequestBody LinkData linkData) throws URISyntaxException {
        log.debug("REST request to save LinkData : {}", linkData);
        if (linkData.getId() != null) {
            throw new BadRequestAlertException("A new linkData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LinkData result = linkDataRepository.save(linkData);
        return ResponseEntity
            .created(new URI("/api/link-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /link-data/:id} : Updates an existing linkData.
     *
     * @param id the id of the linkData to save.
     * @param linkData the linkData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated linkData,
     * or with status {@code 400 (Bad Request)} if the linkData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the linkData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/link-data/{id}")
    public ResponseEntity<LinkData> updateLinkData(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LinkData linkData
    ) throws URISyntaxException {
        log.debug("REST request to update LinkData : {}, {}", id, linkData);
        if (linkData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, linkData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!linkDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LinkData result = linkDataRepository.save(linkData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, linkData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /link-data/:id} : Partial updates given fields of an existing linkData, field will ignore if it is null
     *
     * @param id the id of the linkData to save.
     * @param linkData the linkData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated linkData,
     * or with status {@code 400 (Bad Request)} if the linkData is not valid,
     * or with status {@code 404 (Not Found)} if the linkData is not found,
     * or with status {@code 500 (Internal Server Error)} if the linkData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/link-data/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<LinkData> partialUpdateLinkData(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LinkData linkData
    ) throws URISyntaxException {
        log.debug("REST request to partial update LinkData partially : {}, {}", id, linkData);
        if (linkData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, linkData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!linkDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LinkData> result = linkDataRepository
            .findById(linkData.getId())
            .map(
                existingLinkData -> {
                    if (linkData.getTheRole() != null) {
                        existingLinkData.setTheRole(linkData.getTheRole());
                    }
                    if (linkData.getImageCard() != null) {
                        existingLinkData.setImageCard(linkData.getImageCard());
                    }
                    if (linkData.getImageCardContentType() != null) {
                        existingLinkData.setImageCardContentType(linkData.getImageCardContentType());
                    }
                    if (linkData.getImage() != null) {
                        existingLinkData.setImage(linkData.getImage());
                    }
                    if (linkData.getImageContentType() != null) {
                        existingLinkData.setImageContentType(linkData.getImageContentType());
                    }
                    if (linkData.getImage2() != null) {
                        existingLinkData.setImage2(linkData.getImage2());
                    }
                    if (linkData.getImage2ContentType() != null) {
                        existingLinkData.setImage2ContentType(linkData.getImage2ContentType());
                    }
                    if (linkData.getImage3() != null) {
                        existingLinkData.setImage3(linkData.getImage3());
                    }
                    if (linkData.getImage3ContentType() != null) {
                        existingLinkData.setImage3ContentType(linkData.getImage3ContentType());
                    }

                    return existingLinkData;
                }
            )
            .map(linkDataRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, linkData.getId().toString())
        );
    }

    /**
     * {@code GET  /link-data} : get all the linkData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of linkData in body.
     */
    @GetMapping("/link-data")
    public List<LinkData> getAllLinkData() {
        log.debug("REST request to get all LinkData");
        return linkDataRepository.findAll();
    }

    /**
     * {@code GET  /link-data/:id} : get the "id" linkData.
     *
     * @param id the id of the linkData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the linkData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/link-data/{id}")
    public ResponseEntity<LinkData> getLinkData(@PathVariable Long id) {
        log.debug("REST request to get LinkData : {}", id);
        Optional<LinkData> linkData = linkDataRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(linkData);
    }

    /**
     * {@code DELETE  /link-data/:id} : delete the "id" linkData.
     *
     * @param id the id of the linkData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/link-data/{id}")
    public ResponseEntity<Void> deleteLinkData(@PathVariable Long id) {
        log.debug("REST request to delete LinkData : {}", id);
        linkDataRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
