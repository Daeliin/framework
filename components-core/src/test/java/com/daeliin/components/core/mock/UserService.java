package com.daeliin.components.core.mock;

import com.daeliin.components.core.resource.service.ResourceService;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ResourceService<User, Long, UserRepository> {
}
