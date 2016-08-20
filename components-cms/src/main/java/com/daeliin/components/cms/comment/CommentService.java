package com.daeliin.components.cms.comment;

import com.daeliin.components.core.resource.service.ResourceService;
import org.springframework.stereotype.Service;

@Service
public class CommentService extends ResourceService<Comment, Long, CommentRepository> {
}
