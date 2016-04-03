package com.daeliin.framework.cms.comment;

import com.daeliin.framework.core.resource.repository.ResourceRepository;
import java.io.Serializable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CommentRepository<E extends Comment, ID extends Serializable> extends ResourceRepository<E, ID> {
}
