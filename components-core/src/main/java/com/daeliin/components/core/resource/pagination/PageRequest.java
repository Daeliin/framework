package com.daeliin.components.core.resource.pagination;

import java.util.Collections;
import java.util.List;

public final class PageRequest {

    private static final int DEFAULT_INDEX = 0;
    private static final int DEFAULT_SIZE = 25;

    public final int index;
    public final int size;
    public final int offset;
    public final List<Sort> sorts;

    public PageRequest() {
        this(DEFAULT_INDEX);
    }

    public PageRequest(int index) {
        this(index, DEFAULT_SIZE);
    }

    public PageRequest(int index, int size) {
        this(index, size, Collections.EMPTY_LIST);
    }

    public PageRequest(int index, int size, List<Sort> sorts) {
        this.index = index >= 0 && index <= Integer.MAX_VALUE ? index : DEFAULT_INDEX;
        this.size = size >= 0 && size <= Integer.MAX_VALUE? size : DEFAULT_SIZE;
        this.offset = index * size;
        this.sorts = sorts == null || sorts.isEmpty() ? Collections.EMPTY_LIST : sorts;
    }
}
