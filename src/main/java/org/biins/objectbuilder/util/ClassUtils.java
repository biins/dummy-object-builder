package org.biins.objectbuilder.util;

import org.biins.objectbuilder.types.wrapper.WrapperTypeRegistry;

import java.util.Collection;

/**
 * @author Martin Janys
 */
public class ClassUtils {

    public static <T> boolean isPrimitive(Class<T> cls) {
        return cls.isPrimitive();
    }

    public static <T> boolean isWrapperClass(Class<T> cls) {
        return WrapperTypeRegistry.WRAPPER_CLASSES.contains(cls);
    }

    public static <T> boolean isArray(Class<T> cls) {
        return cls.isArray();
    }

    public static <T> boolean isString(Class<T> cls) {
        return String.class.isAssignableFrom(cls);
    }

    public static <T> boolean isCollection(Class<T> cls) {
        return Collection.class.isAssignableFrom(cls);
    }
}
