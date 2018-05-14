package com.educationgenie.repository;

import com.educationgenie.domain.Content;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Content entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    @Query(value = "select distinct content from Content content left join fetch content.grades",
        countQuery = "select count(distinct content) from Content content")
    Page<Content> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct content from Content content left join fetch content.grades")
    List<Content> findAllWithEagerRelationships();

    @Query("select content from Content content left join fetch content.grades where content.id =:id")
    Optional<Content> findOneWithEagerRelationships(@Param("id") Long id);

}
