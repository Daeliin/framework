package com.daeliin.components.webservices.configuration;

import com.daeliin.components.webservices.fake.UuidPersistentResourceDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonTest extends AbstractJUnit4SpringContextTests {

    @Inject
    private ObjectMapper jsonMapper;

    @Test
    public void shouldInjectAnObjectMapper() throws Exception {
        assertThat(jsonMapper).isNotNull();
    }

    @Test
    public void shouldSerializeImmutableTypes() throws Exception {
        UuidPersistentResourceDto uuidPersistentResource = new UuidPersistentResourceDto("id",
            LocalDateTime.of(2017, 1, 1, 12, 32, 12).toInstant(ZoneOffset.UTC), "label");

        String serializeduuidPersistentResource = jsonMapper.writeValueAsString(uuidPersistentResource);

        assertThat(serializeduuidPersistentResource).isEqualTo("{\"id\":\"id\",\"creationDate\":\"2017-01-01T12:32:12Z\",\"label\":\"label\"}");
    }

    @Test
    public void shouldDeserializeImmutableTypes() throws Exception {
        UuidPersistentResourceDto uuidPersistentResource = new UuidPersistentResourceDto("id",
            LocalDateTime.of(2017, 1, 1, 12, 32, 12).toInstant(ZoneOffset.UTC), "label");

        UuidPersistentResourceDto deserializedUuidPersistentResource =
                jsonMapper.readValue("{\"id\":\"id\",\"creationDate\":\"2017-01-01T12:32:12Z\",\"label\":\"label\"}", UuidPersistentResourceDto.class);

        assertThat(deserializedUuidPersistentResource).isEqualToComparingFieldByField(uuidPersistentResource);
    }

    @Test
    public void shouldSerializeJSR310AsIso() throws Exception{
        LocalDateTime localDateTime = LocalDateTime.of(2017, 1, 1, 12, 32, 12);

        String serializedLocalDateTime = jsonMapper.writeValueAsString(localDateTime);

        assertThat(serializedLocalDateTime).isEqualTo("\"2017-01-01T12:32:12\"");
    }

    @Test
    public void shouldDeserializeISoDateToJSR310() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.of(2017, 1, 1, 12, 32, 12);

        LocalDateTime deserializedLocalDateTime = jsonMapper.readValue("\"2017-01-01T12:32:12Z\"", LocalDateTime.class);

        assertThat(deserializedLocalDateTime).isEqualTo(localDateTime);
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
}