package com.daeliin.components.core.event;

import com.daeliin.components.domain.resource.PersistentResource;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a log for an event in the system, with an description i18n key.
 */
public final class EventLog extends PersistentResource<String> implements Comparable<EventLog> {

    public final String description;

    public EventLog(String id, LocalDateTime creationDate, String description) {
        super(id, creationDate);
        this.description = Objects.requireNonNull(description, "description should not be null");
    }

    @Override
    public int compareTo(EventLog other) {
        if (this.equals(other)) {
            return 0;
        }

        return this.creationDate().compareTo(other.creationDate());
    }

    @Override
    public String toString() {
        return super.toStringHelper()
                .add("description", description)
                .toString();
    }
}
