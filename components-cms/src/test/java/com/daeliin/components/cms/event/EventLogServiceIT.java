package com.daeliin.components.cms.event;

import com.daeliin.components.cms.fixtures.JavaFixtures;
import com.daeliin.components.persistence.resource.service.ResourceService;
import com.daeliin.components.test.rule.DbFixture;
import com.daeliin.components.test.rule.DbMemory;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EventLogServiceIT {

    @Inject
    private EventLogService eventLogService;

    @ClassRule
    public static DbMemory dbMemory = new DbMemory();

    @Rule
    public DbFixture dbFixture = new DbFixture(dbMemory, JavaFixtures.event_log());

    @Test
    public void shouldExtendResourceService() {
        assertThat(EventLogService.class.getSuperclass().getClass()).isEqualTo(ResourceService.class.getClass());

        dbFixture.noRollback();
    }

    @Test
    public void shouldCreateAnEventLogFromDescription() {
        EventLog eventLog = eventLogService.create("Test");

        assertThat(eventLog.getId()).isNotBlank();
        assertThat(eventLog.getCreationDate()).isNotNull();
        assertThat(eventLog.description).isEqualTo("Test");
    }
}