package org.biins.objectbuilder.types.map;

import org.biins.objectbuilder.types.Type;

import java.lang.reflect.Array;

/**
 * @author Martin Janys
 */
public class MapType<T> extends Type<T> {

    private final Class<?> componentType;

    @SuppressWarnings("unchecked")
    public MapType(Class<T> cls) {
        super(cls, (T) (cls.getComponentType() != null ? Array.newInstance(cls.getComponentType(), 0) : null));
        this.componentType = cls.getComponentType();
    }

    public Class<?> getComponentType() {
        return componentType;
    }
}
