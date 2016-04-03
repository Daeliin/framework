package com.daeliin.framework.cms.article;

import com.daeliin.framework.core.resource.controller.ResourceController;
import java.io.Serializable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class ArticleController<A extends Article, ID extends Serializable, S extends ArticleService<A, ID, ? extends ArticleRepository>> 
    extends ResourceController<A, ID, S> {
}
