package com.daeliin.framework.sample.api.user.controller;

import com.daeliin.framework.sample.api.user.model.User;
import com.daeliin.framework.core.controller.ResourceController;
import com.daeliin.framework.sample.api.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController extends ResourceController<User, Long, UserService>{
}
