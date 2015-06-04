package com.daeliin.framework.core.repository;

import com.daeliin.framework.commons.model.PersistentEntity;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface ResourceRepository extends PagingAndSortingRepository<PersistentEntity, Long>{
}
