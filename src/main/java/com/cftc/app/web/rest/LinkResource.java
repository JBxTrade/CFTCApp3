package com.cftc.app.web.rest;

import com.cftc.app.domain.Link;
import com.cftc.app.repository.LinkRepository;
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
 * REST controller for managing {@link com.cftc.app.domain.Link}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LinkResource {

    private final Logger log = LoggerFactory.getLogger(LinkResource.class);

    private static final String ENTITY_NAME = "link";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LinkRepository linkRepository;

    public LinkResource(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    /**
     * {@code POST  /links} : Create a new link.
     *
     * @param link the link to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new link, or with status {@code 400 (Bad Request)} if the link has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/links")
    public ResponseEntity<Link> createLink(@Valid @RequestBody Link link) throws URISyntaxException {
        log.debug("REST request to save Link : {}", link);
        if (link.getId() != null) {
            throw new BadRequestAlertException("A new link cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Link result = linkRepository.save(link);
        return ResponseEntity
            .created(new URI("/api/links/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /links/:id} : Updates an existing link.
     *
     * @param id the id of the link to save.
     * @param link the link to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated link,
     * or with status {@code 400 (Bad Request)} if the link is not valid,
     * or with status {@code 500 (Internal Server Error)} if the link couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/links/{id}")
    public ResponseEntity<Link> updateLink(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Link link)
        throws URISyntaxException {
        log.debug("REST request to update Link : {}, {}", id, link);
        if (link.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, link.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!linkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Link result = linkRepository.save(link);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, link.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /links/:id} : Partial updates given fields of an existing link, field will ignore if it is null
     *
     * @param id the id of the link to save.
     * @param link the link to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated link,
     * or with status {@code 400 (Bad Request)} if the link is not valid,
     * or with status {@code 404 (Not Found)} if the link is not found,
     * or with status {@code 500 (Internal Server Error)} if the link couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/links/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Link> partialUpdateLink(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Link link
    ) throws URISyntaxException {
        log.debug("REST request to partial update Link partially : {}, {}", id, link);
        if (link.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, link.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!linkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Link> result = linkRepository
            .findById(link.getId())
            .map(
                existingLink -> {
                    if (link.getTheRole() != null) {
                        existingLink.setTheRole(link.getTheRole());
                    }
                    if (link.getImage() != null) {
                        existingLink.setImage(link.getImage());
                    }
                    if (link.getImageContentType() != null) {
                        existingLink.setImageContentType(link.getImageContentType());
                    }
                    if (link.getTheLink() != null) {
                        existingLink.setTheLink(link.getTheLink());
                    }

                    return existingLink;
                }
            )
            .map(linkRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, link.getId().toString())
        );
    }

    /**
     * {@code GET  /links} : get all the links.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of links in body.
     */
    @GetMapping("/links")
    public List<Link> getAllLinks() {
        log.debug("REST request to get all Links");
        return linkRepository.findAll();
    }

    /**
     * {@code GET  /links/:id} : get the "id" link.
     *
     * @param id the id of the link to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the link, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/links/{id}")
    public ResponseEntity<Link> getLink(@PathVariable Long id) {
        log.debug("REST request to get Link : {}", id);
        Optional<Link> link = linkRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(link);
    }

    /**
     * {@code DELETE  /links/:id} : delete the "id" link.
     *
     * @param id the id of the link to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/links/{id}")
    public ResponseEntity<Void> deleteLink(@PathVariable Long id) {
        log.debug("REST request to delete Link : {}", id);
        linkRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
