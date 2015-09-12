package org.biins.objectbuilder.types.wrapper;

import org.biins.objectbuilder.types.Type;

/**
 * @author Martin Janys
 */
public abstract class WrapperType<T> extends Type<T> {

    private final T minValue;
    private final T maxValue;

    public WrapperType(Class<T> cls, T defaultValue, T minValue, T maxValue) {
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
