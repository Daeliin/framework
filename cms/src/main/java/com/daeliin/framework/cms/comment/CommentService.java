package com.daeliin.framework.cms.comment;

import com.daeliin.framework.core.resource.service.ResourceService;
import java.io.Serializable;
import org.springframework.stereotype.Service;

@Service
public abstract class CommentService<C extends Comment, ID extends Serializable, R extends CommentRepository<C, ID>>
    extends ResourceService<C, ID, R> {
}
