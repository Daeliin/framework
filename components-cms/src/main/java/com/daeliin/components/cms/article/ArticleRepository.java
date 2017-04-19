package com.daeliin.components.cms.article;

import com.daeliin.components.core.resource.repository.BaseRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class ArticleRepository extends BaseRepository<BArticle> {

    public ArticleRepository () {
        super(QArticle.article, QArticle.article.uuid);
    }
}
