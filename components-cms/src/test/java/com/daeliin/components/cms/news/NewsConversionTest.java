package com.daeliin.components.cms.news;

import com.daeliin.components.cms.fixtures.NewsRows;
import com.daeliin.components.cms.library.NewsLibrary;
import com.daeliin.components.cms.library.PersistentConversionTest;
import com.daeliin.components.cms.sql.BNews;
import com.daeliin.components.core.resource.Conversion;

public final class NewsConversionTest extends PersistentConversionTest<News, BNews> {

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
        return NewsRows.publishedNews();
    }
}