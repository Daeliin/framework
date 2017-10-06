package com.daeliin.components.cms.article;

import com.daeliin.components.persistence.resource.repository.ResourceRepository;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticleRepositoryTest {

    @Test
    public void shouldExtendResourceRepository() {
        assertThat(ArticleRepository.class.getSuperclass().getClass()).isEqualTo(ResourceRepository.class.getClass());
    }
}