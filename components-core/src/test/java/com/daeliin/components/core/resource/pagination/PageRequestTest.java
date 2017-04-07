package com.daeliin.components.core.resource.pagination;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public final class PageRequestTest {

    @Test
    public void shouldHaveADefaultIndexOf0() {
        assertThat(new PageRequest().index).isEqualTo(0);
        assertThat(new PageRequest(-1).index).isEqualTo(0);
        assertThat(new PageRequest(Integer.MAX_VALUE + 1).index).isEqualTo(0);
    }

    @Test
    public void shouldSetIndex() {
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
    public void shouldSetSize() {
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
    public void shouldSetSorts() {
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
}