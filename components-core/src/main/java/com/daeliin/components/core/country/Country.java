package com.daeliin.components.core.country;

import com.daeliin.components.domain.resource.PersistentResource;

import java.time.LocalDateTime;
import java.util.Objects;

public class Country extends PersistentResource<String> implements Comparable<Country> {

    public final String code;
    public final String name;
    
    public Country(String code, LocalDateTime creationDate, String name) {
        super(code, creationDate);
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
