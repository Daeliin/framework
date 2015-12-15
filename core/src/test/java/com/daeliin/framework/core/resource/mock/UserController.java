package com.daeliin.framework.core.resource.mock;

import com.daeliin.framework.core.resource.controller.ResourceController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends ResourceController<User, Long, UserService>{
}
