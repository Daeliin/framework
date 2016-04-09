package com.daeliin.framework.cms.comment;

import com.daeliin.framework.core.resource.repository.ResourceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends ResourceRepository<Comment, Long> {
}
