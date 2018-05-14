package com.educationgenie.repository;

import com.educationgenie.domain.ContentList;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the ContentList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContentListRepository extends JpaRepository<ContentList, Long> {

}
