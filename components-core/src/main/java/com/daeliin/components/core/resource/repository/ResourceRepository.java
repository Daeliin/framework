package com.daeliin.components.core.resource.repository;

import com.daeliin.components.domain.resource.PersistentResource;
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
