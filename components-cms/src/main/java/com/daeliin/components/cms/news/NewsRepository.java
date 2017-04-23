package com.daeliin.components.cms.news;

import com.daeliin.components.core.resource.repository.ResourceRepository;
import com.daeliin.components.core.sql.BNews;
import com.daeliin.components.core.sql.QNews;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class NewsRepository extends ResourceRepository<BNews, String> {

    public NewsRepository() {
        super(QNews.news, QNews.news.id, BNews::getId);
    }
}
