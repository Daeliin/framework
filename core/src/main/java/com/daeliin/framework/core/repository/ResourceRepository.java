package com.daeliin.framework.core.repository;

import com.daeliin.framework.commons.model.PersistentResource;
import java.io.Serializable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface ResourceRepository<E extends PersistentResource, ID extends Serializable> extends PagingAndSortingRepository<E, ID> {
}
