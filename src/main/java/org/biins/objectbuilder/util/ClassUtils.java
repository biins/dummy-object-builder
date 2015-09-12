package org.biins.objectbuilder.util;

import org.biins.objectbuilder.types.wrapper.WrapperTypeRegistry;

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

}
