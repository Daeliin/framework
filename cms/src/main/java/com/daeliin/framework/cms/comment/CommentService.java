package com.daeliin.framework.cms.comment;

import com.daeliin.framework.core.resource.service.ResourceService;
import org.springframework.stereotype.Service;

@Service
public class CommentService extends ResourceService<Comment, Long, CommentRepository> {
}
