package com.educationgenie.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ChapterSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ChapterSearchRepositoryMockConfiguration {

    @MockBean
    private ChapterSearchRepository mockChapterSearchRepository;

}
