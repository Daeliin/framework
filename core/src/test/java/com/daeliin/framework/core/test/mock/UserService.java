package com.daeliin.framework.core.test.mock;

import com.daeliin.framework.core.service.ResourceService;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ResourceService<User, Long, UserRepository> {
}
