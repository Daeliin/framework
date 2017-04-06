package com.daeliin.components.core.event;

import com.daeliin.components.core.resource.repository.PagingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventLogRepository extends PagingRepository<EventLog, Long> {
}
