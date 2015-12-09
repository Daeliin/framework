package com.daeliin.framework.security.mock.user.service;

import com.daeliin.framework.security.mock.user.model.User;
import com.daeliin.framework.core.service.ResourceService;
import com.daeliin.framework.security.mock.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ResourceService<User, Long, UserRepository> {
}
