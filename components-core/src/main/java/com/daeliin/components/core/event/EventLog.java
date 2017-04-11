package com.daeliin.components.core.event;

import com.daeliin.components.domain.resource.UUIDPersistentResource;

import java.time.LocalDateTime;
import java.util.Objects;

public final class EventLog extends UUIDPersistentResource implements Comparable<EventLog> {

    private static final long serialVersionUID = -5353349286441881283L;

    public final String descriptionKey;

    public EventLog(Long id, String uuid, LocalDateTime creationDate, String descriptionKey) {
        super(id, uuid, creationDate);
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
