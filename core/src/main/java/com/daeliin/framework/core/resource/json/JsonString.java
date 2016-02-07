package com.daeliin.framework.core.resource.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Serializes an Object into a JSON String,
 * if the object can't be serialized, the value is an empty String.
 */
@Slf4j
@EqualsAndHashCode(of = {"json"})
@ToString(of = {"json"})
public class JsonString {
    
    private final ObjectMapper mapper;
    private final Object objectToSerialize;
    private String json;
    
    /**
     * Builds a JSON String from an object.
     * @param objectToSerialize the object to serialize into a JSON String
     */
    public JsonString(final Object objectToSerialize) {
        this.mapper = new ObjectMapper();
        this.objectToSerialize = objectToSerialize;
        serialize();
    }
    
    /**
     * Returns the object as a JSON string.
     * @return the ojbect as a JSON String
     */
    public String value() {
        return this.json;
    }

    private void serialize() {
        try {
            this.json = mapper.writeValueAsString(objectToSerialize);
        } catch (JsonProcessingException e) {
            log.warn("Error while json processing", e);
            this.json = "";
        }
    }
}
