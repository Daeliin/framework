package com.daeliin.components.webservices.validation;

import com.daeliin.components.core.pagination.Sort;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PageRequestValidationTest {
    
    @Test
    public void shouldThrowException_whenIndexIsNotANumber() {
        assertThrows(IllegalArgumentException.class, () -> new PageRequestValidation("notANumber", "10", "ASC", "id").validate());
    }

    @Test
    public void shouldThrowException_whenIndexIsNotAnInteger() {
        assertThrows(IllegalArgumentException.class, () -> new PageRequestValidation("10.5", "10", "ASC", "id").validate());
    }

    @Test
    public void shouldThrowException_whenIndexIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new PageRequestValidation("-2", "10", "ASC", "id").validate());
    }

    @Test
    public void shouldAssignIndex() {
        PageRequestValidation pageRequestValidation = new PageRequestValidation("1", "10", "ASC", "id");

        assertThat(pageRequestValidation.validate().index).isEqualTo(1);
    }

    @Test
    public void shouldThrowException_whenSizeIsNotANumber() {
        assertThrows(IllegalArgumentException.class, () -> new PageRequestValidation("2", "notANumber", "ASC", "id").validate());
    }

    @Test
    public void shouldThrowException_whenSizeIsNotAnInteger() {
        assertThrows(IllegalArgumentException.class, () -> new PageRequestValidation("2", "10.5", "ASC", "id").validate());
    }

    @Test
    public void shouldThrowException_whenSizeIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new PageRequestValidation("2", "-2", "ASC", "id").validate());
    }

    @Test
    public void shouldAssignSize() {
        PageRequestValidation pageRequestValidation = new PageRequestValidation("1", "2", "ASC", "id");

        assertThat(pageRequestValidation.validate().size).isEqualTo(2);
    }

    @Test
    public void shouldThrowException_whenDirectionIsNotAscNorDesc() {
        assertThrows(IllegalArgumentException.class, () -> new PageRequestValidation("2", "-2", "notAscNotDesc", "id").validate());
    }

    @Test
    public void shouldComputeSorts() {
        Set<Sort> sorts = new PageRequestValidation("1", "2", "ASC", "id", "label").validate().sorts;

        assertThat(sorts).containsOnly(new Sort("id", Sort.Direction.ASC), new Sort("label", Sort.Direction.ASC));
    }
}
