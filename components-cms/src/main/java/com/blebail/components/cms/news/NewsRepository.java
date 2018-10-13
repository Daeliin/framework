package com.blebail.components.cms.news;

import com.blebail.components.persistence.resource.repository.ResourceRepository;
import com.blebail.components.cms.sql.BNews;
import com.blebail.components.cms.sql.QNews;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class NewsRepository extends ResourceRepository<BNews, String> {

    public NewsRepository() {
        super(QNews.news, QNews.news.id, BNews::getId);
    }
}
