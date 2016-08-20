package com.daeliin.components.cms.article;

import com.daeliin.components.core.resource.repository.ResourceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends ResourceRepository<Article, Long>{
}
