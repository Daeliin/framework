package com.daeliin.components.cms.article;

import com.daeliin.components.persistence.resource.repository.ResourceRepository;
import com.daeliin.components.cms.sql.BArticle;
import com.daeliin.components.cms.sql.QArticle;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class ArticleRepository extends ResourceRepository<BArticle, String> {

    public ArticleRepository () {
        super(QArticle.article, QArticle.article.id, BArticle::getId);
    }
}
