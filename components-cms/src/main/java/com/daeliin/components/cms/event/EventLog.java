package com.daeliin.components.cms.event;

import com.daeliin.components.persistence.resource.PersistentResource;

import java.time.Instant;
import java.util.Objects;

/**
 * Represents a log for an event in the system, with an description i18n key.
 */
public final class EventLog extends PersistentResource<String> implements Comparable<EventLog> {

    public final String description;

    public EventLog(String id, Instant creationDate, String description) {
        super(id, creationDate);
        this.description = Objects.requireNonNull(description);
    }

    @Override
    public int compareTo(EventLog other) {
        if (this.equals(other)) {
            return 0;
        }

        return this.getCreationDate().compareTo(other.getCreationDate());
    }

    @Override
    public String toString() {
        return super.toStringHelper()
                .add("description", description)
                .toString();
    }
}
