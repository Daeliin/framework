package com.daeliin.framework.core.resource.mock;

import com.daeliin.framework.core.resource.repository.ResourceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ResourceRepository<User, Long>{
    
    public User findFirstByName(final String name);
}
