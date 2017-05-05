package com.daeliin.components.webservices.configuration;

import com.daeliin.components.webservices.fake.UuidPersistentResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.time.LocalDateTime;

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
        UuidPersistentResource uuidPersistentResource = new UuidPersistentResource("id", LocalDateTime.of(2017, 1, 1, 12, 32, 12), "label");

        String serializeduuidPersistentResource = jsonMapper.writeValueAsString(uuidPersistentResource);

        assertThat(serializeduuidPersistentResource).isEqualTo("{\"id\":\"id\",\"creationDate\":\"2017-01-01T12:32:12\",\"label\":\"label\"}");
    }

    @Test
    public void shouldDeserializeImmutableTypes() throws Exception {
        UuidPersistentResource uuidPersistentResource = new UuidPersistentResource("id", LocalDateTime.of(2017, 1, 1, 12, 32, 12), "label");

        UuidPersistentResource deserializedUuidPersistentResource =
                jsonMapper.readValue("{\"id\":\"id\",\"creationDate\":\"2017-01-01T12:32:12\",\"label\":\"label\"}", UuidPersistentResource.class);

        assertThat(deserializedUuidPersistentResource).isEqualTo(uuidPersistentResource);
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

        LocalDateTime deserializedLocalDateTime = jsonMapper.readValue("\"2017-01-01T12:32:12\"", LocalDateTime.class);

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