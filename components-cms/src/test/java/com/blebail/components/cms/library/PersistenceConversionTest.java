package com.blebail.components.cms.library;

import com.blebail.components.core.resource.Conversion;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class PersistenceConversionTest<O, C> {

    protected abstract Conversion<O,C> conversion();

    protected abstract O object();

    protected abstract C converted();

    @Test
    public void shouldThrowException_whenConvertingTogNull() {
        assertThrows(NullPointerException.class, () -> conversion().to((O) null));
    }

    @Test
    public void shouldConvertTo() {
        C converted = conversion().to(object());

        assertThat(converted).isEqualToComparingFieldByField(converted());
    }

    @Test
    public void shouldThrowException_whenConvertingFromNull() {
        assertThrows(NullPointerException.class, () -> conversion().from((C) null));
    }

    @Test
    public void shouldConvertFrom() {
        O object = conversion().from(converted());

        assertThat(object).isEqualTo(object());
    }
}
