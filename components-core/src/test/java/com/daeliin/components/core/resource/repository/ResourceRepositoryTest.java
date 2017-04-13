//package com.daeliin.components.core.resource.repository;
//
//import com.daeliin.components.core.Application;
//import com.daeliin.components.core.fake.User;
//import com.daeliin.components.core.fake.UserRepository;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import javax.validation.ConstraintViolationException;
//import org.junit.Assert;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//import org.junit.Test;
//import javax.inject.Inject;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.dao.InvalidDataAccessApiUsageException;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.mapping.PropertyReferenceException;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
//
//@ContextConfiguration(classes = Application.class)
//public class ResourceRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
//
//    @Inject
//    private UserRepository repository;
//
//    @Test
//    public void save_validResource_returnsPersistedResource() {
//        User user = new User().withName("newUser");
//
//        User returnedUser = repository.save(user);
//
//        assertNotNull(returnedUser);
//        assertNotNull(returnedUser.getId());
//    }
//
//    @Test
//    public void save_validResource_persistsResource() {
//        User user = new User().withName("newUser");
//        long userCountBeforeCreate = repository.count();
//
//        User persistedUser = repository.findOne(repository.save(user).getId());
//
//        long userCountAfterCreate = repository.count();
//
//        assertEquals(userCountAfterCreate, userCountBeforeCreate + 1);
//        assertNotNull(persistedUser);
//        assertNotNull(persistedUser.getId());
//    }
//
//    @Test(expected = ConstraintViolationException.class)
//    public void save_invalidResource_throwsConstraintViolationException() {
//        User user = new User().withName("");
//
//        repository.save(user);
//    }
//
//    @Test
//    public void save_multipleValidResources_returnsPersistedResources() {
//        List<User> users =
//            Arrays.asList(
//                new User().withName("newUser1"),
//                new User().withName("newUser2"));
//
//        List<User> returnedUsers = (List<User>)repository.save(users);
//
//        returnedUsers.forEach(Assert::assertNotNull);
//        returnedUsers.forEach((User user) -> assertNotNull(user));
//
//        for (int i = 0; i < returnedUsers.size(); i++) {
//            assertEquals(returnedUsers.get(i).getName(), users.get(i).getName());
//        }
//    }
//
//    @Test
//    public void save_multipleValidResources_persistsResources() {
//        List<User> users =
//            Arrays.asList(
//                new User().withName("newUser1"),
//                new User().withName("newUser2"));
//
//        long userCountBeforeCreate = repository.count();
//
//        List<Long> persistedUsersIds = new ArrayList<>();
//        repository.save(users).forEach(user -> persistedUsersIds.add(user.getId()));
//
//        List<User> persistedUsers = (List<User>)repository.findAll(persistedUsersIds);
//
//        long userCountAfterCreate = repository.count();
//
//        assertEquals(userCountAfterCreate, userCountBeforeCreate + users.size());
//        persistedUsers.forEach(Assert::assertNotNull);
//        persistedUsers.forEach((User user) -> assertNotNull(user));
//
//        for (int i = 0; i < persistedUsers.size(); i++) {
//            assertEquals(persistedUsers.get(i).getName(), users.get(i).getName());
//        }
//    }
//
//    @Test
//    public void exists_existingResourceId_returnsTrue() {
//        assertTrue(repository.exists(1L));
//    }
//
//    @Test
//    public void exists_nonExistingResourceId_returnsFalse() {
//        assertFalse(repository.exists(-1L));
//    }
//
//    @Test(expected = InvalidDataAccessApiUsageException.class)
//    public void exists_nullResourceId_throwsInvalidDataAccessApiUsageException() {
//        repository.exists(null);
//    }
//
//    @Test
//    public void count_returnsResourceCount() {
//        assertEquals(repository.count(), 22);
//    }
//
//    @Test
//    public void findOne_existingResourceId_returnsResource() {
//        User user = repository.findOne(1L);
//
//        assertEquals(user.getName(), "Tom");
//    }
//
//    @Test
//    public void findOne_nonExistingResourceId_returnsNull() {
//        assertNull(repository.findOne(-1L));
//    }
//
//    @Test
//    public void findAll_returnsAllResources() {
//        List<User> users = (List<User>)repository.findAll();
//
//        assertEquals(users.size(), repository.count());
//    }
//
//    @Test
//    public void findAll_existingResourcesIds_returnsResources() {
//        List<Long> usersIds = Arrays.asList(1L, 2L);
//        List<User> users = (List<User>)repository.findAll(usersIds);
//
//        assertEquals(users.size(), usersIds.size());
//        users.forEach((User user) -> usersIds.contains(user.getId()));
//    }
//
//    @Test
//    public void findAll_nonExistingResourcesIds_returnsNoResources() {
//        List<User> users = (List<User>)repository.findAll(Arrays.asList(-1L, -2L));
//
//        assertEquals(users.size(), 0);
//    }
//
//    @Test
//    public void findAll_noUsersIds_returnsNoResources() {
//        List<User> users = (List<User>)repository.findAll(new ArrayList<>());
//
//        assertEquals(users.size(), 0);
//    }
//
//    @Test
//    public void findAll_existingAndNonExistingResourcesIds_returnsOnlyExistingResources() {
//        List<Long> usersIds = Arrays.asList(1L, 2L, -1L);
//        List<User> users = (List<User>)repository.findAll(usersIds);
//
//        assertEquals(users.size(), 2);
//        users.forEach((User user) -> assertNotEquals(user.getId().longValue(), -1L));
//    }
//
//    @Test
//    public void findAll_sortByNameAsc_returnsResourcesSortedByNameAsc() {
//        List<User> usersSortedByNameAsc = (List<User>)repository.findAll();
//        Collections.sort(usersSortedByNameAsc);
//
//        List<User> users = (List<User>)repository.findAll(new Sort(Sort.Direction.ASC, "name"));
//
//        assertEquals(users, usersSortedByNameAsc);
//    }
//
//    @Test
//    public void findAll_sortByNameDesc_returnsResourcesSortedByNameDesc() {
//        List<User> usersSortedByNameDesc = (List<User>)repository.findAll();
//        Collections.sort(usersSortedByNameDesc);
//        Collections.reverse(usersSortedByNameDesc);
//
//        List<User> users = (List<User>)repository.findAll(new Sort(Sort.Direction.DESC, "name"));
//
//        assertEquals(users, usersSortedByNameDesc);
//    }
//
//    @Test(expected = PropertyReferenceException.class)
//    public void findAll_sortByInvalidProperty_throwsPropertyReferenceException() {
//        List<User> users = (List<User>)repository.findAll(new Sort(Sort.Direction.DESC, "invalidProperty"));
//    }
//
//    @Test
//    public void findAll_page1WithSize5SortedByNameDesc_returnsPage1WithSize5SortedByNameDesc() {
//        List<User> usersPageContent = (List<User>)repository.findAll(Arrays.asList(21L, 6L, 12L, 19L, 17L));
//        Collections.sort(usersPageContent);
//        Collections.reverse(usersPageContent);
//        Page<User> usersPage = repository.findAll(new PageRequest(1, 5, Sort.Direction.DESC, "name"));
//
//        assertEquals(usersPage.getNumber(), 1);
//        assertEquals(usersPage.getSize(), 5);
//        assertEquals(usersPage.getNumberOfElements(), 5);
//        assertEquals(usersPage.getTotalElements(), 22);
//        assertEquals(usersPage.getTotalPages(), 5);
//        assertEquals(usersPage.getSort(), new Sort(Sort.Direction.DESC, "name"));
//        assertEquals(usersPage.getContent().size(), 5);
//
//        assertEquals(usersPage.getContent(), usersPageContent);
//    }
//
//    @Test
//    public void delete_existingResourceId_deletesResource() {
//        long userCountBeforeDelete = repository.count();
//
//        repository.delete(1L);
//
//        long userCountAfterDelete = repository.count();
//
//        assertEquals(userCountAfterDelete, userCountBeforeDelete - 1);
//        assertNull(repository.findOne(1L));
//    }
//
//    @Test(expected = EmptyResultDataAccessException.class)
//    public void delete_nonExistingResourceId_throwsEmptyResultDataAccessException() {
//        repository.delete(-1L);
//    }
//
//    @Test
//    public void delete_nonExistingResourceId_doesntDeleteAnyResource() {
//        long userCountBeforeDelete = repository.count();
//
//        try {
//            repository.delete(-1L);
//        } catch (EmptyResultDataAccessException e) {
//            long userCountAfterDelete = repository.count();
//
//            assertEquals(userCountAfterDelete, userCountBeforeDelete);
//            return;
//        }
//
//        fail();
//    }
//
//    @Test
//    public void delete_existingResource_deletesResource() {
//        long userCountBeforeDelete = repository.count();
//
//        repository.delete(repository.findOne(1L));
//
//        long userCountAfterDelete = repository.count();
//
//        assertEquals(userCountAfterDelete, userCountBeforeDelete - 1);
//        assertNull(repository.findOne(1L));
//    }
//
//    @Test
//    public void delete_nonExistingResource_doesntDeleteAnyResource() {
//        long userCountBeforeDelete = repository.count();
//
//        repository.delete(new User().withId(-1L).withName("nonExistingUser"));
//
//        long userCountAfterDelete = repository.count();
//
//        assertEquals(userCountAfterDelete, userCountBeforeDelete);
//    }
//
//    @Test
//    public void delete_multipleExistingResources_deletesResources() {
//        List<User> users = (List<User>)repository.findAll(Arrays.asList(1L, 2L));
//        long userCountBeforeDelete = repository.count();
//
//        repository.delete(users);
//
//        long userCountAfterDelete = repository.count();
//
//        assertEquals(userCountAfterDelete, userCountBeforeDelete - users.size());
//        users.forEach((User user) -> assertNull(repository.findOne(user.getId())));
//    }
//
//    @Test
//    public void delete_multipleNonExistingResources_doesntDeleteAnyResource() {
//        List<User> users =
//            Arrays.asList(
//                new User().withId(-1L).withName("nonExistingUser1"),
//                new User().withId(-2L).withName("nonExistingUser2"));
//
//        long userCountBeforeDelete = repository.count();
//
//        repository.delete(users);
//
//        long userCountAfterDelete = repository.count();
//
//        assertEquals(userCountAfterDelete, userCountBeforeDelete);
//    }
//
//    @Test
//    public void delete_multipleNonExistingAndExistingResources_deletesOnlyExistingResources() {
//        List<User> users =
//            Arrays.asList(
//                new User().withId(-1L).withName("nonExistingUser1"),
//                new User().withId(1L).withName("Tom"));
//
//        long userCountBeforeDelete = repository.count();
//
//        repository.delete(users);
//
//        long userCountAfterDelete = repository.count();
//
//        assertEquals(userCountAfterDelete, userCountBeforeDelete - 1);
//        assertNull(repository.findOne(1L));
//    }
//
//    @Test
//    public void deleteAll_deletesAllResources() {
//        repository.deleteAll();
//
//        assertEquals(repository.count(), 0);
//    }
//}
