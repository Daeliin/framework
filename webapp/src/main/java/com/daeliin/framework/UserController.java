package com.daeliin.framework;

import com.daeliin.framework.core.controller.ResourceController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends ResourceController<User, Long, UserService>{
    
    @RequestMapping("hello")
    public User hello() {
        return 
            new User()
                .setId(1L)
                .setName("John");
    }
}
