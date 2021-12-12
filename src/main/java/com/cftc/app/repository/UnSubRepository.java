package com.cftc.app.repository;

import com.cftc.app.domain.UnSub;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UnSub entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnSubRepository extends JpaRepository<UnSub, Long> {}
