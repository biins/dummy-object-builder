package org.biins.objectbuilder.util;

import org.apache.commons.beanutils.BeanUtils;
import org.biins.objectbuilder.types.wrapper.WrapperTypeRegistry;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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

    public static <T> boolean isEnum(Class<T> type) {
        return type.isEnum();
    }

    public static boolean isComposite(Class<?> cls) {
        return isArray(cls) || isCollection(cls);
    }

    public static boolean isSameCompositeType(Class<?> type1, Class<?> type2) {
        return (isArray(type1) && isArray(type2)) ||
                (isCollection(type1) && isCollection(type2));
    }

    public static List<Field> getFields(Class<?> type) {
        if (!Object.class.equals(type.getSuperclass())) {
            List<Field> list = new ArrayList<>();
            list.addAll(getFields(type.getSuperclass()));
            list.addAll(Arrays.asList(type.getDeclaredFields()));
            return list;
        }
        else {
            return Arrays.asList(type.getDeclaredFields());
        }
    }

    public static <T> T newInstance(Class<T> type) {
        try {
            Constructor<T> constructor = type.getDeclaredConstructor();
            if (type.getEnclosingClass() != null && constructor == null) {
                // inner, no static class
                throw new IllegalArgumentException("Inner class is not supported");
                // Object enclosing = newInstance(type.getEnclosingClass());
                // Constructor<T> constructor = type.getConstructor(enclosing.getClass());
                // return constructor.newInstance(enclosing);

            }
            else {
                if (!constructor.isAccessible()) {
                    constructor.setAccessible(true);
                }
                return constructor.newInstance();
            }
        }
        catch (ReflectiveOperationException e) {
            throw  new RuntimeException(e);
        }
    }

    public static <T> T newInstance(Class<T> type, Constructor<?> constructor, Object ... parameters) {
        try {
            return (T) constructor.newInstance(parameters);
        }
        catch (ReflectiveOperationException e) {
            return null;
        }
    }

    public static void setProperty(Object o, Field field, Object fieldValue) {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            field.set(o, fieldValue);
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
