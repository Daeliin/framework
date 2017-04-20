package com.daeliin.components.core.configuration;

import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringConnectionProvider;
import com.querydsl.sql.spring.SpringExceptionTranslator;
import com.querydsl.sql.types.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
public class Persistence {

    @Bean
    public com.querydsl.sql.Configuration querydslConfiguration() {
        SQLTemplates templates = MySQLTemplates.builder().build();
        com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(templates);

        configuration.setExceptionTranslator(new SpringExceptionTranslator());
        configuration.register(new JSR310InstantType());
        configuration.register(new JSR310LocalDateType());
        configuration.register(new JSR310LocalTimeType());
        configuration.register(new JSR310LocalDateTimeType());
        configuration.register(new JSR310OffsetTimeType());
        configuration.register(new JSR310OffsetDateTimeType());
        configuration.register(new JSR310ZonedDateTimeType());

        return configuration;
    }

    @Bean
    @Inject
    public SQLQueryFactory queryFactory(DataSource dataSource) {
        Provider<Connection> provider = new SpringConnectionProvider(dataSource);

        return new SQLQueryFactory(querydslConfiguration(), provider);
    }
}
