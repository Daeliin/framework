package com.daeliin.components.persistence.event;

import com.daeliin.components.persistence.Application;
import com.daeliin.components.persistence.resource.service.ResourceService;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public final class EventLogServiceTest extends AbstractJUnit4SpringContextTests {

    @Inject
    private EventLogService eventLogService;

    @Test
    public void shouldExtendResourceService() {
        assertThat(EventLogService.class.getSuperclass().getClass()).isEqualTo(ResourceService.class.getClass());
    }

    @Test
    public void shouldCreateAnEventLogFromDescription() {
        EventLog eventLog = eventLogService.create("Test");

        assertThat(eventLog.getId()).isNotBlank();
        assertThat(eventLog.getCreationDate()).isNotNull();
        assertThat(eventLog.description).isEqualTo("Test");
    }
}