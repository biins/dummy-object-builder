package org.biins.objectbuilder.types.primitive;

import org.biins.objectbuilder.types.Type;

/**
 * @author Martin Janys
 */
public abstract class PrimitiveType<T> extends Type<T> {

    private final T minValue;
    private final T maxValue;

    public PrimitiveType(Class<T> cls, T defaultValue, T minValue, T maxValue) {
        super(cls, defaultValue);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public T getMinValue() {
        return minValue;
    }

    public T getMaxValue() {
        return maxValue;
    }

}
