package com.daeliin.components.cms.article;

import com.daeliin.components.core.resource.service.ResourceService;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ArticleService extends ResourceService<Article, Long, ArticleRepository> {

    /**
     * Publishes an article.
     * @param article the article to publish
     */
    public void publish(Article article) {
        setInPublishedState(article);
        super.update(article.getId(), article);
    }
    
    /**
     * Publishes several articles.
     * @param articles the articles to publish
     */
    public void publish(List<Article> articles) {
        articles.forEach(this::setInPublishedState);
        super.update(articles);
    } 
    
    private void setInPublishedState(Article article) {
        if (!article.isPublished()) {
            article.setPublicationDate(new Date());
            article.setPublished(true);
        }
    }
}
