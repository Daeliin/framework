package com.daeliin.framework.security.mock.user.controller;

import com.daeliin.framework.security.mock.user.model.User;
import com.daeliin.framework.core.resource.controller.ResourceController;
import com.daeliin.framework.core.resource.exception.ResourceNotFoundException;
import static com.daeliin.framework.security.mock.Application.API_ROOT_PATH;
import com.daeliin.framework.security.mock.user.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_ROOT_PATH + "/users")
public class UserController extends ResourceController<User, Long, UserService>{

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void deleteAll() {
        super.deleteAll(); 
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        super.delete(id);
    }
    
}
