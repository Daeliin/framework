package com.daeliin.framework.core.repository;

import com.daeliin.framework.core.Application;
import com.daeliin.framework.core.mock.User;
import com.daeliin.framework.core.mock.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;
import org.testng.annotations.Test;

@ContextConfiguration(classes = Application.class)
public class ResourceRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void save_validResource_persistsResource() {
        User user = new User().withName("newUser");
        long userCountBeforeCreate = userRepository.count();
        
        User persistedUser = userRepository.save(user);
        
        long userCountAfterCreate = userRepository.count();
        
        assertEquals(userCountAfterCreate, userCountBeforeCreate + 1);
        assertNotNull(persistedUser);
        assertNotNull(persistedUser.getId());
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void save_invalidResource_throwsConstraintViolationException() {
        User user = new User().withName("");
        
        userRepository.save(user);
    }
    
    @Test
    public void save_multipleValidResources_persistsResources() {
        List<User> users = 
            Arrays.asList(
                new User().withName("newUser1"),
                new User().withName("newUser2"));
                
        long userCountBeforeCreate = userRepository.count();
        
        List<User> persistedUsers = (List<User>)userRepository.save(users);
        
        long userCountAfterCreate = userRepository.count();
        
        assertEquals(userCountAfterCreate, userCountBeforeCreate + users.size());
        persistedUsers.forEach(Assert::assertNotNull);
        persistedUsers.forEach((User user) -> assertNotNull(user));
        
        for (int i = 0; i < persistedUsers.size(); i++) {
            assertEquals(persistedUsers.get(i).getName(), users.get(i).getName());
        }
    }
    
    @Test
    public void exists_existingResourceId_returnsTrue() {
        assertTrue(userRepository.exists(1L));
    }
    
    @Test
    public void exists_nonExistingResourceId_returnsFalse() {
        assertFalse(userRepository.exists(-1L));
    }
    
    @Test
    public void count_returnsResourceCount() {
        assertEquals(userRepository.count(), 22);
    }
    
    @Test
    public void findOne_existingResourceId_returnsResource() {
        User user = userRepository.findOne(1L);
        
        assertEquals(user.getName(), "Tom");
    }
    
    @Test
    public void findOne_nonExistingResourceId_returnsNull() {
        assertNull(userRepository.findOne(-1L));
    }
    
    @Test
    public void findAll_returnsAllResources() {
        List<User> users = (List<User>)userRepository.findAll();
        
        assertEquals(users.size(), userRepository.count());
    }
    
    @Test
    public void findAll_existingResourcesIds_returnsResources() {
        List<Long> usersIds = Arrays.asList(1L, 2L);
        List<User> users = (List<User>)userRepository.findAll(usersIds);
        
        assertEquals(users.size(), usersIds.size());
        users.forEach((User user) -> usersIds.contains(user.getId()));
    }
    
    @Test
    public void findAll_nonExistingResourcesIds_returnsNoResources() {
        List<User> users = (List<User>)userRepository.findAll(Arrays.asList(-1L, -2L));
        
        assertEquals(users.size(), 0);
    }
    
    @Test
    public void findAll_noUsersIds_returnsNoResources() {
        List<User> users = (List<User>)userRepository.findAll(new ArrayList<>());
        
        assertEquals(users.size(), 0);
    }
    
    @Test
    public void findAll_existingAndNonExistingResourcesIds_returnsOnlyExistingResources() {
        List<Long> usersIds = Arrays.asList(1L, 2L, -1L);
        List<User> users = (List<User>)userRepository.findAll(usersIds);
        
        assertEquals(users.size(), 2);
        users.forEach((User user) -> assertNotEquals(user.getId(), -1L));
    }
    
    @Test
    public void findAll_sortByNameAsc_returnsResourcesSortedByNameAsc() {
        List<User> usersSortedByNameAsc = (List<User>)userRepository.findAll();
        Collections.sort(usersSortedByNameAsc);
        
        List<User> users = (List<User>)userRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
        
        assertEquals(users, usersSortedByNameAsc);
    }
    
    @Test
    public void findAll_sortByNameDesc_returnsResourcesSortedByNameDesc() {
        List<User> usersSortedByNameDesc = (List<User>)userRepository.findAll();
        Collections.sort(usersSortedByNameDesc);
        Collections.reverse(usersSortedByNameDesc);
        
        List<User> users = (List<User>)userRepository.findAll(new Sort(Sort.Direction.DESC, "name"));
        
        assertEquals(users, usersSortedByNameDesc);
    }
    
    @Test(expectedExceptions = PropertyReferenceException.class)
    public void findAll_sortByInvalidProperty_throwsPropertyReferenceException() {
        List<User> users = (List<User>)userRepository.findAll(new Sort(Sort.Direction.DESC, "invalidProperty"));
    }
    
    @Test
    public void findAll_page1WithSize5SortedByNameDesc_returnsPage1WithSize5SortedByNameDesc() {
        List<User> usersPageContent = (List<User>)userRepository.findAll(Arrays.asList(21L, 6L, 12L, 19L, 17L));
        Collections.sort(usersPageContent);
        Collections.reverse(usersPageContent);
        Page<User> usersPage = userRepository.findAll(new PageRequest(1, 5, Sort.Direction.DESC, "name"));
        
        assertEquals(usersPage.getNumber(), 1);
        assertEquals(usersPage.getSize(), 5);
        assertEquals(usersPage.getNumberOfElements(), 5);
        assertEquals(usersPage.getTotalElements(), 22);
        assertEquals(usersPage.getTotalPages(), 5);
        assertEquals(usersPage.getSort(), new Sort(Sort.Direction.DESC, "name"));
        assertEquals(usersPage.getContent().size(), 5);
        
        usersPage.getContent().forEach(System.out::println);
        
        assertEquals(usersPage.getContent(), usersPageContent);
    }
    
    @Test
    public void delete_existingResourceId_deletesResource() {
        long userCountBeforeDelete = userRepository.count();
        
        userRepository.delete(1L);
        
        long userCountAfterDelete = userRepository.count();
        
        assertEquals(userCountAfterDelete, userCountBeforeDelete - 1);
        assertNull(userRepository.findOne(1L));
    }
    
    @Test(expectedExceptions = EmptyResultDataAccessException.class)
    public void delete_nonExistingResourceId_throwsEmptyResultDataAccessException() {
        userRepository.delete(-1L);
    }
    
    @Test
    public void delete_nonExistingResourceId_doesntDeleteAnyResource() {
        long userCountBeforeDelete = userRepository.count();
        
        try {
            userRepository.delete(-1L);
        } catch (EmptyResultDataAccessException e) {
            long userCountAfterDelete = userRepository.count();
        
            assertEquals(userCountAfterDelete, userCountBeforeDelete);
            return;
        }
        
        fail();
    }
        
    @Test
    public void delete_existingResource_deletesResource() {
        long userCountBeforeDelete = userRepository.count();
        
        userRepository.delete(userRepository.findOne(1L));
        
        long userCountAfterDelete = userRepository.count();
        
        assertEquals(userCountAfterDelete, userCountBeforeDelete - 1);
        assertNull(userRepository.findOne(1L));
    }
    
    @Test
    public void delete_nonExistingResource_doesntDeleteAnyResource() {
        long userCountBeforeDelete = userRepository.count();
        
        userRepository.delete(new User().withId(-1L).withName("nonExistingUser"));
        
        long userCountAfterDelete = userRepository.count();
        
        assertEquals(userCountAfterDelete, userCountBeforeDelete);
    }
    
    @Test
    public void delete_multipleExistingResources_deletesResources() {
        List<User> users = (List<User>)userRepository.findAll(Arrays.asList(1L, 2L));
        long userCountBeforeDelete = userRepository.count();
        
        userRepository.delete(users);
        
        long userCountAfterDelete = userRepository.count();
        
        assertEquals(userCountAfterDelete, userCountBeforeDelete - users.size());
        users.forEach((User user) -> assertNull(userRepository.findOne(user.getId())));
    }
    
    @Test
    public void delete_multipleNonExistingResources_doesntDeleteAnyResource() {
        List<User> users = 
            Arrays.asList(
                new User().withId(-1L).withName("nonExistingUser1"), 
                new User().withId(-2L).withName("nonExistingUser2"));
        
        long userCountBeforeDelete = userRepository.count();
        
        userRepository.delete(users);
        
        long userCountAfterDelete = userRepository.count();
        
        assertEquals(userCountAfterDelete, userCountBeforeDelete);
    }
    
    @Test
    public void delete_multipleNonExistingAndExistingResources_deletesOnlyExistingResources() {
        List<User> users = 
            Arrays.asList(
                new User().withId(-1L).withName("nonExistingUser1"), 
                new User().withId(1L).withName("Tom"));
        
        long userCountBeforeDelete = userRepository.count();
        
        userRepository.delete(users);
        
        long userCountAfterDelete = userRepository.count();
        
        assertEquals(userCountAfterDelete, userCountBeforeDelete - 1);
        assertNull(userRepository.findOne(1L));
    }
    
    @Test
    public void deleteAll_deletesAllResources() {
        userRepository.deleteAll();
        
        assertEquals(userRepository.count(), 0);
    }
}
