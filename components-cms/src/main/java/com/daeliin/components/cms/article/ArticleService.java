package com.daeliin.components.cms.article;

import com.daeliin.components.core.resource.service.ResourceService;
import com.daeliin.components.domain.utils.UrlFriendlyString;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ArticleService extends ResourceService<Article, Long, ArticleRepository> {

    @Override
    public Article create(Article article) {
        generateUrlFriendlyTitle(article);
        return super.create(article); 
    }
    
    @Override
    public Iterable<Article> create(Iterable<Article> articles) {
        articles.forEach(this::generateUrlFriendlyTitle);
        return super.create(articles);
    }
    
    @Override
    public Iterable<Article> update(Iterable<Article> articles) {
        articles.forEach(this::generateUrlFriendlyTitle);
        return super.update(articles);
    }

    @Override
    public Article update(Long id, Article article) {
        generateUrlFriendlyTitle(article);
        return super.update(id, article);
    }

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
            article.setPublicationDate(LocalDateTime.now());
            article.setPublished(true);
        }
    }
    
    private void generateUrlFriendlyTitle(Article article) {
        article.setUrlFriendlyTitle(new UrlFriendlyString(article.getTitle()).value());
    }
}
