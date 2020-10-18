package com.blebail.components.cms.news;

import com.blebail.components.cms.sql.BNews;
import com.blebail.components.cms.sql.QNews;
import com.blebail.components.persistence.resource.repository.SpringCrudRepository;
import com.blebail.querydsl.crud.commons.resource.IdentifiableQDSLResource;
import com.querydsl.sql.SQLQueryFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Transactional
@Component
public class NewsRepository extends SpringCrudRepository<QNews, BNews, String> {

    @Inject
    public NewsRepository(SQLQueryFactory queryFactory) {
        super(new IdentifiableQDSLResource<>(QNews.news, QNews.news.id, BNews::getId), queryFactory);
    }
}
