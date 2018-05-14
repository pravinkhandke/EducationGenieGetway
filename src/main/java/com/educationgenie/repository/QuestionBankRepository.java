package com.educationgenie.repository;

import com.educationgenie.domain.QuestionBank;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the QuestionBank entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionBankRepository extends JpaRepository<QuestionBank, Long> {

}
