package org.biins.objectbuilder.types.map;

import org.biins.objectbuilder.types.Type;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Map;

/**
 * @author Martin Janys
 */
public class MapType<T extends Map> extends Type<T> {

    @SuppressWarnings("unchecked")
    public MapType(Class<T> cls) {
        super(cls, (T) MapTypeRegistry.getDefault(cls));
    }

}
