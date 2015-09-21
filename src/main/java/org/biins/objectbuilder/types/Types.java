package org.biins.objectbuilder.types;

import java.lang.reflect.*;
import java.lang.reflect.Type;

/**
 * @author Martin Janys
 */
public class Types {

    private final Class<?> type;

    private Types next;
    private Types last;

    private Types(Class<?> type) {
        this.type = type;
        this.last = this;
    }

    public Types of(Class<?> type) {
        this.last.next = new Types(type);
        this.last = this.last.next;
        return this;
    }

    public Class<?> getType() {
        return type;
    }

    public Types next() {
        return next;
    }

    public static Types typeOf(Class<?> type) {
        return new Types(type);
    }

    public static Types typeOf(Type genericType) {
        if (genericType instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) genericType;
            Types types = typeOf(type.getRawType());
            return typeOf(type, types);
        }
        else if (genericType instanceof Class) {
            return typeOf((Class)genericType);
        }
        else {
            return null;
        }
    }

    public static Types typeOf(ParameterizedType parameterizedType) {
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (actualTypeArguments.length > 0) {
            Types types = Types.typeOf(resolveClass(actualTypeArguments[0]));
            types = typeOf(actualTypeArguments[0], types);
            return types;
        }

        return null;
    }

    private static Types typeOf(Type genericType, Types types) {
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length > 0) {
                types.of(resolveClass(actualTypeArguments[0]));
                types = typeOf(actualTypeArguments[0], types);
                return types;
            }
        }
        return types;
    }

    private static Class resolveClass(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        else if (type instanceof ParameterizedType) {
            return (Class) ((ParameterizedType)type).getRawType();
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}
