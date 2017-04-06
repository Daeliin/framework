package com.daeliin.components.cms.comment;

import com.daeliin.components.core.resource.repository.PagingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends PagingRepository<Comment, Long> {
}
