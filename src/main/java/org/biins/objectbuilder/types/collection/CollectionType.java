package org.biins.objectbuilder.types.collection;

import org.biins.objectbuilder.types.Type;

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * @author Martin Janys
 */
public class CollectionType<T> extends Type<T> {

    @SuppressWarnings("unchecked")
    public CollectionType(Class<T> cls) {
        super(cls, (T) CollectionTypeRegistry.getDefault((Class<? extends Collection>) cls));
    }

}
