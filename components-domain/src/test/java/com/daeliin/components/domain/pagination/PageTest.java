package com.daeliin.components.domain.pagination;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public final class PageTest {

    @Test(expected = Exception.class)
    public void shouldThrowAnException_whenItemsIsNull() {
        assertThat(new Page(null, 0, 0));
    }

    @Test
    public void shouldHaveADefaultTotalItemsOf0() {
        Assertions.assertThat(new Page(new ArrayList<>(), -1, 10).totalItems).isEqualTo(0);
        Assertions.assertThat(new Page(new ArrayList<>(), Long.MAX_VALUE + 1, 10).totalItems).isEqualTo(0);
    }

    @Test
    public void shouldAssignTotalItems() {
        Assertions.assertThat(new Page(new ArrayList<>(), 15, 10).totalItems).isEqualTo(15);
    }

    @Test
    public void shouldHaveADefaultTotalPagesOf0() {
        Assertions.assertThat(new Page(new ArrayList<>(), 10, -1).totalPages).isEqualTo(1);
        Assertions.assertThat(new Page(new ArrayList<>(), 10, Long.MAX_VALUE + 1).totalPages).isEqualTo(1);
    }

    @Test
    public void shouldAssignTotalPages() {
        Assertions.assertThat(new Page(new ArrayList<>(), 10, 15).totalPages).isEqualTo(15);
    }

    @Test
    public void shouldComputeNbItems() {
        List<String> items = Arrays.asList("John", "Jane");
        Page<String> page = new Page<>(items, 4, 2);

        assertThat(page.nbItems).isEqualTo(items.size());
    }

    @Test
    public void shouldBeEqual_whenSameItemsSameTotalItemsAndSameTotalPages() {
        List<String> items = Arrays.asList("John", "Jane");
        Page<String> page = new Page<>(items, 4, 2);
        Page<String> samePage = new Page<>(items, 4, 2);

        assertThat(page).isEqualTo(samePage);
    }

    @Test
    public void shouldPrintsItsItemsNbItemsTotalItemsAndtotalPages() {
        Page<String> page = new Page<>(Arrays.asList("John", "Jane"), 4, 2);

        Assertions.assertThat(page.toString()).contains(page.items.toString(), String.valueOf(page.nbItems), String.valueOf(page.totalItems), String.valueOf(page.totalPages));
    }
}