package com.daeliin.framework.sample;

import com.daeliin.framework.core.repository.FullCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends FullCrudRepository<User, Long>{
}
