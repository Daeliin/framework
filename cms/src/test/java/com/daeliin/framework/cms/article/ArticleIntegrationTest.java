package com.daeliin.framework.cms.article;

import com.daeliin.framework.cms.Application;
import com.daeliin.framework.commons.test.SecuredIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

@ContextConfiguration(classes = Application.class)
public class ArticleIntegrationTest extends SecuredIntegrationTest {
    
    @Test
    public void publish_notPublishedArticle_makesItPublished() {
    }
    
    @Test
    public void publish_alreadyPublishedArticle_doesntChangeAnything() {
    }
}
