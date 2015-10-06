package com.daeliin.framework.security.mock.membership.controller;

import com.daeliin.framework.security.membership.MembershipController;
import static com.daeliin.framework.security.mock.Application.API_ROOT_PATH;
import com.daeliin.framework.security.mock.user.model.User;
import com.daeliin.framework.security.mock.user.repository.UserRepository;
import com.daeliin.framework.security.mock.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_ROOT_PATH + "public/membership")
public class GuestController extends MembershipController<User, Long, UserRepository, UserService> {
}
