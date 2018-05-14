package com.educationgenie.repository;

import com.educationgenie.domain.ContentReview;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the ContentReview entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContentReviewRepository extends JpaRepository<ContentReview, Long> {

}
