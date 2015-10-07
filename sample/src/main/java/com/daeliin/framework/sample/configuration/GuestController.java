package com.daeliin.framework.sample.configuration;

import static com.daeliin.framework.sample.Application.API_ROOT_PATH;
import com.daeliin.framework.sample.api.user.model.User;
import com.daeliin.framework.sample.api.user.repository.UserRepository;
import com.daeliin.framework.sample.api.user.service.UserService;
import com.daeliin.framework.security.membership.MembershipController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_ROOT_PATH + "/public/membership")
public class GuestController extends MembershipController<User, Long, UserRepository, UserService> {
}

