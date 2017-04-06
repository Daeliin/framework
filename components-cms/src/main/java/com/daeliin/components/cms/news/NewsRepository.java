package com.daeliin.components.cms.news;

import com.daeliin.components.core.resource.repository.PagingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends PagingRepository<News, Long> {
}
