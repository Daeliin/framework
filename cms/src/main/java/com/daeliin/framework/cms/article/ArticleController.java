package com.daeliin.framework.cms.article;

import static com.daeliin.framework.cms.Application.API_ROOT_PATH;
import com.daeliin.framework.core.resource.controller.ResourceController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(API_ROOT_PATH + "/articles")
@RestController
public class ArticleController extends ResourceController<Article, Long, ArticleService> {
}
