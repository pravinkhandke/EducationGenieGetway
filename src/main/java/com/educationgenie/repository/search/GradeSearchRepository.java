package com.educationgenie.repository.search;

import com.educationgenie.domain.Grade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Grade entity.
 */
public interface GradeSearchRepository extends ElasticsearchRepository<Grade, Long> {
}
