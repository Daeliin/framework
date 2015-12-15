package com.daeliin.framework.core.resource.repository;

import com.daeliin.framework.commons.model.PersistentResource;
import java.io.Serializable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Provides CRUD operations and pagination for a resource.
 * @param <E> resource type
 * @param <ID> resource id type  
 */
@NoRepositoryBean
public interface ResourceRepository<E extends PersistentResource, ID extends Serializable> extends PagingAndSortingRepository<E, ID> {
}
