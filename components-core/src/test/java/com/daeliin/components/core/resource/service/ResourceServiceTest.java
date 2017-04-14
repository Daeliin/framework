package com.daeliin.components.core.resource.service;

import com.daeliin.components.core.Application;
import com.daeliin.components.core.exception.PersistentResourceNotFoundException;
import com.daeliin.components.core.fake.UUIDEntity;
import com.daeliin.components.core.fake.UUIDEntityRepository;
import com.daeliin.components.core.fake.UUIDEntityService;
import com.daeliin.components.core.fixtures.UUIDEntityFixtures;
import org.apache.catalina.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = Application.class)
public class ResourceServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Mock
    private UUIDEntityRepository repository;

    @InjectMocks
    private UUIDEntityService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCallRepositorySaveAndReturnResource_whenCreatingResource() {
        UUIDEntity newUuuidEntity = new UUIDEntity(100L, UUID.randomUUID().toString(), LocalDateTime.now(), "label100");

        doReturn(newUuuidEntity).when(repository).save(newUuuidEntity);

        UUIDEntity createdUuidEntity = service.create(newUuuidEntity);

        verify(repository).save(newUuuidEntity);
        assertThat(createdUuidEntity).isEqualTo(newUuuidEntity);
    }

    @Test
    public void shouldCallRepositorySaveAndReturnsResources_whenCreatingResources() {
        UUIDEntity newUuidEntity1 = new UUIDEntity(101L, UUID.randomUUID().toString(), LocalDateTime.now(), "label101");
        UUIDEntity newUuidEntity2 = new UUIDEntity(102L, UUID.randomUUID().toString(), LocalDateTime.now(), "label102");

        List<UUIDEntity> newUuidEntites = Arrays.asList(newUuidEntity1, newUuidEntity2);

        doReturn(newUuidEntites).when(repository).save(newUuidEntites);

        Collection<UUIDEntity> createdUuidEntities = service.create(newUuidEntites);

        verify(repository).save(newUuidEntites);

        assertThat(createdUuidEntities).containsOnly(newUuidEntity1, newUuidEntity2);
    }
//
//    @Test
//    public void create_invalidResources_callsRepositorySaveAndThrowsConstraintViolationException() {
//        List<User> invalidUsers =
//            Arrays.asList(
//                new User().withName(""),
//                new User().withName(""));
//
//        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//        Set<? extends ConstraintViolation<User>> constraintViolations = new HashSet<>();
//        invalidUsers.forEach((User invalidUser) -> constraintViolations.addAll((Set)validator.validateProperty(invalidUser, "name")));
//
//        when(repository.save(invalidUsers))
//            .thenThrow(
//                new ConstraintViolationException(constraintViolations));
//
//        try {
//            service.create(invalidUsers);
//        } catch (ConstraintViolationException e) {
//            verify(repository).save(invalidUsers);
//            return;
//        }
//
//        fail();
//    }

    @Test
    public void shouldCheckThatNullDoesntExist() {
        assertThat(service.exists(null)).isFalse();
    }

    @Test
    public void shouldCallRepositoryExistAndReturnTrue_whenResourceIdExists() {
        Long existingUuidEntityId = UUIDEntityFixtures.uuidEntity1().id();

        doReturn(true).when(repository).exists(existingUuidEntityId);

        assertThat(service.exists(existingUuidEntityId)).isTrue();
        verify(repository).exists(existingUuidEntityId);
    }

    @Test
    public void shouldCallRepositoryExistAndReturnTrue_whenResourceIdDoesntExist() {
        doReturn(false).when(repository).exists(-1L);

        assertThat(service.exists(-1L)).isFalse();
        verify(repository).exists(-1L);
    }

    @Test
    public void shouldCallRepositoryCountAndReturnTheSameResult() {
        doReturn(5L).when(repository).count();

        assertThat(service.count()).isEqualTo(5L);
        verify(repository).count();
    }

    @Test(expected = PersistentResourceNotFoundException.class)
    public void shouldThrowResourceNotFoundException_whenIdIsNull() {
        service.findOne(null);
    }

    @Test
    public void shouldThrowResourceNotFoundException_whenResourceIdDoesntExist() {
        doThrow(new PersistentResourceNotFoundException("")).when(repository).findOne(-1L);

        try {
             service.findOne(-1L);
        } catch (PersistentResourceNotFoundException e) {
            verify(repository).findOne(-1L);
            return;
        }

        fail();
    }

    @Test
    public void shouldCallRepositoryFindOneAndReturnsResource_whenFindingResource() {
        UUIDEntity existingUuidEntity = UUIDEntityFixtures.uuidEntity1();

        doReturn(existingUuidEntity).when(repository).findOne(existingUuidEntity.id());

        assertThat(service.findOne(existingUuidEntity.id())).isEqualTo(existingUuidEntity);
        verify(repository).findOne(existingUuidEntity.id());
    }
//
//    @Test
//    public void findAll_callsRepositoryFindAllAndReturnsAllResources() {
//        List<User> allUsers =
//            Arrays.asList(
//                new User().withId(1L).withName("userName1"),
//                new User().withId(2L).withName("userName2"),
//                new User().withId(3L).withName("userName3"));
//
//        when(repository.findAll()).thenReturn(allUsers);
//
//        List<User> users = (List<User>)service.findAll();
//
//        verify(repository).findAll();
//        assertEquals(users, allUsers);
//    }
//
//    @Test
//    public void findAll_pageRequest_callsRepositoryFindAllWithPageRequest() {
//        PageRequest userPageRequest = new PageRequest(1, 10, Sort.Direction.ASC, "id", "name");
//
//        service.findAll(userPageRequest);
//        verify(repository).findAll(userPageRequest);
//    }
//
//    @Test
//    public void findAll_multipleResourcesIds_callsRepositoryFindAllWithMultipleResourcesIds() {
//        List<Long> usersIds = Arrays.asList(1L, 2L);
//
//        service.findAll(usersIds);
//        verify(repository).findAll(usersIds);
//    }
//
//    @Test(expected = ResourceNotFoundException.class)
//    public void update_nullResourceId_throwsResourceNotFoundException() {
//        service.update(null, null);
//    }
//
//    @Test
//    public void update_nonExistingResourceId_callsRepositoryExistsAndThrowsResourceNotFoundException() {
//        when(repository.exists(-1L)).thenReturn(false);
//
//        try {
//            service.update(-1L, null);
//        } catch (ResourceNotFoundException e) {
//            verify(repository).exists(-1L);
//            return;
//        }
//
//        fail();
//    }
//
//    @Test(expected = ResourceNotFoundException.class)
//    public void update_nullResource_throwsResourceNotFoundException() {
//        when(repository.exists(1L)).thenReturn(true);
//
//        service.update(1L, null);
//    }
//
//    @Test(expected = ResourceNotFoundException.class)
//    public void update_existingResourceIdButDiffersFromActualResourceId_throwsResourceNotFoundException() {
//        when(repository.exists(1L)).thenReturn(true);
//
//        service.update(1L, new User().withId(2L).withName("userName"));
//    }
//
//    @Test
//    public void update_existingResourceIdAndValidResource_callsRepositorySaveWithResourceAndReturnsResource() {
//        User user = new User().withId(1L).withName("userName");
//        when(repository.exists(user.getId())).thenReturn(true);
//        when(repository.save(user)).thenReturn(user);
//
//        User updatedUser = service.update(user.getId(), user);
//
//        verify(repository).save(user);
//        assertEquals(updatedUser, user);
//    }
//
//    @Test
//    public void update_multipleResources_callsRepositorySaveWithMultipleResources() {
//        List<User> users =
//            Arrays.asList(
//                new User().withId(1L).withName("userName1"),
//                new User().withId(2L).withName("userName2"));
//
//        when(repository.save(users)).thenReturn(users);
//
//        Iterable<User> updatedUsers = service.update(users);
//
//        verify(repository).save(users);
//        assertEquals(updatedUsers, users);
//    }
//
//    @Test(expected = ResourceNotFoundException.class)
//    public void delete_nonExistingResourceId_throwsResourceNotFoundException() {
//        when(repository.exists(-1L)).thenReturn(false);
//
//        service.delete(-1L);
//    }
//
//    @Test
//    public void delete_existingResourceId_callsRepositoryExistsAndDelete() {
//        when(repository.exists(1L)).thenReturn(true);
//
//        service.delete(1L);
//        verify(repository).exists(1L);
//        verify(repository).delete(1L);
//    }
//
//    @Test
//    public void delete_existingResourceIds_deletesResources() {
//        User existingUser1 = new User().withId(1L).withName("userName1");
//        User existingUser2 = new User().withId(2L).withName("userName2");
//
//        when(repository.findAll(Arrays.asList(1L, 2L))).thenReturn(Arrays.asList(existingUser1, existingUser2));
//        service.delete(Arrays.asList(1L, 2L));
//
//        verify(repository).delete(existingUser1);
//        verify(repository).delete(existingUser2);
//    }
//
//    @Test
//    public void delete_nonExistingResourceIds_doesntCallRepositoryDeleteWithResourceIds() {
//        User nonExistingUser1 = new User().withId(-1L).withName("userName1");
//        User nonExistingUser2 = new User().withId(-2L).withName("userName2");
//
//        when(repository.findAll(Arrays.asList(-1L, -2L))).thenReturn(new ArrayList<>());
//        service.delete(Arrays.asList(-1L, -2L));
//
//        verify(repository, never()).delete(nonExistingUser1);
//        verify(repository, never()).delete(nonExistingUser2);
//    }
//
//    @Test
//    public void delete_nonExistingAndExistingResourceIds_deletesOnlyExistingResources() {
//        User existingUser1 = new User().withId(1L).withName("userName1");
//        User existingUser2 = new User().withId(2L).withName("userName2");
//        User nonExistingUser = new User().withId(-3L).withName("userName3");
//
//        when(repository.findAll(Arrays.asList(1L, 2L, -3L))).thenReturn(Arrays.asList(existingUser1, existingUser2));
//        service.delete(Arrays.asList(1L, 2L, -3L));
//
//        verify(repository).delete(existingUser1);
//        verify(repository).delete(existingUser2);
//        verify(repository, never()).delete(nonExistingUser);
//    }
//
//    @Test
//    public void delete_resource_callsRepositoryDeleteWithResource() {
//        User user = new User().withId(1L).withName("userName");
//
//        service.delete(user);
//        verify(repository).delete(user);
//    }
//
//    @Test
//    public void delete_multipleResources_callsRepositoryDeleteWithResources() {
//        List<User> users =
//            Arrays.asList(
//                new User().withId(1L).withName("userName1"),
//                new User().withId(2L).withName("userName2"));
//
//        service.delete(users);
//        verify(repository).delete(users);
//    }
//
//    @Test
//    public void deleteAll_callsRepositoryDeleteAll() {
//        service.deleteAll();
//        verify(repository).deleteAll();
//    }
}
