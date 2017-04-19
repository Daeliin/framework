package com.daeliin.components.cms.news;

import com.daeliin.components.core.resource.repository.BaseRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class NewsRepository extends BaseRepository<BNews> {

    public NewsRepository() {
        super(QNews.news, QNews.news.uuid);
    }
}
