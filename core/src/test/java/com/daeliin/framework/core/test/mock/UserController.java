package com.daeliin.framework.core.test.mock;

import com.daeliin.framework.core.controller.ResourceController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends ResourceController<User, Long, UserService>{
}
