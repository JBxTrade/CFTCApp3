package com.cftc.app.repository;

import com.cftc.app.domain.LinkData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LinkData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LinkDataRepository extends JpaRepository<LinkData, Long> {}
