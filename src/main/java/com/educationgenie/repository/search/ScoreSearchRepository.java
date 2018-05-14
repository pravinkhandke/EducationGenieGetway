package com.educationgenie.repository.search;

import com.educationgenie.domain.Score;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Score entity.
 */
public interface ScoreSearchRepository extends ElasticsearchRepository<Score, Long> {
}
