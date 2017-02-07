package com.daeliin.components.core.event;

import com.daeliin.components.core.resource.repository.ResourceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventLogRepository extends ResourceRepository<EventLog, Long> {
}
