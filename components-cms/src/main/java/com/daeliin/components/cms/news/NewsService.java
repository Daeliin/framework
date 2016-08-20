package com.daeliin.components.cms.news;

import com.daeliin.components.core.resource.service.ResourceService;
import org.springframework.stereotype.Service;

@Service
public class NewsService extends ResourceService<News, Long, NewsRepository> {
}
