package com.daeliin.framework.sample.user;

import com.daeliin.framework.core.controller.ResourceController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController extends ResourceController<User, Long, UserService>{
}
