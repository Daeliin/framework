package com.blebail.components.cms.event;

import com.blebail.components.cms.fixtures.JavaFixtures;
import com.blebail.components.test.rule.SqlFixture;
import com.blebail.components.test.rule.SqlMemoryDatabase;
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
    private EventLogService tested;

    @RegisterExtension
    public static SqlMemoryDatabase sqlMemoryDatabase = new SqlMemoryDatabase();

    @RegisterExtension
    public SqlFixture dbFixture = new SqlFixture(sqlMemoryDatabase, JavaFixtures.event_log());

    @Test
    public void shouldCreateAnEventLogFromDescription() {
        EventLog eventLog = tested.create("Test");

        assertThat(eventLog.id()).isNotBlank();
        assertThat(eventLog.creationDate()).isNotNull();
        assertThat(eventLog.description).isEqualTo("Test");
    }
}