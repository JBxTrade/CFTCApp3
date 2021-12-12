package com.cftc.app.repository;

import com.cftc.app.domain.I18n;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the I18n entity.
 */
@SuppressWarnings("unused")
@Repository
public interface I18nRepository extends JpaRepository<I18n, Long> {}
