package org.biins.objectbuilder.types;

/**
 * @author Martin Janys
 */
public abstract class Type<T> {

    private final Class<T> type;
    private final T defaultValue;

    public Type(Class<T> type, T defaultValue) {
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public Class<T> getType() {
        return type;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

}
