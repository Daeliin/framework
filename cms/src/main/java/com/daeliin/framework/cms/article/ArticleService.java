package com.daeliin.framework.cms.article;

import com.daeliin.framework.core.resource.service.ResourceService;
import org.springframework.stereotype.Service;

@Service
public class ArticleService extends ResourceService<Article, Long, ArticleRepository> {
}
