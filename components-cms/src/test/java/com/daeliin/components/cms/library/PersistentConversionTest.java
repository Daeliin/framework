package com.daeliin.components.cms.library;

import com.daeliin.components.core.resource.Conversion;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class PersistentConversionTest<O, C> {

    protected abstract Conversion<O,C> conversion();

    protected abstract O object();

    protected abstract C converted();

    @Test(expected = NullPointerException.class)
    public void shouldThrowException_whenConvertingTogNull() {
        O nullObject = null;

        conversion().to(nullObject);
    }

    @Test
    public void shouldConvertTo() {
        C converted = conversion().to(object());

        assertThat(converted).isEqualToComparingFieldByField(converted());
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowException_whenConvertingFromNull() {
        C nullObject = null;

        conversion().from(nullObject);
    }

    @Test
    public void shouldConvertFrom() {
        O object = conversion().from(converted());

        assertThat(object).isEqualTo(object());
    }
}
