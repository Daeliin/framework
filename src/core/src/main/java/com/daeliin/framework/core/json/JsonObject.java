package com.daeliin.framework.core.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Optional;

public final class JsonObject<T> {

    private final ObjectMapper mapper;
    private final String jsonString;
    private final Class<T> deserializedObjectClass;
    private Optional<T> deserializedObject;
    
    public JsonObject(final String jsonString, final Class<T> deserializedObjectClass) {
        this.mapper = new ObjectMapper();
        this.jsonString = jsonString;
        this.deserializedObjectClass = deserializedObjectClass;
        deserialize();
    }
    
    public Optional<T> value() {
        return this.deserializedObject;
    }
    
    private void deserialize() {
        try {
            deserializedObject = Optional.ofNullable(mapper.readValue(jsonString, deserializedObjectClass));
        } catch (NullPointerException | IOException e) {
            deserializedObject = Optional.empty();
        }
    }
}
