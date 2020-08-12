package com.blebail.components.cms.news;

import com.blebail.components.cms.library.NewsLibrary;
import com.blebail.components.cms.library.PersistenceConversionTest;
import com.blebail.components.cms.sql.BNews;
import com.blebail.components.core.resource.Conversion;

public final class NewsConversionTest extends PersistenceConversionTest<News, BNews> {

    @Override
    protected Conversion<News, BNews> conversion() {
        return new NewsConversion();
    }

    @Override
    protected News object() {
        return NewsLibrary.publishedNews();
    }

    @Override
    protected BNews converted() {
        return NewsLibrary.publishedNewsRow();
    }
}