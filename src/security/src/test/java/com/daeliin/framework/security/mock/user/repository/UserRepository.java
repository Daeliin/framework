package com.daeliin.framework.security.mock.user.repository;

import com.daeliin.framework.security.mock.user.model.User;
import com.daeliin.framework.commons.security.details.UserDetailsRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends UserDetailsRepository<User, Long> {
}
