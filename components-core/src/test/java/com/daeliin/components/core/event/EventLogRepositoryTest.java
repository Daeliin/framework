package com.daeliin.components.core.event;

import com.daeliin.components.core.Application;
import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;
import com.daeliin.components.domain.pagination.Sort;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;
import java.util.Arrays;

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
        PageRequest pageRequest = new PageRequest(0, 5, Arrays.asList(new Sort("creationDate"), new Sort("descriptionKey", Sort.Direction.DESC)));
        Page<EventLog> eventLogs = eventLogRepository.findAll(pageRequest);

        assertThat(eventLogs.items).isNotEmpty();
    }
}