package com.educationgenie.repository.search;

import com.educationgenie.domain.Chapter;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Chapter entity.
 */
public interface ChapterSearchRepository extends ElasticsearchRepository<Chapter, Long> {
}
