package com.daeliin.framework.cms.article;

import com.daeliin.framework.core.resource.repository.ResourceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends ResourceRepository<Article, Long>{
}
