package org.biins.objectbuilder.types;

import java.lang.reflect.*;
import java.lang.reflect.Type;

/**
 * @author Martin Janys
 */
public class Types<TYPE> {

    private final Class<TYPE> type;

    private Types<?> next;
    private Types<?> last;

    private Types(Class<TYPE> type) {
        this.type = type;
        this.last = this;
    }

    public Types<TYPE> of(Class<?> type) {
        Types<?> next = new Types<>(type);
        this.last.next = next;
        this.last = this.last.next;
        return this;
    }

    public Class<TYPE> getType() {
        return type;
    }

    public Types next() {
        return next;
    }

    public static Types<?> typeOf(Class<?> type) {
        return new Types<>(type);
    }

    public static Types<?> typeForCollection(Type genericType) {
        if (genericType instanceof ParameterizedType) {
            return typeForCollection((ParameterizedType) genericType);
        }
        else {
            return null;
        }
    }

    public static Types<?> typeForCollection(ParameterizedType parameterizedType) {
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (actualTypeArguments.length > 0) {
            Types<?> types = Types.typeOf(resolveClass(actualTypeArguments[0]));
            types = typeForCollection(actualTypeArguments[0], types);
            return types;
        }

        return null;
    }

    private static Types<?> typeForCollection(Type genericType, Types<?> types) {
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length > 0) {
                types.of(resolveClass(actualTypeArguments[0]));
                types = typeForCollection(actualTypeArguments[0], types);
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
