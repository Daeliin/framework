package com.daeliin.framework.cms.article;

import com.daeliin.framework.core.resource.service.ResourceService;
import java.io.Serializable;
import org.springframework.stereotype.Service;

@Service
public abstract class ArticleService<A extends Article, ID extends Serializable, R extends ArticleRepository<A, ID>> 
    extends ResourceService<A, ID, R>{
}
