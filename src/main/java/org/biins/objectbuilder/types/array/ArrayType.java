package org.biins.objectbuilder.types.array;

import org.biins.objectbuilder.types.Type;

import java.lang.reflect.Array;

/**
 * @author Martin Janys
 */
public class ArrayType<T> extends Type<T> {

    private final Class<?> componentType;

    @SuppressWarnings("unchecked")
    public ArrayType(Class<T> cls) {
        super(cls, (T) (cls.getComponentType() != null ? Array.newInstance(cls.getComponentType(), 0) : null)); // todo default by type - not null
        this.componentType = cls.getComponentType();
    }

    public Class<?> getComponentType() {
        return componentType;
    }
}
