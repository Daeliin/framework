package com.daeliin.framework.sample.user;

import com.daeliin.framework.core.service.ResourceService;
import com.daeliin.framework.security.details.User;
import com.daeliin.framework.security.details.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ResourceService<User, Long, UserRepository> {
}
