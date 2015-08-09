package com.daeliin.framework.sample.api.user.repository;

import com.daeliin.framework.sample.api.user.model.User;
import com.daeliin.framework.commons.security.details.UserDetailsRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends UserDetailsRepository<User, Long> {
}
