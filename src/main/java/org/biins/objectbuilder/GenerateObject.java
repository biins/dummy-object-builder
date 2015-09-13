package org.biins.objectbuilder;

import org.biins.objectbuilder.builder.ObjectBuilder;

/**
 * @author Martin Janys
 */
public class GenerateObject {

    public static <T> ObjectBuilder<T> forType(Class<T> cls) {
        return ObjectBuilder.forType(cls);
    }
}
