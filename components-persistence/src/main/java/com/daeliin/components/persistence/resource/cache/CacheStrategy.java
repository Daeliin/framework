package com.daeliin.components.persistence.resource.cache;

import java.util.concurrent.TimeUnit;

public interface CacheStrategy {

    long duration();

    TimeUnit timeUnit();

    long maxSize();
}
