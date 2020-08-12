package com.blebail.components.core.configuration;

import com.blebail.components.core.fake.ImmutableResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class JsonIT {

    @Inject
    private ObjectMapper jsonMapper;

    @Test
    public void shouldInjectAnObjectMapper() {
        assertThat(jsonMapper).isNotNull();
    }

    @Test
    public void shouldSerializeImmutableTypes() throws Exception {
        ImmutableResource uuidPersistentResource = new ImmutableResource("id",
            LocalDateTime.of(2017, 1, 1, 12, 32, 12).toInstant(ZoneOffset.UTC), "label");

        String serializeduuidPersistentResource = jsonMapper.writeValueAsString(uuidPersistentResource);

        assertThat(serializeduuidPersistentResource).isEqualTo("{\"id\":\"id\",\"creationDate\":\"2017-01-01T12:32:12Z\",\"label\":\"label\"}");
    }

    @Test
    public void shouldDeserializeImmutableTypes() throws Exception {
        ImmutableResource uuidPersistentResource = new ImmutableResource("id",
            LocalDateTime.of(2017, 1, 1, 12, 32, 12).toInstant(ZoneOffset.UTC), "label");

        ImmutableResource deserializedUuidPersistentResource =
                jsonMapper.readValue("{\"id\":\"id\",\"creationDate\":\"2017-01-01T12:32:12Z\",\"label\":\"label\"}", ImmutableResource.class);

        assertThat(deserializedUuidPersistentResource).isEqualToComparingFieldByField(uuidPersistentResource);
    }

    @Test
    public void shouldSerializeJSR310AsIso() throws Exception {
        Instant instant = LocalDateTime.of(2017, 1, 1, 12, 32, 12).toInstant(ZoneOffset.UTC);

        String serializedInstant = jsonMapper.writeValueAsString(instant);

        assertThat(serializedInstant).isEqualTo("\"2017-01-01T12:32:12Z\"");
    }

    @Test
    public void shouldDeserializeISoDateToJSR310() throws Exception {
        Instant instant = LocalDateTime.of(2017, 1, 1, 12, 32, 12).toInstant(ZoneOffset.UTC);

        Instant deserializedInstant = jsonMapper.readValue("\"2017-01-01T12:32:12Z\"", Instant.class);

        assertThat(deserializedInstant).isEqualTo(instant);
    }

    @Test
    public void shouldSerializeGuavaTypes() throws Exception {
        ImmutableList<String> immutableList = ImmutableList.of("string1", "string2");

        String immutableListAsJson = jsonMapper.writeValueAsString(immutableList);

        assertThat(immutableListAsJson).contains("string1", "string2");
    }

    @Test
    public void shouldDeserializeGuavaTypes() throws Exception {
        ImmutableList<String> strings = jsonMapper.readValue("[\"string1\", \"string2\"]", ImmutableList.class);

        assertThat(strings).contains("string1", "string2");
    }

    @Test
    public void shouldNotSerializeNullProperties() throws Exception {
        ImmutableResource immutableResource = new ImmutableResource("id", null, "label");

        assertThat(jsonMapper.writeValueAsString(immutableResource)).doesNotContain("null");
    }

    @Test
    public void shouldIgnoreUnknownProperties() throws Exception {
        ImmutableResource uuidPersistentResource = new ImmutableResource("id",
            LocalDateTime.of(2017, 1, 1, 12, 32, 12).toInstant(ZoneOffset.UTC), "label");

        ImmutableResource deserializedUuidPersistentResource =
            jsonMapper.readValue("{\"id\":\"id\",\"creationDate\":\"2017-01-01T12:32:12Z\",\"label\":\"label\",\"unknow\":\"unknow\"}", ImmutableResource.class);

        assertThat(deserializedUuidPersistentResource).isEqualToComparingFieldByField(uuidPersistentResource);
    }
}