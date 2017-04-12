package com.daeliin.components.core.fake;

import com.daeliin.components.domain.resource.PersistentResource;

import java.time.LocalDateTime;

public class UUIDEntity extends PersistentResource implements Comparable<UUIDEntity> {

    private static final long serialVersionUID = 6434352024112491080L;

    public final String label;

    public UUIDEntity(Long id, String uuid, LocalDateTime creationDate, String label) {
        super(id, uuid, creationDate);
        this.label = label;
    }

    @Override
    public int compareTo(UUIDEntity other) {
        if (this.equals(other)) {
            return 0;
        }

        return label.compareTo(other.label);
    }
}
