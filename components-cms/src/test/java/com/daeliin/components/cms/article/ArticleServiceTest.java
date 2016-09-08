package com.daeliin.components.cms.article;

import com.daeliin.components.cms.Application;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.Test;

@ContextConfiguration(classes = Application.class)
public class ArticleServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private ArticleService articleService; 
    
    @Test
    public void publish_notPublishedArticle_makesItPublished() {
        Article notPublishedArticle = articleService.findOne(2L);
        
        articleService.publish(notPublishedArticle);
        
        Article publishedArticle = articleService.findOne(2L);
        
        assertNotNull(publishedArticle.getPublicationDate());
        assertTrue(publishedArticle.isPublished());
    }
    
    @Test
    public void publish_alreadyPublishedArticle_doesntChangeAnything() {
        Article alreadyPublishedArticle = articleService.findOne(1L);
        
        articleService.publish(alreadyPublishedArticle);
        
        Article republishedArticle = articleService.findOne(1L);
        
        assertEquals(republishedArticle, alreadyPublishedArticle);
    }
    
    @Test
    public void publish_notPublishedArticles_makesAllOfThemPublished() {
        List<Article> notPublishedArticles = Arrays.asList(articleService.findOne(2L), articleService.findOne(3L));
        
        articleService.publish(notPublishedArticles);
        
        List<Article> publishedArticles = Arrays.asList(articleService.findOne(2L), articleService.findOne(3L));
        
        publishedArticles.forEach(publishedArticle -> {
            assertNotNull(publishedArticle.getPublicationDate());
            assertTrue(publishedArticle.isPublished());
        });
    }
    
    @Test
    public void publish_alreadyPublishedArticles_doesntChangeAnything() {
        List<Article> alreadyPublishedArticles = Arrays.asList(articleService.findOne(1L), articleService.findOne(4L));
        
        articleService.publish(alreadyPublishedArticles);
        
        List<Article> publishedArticle = Arrays.asList(articleService.findOne(1L), articleService.findOne(4L));
        
        assertEquals(alreadyPublishedArticles, publishedArticle);
    }
}
