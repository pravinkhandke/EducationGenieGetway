package com.educationgenie.repository.search;

import com.educationgenie.domain.Questions;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Questions entity.
 */
public interface QuestionsSearchRepository extends ElasticsearchRepository<Questions, Long> {
}
