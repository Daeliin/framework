package com.daeliin.components.persistence.event;


import com.daeliin.components.persistence.event.EventLogRepository;
import com.daeliin.components.persistence.resource.repository.ResourceRepository;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class EventLogRepositoryTest {

    @Test
    public void shouldExtendResourceRepository() {
        assertThat(EventLogRepository.class.getSuperclass().getClass()).isEqualTo(ResourceRepository.class.getClass());
    }
}