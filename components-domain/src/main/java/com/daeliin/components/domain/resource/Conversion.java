package com.daeliin.components.domain.resource;

public interface Conversion<O, C> {

    O instantiate(C conversion);

    C map(O object);
}
