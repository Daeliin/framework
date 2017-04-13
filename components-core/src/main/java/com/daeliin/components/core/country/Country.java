package com.daeliin.components.core.country;

import com.daeliin.components.domain.resource.PersistentResource;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Objects;

public class Country extends PersistentResource implements Comparable<Country> {

    private static final long serialVersionUID = -5165401765623832715L;
    
    public final String code;
    public final String name;
    
    public Country(Long id, String uuid, LocalDateTime creationDate, String code, String name) {
        super(id, uuid, creationDate);
        this.code = Objects.requireNonNull(code, "code should not be null");
        this.name = Objects.requireNonNull(name, "name should not be null");
    }

    @Override
    public String toString() {
        return super.toStringHelper()
                .add("code", code)
                .add("name", name)
                .toString();
    }

    @Override
    public int compareTo(Country other) {
        if (this.equals(other)) {
            return 0;
        }
        
        return this.code.compareTo(other.code);
    }
}
