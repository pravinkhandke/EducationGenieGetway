package com.educationgenie.repository;

import com.educationgenie.domain.UserReview;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the UserReview entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserReviewRepository extends JpaRepository<UserReview, Long> {

}
