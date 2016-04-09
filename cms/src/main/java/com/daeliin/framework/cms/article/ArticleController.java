package com.daeliin.framework.cms.article;

import static com.daeliin.framework.cms.Application.API_ROOT_PATH;
import com.daeliin.framework.core.resource.controller.ResourceController;
import com.daeliin.framework.core.resource.exception.ResourceNotFoundException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(API_ROOT_PATH + "/articles")
@RestController
public class ArticleController extends ResourceController<Article, Long, ArticleService> {

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @Override
    public Article create(@Valid Article resource) {
        return super.create(resource);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @Override
    public Article update(@PathVariable Long id, @Valid Article resource) {
        return super.update(id, resource);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @Override
    public void delete(List<Long> ids) {
        super.delete(ids);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @Override
    public void delete(@PathVariable Long id) {
        super.delete(id);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @RequestMapping(value = "{id}/publish", method = RequestMethod.PUT)
    public void publish(@PathVariable Long id) {
        Article article = service.findOne(id);
        
        if (article == null) {
            throw new ResourceNotFoundException(String.format("The article %s does no exist", id));
        }
        
        service.publish(article);
    }
}
