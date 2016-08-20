package com.daeliin.components.webservices.mock;

import com.daeliin.components.webservices.rest.controller.ResourceController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends ResourceController<User, Long, UserService> {
}
