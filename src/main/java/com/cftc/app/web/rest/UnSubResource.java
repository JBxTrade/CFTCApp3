package com.cftc.app.web.rest;

import com.cftc.app.domain.UnSub;
import com.cftc.app.repository.UnSubRepository;
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
 * REST controller for managing {@link com.cftc.app.domain.UnSub}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UnSubResource {

    private final Logger log = LoggerFactory.getLogger(UnSubResource.class);

    private static final String ENTITY_NAME = "unSub";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnSubRepository unSubRepository;

    public UnSubResource(UnSubRepository unSubRepository) {
        this.unSubRepository = unSubRepository;
    }

    /**
     * {@code POST  /un-subs} : Create a new unSub.
     *
     * @param unSub the unSub to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unSub, or with status {@code 400 (Bad Request)} if the unSub has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/un-subs")
    public ResponseEntity<UnSub> createUnSub(@Valid @RequestBody UnSub unSub) throws URISyntaxException {
        log.debug("REST request to save UnSub : {}", unSub);
        if (unSub.getId() != null) {
            throw new BadRequestAlertException("A new unSub cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnSub result = unSubRepository.save(unSub);
        return ResponseEntity
            .created(new URI("/api/un-subs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /un-subs/:id} : Updates an existing unSub.
     *
     * @param id the id of the unSub to save.
     * @param unSub the unSub to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unSub,
     * or with status {@code 400 (Bad Request)} if the unSub is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unSub couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/un-subs/{id}")
    public ResponseEntity<UnSub> updateUnSub(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody UnSub unSub)
        throws URISyntaxException {
        log.debug("REST request to update UnSub : {}, {}", id, unSub);
        if (unSub.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, unSub.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!unSubRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UnSub result = unSubRepository.save(unSub);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, unSub.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /un-subs/:id} : Partial updates given fields of an existing unSub, field will ignore if it is null
     *
     * @param id the id of the unSub to save.
     * @param unSub the unSub to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unSub,
     * or with status {@code 400 (Bad Request)} if the unSub is not valid,
     * or with status {@code 404 (Not Found)} if the unSub is not found,
     * or with status {@code 500 (Internal Server Error)} if the unSub couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/un-subs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<UnSub> partialUpdateUnSub(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UnSub unSub
    ) throws URISyntaxException {
        log.debug("REST request to partial update UnSub partially : {}, {}", id, unSub);
        if (unSub.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, unSub.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!unSubRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UnSub> result = unSubRepository
            .findById(unSub.getId())
            .map(
                existingUnSub -> {
                    if (unSub.getTheRole() != null) {
                        existingUnSub.setTheRole(unSub.getTheRole());
                    }
                    if (unSub.getImage() != null) {
                        existingUnSub.setImage(unSub.getImage());
                    }
                    if (unSub.getImageContentType() != null) {
                        existingUnSub.setImageContentType(unSub.getImageContentType());
                    }

                    return existingUnSub;
                }
            )
            .map(unSubRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, unSub.getId().toString())
        );
    }

    /**
     * {@code GET  /un-subs} : get all the unSubs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of unSubs in body.
     */
    @GetMapping("/un-subs")
    public List<UnSub> getAllUnSubs() {
        log.debug("REST request to get all UnSubs");
        return unSubRepository.findAll();
    }

    /**
     * {@code GET  /un-subs/:id} : get the "id" unSub.
     *
     * @param id the id of the unSub to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unSub, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/un-subs/{id}")
    public ResponseEntity<UnSub> getUnSub(@PathVariable Long id) {
        log.debug("REST request to get UnSub : {}", id);
        Optional<UnSub> unSub = unSubRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(unSub);
    }

    /**
     * {@code DELETE  /un-subs/:id} : delete the "id" unSub.
     *
     * @param id the id of the unSub to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/un-subs/{id}")
    public ResponseEntity<Void> deleteUnSub(@PathVariable Long id) {
        log.debug("REST request to delete UnSub : {}", id);
        unSubRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
