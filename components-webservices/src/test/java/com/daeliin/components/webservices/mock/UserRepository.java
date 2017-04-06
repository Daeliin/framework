package com.daeliin.components.webservices.mock;

import com.daeliin.components.core.resource.repository.PagingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingRepository<User, Long> {
    
    public User findFirstByName(final String name);
}
