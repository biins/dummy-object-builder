package org.biins.objectbuilder.types;

import java.util.Random;

/**
 * @author Martin Janys
 */
public abstract class Type<T> {

    private final Class<T> cls;
    private final T defaultValue;

    protected final Random random = new Random(System.currentTimeMillis());

    public Type(Class<T> cls, T defaultValue) {
        this.cls = cls;
        this.defaultValue = defaultValue;
    }

    public abstract T getRandomValue();

    public Class<T> getTypeClass() {
        return cls;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

}
