package com.daeliin.components.webservices.validation;

import com.daeliin.components.core.pagination.Sort;
import com.daeliin.components.webservices.exception.PageRequestException;
import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class PageRequestValidationTest {
    
    @Test(expected = PageRequestException.class)
    public void shouldThrowException_whenIndexIsNotANumber() throws Exception {
        new PageRequestValidation("notANumber", "10", "ASC", "id");
    }

    @Test(expected = PageRequestException.class)
    public void shouldThrowException_whenIndexIsNotAnInteger() throws Exception {
        new PageRequestValidation("10.5", "10", "ASC", "id");
    }

    @Test(expected = PageRequestException.class)
    public void shouldThrowException_whenIndexIsNegative() throws Exception {
        new PageRequestValidation("-2", "10", "ASC", "id");
    }

    @Test
    public void shouldAssignIndex() throws Exception {
        PageRequestValidation pageRequestValidation = new PageRequestValidation("1", "10", "ASC", "id");

        assertThat(pageRequestValidation.index).isEqualTo(1);
    }

    @Test(expected = PageRequestException.class)
    public void shouldThrowException_whenSizeIsNotANumber() throws Exception {
        new PageRequestValidation("2", "notANumber", "ASC", "id");
    }

    @Test(expected = PageRequestException.class)
    public void shouldThrowException_whenSizeIsNotAnInteger() throws Exception {
        new PageRequestValidation("2", "10.5", "ASC", "id");
    }

    @Test(expected = PageRequestException.class)
    public void shouldThrowException_whenSizeIsNegative() throws Exception {
        new PageRequestValidation("2", "-2", "ASC", "id");
    }

    @Test
    public void shouldAssignSize() throws Exception {
        PageRequestValidation pageRequestValidation = new PageRequestValidation("1", "2", "ASC", "id");

        assertThat(pageRequestValidation.size).isEqualTo(2);
    }

    @Test(expected = PageRequestException.class)
    public void shouldThrowException_whenDirectionIsNotAscNorDesc() throws Exception {
        new PageRequestValidation("2", "-2", "notAscNotDesc", "id");
    }

    @Test
    public void shouldComputeSorts() throws Exception {
        Set<Sort> sorts = new PageRequestValidation("1", "2", "ASC", "id", "label").sorts;

        assertThat(sorts).containsOnly(new Sort("id", Sort.Direction.ASC), new Sort("label", Sort.Direction.ASC));
    }
}
