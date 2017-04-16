package com.daeliin.components.core.event;

import com.daeliin.components.domain.resource.PersistentResource;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a log for an event in the system, with an description i18n key.
 */
public final class EventLog extends PersistentResource implements Comparable<EventLog> {

    private static final long serialVersionUID = -5353349286441881283L;

    public final String descriptionKey;

    public EventLog(String uuid, LocalDateTime creationDate, String descriptionKey) {
        super(uuid, creationDate);
        this.descriptionKey = Objects.requireNonNull(descriptionKey, "descriptionKey should not be null");
    }

    @Override
    public int compareTo(EventLog other) {
        if (this.equals(other)) {
            return 0;
        }

        return this.creationDate.compareTo(other.creationDate);
    }

    @Override
    public String toString() {
        return super.toStringHelper()
                .add("descriptionKey", descriptionKey)
                .toString();
    }
}
