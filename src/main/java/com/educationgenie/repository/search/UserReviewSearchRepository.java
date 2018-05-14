package com.educationgenie.repository.search;

import com.educationgenie.domain.UserReview;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the UserReview entity.
 */
public interface UserReviewSearchRepository extends ElasticsearchRepository<UserReview, Long> {
}
