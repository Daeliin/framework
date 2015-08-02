package com.daeliin.framework.sample.user;

import com.daeliin.framework.core.repository.ResourceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ResourceRepository<User, Long>{
    
    public User findFirstByName(final String name);
}
