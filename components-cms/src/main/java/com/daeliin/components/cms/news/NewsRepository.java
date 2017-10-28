package com.daeliin.components.cms.news;

import com.daeliin.components.cms.sql.BNews;
import com.daeliin.components.cms.sql.QNews;
import com.daeliin.components.persistence.resource.repository.ResourceRepository;
import com.querydsl.core.Tuple;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

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

    public Map<String, Long> countByArticleId(Set<String> articleIds) {
        List<Tuple> articleIdAndNewsCountList = queryFactory.from(QNews.news)
                .groupBy(QNews.news.articleId)
                .select(QNews.news.articleId, QNews.news.articleId.count())
                .fetch();

        Map<String, Long> countByArticle = articleIdAndNewsCountList
                .stream()
                .collect(toMap(tuple -> tuple.get(QNews.news.articleId), tuple -> tuple.get(QNews.news.articleId.count())));

        return articleIds.stream()
            .collect(toMap(articleId -> articleId, articleId -> countByArticle.getOrDefault(articleId, 0L)));
    }
}
