package org.biins.objectbuilder.util;

import org.biins.objectbuilder.types.wrapper.WrapperTypeRegistry;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.TypeVariable;
import java.util.*;

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

    public static <T> boolean isMap(Class<T> type) {
        return Map.class.isAssignableFrom(type);
    }

    public static <T> boolean isObject(Class<T> type) {
        return Object.class.equals(type);
    }

    public static boolean isComposite(Class<?> cls) {
        return isArray(cls) || isCollection(cls);
    }

    public static boolean isSameCompositeType(Class<?> type1, Class<?> type2) {
        return (isArray(type1) && isArray(type2)) ||
                (isCollection(type1) && isCollection(type2));
    }

    public static List<Field> getFields(Class<?> type) {
        if (type.getSuperclass() != null) {
            List<Field> list = new ArrayList<>();
            list.addAll(getFields(type.getSuperclass()));
            list.addAll(getInstanceFields(type.getDeclaredFields()));
            return list;
        }
        else if (Object.class.equals(type)) {
            return Collections.emptyList();
        }
        else {
            return Arrays.asList(type.getDeclaredFields());
        }
    }

    private static Collection<? extends Field> getInstanceFields(Field[] fields) {
        List<Field> fieldList = new ArrayList<>();
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers()) && !(field.getGenericType() instanceof TypeVariable)) {
                fieldList.add(field);
            }
        }
        return fieldList;
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

    @SuppressWarnings("unchecked")
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
            if (Modifier.isFinal(field.getModifiers())) {
                return;
            }
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
