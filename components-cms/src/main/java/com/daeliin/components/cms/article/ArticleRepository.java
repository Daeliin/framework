package com.daeliin.components.cms.article;

import com.daeliin.components.core.resource.repository.PagingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends PagingRepository<Article, Long> {
}
