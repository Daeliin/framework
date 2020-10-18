package com.blebail.components.webservices.controller;

import com.blebail.components.webservices.fake.UuidResourceDto;
import com.blebail.components.webservices.fake.UuidResourceDtoConversion;
import com.blebail.components.webservices.fake.UuidResourceService;
import com.blebail.components.webservices.fixtures.JavaFixtures;
import com.blebail.components.webservices.library.UuidResourceDtoLibrary;
import com.blebail.components.webservices.sql.QUuidResource;
import com.blebail.junit.SqlFixture;
import com.blebail.junit.SqlMemoryDb;
import com.blebail.querydsl.crud.commons.utils.Factories;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class BaseControllerIT {

    @Inject
    private ObjectMapper jsonMapper;

    @Inject
    private UuidResourceService service;

    @Inject
    private MockMvc mockMvc;

    private UuidResourceDtoConversion conversion = new UuidResourceDtoConversion();

    @RegisterExtension
    public static SqlMemoryDb sqlMemoryDb = new SqlMemoryDb();

    @RegisterExtension
    public SqlFixture dbFixture = new SqlFixture(sqlMemoryDb::dataSource, JavaFixtures.uuidResources());

    @Test
    public void shouldReturnHttpCreatedAndCreatedResource() throws Exception {
        UuidResourceDto uuidPersistentResourceDto = new UuidResourceDto("id", Instant.now(), "label");

        MvcResult result = mockMvc.perform(post("/uuid/base")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(uuidPersistentResourceDto)))
                .andExpect(status().isCreated())
                .andReturn();

        UuidResourceDto createdUuidPersistentResourceDto = jsonMapper.readValue(result.getResponse().getContentAsString(), UuidResourceDto.class);

        assertThat(createdUuidPersistentResourceDto.id).isNotBlank();
        assertThat(createdUuidPersistentResourceDto.creationDate).isNotNull();
        assertThat(createdUuidPersistentResourceDto.label).isEqualTo(uuidPersistentResourceDto.label);
    }

    @Test
    public void shouldPersistResource() throws Exception {
        UuidResourceDto uuidPersistentResourceDto = new UuidResourceDto("id", Instant.now(), "label");
        long uuidPersistentResourceCountBeforeCreate = countRows();

        UuidResourceDto returnedUuidPersistentResourceDto = jsonMapper.readValue(
                mockMvc.perform(post("/uuid/base")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(uuidPersistentResourceDto)))
                        .andReturn()
                        .getResponse()
                        .getContentAsString(), UuidResourceDto.class
        );

        long uuidPersistentResourceCountAfterCreate = countRows();

        UuidResourceDto persistedUuidPersistentResourceDto = conversion.from(service.findOne(returnedUuidPersistentResourceDto.id));

        assertThat(uuidPersistentResourceDto.id).isNotBlank();
        assertThat(uuidPersistentResourceDto.creationDate).isNotNull();
        assertThat(persistedUuidPersistentResourceDto.label).isEqualTo(uuidPersistentResourceDto.label);
        assertThat(uuidPersistentResourceCountAfterCreate).isEqualTo(uuidPersistentResourceCountBeforeCreate + 1);
    }

    @Test
    public void shouldReturnHttpBadRequest_whenCreatingInvalidResource() throws Exception {
        dbFixture.readOnly();

        UuidResourceDto invalidUuidPersistentResourceDto = new UuidResourceDto("id", Instant.now(), " ");

        mockMvc.perform(post("/uuid/base")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(invalidUuidPersistentResourceDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotPersistResource_whenCreatingInvalidResource() throws Exception {
        dbFixture.readOnly();

        long uuidPersistentResourceCountBeforeCreate = countRows();

        UuidResourceDto invalidUuidPersistentResourceDto = new UuidResourceDto("id", Instant.now(), " ");

        mockMvc.perform(post("/uuid/base")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(invalidUuidPersistentResourceDto)));

        long uuidPersistentResourceCountAfterCreate = countRows();

        assertThat(service.exists(invalidUuidPersistentResourceDto.id)).isFalse();
        assertThat(uuidPersistentResourceCountAfterCreate).isEqualTo(uuidPersistentResourceCountBeforeCreate);
    }

    @Test
    public void shouldReturnHttpOkAndResource_whenResourceExists() throws Exception {
        dbFixture.readOnly();

        UuidResourceDto existingUuidPersistentResourceDto = UuidResourceDtoLibrary.uuidResourceDto1();

        MvcResult result = mockMvc.perform(get("/uuid/base/" + existingUuidPersistentResourceDto.id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UuidResourceDto retrievedUuidPersistentResourceDto = jsonMapper.readValue(result.getResponse().getContentAsString(), UuidResourceDto.class);
        assertThat(retrievedUuidPersistentResourceDto).isEqualToComparingFieldByField(existingUuidPersistentResourceDto);
    }

    @Test
    public void shouldReturnHttpNotFound_whenResourceDoesntExist() throws Exception {
        dbFixture.readOnly();

        mockMvc.perform(get("/uuid/base/nonExistingId")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnHttpOkAndAllResources() throws Exception {
        dbFixture.readOnly();

        mockMvc.perform(get("/uuid/base")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void shouldReturnHttpOkAndUpdatedResource_whenUpdatingResource() throws Exception {
        UuidResourceDto updatedUuidPersistentResourceDto = new UuidResourceDto(
                UuidResourceDtoLibrary.uuidResourceDto1().id,
                Instant.now(),
                "newLabel");

        MvcResult result = mockMvc.perform(put("/uuid/base/" + updatedUuidPersistentResourceDto.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(updatedUuidPersistentResourceDto)))
                .andExpect(status().isOk())
                .andReturn();

        UuidResourceDto retrievedUuidPersistentResourceDto = jsonMapper.readValue(result.getResponse().getContentAsString(), UuidResourceDto.class);
        assertThat(retrievedUuidPersistentResourceDto.label).isEqualTo(updatedUuidPersistentResourceDto.label);
    }

    @Test
    public void shouldUpdateResource() throws Exception {
        UuidResourceDto updatedUuidPersistentResourceDto = new UuidResourceDto(
                UuidResourceDtoLibrary.uuidResourceDto1().id,
                Instant.now(),
                "newLabel");

        mockMvc.perform(put("/uuid/base/" + updatedUuidPersistentResourceDto.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(updatedUuidPersistentResourceDto)));

        UuidResourceDto retrievedUuidPersistentResourceDto = conversion.from(service.findOne(updatedUuidPersistentResourceDto.id));

        assertThat(retrievedUuidPersistentResourceDto.label).isEqualTo(updatedUuidPersistentResourceDto.label);
    }

    @Test
    public void shouldReturnHttpBadRequest_whenUpdatingInvalidResource() throws Exception {
        dbFixture.readOnly();

        UuidResourceDto invalidUuidPersistentResourceDto = new UuidResourceDto(
                UuidResourceDtoLibrary.uuidResourceDto1().id,
                Instant.now(),
                " ");

        mockMvc.perform(put("/uuid/base/" + invalidUuidPersistentResourceDto.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(invalidUuidPersistentResourceDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotUpdateResource_whenUpdatingInvalidResource() throws Exception {
        dbFixture.readOnly();

        UuidResourceDto invalidUuidPersistentResourceDto = new UuidResourceDto(
                UuidResourceDtoLibrary.uuidResourceDto1().id,
                Instant.now(),
                " ");

        mockMvc.perform(put("/uuid/base/" + invalidUuidPersistentResourceDto.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(invalidUuidPersistentResourceDto)));

        UuidResourceDto retrievedUuidPersistenceResourceDto = conversion.from(service.findOne(invalidUuidPersistentResourceDto.id));

        assertThat(retrievedUuidPersistenceResourceDto).isEqualToComparingFieldByFieldRecursively(UuidResourceDtoLibrary.uuidResourceDto1());
    }

    @Test
    public void shouldReturnHttpNotFound_whenUpdatingNonExistingResource() throws Exception {
        dbFixture.readOnly();

        UuidResourceDto updatedUuidPersistentResourceDto = new UuidResourceDto(
                UuidResourceDtoLibrary.uuidResourceDto1().id,
                Instant.now(),
                "newLabel");

        mockMvc.perform(put("/uuid/base/nonExistingId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(updatedUuidPersistentResourceDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnHttpNoContent_whenDeletingAResource() throws Exception {
        mockMvc.perform(delete("/uuid/base/" + UuidResourceDtoLibrary.uuidResourceDto1().id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteAResource() throws Exception {
        mockMvc.perform(delete("/uuid/base/" + UuidResourceDtoLibrary.uuidResourceDto1().id));

        assertThat(service.exists(UuidResourceDtoLibrary.uuidResourceDto1().id)).isFalse();
    }

    @Test
    public void shouldReturnHttpNotFound_whenDeletingNonExistingResource() throws Exception {
        dbFixture.readOnly();

        mockMvc.perform(delete("/uuid/base/nonExistingId"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnHttpNoContent_whenDeletingResources() throws Exception {
        mockMvc.perform(post("/uuid/base/deleteSeveral")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(Arrays.asList(
                        UuidResourceDtoLibrary.uuidResourceDto1().id,
                        UuidResourceDtoLibrary.uuidResourceDto2().id))))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteResources() throws Exception {
        mockMvc.perform(post("/uuid/base/deleteSeveral")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(Arrays.asList(
                        UuidResourceDtoLibrary.uuidResourceDto1().id,
                        UuidResourceDtoLibrary.uuidResourceDto2().id))));

        assertThat(service.exists(UuidResourceDtoLibrary.uuidResourceDto1().id)).isFalse();
        assertThat(service.exists(UuidResourceDtoLibrary.uuidResourceDto2().id)).isFalse();
    }

    @Test
    public void shouldDeletesExistingResources_whenDeletingNonExistingAndExistingResourceIds() throws Exception {
        mockMvc.perform(post("/uuid/base/deleteSeveral")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(Arrays.asList(
                        UuidResourceDtoLibrary.uuidResourceDto1().id,
                        UuidResourceDtoLibrary.uuidResourceDto2().id,
                        "nonExistingId"))));

        assertThat(service.exists(UuidResourceDtoLibrary.uuidResourceDto1().id)).isFalse();
        assertThat(service.exists(UuidResourceDtoLibrary.uuidResourceDto2().id)).isFalse();
    }

    @Test
    public void shouldReturnHttpBadRequest_whenDeletingNull() throws Exception {
        dbFixture.readOnly();

        mockMvc.perform(post("/uuid/base/deleteSeveral")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

    private long countRows() {
        return Factories.defaultQueryFactory(sqlMemoryDb.dataSource())
                .select(QUuidResource.uuidResource)
                .from(QUuidResource.uuidResource)
                .fetchCount();
    }
}
