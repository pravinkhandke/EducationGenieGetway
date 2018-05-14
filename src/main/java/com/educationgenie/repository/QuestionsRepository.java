package com.educationgenie.repository;

import com.educationgenie.domain.Questions;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Questions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Long> {

}
