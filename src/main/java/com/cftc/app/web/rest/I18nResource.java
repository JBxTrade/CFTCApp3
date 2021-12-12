package com.cftc.app.web.rest;

import com.cftc.app.domain.I18n;
import com.cftc.app.repository.I18nRepository;
import com.cftc.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cftc.app.domain.I18n}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class I18nResource {

    private final Logger log = LoggerFactory.getLogger(I18nResource.class);

    private static final String ENTITY_NAME = "i18n";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final I18nRepository i18nRepository;

    public I18nResource(I18nRepository i18nRepository) {
        this.i18nRepository = i18nRepository;
    }

    /**
     * {@code POST  /i-18-ns} : Create a new i18n.
     *
     * @param i18n the i18n to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new i18n, or with status {@code 400 (Bad Request)} if the i18n has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/i-18-ns")
    public ResponseEntity<I18n> createI18n(@RequestBody I18n i18n) throws URISyntaxException {
        log.debug("REST request to save I18n : {}", i18n);
        if (i18n.getId() != null) {
            throw new BadRequestAlertException("A new i18n cannot already have an ID", ENTITY_NAME, "idexists");
        }
        I18n result = i18nRepository.save(i18n);
        return ResponseEntity
            .created(new URI("/api/i-18-ns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /i-18-ns/:id} : Updates an existing i18n.
     *
     * @param id the id of the i18n to save.
     * @param i18n the i18n to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated i18n,
     * or with status {@code 400 (Bad Request)} if the i18n is not valid,
     * or with status {@code 500 (Internal Server Error)} if the i18n couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/i-18-ns/{id}")
    public ResponseEntity<I18n> updateI18n(@PathVariable(value = "id", required = false) final Long id, @RequestBody I18n i18n)
        throws URISyntaxException {
        log.debug("REST request to update I18n : {}, {}", id, i18n);
        if (i18n.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, i18n.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!i18nRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        I18n result = i18nRepository.save(i18n);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, i18n.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /i-18-ns/:id} : Partial updates given fields of an existing i18n, field will ignore if it is null
     *
     * @param id the id of the i18n to save.
     * @param i18n the i18n to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated i18n,
     * or with status {@code 400 (Bad Request)} if the i18n is not valid,
     * or with status {@code 404 (Not Found)} if the i18n is not found,
     * or with status {@code 500 (Internal Server Error)} if the i18n couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/i-18-ns/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<I18n> partialUpdateI18n(@PathVariable(value = "id", required = false) final Long id, @RequestBody I18n i18n)
        throws URISyntaxException {
        log.debug("REST request to partial update I18n partially : {}, {}", id, i18n);
        if (i18n.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, i18n.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!i18nRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<I18n> result = i18nRepository
            .findById(i18n.getId())
            .map(
                existingI18n -> {
                    if (i18n.getFr() != null) {
                        existingI18n.setFr(i18n.getFr());
                    }
                    if (i18n.getEn() != null) {
                        existingI18n.setEn(i18n.getEn());
                    }

                    return existingI18n;
                }
            )
            .map(i18nRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, i18n.getId().toString())
        );
    }

    /**
     * {@code GET  /i-18-ns} : get all the i18ns.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of i18ns in body.
     */
    @GetMapping("/i-18-ns")
    public List<I18n> getAllI18ns(@RequestParam(required = false) String filter) {
        if ("maintitlefr-is-null".equals(filter)) {
            log.debug("REST request to get all I18ns where mainTitleFr is null");
            return StreamSupport
                .stream(i18nRepository.findAll().spliterator(), false)
                .filter(i18n -> i18n.getMainTitleFr() == null)
                .collect(Collectors.toList());
        }
        if ("maintitleen-is-null".equals(filter)) {
            log.debug("REST request to get all I18ns where mainTitleEn is null");
            return StreamSupport
                .stream(i18nRepository.findAll().spliterator(), false)
                .filter(i18n -> i18n.getMainTitleEn() == null)
                .collect(Collectors.toList());
        }
        if ("maindescriptionfr-is-null".equals(filter)) {
            log.debug("REST request to get all I18ns where mainDescriptionFr is null");
            return StreamSupport
                .stream(i18nRepository.findAll().spliterator(), false)
                .filter(i18n -> i18n.getMainDescriptionFr() == null)
                .collect(Collectors.toList());
        }
        if ("maindescriptionen-is-null".equals(filter)) {
            log.debug("REST request to get all I18ns where mainDescriptionEn is null");
            return StreamSupport
                .stream(i18nRepository.findAll().spliterator(), false)
                .filter(i18n -> i18n.getMainDescriptionEn() == null)
                .collect(Collectors.toList());
        }
        if ("subtitlefr-is-null".equals(filter)) {
            log.debug("REST request to get all I18ns where subTitleFr is null");
            return StreamSupport
                .stream(i18nRepository.findAll().spliterator(), false)
                .filter(i18n -> i18n.getSubTitleFr() == null)
                .collect(Collectors.toList());
        }
        if ("subtitleen-is-null".equals(filter)) {
            log.debug("REST request to get all I18ns where subTitleEn is null");
            return StreamSupport
                .stream(i18nRepository.findAll().spliterator(), false)
                .filter(i18n -> i18n.getSubTitleEn() == null)
                .collect(Collectors.toList());
        }
        if ("subdescriptionfr-is-null".equals(filter)) {
            log.debug("REST request to get all I18ns where subDescriptionFr is null");
            return StreamSupport
                .stream(i18nRepository.findAll().spliterator(), false)
                .filter(i18n -> i18n.getSubDescriptionFr() == null)
                .collect(Collectors.toList());
        }
        if ("subdescriptionen-is-null".equals(filter)) {
            log.debug("REST request to get all I18ns where subDescriptionEn is null");
            return StreamSupport
                .stream(i18nRepository.findAll().spliterator(), false)
                .filter(i18n -> i18n.getSubDescriptionEn() == null)
                .collect(Collectors.toList());
        }
        if ("unsubtitlefr-is-null".equals(filter)) {
            log.debug("REST request to get all I18ns where unSubTitleFr is null");
            return StreamSupport
                .stream(i18nRepository.findAll().spliterator(), false)
                .filter(i18n -> i18n.getUnSubTitleFr() == null)
                .collect(Collectors.toList());
        }
        if ("unsubtitleen-is-null".equals(filter)) {
            log.debug("REST request to get all I18ns where unSubTitleEn is null");
            return StreamSupport
                .stream(i18nRepository.findAll().spliterator(), false)
                .filter(i18n -> i18n.getUnSubTitleEn() == null)
                .collect(Collectors.toList());
        }
        if ("unsubdescriptionfr-is-null".equals(filter)) {
            log.debug("REST request to get all I18ns where unSubDescriptionFr is null");
            return StreamSupport
                .stream(i18nRepository.findAll().spliterator(), false)
                .filter(i18n -> i18n.getUnSubDescriptionFr() == null)
                .collect(Collectors.toList());
        }
        if ("unsubdescriptionen-is-null".equals(filter)) {
            log.debug("REST request to get all I18ns where unSubDescriptionEn is null");
            return StreamSupport
                .stream(i18nRepository.findAll().spliterator(), false)
                .filter(i18n -> i18n.getUnSubDescriptionEn() == null)
                .collect(Collectors.toList());
        }
        if ("linktitlefr-is-null".equals(filter)) {
            log.debug("REST request to get all I18ns where linkTitleFr is null");
            return StreamSupport
                .stream(i18nRepository.findAll().spliterator(), false)
                .filter(i18n -> i18n.getLinkTitleFr() == null)
                .collect(Collectors.toList());
        }
        if ("linktitleen-is-null".equals(filter)) {
            log.debug("REST request to get all I18ns where linkTitleEn is null");
            return StreamSupport
                .stream(i18nRepository.findAll().spliterator(), false)
                .filter(i18n -> i18n.getLinkTitleEn() == null)
                .collect(Collectors.toList());
        }
        if ("linkdescriptionfr-is-null".equals(filter)) {
            log.debug("REST request to get all I18ns where linkDescriptionFr is null");
            return StreamSupport
                .stream(i18nRepository.findAll().spliterator(), false)
                .filter(i18n -> i18n.getLinkDescriptionFr() == null)
                .collect(Collectors.toList());
        }
        if ("linkdescriptionen-is-null".equals(filter)) {
            log.debug("REST request to get all I18ns where linkDescriptionEn is null");
            return StreamSupport
                .stream(i18nRepository.findAll().spliterator(), false)
                .filter(i18n -> i18n.getLinkDescriptionEn() == null)
                .collect(Collectors.toList());
        }
        if ("linkdatatitlefr-is-null".equals(filter)) {
            log.debug("REST request to get all I18ns where linkdataTitleFr is null");
            return StreamSupport
                .stream(i18nRepository.findAll().spliterator(), false)
                .filter(i18n -> i18n.getLinkdataTitleFr() == null)
                .collect(Collectors.toList());
        }
        if ("linkdatatitleen-is-null".equals(filter)) {
            log.debug("REST request to get all I18ns where linkdataTitleEn is null");
            return StreamSupport
                .stream(i18nRepository.findAll().spliterator(), false)
                .filter(i18n -> i18n.getLinkdataTitleEn() == null)
                .collect(Collectors.toList());
        }
        if ("linkdatadescriptionfr-is-null".equals(filter)) {
            log.debug("REST request to get all I18ns where linkdataDescriptionFr is null");
            return StreamSupport
                .stream(i18nRepository.findAll().spliterator(), false)
                .filter(i18n -> i18n.getLinkdataDescriptionFr() == null)
                .collect(Collectors.toList());
        }
        if ("linkdatadescriptionen-is-null".equals(filter)) {
            log.debug("REST request to get all I18ns where linkdataDescriptionEn is null");
            return StreamSupport
                .stream(i18nRepository.findAll().spliterator(), false)
                .filter(i18n -> i18n.getLinkdataDescriptionEn() == null)
                .collect(Collectors.toList());
        }
        if ("codefr-is-null".equals(filter)) {
            log.debug("REST request to get all I18ns where codeFr is null");
            return StreamSupport
                .stream(i18nRepository.findAll().spliterator(), false)
                .filter(i18n -> i18n.getCodeFr() == null)
                .collect(Collectors.toList());
        }
        if ("codeen-is-null".equals(filter)) {
            log.debug("REST request to get all I18ns where codeEn is null");
            return StreamSupport
                .stream(i18nRepository.findAll().spliterator(), false)
                .filter(i18n -> i18n.getCodeEn() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all I18ns");
        return i18nRepository.findAll();
    }

    /**
     * {@code GET  /i-18-ns/:id} : get the "id" i18n.
     *
     * @param id the id of the i18n to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the i18n, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/i-18-ns/{id}")
    public ResponseEntity<I18n> getI18n(@PathVariable Long id) {
        log.debug("REST request to get I18n : {}", id);
        Optional<I18n> i18n = i18nRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(i18n);
    }

    /**
     * {@code DELETE  /i-18-ns/:id} : delete the "id" i18n.
     *
     * @param id the id of the i18n to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/i-18-ns/{id}")
    public ResponseEntity<Void> deleteI18n(@PathVariable Long id) {
        log.debug("REST request to delete I18n : {}", id);
        i18nRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
