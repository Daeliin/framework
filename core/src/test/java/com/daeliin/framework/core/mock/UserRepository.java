package com.daeliin.framework.core.mock;

import com.daeliin.framework.core.repository.ResourceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ResourceRepository<User, Long>{
    
    public User findFirstByName(final String name);
}
