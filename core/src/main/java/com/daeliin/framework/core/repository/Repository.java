package com.daeliin.framework.core.repository;

import com.daeliin.framework.commons.model.PersistentEntity;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 */
@NoRepositoryBean
public interface Repository extends org.springframework.data.repository.Repository<PersistentEntity, Long>{
}
