package com.daeliin.components.core.event;

import com.daeliin.components.core.Application;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class EventLogRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private EventLogRepository eventLogRepository;

    @Test
    public void test() {
        EventLog eventLog = eventLogRepository.findOne(1L);

        assertThat(eventLog).isNotNull();
    }

    @Test
    public void test2() {
        Collection<EventLog> eventLogs = eventLogRepository.findAll();

        assertThat(eventLogs).isNotEmpty();
    }
}