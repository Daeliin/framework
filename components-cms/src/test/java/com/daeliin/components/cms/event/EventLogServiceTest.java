package com.daeliin.components.cms.event;

import com.daeliin.components.persistence.resource.service.ResourceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EventLogServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

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