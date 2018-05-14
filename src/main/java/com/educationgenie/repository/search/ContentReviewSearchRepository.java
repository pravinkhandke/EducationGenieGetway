package com.educationgenie.repository.search;

import com.educationgenie.domain.ContentReview;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ContentReview entity.
 */
public interface ContentReviewSearchRepository extends ElasticsearchRepository<ContentReview, Long> {
}
