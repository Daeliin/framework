package com.daeliin.components.core.event;


import com.daeliin.components.core.resource.repository.ResourceRepository;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class EventLogRepositoryTest {

    @Test
    public void shouldExtendResourceRepository() {
        assertThat(EventLogRepository.class.getSuperclass().getClass()).isEqualTo(ResourceRepository.class.getClass());
    }
}