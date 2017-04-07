package com.daeliin.components.domain.pagination;

import com.daeliin.components.domain.pagination.PageRequest;
import com.daeliin.components.domain.pagination.Sort;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public final class PageRequestTest {

    @Test
    public void shouldHaveADefaultIndexOf0() {
        assertThat(new PageRequest().index).isEqualTo(0);
        assertThat(new PageRequest(-1).index).isEqualTo(0);
        assertThat(new PageRequest(Integer.MAX_VALUE + 1).index).isEqualTo(0);
    }

    @Test
    public void shouldAssignAnIndex() {
        assertThat(new PageRequest(5).index).isEqualTo(5);
        assertThat(new PageRequest(5, 20).index).isEqualTo(5);
        assertThat(new PageRequest(5, 20, null).index).isEqualTo(5);
    }

    @Test
    public void shouldHaveADefaultSizeOf25() {
        assertThat(new PageRequest().size).isEqualTo(25);
        assertThat(new PageRequest(0).size).isEqualTo(25);
        assertThat(new PageRequest(0, -1).size).isEqualTo(25);
        assertThat(new PageRequest(0, Integer.MAX_VALUE + 1).size).isEqualTo(25);
    }

    @Test
    public void shouldAssignASize() {
        assertThat(new PageRequest(0, 50).size).isEqualTo(50);
        assertThat(new PageRequest(0, 50, null).size).isEqualTo(50);
    }

    @Test
    public void shouldHaveNoDefaultSorts() {
        assertThat(new PageRequest().sorts).isEmpty();
        assertThat(new PageRequest(0).sorts).isEmpty();
        assertThat(new PageRequest(2, 25).sorts).isEmpty();
        assertThat(new PageRequest(2, 25, null).sorts).isEmpty();
        assertThat(new PageRequest(2, 25, new ArrayList<>()).sorts).isEmpty();
    }

    @Test
    public void shouldAssignSorts() {
        Sort idAsc = new Sort("id", Sort.Direction.ASC);
        Sort nameDesc = new Sort("name", Sort.Direction.DESC);

        assertThat(new PageRequest(2, 25, Arrays.asList(idAsc, nameDesc)).sorts).containsOnly(idAsc, nameDesc);
    }

    @Test
    public void shouldComputeOffset() {
        assertThat(new PageRequest().offset).isEqualTo(0);
        assertThat(new PageRequest(0).offset).isEqualTo(0);
        assertThat(new PageRequest(2, 25).offset).isEqualTo(50);
    }

    @Test
    public void shouldBeEqual_whenSameIndexSameSizeAndSameSorts() {
        int index = 5;
        int size = 10;
        List<Sort> sorts = Arrays.asList(new Sort("id", Sort.Direction.ASC), new Sort("name", Sort.Direction.DESC));

        PageRequest pageRequest = new PageRequest(index, size, sorts);
        PageRequest samePageRequest = new PageRequest(index, size, sorts);

        assertThat(pageRequest).isEqualTo(samePageRequest);
    }

    @Test
    public void shouldPrintsItsIndexSizeAndSorts() {
        PageRequest pageRequest = new PageRequest();

        assertThat(pageRequest.toString()).contains(String.valueOf(pageRequest.index), String.valueOf(pageRequest.size), pageRequest.sorts.toString());
    }

    @Test
    public void shouldBeComparedOnIndex() {
        PageRequest pageRequest1 = new PageRequest(1);
        PageRequest pageRequest2 = new PageRequest(5);

        assertThat(pageRequest1.compareTo(pageRequest2)).isNegative();
        assertThat(pageRequest2.compareTo(pageRequest1)).isPositive();
    }
}