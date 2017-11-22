package com.daeliin.components.cms.news;

import com.daeliin.components.cms.sql.BNews;
import com.daeliin.components.cms.sql.QNews;
import com.daeliin.components.persistence.resource.repository.ResourceRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class NewsRepository extends ResourceRepository<BNews, String> {

    public NewsRepository() {
        super(QNews.news, QNews.news.id, BNews::getId);
    }
}
