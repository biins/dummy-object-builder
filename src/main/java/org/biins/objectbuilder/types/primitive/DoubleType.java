package org.biins.objectbuilder.types.primitive;

import org.biins.objectbuilder.ConstantPool;

/**
 * @author Martin Janys
 */
public class DoubleType extends PrimitiveType<Double> {

    public DoubleType() {
        super(double.class, ConstantPool.DOUBLE_DEFAULT, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    @Override
    public Double getRandomValue() {
        return random.nextDouble();
    }
}
