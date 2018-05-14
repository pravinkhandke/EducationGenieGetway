package com.educationgenie.repository.search;

import com.educationgenie.domain.ContentList;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ContentList entity.
 */
public interface ContentListSearchRepository extends ElasticsearchRepository<ContentList, Long> {
}
