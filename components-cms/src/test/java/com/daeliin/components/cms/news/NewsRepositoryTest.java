package com.daeliin.components.cms.news;

import com.daeliin.components.core.resource.repository.ResourceRepository;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NewsRepositoryTest {

    @Test
    public void shouldExtendResourceRepository() {
        assertThat(NewsRepository.class.getSuperclass().getClass()).isEqualTo(ResourceRepository.class.getClass());
    }
}