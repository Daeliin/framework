package com.daeliin.components.cms.news;

import com.daeliin.components.core.resource.repository.ResourceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends ResourceRepository<News, Long> {
}
