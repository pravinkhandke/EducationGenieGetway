package com.educationgenie.repository.search;

import com.educationgenie.domain.QuestionBank;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the QuestionBank entity.
 */
public interface QuestionBankSearchRepository extends ElasticsearchRepository<QuestionBank, Long> {
}
