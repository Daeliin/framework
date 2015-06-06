package com.daeliin.framework;

import com.daeliin.framework.core.repository.ResourceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ResourceRepository<User, Long>{
}
