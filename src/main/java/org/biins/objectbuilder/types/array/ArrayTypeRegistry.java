package org.biins.objectbuilder.types.array;

/**
 * @author Martin Janys
 */
public class ArrayTypeRegistry {

    @SuppressWarnings("unchecked")
    public static <T> ArrayType<T> get(Class<T> cls) {
        return new ArrayType<T>(cls);
    }
}
