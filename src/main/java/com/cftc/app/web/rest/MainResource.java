package com.cftc.app.web.rest;

import com.cftc.app.domain.Main;
import com.cftc.app.domain.enumeration.TheRole;
import com.cftc.app.repository.MainRepository;
import com.cftc.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cftc.app.domain.Main}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MainResource {

    private final Logger log = LoggerFactory.getLogger(MainResource.class);

    private static final String ENTITY_NAME = "main";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MainRepository mainRepository;

    public MainResource(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    /**
     * {@code POST  /mains} : Create a new main.
     *
     * @param main the main to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new main, or with status {@code 400 (Bad Request)} if the main has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mains")
    public ResponseEntity<Main> createMain(@Valid @RequestBody Main main) throws URISyntaxException {
        log.debug("REST request to save Main : {}", main);
        if (main.getId() != null) {
            throw new BadRequestAlertException("A new main cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Main result = mainRepository.save(main);
        return ResponseEntity
            .created(new URI("/api/mains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mains/:id} : Updates an existing main.
     *
     * @param id the id of the main to save.
     * @param main the main to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated main,
     * or with status {@code 400 (Bad Request)} if the main is not valid,
     * or with status {@code 500 (Internal Server Error)} if the main couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mains/{id}")
    public ResponseEntity<Main> updateMain(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Main main)
        throws URISyntaxException {
        log.debug("REST request to update Main : {}, {}", id, main);
        if (main.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, main.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Main result = mainRepository.save(main);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, main.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mains/:id} : Partial updates given fields of an existing main, field will ignore if it is null
     *
     * @param id the id of the main to save.
     * @param main the main to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated main,
     * or with status {@code 400 (Bad Request)} if the main is not valid,
     * or with status {@code 404 (Not Found)} if the main is not found,
     * or with status {@code 500 (Internal Server Error)} if the main couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mains/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Main> partialUpdateMain(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Main main
    ) throws URISyntaxException {
        log.debug("REST request to partial update Main partially : {}, {}", id, main);
        if (main.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, main.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Main> result = mainRepository
            .findById(main.getId())
            .map(
                existingMain -> {
                    if (main.getTheRole() != null) {
                        existingMain.setTheRole(main.getTheRole());
                    }
                    if (main.getImage() != null) {
                        existingMain.setImage(main.getImage());
                    }
                    if (main.getImageContentType() != null) {
                        existingMain.setImageContentType(main.getImageContentType());
                    }

                    return existingMain;
                }
            )
            .map(mainRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, main.getId().toString())
        );
    }

    /**
     * {@code GET  /mains} : get all the mains.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mains in body.
     */
    @GetMapping("/mains")
    public List<Main> getAllMains() {
        log.debug("REST request to get all Mains");
        return mainRepository.findAll();
    }

    @GetMapping("/mainsa")
    public List<Main> getAllAnonymous() {
        log.debug("REST request to get all Anonymous Mains");
        return mainRepository.findByTheRoleAnonymous();
    }

    @GetMapping("/mainsr")
    public List<Main> getAllRoleMains() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String role = Arrays.toString(user.getAuthorities().toArray(new GrantedAuthority[0]));

        TheRole theRole = null;

        log.debug("REST request to get all Mains with Role");

        if (role.contains("ADMIN")) {
            return mainRepository.findByTheRole(theRole.ADMIN);
        } else if (role.contains("USER")) {
            return null;
        } else if (role.contains("TEACHER")) {
            return mainRepository.findByTheRole(theRole.TEACHER);
        } else if (role.contains("STUDENT")) {
            return mainRepository.findByTheRole(theRole.STUDENT);
        } else {
            return null;
        }
    }

    /**
     * {@code GET  /mains/:id} : get the "id" main.
     *
     * @param id the id of the main to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the main, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mains/{id}")
    public ResponseEntity<Main> getMain(@PathVariable Long id) {
        log.debug("REST request to get Main : {}", id);
        Optional<Main> main = mainRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(main);
    }

    /**
     * {@code DELETE  /mains/:id} : delete the "id" main.
     *
     * @param id the id of the main to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mains/{id}")
    public ResponseEntity<Void> deleteMain(@PathVariable Long id) {
        log.debug("REST request to delete Main : {}", id);
        mainRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
