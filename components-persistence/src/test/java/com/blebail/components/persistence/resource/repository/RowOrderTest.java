package com.blebail.components.persistence.resource.repository;

import com.blebail.components.core.pagination.PageRequest;
import com.blebail.components.core.pagination.Sort;
import com.blebail.components.persistence.sql.QUuidResource;
import com.google.common.collect.Sets;
import com.querydsl.core.types.OrderSpecifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public final class RowOrderTest {

    private RowOrder tested;

    @BeforeEach
    public void setUp() {
        tested = new RowOrder(QUuidResource.uuidResource);
    }

    @Test
    public void shouldComputeTotalPages() {
        assertThat(tested.computeTotalPages(10, 5)).isEqualTo(2);
        assertThat(tested.computeTotalPages(11, 5)).isEqualTo(3);
        assertThat(tested.computeTotalPages(2, 5)).isEqualTo(1);
    }

    @Test
    public void shouldGetSortablePaths() {
        assertThat(tested.getSortablePaths()).containsOnly(
                QUuidResource.uuidResource.uuid,
                QUuidResource.uuidResource.creationDate,
                QUuidResource.uuidResource.label);
    }

    @Test
    public void shouldComputeOrders() {
        PageRequest pageRequest = new PageRequest(0, 10, Sets.newLinkedHashSet(Arrays.asList(
                new Sort("uuid", Sort.Direction.DESC),
                new Sort("CreationDate", Sort.Direction.ASC))));

        OrderSpecifier[] orderSpecifiers = tested.computeOrders(pageRequest);

        assertThat(orderSpecifiers).containsExactly(
                QUuidResource.uuidResource.uuid.desc(),
                QUuidResource.uuidResource.creationDate.asc());
    }
}