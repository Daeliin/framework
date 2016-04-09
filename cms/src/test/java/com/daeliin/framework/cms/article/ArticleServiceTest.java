package com.daeliin.framework.cms.article;

import com.daeliin.framework.cms.Application;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.Test;

@ContextConfiguration(classes = Application.class)
public class ArticleServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private ArticleService articleService; 
    
    @Test
    public void publish_notPublishedArticle_makesItPublished() {
        LocalDate today = LocalDate.now();
        Article notPublishedArticle = articleService.findOne(2L);
        
        articleService.publish(notPublishedArticle);
        
        Article publishedArticle = articleService.findOne(2L);
        
        assertTrue(publishedArticle.isPublished());
    }
    
    @Test
    public void publish_alreadyPublishedArticle_doesntChangeAnything() {
        Article alreadyPublishedArticle = articleService.findOne(2L);
        
        articleService.publish(alreadyPublishedArticle);
        
        Article publishedArticle = articleService.findOne(2L);
        
        Assert.assertEquals(publishedArticle, alreadyPublishedArticle);
    }
}
