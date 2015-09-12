package org.biins.objectbuilder.types.primitive;

import org.biins.objectbuilder.types.Type;

import java.util.Random;

/**
 * @author Martin Janys
 */
public abstract class PrimitiveType<T> extends Type<T> {

    private final T minValue;
    private final T maxValue;

    protected final Random random = new Random(System.currentTimeMillis());

    public PrimitiveType(Class<T> cls, T defaultValue, T minValue, T maxValue) {
        super(cls, defaultValue);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public abstract T getRandomValue();

    public T getMinValue() {
        return minValue;
    }

    public T getMaxValue() {
        return maxValue;
    }

}
