package com.daeliin.components.webservices.rest.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 * Deserialize an object from a JSON String.
 * @param <T> the object type
 */
@Slf4j
public final class JsonObject<T> {

    private final ObjectMapper mapper;
    private final String jsonString;
    private final Class<T> deserializedObjectClass;
    private Optional<T> deserializedObject;
    
    /**
     * Builds an object from a JSON String according to the object type.
     * @param jsonString the JSON String to deserialize
     * @param deserializedObjectClass the object type class
     */
    public JsonObject(final String jsonString, final Class<T> deserializedObjectClass) {
        this.mapper = new ObjectMapper();
        this.jsonString = jsonString;
        this.deserializedObjectClass = deserializedObjectClass;
        deserialize();
    }
    
    /**
     * Returns the object deserialized from the JSON String.
     * @return the object deserialized from the JSON String
     */
    public Optional<T> value() {
        return this.deserializedObject;
    }
    
    private void deserialize() {
        try {
            deserializedObject = Optional.ofNullable(mapper.readValue(jsonString, deserializedObjectClass));
        } catch (NullPointerException e) {
            log.debug("Cannot deserialize null");
            deserializedObject = Optional.empty();
        } catch (IOException e) {
            log.debug(String.format("Error while deserializing JSON string : %s", jsonString), e);
            deserializedObject = Optional.empty();
        }
    }
}
