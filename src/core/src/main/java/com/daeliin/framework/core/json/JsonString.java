package com.daeliin.framework.core.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(of = {"jsonString"})
@ToString(of = {"jsonString"})
public final class JsonString {
    
    private final ObjectMapper mapper;
    private final Object objectToSerialize;
    private String jsonString;
    
    public JsonString(final Object objectToSerialize) {
        this.mapper = new ObjectMapper();
        this.objectToSerialize = objectToSerialize;
        serialize();
    }
    
    public String value() {
        return this.jsonString;
    }

    private void serialize() {
        try {
            this.jsonString = mapper.writeValueAsString(objectToSerialize);
        } catch (JsonProcessingException e) {
            this.jsonString = "";
        }
    }
}
