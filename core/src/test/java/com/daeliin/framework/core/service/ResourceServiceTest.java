package com.daeliin.framework.core.service;

import com.daeliin.framework.core.Application;
import com.daeliin.framework.core.mock.User;
import com.daeliin.framework.core.mock.UserRepository;
import com.daeliin.framework.core.mock.UserService;
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
    public void create_validResource_callRepositorySaveAndReturnsResource() {
        User user = new User().withName("validUserName");
        when(userRepository.save(user)).thenReturn(new User().withId(1L).withName("validUserName"));
        
        User createdUser = userService.create(user);
        
        verify(userRepository).save(user);
        assertEquals(createdUser.getId(), Long.valueOf(1L));
        assertEquals(createdUser.getName(), "validUserName");
    }
    
    @Test
    public void create_invalidResource_callRepositorySaveAndThrowsConstraintViolationException() {
        User user = new User().withName("");
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        when(userRepository.save(user))
            .thenThrow(
                new ConstraintViolationException(
                    validator.validateProperty(user, "name")));
        
        try {
            userService.create(user);
        } catch (ConstraintViolationException e) {
            verify(userRepository).save(user);
            return;
        }
        
        fail();
    }
}
