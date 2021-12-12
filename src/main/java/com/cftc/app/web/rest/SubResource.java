package com.cftc.app.web.rest;

import com.cftc.app.domain.Link;
import com.cftc.app.domain.LinkData;
import com.cftc.app.domain.Sub;
import com.cftc.app.repository.SubRepository;
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
 * REST controller for managing {@link com.cftc.app.domain.Sub}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SubResource {

    private final Logger log = LoggerFactory.getLogger(SubResource.class);

    private static final String ENTITY_NAME = "sub";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubRepository subRepository;

    public SubResource(SubRepository subRepository) {
        this.subRepository = subRepository;
    }

    /**
     * {@code POST  /subs} : Create a new sub.
     *
     * @param sub the sub to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sub, or with status {@code 400 (Bad Request)} if the sub has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subs")
    public ResponseEntity<Sub> createSub(@Valid @RequestBody Sub sub) throws URISyntaxException {
        log.debug("REST request to save Sub : {}", sub);
        if (sub.getId() != null) {
            throw new BadRequestAlertException("A new sub cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sub result = subRepository.save(sub);
        return ResponseEntity
            .created(new URI("/api/subs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subs/:id} : Updates an existing sub.
     *
     * @param id the id of the sub to save.
     * @param sub the sub to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sub,
     * or with status {@code 400 (Bad Request)} if the sub is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sub couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subs/{id}")
    public ResponseEntity<Sub> updateSub(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Sub sub)
        throws URISyntaxException {
        log.debug("REST request to update Sub : {}, {}", id, sub);
        if (sub.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sub.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Sub result = subRepository.save(sub);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sub.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /subs/:id} : Partial updates given fields of an existing sub, field will ignore if it is null
     *
     * @param id the id of the sub to save.
     * @param sub the sub to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sub,
     * or with status {@code 400 (Bad Request)} if the sub is not valid,
     * or with status {@code 404 (Not Found)} if the sub is not found,
     * or with status {@code 500 (Internal Server Error)} if the sub couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/subs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Sub> partialUpdateSub(@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody Sub sub)
        throws URISyntaxException {
        log.debug("REST request to partial update Sub partially : {}, {}", id, sub);
        if (sub.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sub.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sub> result = subRepository
            .findById(sub.getId())
            .map(
                existingSub -> {
                    if (sub.getTheRole() != null) {
                        existingSub.setTheRole(sub.getTheRole());
                    }
                    if (sub.getImage() != null) {
                        existingSub.setImage(sub.getImage());
                    }
                    if (sub.getImageContentType() != null) {
                        existingSub.setImageContentType(sub.getImageContentType());
                    }

                    return existingSub;
                }
            )
            .map(subRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sub.getId().toString())
        );
    }

    /**
     * {@code GET  /subs} : get all the subs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subs in body.
     */
    @GetMapping("/subs")
    public List<Sub> getAllSubs() {
        log.debug("REST request to get all Subs");
        return subRepository.findAll();
    }

    /**
     * {@code GET  /subs/:id} : get the "id" sub.
     *
     * @param id the id of the sub to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sub, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subs/{id}")
    public ResponseEntity<Sub> getSub(@PathVariable Long id) {
        log.debug("REST request to get Sub : {}", id);
        Optional<Sub> sub = subRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sub);
    }

    @GetMapping("/subs/links/{id}")
    public List<Link> getLinksSub(@PathVariable Long id) {
        log.debug("REST request to get Links for Sub : {}", id);
        return subRepository.getLinksById(id);
    }

    @GetMapping("/subs/linkdata/{id}")
    public List<LinkData> getLinkDataSub(@PathVariable Long id) {
        log.debug("REST request to get Links for Sub : {}", id);
        return subRepository.getLinkDataById(id);
    }

    /**
     * {@code DELETE  /subs/:id} : delete the "id" sub.
     *
     * @param id the id of the sub to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subs/{id}")
    public ResponseEntity<Void> deleteSub(@PathVariable Long id) {
        log.debug("REST request to delete Sub : {}", id);
        subRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
