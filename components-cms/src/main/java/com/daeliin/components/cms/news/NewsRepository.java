package com.daeliin.components.cms.news;

import com.daeliin.components.cms.sql.BNews;
import com.daeliin.components.cms.sql.QNews;
import com.daeliin.components.persistence.resource.repository.ResourceRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Transactional
@Component
public class NewsRepository extends ResourceRepository<BNews, String> {

    public NewsRepository() {
        super(QNews.news, QNews.news.id, BNews::getId);
    }

    public boolean deleteForArticle(String articleId) {
        return queryFactory.delete(rowPath)
                .where(QNews.news.articleId.eq(articleId))
                .execute() > 0;
    }

    public Map<String, Set<BNews>> findByArticleId(Set<String> articleIds) {
        Collection<BNews> news = findAll(QNews.news.articleId.in(articleIds));
        Map<String, Set<BNews>> newsByArticle = new HashMap<>();


        for (String articleId : articleIds) {
            Set<BNews> newsForArticle = news.stream().filter(bNews -> bNews.getArticleId().equals(articleId)).collect(toSet());
            newsByArticle.put(articleId, newsForArticle);
        }

        return newsByArticle;
    }
}
