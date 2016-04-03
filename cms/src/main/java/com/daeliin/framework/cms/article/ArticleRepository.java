package com.daeliin.framework.cms.article;

import com.daeliin.framework.core.resource.repository.ResourceRepository;
import java.io.Serializable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ArticleRepository<E extends Article, ID extends Serializable> extends ResourceRepository<E, ID>{
}
