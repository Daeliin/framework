package com.daeliin.components.webservices.validation;

import com.daeliin.components.core.pagination.Sort;
import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class PageRequestValidationTest {
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_whenIndexIsNotANumber() {
        new PageRequestValidation("notANumber", "10", "ASC", "id").validate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_whenIndexIsNotAnInteger() {
        new PageRequestValidation("10.5", "10", "ASC", "id").validate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_whenIndexIsNegative() {
        new PageRequestValidation("-2", "10", "ASC", "id").validate();
    }

    @Test
    public void shouldAssignIndex() {
        PageRequestValidation pageRequestValidation = new PageRequestValidation("1", "10", "ASC", "id");

        assertThat(pageRequestValidation.validate().index).isEqualTo(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_whenSizeIsNotANumber() {
        new PageRequestValidation("2", "notANumber", "ASC", "id").validate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_whenSizeIsNotAnInteger() {
        new PageRequestValidation("2", "10.5", "ASC", "id").validate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_whenSizeIsNegative() {
        new PageRequestValidation("2", "-2", "ASC", "id").validate();
    }

    @Test
    public void shouldAssignSize() {
        PageRequestValidation pageRequestValidation = new PageRequestValidation("1", "2", "ASC", "id");

        assertThat(pageRequestValidation.validate().size).isEqualTo(2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_whenDirectionIsNotAscNorDesc() {
        new PageRequestValidation("2", "-2", "notAscNotDesc", "id").validate();
    }

    @Test
    public void shouldComputeSorts() {
        Set<Sort> sorts = new PageRequestValidation("1", "2", "ASC", "id", "label").validate().sorts;

        assertThat(sorts).containsOnly(new Sort("id", Sort.Direction.ASC), new Sort("label", Sort.Direction.ASC));
    }
}
