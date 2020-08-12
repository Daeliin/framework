package com.blebail.components.persistence.resource;

import com.blebail.components.persistence.fake.UuidResource;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public final class PersistablesTest {

    @Test
    void shouldExtractIds() {
        Collection<UuidResource> resources = Set.of(
            new UuidResource("1"),
            new UuidResource("2"),
            new UuidResource("3")
        );

        assertThat(Persistables.ids(resources)).containsOnly("1", "2", "3");
    }
}