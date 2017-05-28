package com.daeliin.components.webservices.controller;

import com.daeliin.components.webservices.fake.UuidPersistentResourceDto;
import com.daeliin.components.webservices.fake.UuidPersistentResourceDtoConversion;
import com.daeliin.components.webservices.fake.UuidPersistentResourceService;
import com.daeliin.components.webservices.library.UuidPersistentResourceDtoLibrary;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ResourceControllerTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private ObjectMapper jsonMapper;

    @Inject
    private UuidPersistentResourceService service;

    @Inject
    private MockMvc mockMvc;

    private UuidPersistentResourceDtoConversion conversion = new UuidPersistentResourceDtoConversion();

    @Test
    public void shouldReturnHttpCreatedAndCreatedResource() throws Exception {
        UuidPersistentResourceDto uuidPersistentResourceDto = new UuidPersistentResourceDto("id", LocalDateTime.now(), "label");

        MvcResult result =
            mockMvc
                .perform(post("/uuid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(uuidPersistentResourceDto)))
                .andExpect(status().isCreated())
                .andReturn();

        UuidPersistentResourceDto createdUuidPersistentResourceDto = jsonMapper.readValue(result.getResponse().getContentAsString(), UuidPersistentResourceDto.class);

        assertThat(createdUuidPersistentResourceDto.id).isNotBlank();
        assertThat(createdUuidPersistentResourceDto.creationDate).isNotNull();
        assertThat(createdUuidPersistentResourceDto.label).isEqualTo(uuidPersistentResourceDto.label);
    }

    @Test
    public void shouldPersistResource() throws Exception {
        UuidPersistentResourceDto uuidPersistentResourceDto = new UuidPersistentResourceDto("id", LocalDateTime.now(), "label");
        long uuidPersistentResourceCountBeforeCreate = service.count();

         UuidPersistentResourceDto returnedUuidPersistentResourceDto = jsonMapper.readValue(mockMvc
                .perform(post("/uuid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(uuidPersistentResourceDto)))
                .andReturn()
                .getResponse()
                .getContentAsString(), UuidPersistentResourceDto.class);

        long uuidPersistentResourceCountAfterCreate = service.count();

        UuidPersistentResourceDto persistedUuidPersistentResourceDto = conversion.instantiate(service.findOne(returnedUuidPersistentResourceDto.id));

        assertThat(uuidPersistentResourceDto.id).isNotBlank();
        assertThat(uuidPersistentResourceDto.creationDate).isNotNull();
        assertThat(persistedUuidPersistentResourceDto.label).isEqualTo(uuidPersistentResourceDto.label);
        assertThat(uuidPersistentResourceCountAfterCreate).isEqualTo(uuidPersistentResourceCountBeforeCreate + 1);
    }

    @Test
    public void shouldReturnHttpBadRequest_whenCreatingInvalidResource() throws Exception {
        UuidPersistentResourceDto invalidUuidPersistentResourceDto = new UuidPersistentResourceDto("id", LocalDateTime.now(), " ");

        mockMvc
            .perform(post("/uuid")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonMapper.writeValueAsString(invalidUuidPersistentResourceDto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotPersistResource_whenCreatingInvalidResource() throws Exception {
        long uuidPersistentResourceCountBeforeCreate = service.count();

        UuidPersistentResourceDto invalidUuidPersistentResourceDto = new UuidPersistentResourceDto("id", LocalDateTime.now(), " ");

        mockMvc
            .perform(post("/uuid")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonMapper.writeValueAsString(invalidUuidPersistentResourceDto)));

        long uuidPersistentResourceCountAfterCreate = service.count();

        assertThat(service.exists(invalidUuidPersistentResourceDto.id)).isFalse();
        assertThat(uuidPersistentResourceCountAfterCreate).isEqualTo(uuidPersistentResourceCountBeforeCreate);
    }

    @Test
    public void shouldReturnHttpOkAndResource_whenResourceExists() throws Exception {
        UuidPersistentResourceDto existingUuidPersistentResourceDto = UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto1();

        MvcResult result =
            mockMvc
                .perform(get("/uuid/" + existingUuidPersistentResourceDto.id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UuidPersistentResourceDto retrievedUuidPersistentResourceDto = jsonMapper.readValue(result.getResponse().getContentAsString(), UuidPersistentResourceDto.class);
        assertThat(retrievedUuidPersistentResourceDto).isEqualToComparingFieldByField(existingUuidPersistentResourceDto);
    }

    @Test
    public void shouldReturnHttpNotFound_whenResourceDoesntExist() throws Exception {
        mockMvc
            .perform(get("/uuid/nonExistingId")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnHttpOkAndPage0WithSize20SortedByIdAsc_byDefault() throws Exception {
        mockMvc
            .perform(get("/uuid")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.items", hasSize(4)))
            .andExpect(jsonPath("$.totalPages").value(1))
            .andExpect(jsonPath("$.totalItems").value(4))
            .andExpect(jsonPath("$.nbItems").value(4));
    }

    @Test
    public void shouldReturnHttpOkAndPage1WithSize2SortedByLabelDesc() throws Exception {
        mockMvc
            .perform(get("/uuid?page=1&size=2&direction=DESC&properties=label")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.items", hasSize(2)))
            .andExpect(jsonPath("$.totalPages").value(2))
            .andExpect(jsonPath("$.totalItems").value(4))
            .andExpect(jsonPath("$.nbItems").value(2))
            .andExpect(jsonPath("$.items[0].label").value(UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto2().label))
            .andExpect(jsonPath("$.items[1].label").value(UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto1().label));
    }

    @Test
    public void shouldReturnPageSortedByLabelDescThenByIdDesc() throws Exception {
        mockMvc
            .perform(get("/uuid?direction=DESC&properties=label,id")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].label").value(UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto4().label))
                .andExpect(jsonPath("$.items[1].label").value(UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto3().label))
                .andExpect(jsonPath("$.items[2].label").value(UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto2().label))
                .andExpect(jsonPath("$.items[3].label").value(UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto1().label));
    }

    @Test
    public void shouldReturnHttpBadRequest_whenPageIsNotValid() throws Exception {
        mockMvc
            .perform(get("/uuid?page=-1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        mockMvc
            .perform(get("/uuid?page=invalidPageNumber")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnHttpBadRequest_whenPageSizeIsNotValid() throws Exception {
        mockMvc
            .perform(get("/uuid?size=-1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        mockMvc
            .perform(get("/uuid?size=invalidPageSize")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnHttpBadRequest_whenPageDirectionIsNotValid() throws Exception {
        mockMvc
            .perform(get("/uuid?direction=invalidDirection")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnHttpOkAndUpdatedResource_whenUpdatingResource() throws Exception {
        UuidPersistentResourceDto updatedUuidPersistentResourceDto = new UuidPersistentResourceDto(
                UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto1().id,
                LocalDateTime.now(),
                "newLabel");

        MvcResult result =
            mockMvc
                .perform(put("/uuid/" + updatedUuidPersistentResourceDto.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(updatedUuidPersistentResourceDto)))
                .andExpect(status().isOk())
                .andReturn();

        UuidPersistentResourceDto retrievedUuidPersistentResourceDto = jsonMapper.readValue(result.getResponse().getContentAsString(), UuidPersistentResourceDto.class);
        assertThat(retrievedUuidPersistentResourceDto.label).isEqualTo(updatedUuidPersistentResourceDto.label);
    }

    @Test
    public void shouldUpdateResource() throws Exception {
        UuidPersistentResourceDto updatedUuidPersistentResourceDto = new UuidPersistentResourceDto(
                UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto1().id,
                LocalDateTime.now(),
                "newLabel");

        mockMvc
            .perform(put("/uuid/" + updatedUuidPersistentResourceDto.id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonMapper.writeValueAsString(updatedUuidPersistentResourceDto)));

        UuidPersistentResourceDto retrievedUuidPersistentResourceDto = conversion.instantiate(service.findOne(updatedUuidPersistentResourceDto.id));

        assertThat(retrievedUuidPersistentResourceDto.label).isEqualTo(updatedUuidPersistentResourceDto.label);
    }

    @Test
    public void shouldReturnHttpBadRequest_whenUpdatingInvalidResource() throws Exception {
        UuidPersistentResourceDto invalidUuidPersistentResourceDto = new UuidPersistentResourceDto(
                UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto1().id,
                LocalDateTime.now(),
                " ");

        mockMvc
            .perform(put("/uuid/" + invalidUuidPersistentResourceDto.id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonMapper.writeValueAsString(invalidUuidPersistentResourceDto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotUpdateResource_whenUpdatingInvalidResource() throws Exception {
        UuidPersistentResourceDto invalidUuidPersistentResourceDto = new UuidPersistentResourceDto(
                UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto1().id,
                LocalDateTime.now(),
                " ");

        mockMvc
                .perform(put("/uuid/" + invalidUuidPersistentResourceDto.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(invalidUuidPersistentResourceDto)));

        UuidPersistentResourceDto retrivedUuidPersistenceResourceDto = conversion.instantiate(service.findOne(invalidUuidPersistentResourceDto.id));

        assertThat(retrivedUuidPersistenceResourceDto).isEqualToComparingFieldByFieldRecursively(UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto1());
    }

    @Test
    public void shouldReturnHttpNotFound_whenUpdatingNonExistingResource() throws Exception {
        UuidPersistentResourceDto updatedUuidPersistentResourceDto = new UuidPersistentResourceDto(
                UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto1().id,
                LocalDateTime.now(),
                "newLabel");

        mockMvc
            .perform(put("/uuid/nonExistingId")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonMapper.writeValueAsString(updatedUuidPersistentResourceDto)))
            .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnHttpNoContent_whenDeletingAResource() throws Exception {
        mockMvc
                .perform(delete("/uuid/" + UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto1().id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteAResource() throws Exception {
        mockMvc
            .perform(delete("/uuid/" + UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto1().id));

        assertThat(service.exists(UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto1().id)).isFalse();
    }

    @Test
    public void shouldReturnHttpNotFound_whenDeletingNonExistingResource() throws Exception {
        mockMvc
            .perform(delete("/uuid/nonExistingId"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnHttpNoContent_whenDeletingResources() throws Exception {
        mockMvc
            .perform(post("/uuid/deleteSeveral")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonMapper.writeValueAsString(Arrays.asList(
                    UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto1().id,
                    UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto2().id))))
            .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteResources() throws Exception {
        mockMvc
            .perform(post("/uuid/deleteSeveral")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonMapper.writeValueAsString(Arrays.asList(
                    UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto1().id,
                    UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto2().id))));

        assertThat(service.exists(UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto1().id)).isFalse();
        assertThat(service.exists(UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto2().id)).isFalse();
    }

    @Test
    public void shouldDeletesExistingResources_whenDeletingNonExistingAndExistingResourceIds() throws Exception {
        mockMvc
            .perform(post("/uuid/deleteSeveral")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonMapper.writeValueAsString(Arrays.asList(
                    UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto1().id,
                    UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto2().id,
                    "nonExistingId"))));

        assertThat(service.exists(UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto1().id)).isFalse();
        assertThat(service.exists(UuidPersistentResourceDtoLibrary.uuidPersistentResourceDto2().id)).isFalse();
    }

    @Test
    public void shouldReturnHttpBadRequest_whenDeletingNull() throws Exception {
        mockMvc
            .perform(post("/uuid/deleteSeveral")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonMapper.writeValueAsString(null)))
            .andExpect(status().isBadRequest());
    }
}
