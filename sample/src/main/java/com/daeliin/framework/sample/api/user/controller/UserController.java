package com.daeliin.framework.sample.api.user.controller;

import com.daeliin.framework.sample.api.user.model.User;
import com.daeliin.framework.core.controller.ResourceController;
import static com.daeliin.framework.sample.Application.API_ROOT_PATH;
import com.daeliin.framework.sample.api.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_ROOT_PATH + "users")
public class UserController extends ResourceController<User, Long, UserService>{
}
