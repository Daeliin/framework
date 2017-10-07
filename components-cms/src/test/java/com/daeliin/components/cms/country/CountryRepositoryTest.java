package com.daeliin.components.cms.country;

import com.daeliin.components.persistence.resource.repository.ResourceRepository;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class CountryRepositoryTest {

    @Test
    public void shouldExtendResourceRepository() {
        assertThat(CountryRepository.class.getSuperclass().getClass()).isEqualTo(ResourceRepository.class.getClass());
    }
}