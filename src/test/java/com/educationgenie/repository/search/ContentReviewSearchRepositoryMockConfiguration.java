package com.educationgenie.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ContentReviewSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ContentReviewSearchRepositoryMockConfiguration {

    @MockBean
    private ContentReviewSearchRepository mockContentReviewSearchRepository;

}
