package com.daeliin.framework.sample.api.user.service;

import com.daeliin.framework.sample.api.user.model.User;
import com.daeliin.framework.core.service.ResourceService;
import com.daeliin.framework.sample.api.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ResourceService<User, Long, UserRepository> {
}
