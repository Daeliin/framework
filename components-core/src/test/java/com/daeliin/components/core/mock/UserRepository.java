package com.daeliin.components.core.mock;

import com.daeliin.components.core.resource.repository.ResourceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ResourceRepository<User, Long>{
    
    public User findFirstByName(final String name);
}
