package com.daeliin.framework.core.service;

import com.daeliin.framework.core.Application;
import com.daeliin.framework.core.mock.User;
import com.daeliin.framework.core.mock.UserRepository;
import com.daeliin.framework.core.mock.UserService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@ContextConfiguration(classes = Application.class)
public class ResourceServiceTest extends AbstractTransactionalTestNGSpringContextTests {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void create_validResource_callsRepositorySaveAndReturnsResource() {
        User validUser = new User().withName("validUserName");
        User savedValidUser = new User().withId(1L).withName("validUserName");
        
        when(userRepository.save(validUser)).thenReturn(savedValidUser);
        
        User createdUser = userService.create(validUser);
        
        verify(userRepository).save(validUser);
        assertEquals(createdUser, savedValidUser);
    }
    
    @Test
    public void create_invalidResource_callsRepositorySaveAndThrowsConstraintViolationException() {
        User invalidUser = new User().withName("");
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        when(userRepository.save(invalidUser))
            .thenThrow(
                new ConstraintViolationException(
                    validator.validateProperty(invalidUser, "name")));
        
        try {
            userService.create(invalidUser);
        } catch (ConstraintViolationException e) {
            verify(userRepository).save(invalidUser);
            return;
        }
        
        fail();
    }
    
    @Test
    public void create_validResources_callsRepositorySaveAndReturnsResources() {
        List<User> validUsers = 
            Arrays.asList(
                new User().withName("validUserName1"),
                new User().withName("validUserName2"));
    
        
        List<User> savedValidUsers = Arrays.asList(
                new User().withId(1L).withName("validUserName1"),
                new User().withId(2L).withName("validUserName2"));
        
        when(userRepository.save(validUsers)).thenReturn(savedValidUsers);
        
        List<User> createdUsers = (List<User>)userService.create(validUsers);
        
        verify(userRepository).save(validUsers);
        
        assertEquals(createdUsers, savedValidUsers);
    }
    
    @Test
    public void create_invalidResources_callsRepositorySaveAndThrowsConstraintViolationException() {
        List<User> invalidUsers = 
            Arrays.asList(
                new User().withName(""),
                new User().withName(""));
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<? extends ConstraintViolation<User>> constraintViolations = new HashSet<>();
        invalidUsers.forEach((User invalidUser) -> constraintViolations.addAll((Set)validator.validateProperty(invalidUser, "name")));
        
        when(userRepository.save(invalidUsers))
            .thenThrow(
                new ConstraintViolationException(constraintViolations));
        
        try {
            userService.create(invalidUsers);
        } catch (ConstraintViolationException e) {
            verify(userRepository).save(invalidUsers);
            return;
        }
        
        fail();
    }
    
    @Test
    public void exists_callsRepositoryExistsAndReturnsSameResult() {
        when(userRepository.exists(1L)).thenReturn(true);
        when(userRepository.exists(-1L)).thenReturn(false);
        
        assertTrue(userService.exists(1L));
        verify(userRepository).exists(1L);
        
        assertFalse(userService.exists(-1L));
        verify(userRepository).exists(-1L);
    }
    
    @Test
    public void count_callsRepositoryCountAndReturnsSameResult() {
        when(userRepository.count()).thenReturn(22L);
        
        assertEquals(userService.count(), 22);
        verify(userRepository).count();
    }
}
