package com.cftc.app.repository;

import com.cftc.app.domain.Link;
import com.cftc.app.domain.LinkData;
import com.cftc.app.domain.Sub;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Sub entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubRepository extends JpaRepository<Sub, Long> {
    @Query("select sub.links from Sub sub where sub.id = :id")
    List<Link> getLinksById(@Param("id") Long id);

    @Query("select sub.linkData from Sub sub where sub.id = :id")
    List<LinkData> getLinkDataById(@Param("id") Long id);
}
