package org.biins.objectbuilder.types.primitive;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Martin Janys
 */
public class PrimitiveTypeRegistry {

    private static final Map<Class<?>, PrimitiveType<?>> REGISTRY = new HashMap<Class<?>, PrimitiveType<?>>();
    private static final PrimitiveType<?>[] PRIMITIVE_TYPES = new PrimitiveType[]{
            new BooleanType(),
            new ByteType(),
            new CharType(),
            new DoubleType(),
            new FloatType(),
            new IntType(),
            new LongType(),
            new ShortType()
    };

    static {
        for (PrimitiveType<?> primitiveType : PRIMITIVE_TYPES) {
            REGISTRY.put(primitiveType.getTypeClass(), primitiveType);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> PrimitiveType<T> get(Class<T> cls) {
        return (PrimitiveType<T>) REGISTRY.get(cls);
    }

}
