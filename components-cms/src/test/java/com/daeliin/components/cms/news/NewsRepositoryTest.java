package com.daeliin.components.cms.news;

import com.daeliin.components.cms.Application;
import com.daeliin.components.persistence.resource.repository.ResourceRepository;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class NewsRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private NewsRepository newsRepository;

    @Test
    public void shouldExtendResourceRepository() {
        assertThat(NewsRepository.class.getSuperclass().getClass()).isEqualTo(ResourceRepository.class.getClass());
    }
}