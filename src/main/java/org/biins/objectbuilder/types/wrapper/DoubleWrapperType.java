package org.biins.objectbuilder.types.wrapper;

import org.biins.objectbuilder.ConstantPool;

/**
 * @author Martin Janys
 */
public class DoubleWrapperType extends WrapperType<Double> {

    public DoubleWrapperType() {
        super(Double.class, ConstantPool.DOUBLE_WRAPPER_DEFAULT, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    @Override
    public Double getRandomValue() {
        return random.nextDouble();
    }
}
