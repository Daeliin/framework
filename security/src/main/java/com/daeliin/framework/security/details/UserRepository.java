package com.daeliin.framework.security.details;

import com.daeliin.framework.core.repository.ResourceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ResourceRepository<User, Long> {
    
    public User findByUsernameIgnoreCaseAndEnabled(final String username, final boolean enabled);
}
