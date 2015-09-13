package org.biins.objectbuilder.types.string;

/**
 * @author Martin Janys
 */
public class StringTypeRegistry {

    @SuppressWarnings("unchecked")
    public static <T> StringType get() {
        return new StringType();
    }
}
