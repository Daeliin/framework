package com.daeliin.framework.cms.article;

import static com.daeliin.framework.cms.Application.API_ROOT_PATH;
import com.daeliin.framework.core.resource.controller.ResourceController;
import java.util.List;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(API_ROOT_PATH + "/articles")
@RestController
public class ArticleController extends ResourceController<Article, Long, ArticleService> {

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @Override
    public Article create(@Valid Article resource) {
        return super.create(resource);
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @Override
    public Article update(@PathVariable Long id, @Valid Article resource) {
        return super.update(id, resource);
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @Override
    public void delete(List<Long> ids) {
        super.delete(ids);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @Override
    public void delete(@PathVariable Long id) {
        super.delete(id);
    }
}
