package com.cftc.app.repository;

import com.cftc.app.domain.Main;
import com.cftc.app.domain.enumeration.TheRole;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Main entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MainRepository extends JpaRepository<Main, Long> {
    @Query("select main from Main main where main.theRole = com.cftc.app.domain.enumeration.TheRole.ANONYMOUS")
    List<Main> findByTheRoleAnonymous();

    @Query("select main from Main main where main.theRole = :role")
    List<Main> findByTheRole(@Param("role") TheRole role);
}
