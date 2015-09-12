package org.biins.objectbuilder.types.primitive;

import org.apache.commons.lang.Validate;

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
            REGISTRY.put(primitiveType.getType(), primitiveType);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> PrimitiveType<T> get(Class<T> cls) {
        PrimitiveType<?> value = REGISTRY.get(cls);
        Validate.notNull(value, "Unknown type " + cls);
        return (PrimitiveType<T>) value;
    }

}
