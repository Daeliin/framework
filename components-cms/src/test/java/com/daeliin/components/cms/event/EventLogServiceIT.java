package com.daeliin.components.cms.event;

import com.daeliin.components.cms.fixtures.JavaFixtures;
import com.daeliin.components.test.rule.DbFixture;
import com.daeliin.components.test.rule.DbMemory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EventLogServiceIT {

    @Inject
    private EventLogService eventLogService;

    @RegisterExtension
    public static DbMemory dbMemory = new DbMemory();

    @RegisterExtension
    public DbFixture dbFixture = new DbFixture(dbMemory, JavaFixtures.event_log());

    @Test
    public void shouldCreateAnEventLogFromDescription() {
        EventLog eventLog = eventLogService.create("Test");

        assertThat(eventLog.id()).isNotBlank();
        assertThat(eventLog.creationDate()).isNotNull();
        assertThat(eventLog.description).isEqualTo("Test");
    }
}