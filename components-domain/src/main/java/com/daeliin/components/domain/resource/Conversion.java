package com.daeliin.components.domain.resource;

/**
 * Conversion from type O to type C.
 */
public interface Conversion<O, C> {

    /**
     * Instantiate an object form a converted object.
     * @param conversion a converted object
     * @return the new instance
     */
    O instantiate(C conversion);

    /**
     * Maps an object to a converted object.
     * @param object the object to map
     * @return the converted object
     */
    C map(O object);
}
