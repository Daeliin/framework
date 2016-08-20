package com.daeliin.components.cms.comment;

import com.daeliin.components.core.resource.repository.ResourceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends ResourceRepository<Comment, Long> {
}
