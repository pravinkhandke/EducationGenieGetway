package com.educationgenie.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ContentListSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ContentListSearchRepositoryMockConfiguration {

    @MockBean
    private ContentListSearchRepository mockContentListSearchRepository;

}
