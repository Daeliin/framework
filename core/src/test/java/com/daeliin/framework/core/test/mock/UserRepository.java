package com.daeliin.framework.core.test.mock;

import com.daeliin.framework.core.repository.FullCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends FullCrudRepository<User, Long>{
}
