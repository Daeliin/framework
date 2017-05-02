package com.daeliin.components.webservices.rest.controller;

import com.daeliin.components.test.IntegrationTest;
import com.daeliin.components.webservices.Application;
import com.daeliin.components.webservices.fake.UuidPersistentResource;
import com.daeliin.components.webservices.fake.UuidPersistentResourceService;
import com.daeliin.components.webservices.library.UuidPersistentResourceLibrary;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MvcResult;

import javax.inject.Inject;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = Application.class)
public class ResourceControllerTest extends IntegrationTest {

    @Inject
    private ObjectMapper jsonMapper;

    @Inject
    private UuidPersistentResourceService service;

    @Test
    public void shouldReturnHttpCreatedAndCreatedResource() throws Exception {
       UuidPersistentResource uuidPersistentResource = new UuidPersistentResource("id", LocalDateTime.now(), "label");

        MvcResult result =
            mockMvc
                .perform(post("/uuid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(uuidPersistentResource)))
                .andExpect(status().isCreated())
                .andReturn();

        UuidPersistentResource createdUuidPersistentResource = jsonMapper.readValue(result.getResponse().getContentAsString(), UuidPersistentResource.class);
        assertThat(createdUuidPersistentResource).isEqualTo(uuidPersistentResource);
    }

    @Test
    public void shouldPersistResource() throws Exception {
        UuidPersistentResource uuidPersistentResource = new UuidPersistentResource("id", LocalDateTime.now(), "label");
        long uuidPersistentResourceCountBeforeCreate = service.count();

        mockMvc
            .perform(post("/uuid")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonMapper.writeValueAsString(uuidPersistentResource)));

        UuidPersistentResource persistedUuidPersistentResource = service.findOne(uuidPersistentResource.id());

        long uuidPersistentResourceCountAfterCreate = service.count();

        assertThat(persistedUuidPersistentResource).isEqualTo(uuidPersistentResource);
        assertThat(uuidPersistentResourceCountAfterCreate).isEqualTo(uuidPersistentResourceCountBeforeCreate + 1);
    }

//    @Test
//    public void create_invalidResource_returnsHttpBadREquest() throws Exception {
//        User invalidUser = new User().withName("");
//
//        mockMvc
//            .perform(post("/uuid")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(new JsonString(invalidUser).value()))
//            .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void create_invalidResource_doesntPersistResource() throws Exception {
//        User invalidUser = new User().withName("");
//        long userCountBeforeCreate = repository.count();
//
//        mockMvc
//            .perform(post("/uuid")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(new JsonString(invalidUser).value()));
//
//        User persistedUser = repository.findFirstByName(invalidUser.getName());
//        long userCountAfterCreate = repository.count();
//
//        assertNull(persistedUser);
//        assertEquals(userCountAfterCreate, userCountBeforeCreate);
//    }
//
    @Test
    public void shouldReturnHttpOkAndResource_whenResourceExists() throws Exception {
        UuidPersistentResource existingUuidPersistentResource = UuidPersistentResourceLibrary.uuidPersistentResource1();

        MvcResult result =
            mockMvc
                .perform(get("/uuid/" + existingUuidPersistentResource.id())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UuidPersistentResource retrievedUuidPersistentResource = jsonMapper.readValue(result.getResponse().getContentAsString(), UuidPersistentResource.class);
        assertThat(retrievedUuidPersistentResource).isEqualTo(existingUuidPersistentResource);
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
            .andExpect(jsonPath("$.items[0].label").value("label2"))
            .andExpect(jsonPath("$.items[1].label").value("label1"));
    }

//    @Test
//    public void shouldReturnPageSortedByLabelDescThenByIdDesc() throws Exception {
//        mockMvc
//            .perform(get("/uuid?direction=DESC&properties=label,id")
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$.sort[0].direction").value("DESC"))
//            .andExpect(jsonPath("$.sort[0].property").value("name"))
//            .andExpect(jsonPath("$.sort[1].direction").value("DESC"))
//            .andExpect(jsonPath("$.sort[1].property").value("id"));
//    }

//    @Test
//    public void getAll_invalidPageNumber_returnsHttpBadRequest() throws Exception {
//        mockMvc
//            .perform(get("/uuid?pageNumber=-1")
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isBadRequest());
//
//        mockMvc
//            .perform(get("/uuid?pageNumber=invalidPageNumber")
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void getAll_invalidPageSize_returnsHttpBadRequest() throws Exception {
//        mockMvc
//            .perform(get("/uuid?pageSize=-1")
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isBadRequest());
//
//        mockMvc
//            .perform(get("/uuid?pageSize=invalidPageSize")
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void getAll_invalidDirection_returnsHttpBadRequest() throws Exception {
//        mockMvc
//            .perform(get("/uuid?direction=invalidDirection")
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void update_validUpdatedResource_returnsHttpOkAndUpdatedResource() throws Exception {
//        User validUpdatedUser = repository.findOne(1L);
//        validUpdatedUser.setName("updatedName");
//
//        MvcResult result =
//            mockMvc
//                .perform(put("/uuid/" + validUpdatedUser.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new JsonString(validUpdatedUser).value()))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        User retrievedUser = new JsonObject<>(result.getResponse().getContentAsString(), User.class).value().get();
//        assertEquals(retrievedUser, validUpdatedUser);
//    }
//
//    @Test
//    public void update_validUpdatedResource_updatesResource() throws Exception {
//        User originalUser = repository.findOne(1L);
//        User validUpdatedUser = new User().withId(originalUser.getId()).withName("updatedName");
//
//        mockMvc
//            .perform(put("/uuid/" + validUpdatedUser.getId())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(new JsonString(validUpdatedUser).value()));
//
//        assertEquals(repository.findOne(1L).getName(), validUpdatedUser.getName());
//    }
//
//    @Test
//    public void update_invalidUpdatedResource_returnsHttpBadRequest() throws Exception {
//        User invalidUpdatedUser = repository.findOne(1L);
//        invalidUpdatedUser.setName("");
//
//        mockMvc
//            .perform(put("/uuid/" + invalidUpdatedUser.getId())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(new JsonString(invalidUpdatedUser).value()))
//            .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void update_invalidUpdatedResource_doesntUpdateResource() throws Exception {
//        User originalUser = repository.findOne(1L);
//        User invalidUpdatedUser = new User().withId(originalUser.getId()).withName("");
//
//        mockMvc
//            .perform(put("/uuid/" + invalidUpdatedUser.getId())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(new JsonString(invalidUpdatedUser).value()));
//
//        assertEquals(repository.findOne(1L).getName(), originalUser.getName());
//    }
//
//    @Test
//    public void update_nonExistingResourceId_returnsHttpNotFound() throws Exception {
//        User user = new User().withId(-1L).withName("name");
//
//        mockMvc
//            .perform(put("/uuid/" + user.getId())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(new JsonString(user).value()))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void update_existingResourceIdButDiffersFromActualResourceId_returnsHttpNotFound() throws Exception {
//        User user = repository.findOne(1L);
//
//        mockMvc
//            .perform(put("/uuid/" + -1)
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(new JsonString(user).value()))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void update_existingResourceIdButDiffersFromActualResourceId_doesntUpdateResource() throws Exception {
//        User originalUser = repository.findOne(1L);
//        User updatedUser = new User().withId(originalUser.getId()).withName("updatedName");
//
//        mockMvc
//            .perform(put("/uuid/" + -1)
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(new JsonString(updatedUser).value()));
//
//        assertEquals(repository.findOne(1L).getName(), originalUser.getName());
//    }
//
//    @Test
//    public void delete_existingResource_returnsHttpGone() throws Exception {
//        mockMvc
//            .perform(delete("/uuid/1"))
//            .andExpect(status().isGone());
//    }
//
//    @Test
//    public void delete_existingResource_deletesResource() throws Exception {
//        mockMvc
//            .perform(delete("/uuid/1"));
//
//        assertFalse(repository.exists(1L));
//    }
//
//    @Test
//    public void delete_nonExistingResource_returnsHttpNotFound() throws Exception {
//        mockMvc
//            .perform(delete("/uuid/-1"))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void delete_existingResourceIds_returnsHttpGone() throws Exception {
//        mockMvc
//            .perform(post("/uuid/deleteSeveral")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(new JsonString(Arrays.asList(1L, 2L)).value()))
//            .andExpect(status().isGone());
//    }
//
//    @Test
//    public void delete_existingResourceIds_deletesResources() throws Exception {
//        mockMvc
//            .perform(post("/uuid/deleteSeveral")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(new JsonString(Arrays.asList(1L, 2L)).value()));
//
//        assertFalse(repository.exists(1L));
//        assertFalse(repository.exists(2L));
//    }
//
//    @Test
//    public void delete_nonExistingResourceIds_returnsHttpGone() throws Exception {
//        mockMvc
//            .perform(post("/uuid/deleteSeveral")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(new JsonString(Arrays.asList(-1L, -2L)).value()))
//            .andExpect(status().isGone());
//    }
//
//    @Test
//    public void delete_nonExistingAndExistingResourceIds_returnsHttpGone() throws Exception {
//        mockMvc
//            .perform(post("/uuid/deleteSeveral")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(new JsonString(Arrays.asList(-1L, -2L)).value()))
//            .andExpect(status().isGone());
//    }
//
//    @Test
//    public void delete_nonExistingAndExistingResourceIds_deletesExistingResources() throws Exception {
//        mockMvc
//            .perform(post("/uuid/deleteSeveral")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(new JsonString(Arrays.asList(1L, 2L, -3L)).value()));
//
//        assertFalse(repository.exists(1L));
//        assertFalse(repository.exists(2L));
//    }
//
//    @Test
//    public void delete_null_returnsHttpBadRequest() throws Exception {
//        mockMvc
//            .perform(post("/uuid/deleteSeveral")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(new JsonString(null).value()))
//            .andExpect(status().isBadRequest());
//    }
}
