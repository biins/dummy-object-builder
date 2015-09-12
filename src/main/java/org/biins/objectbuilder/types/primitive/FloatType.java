package org.biins.objectbuilder.types.primitive;

import org.biins.objectbuilder.ConstantPool;

/**
 * @author Martin Janys
 */
public class FloatType extends PrimitiveType<Float> {

    public FloatType() {
        super(float.class, ConstantPool.FLOAT_DEFAULT, Float.MIN_VALUE, Float.MAX_VALUE);
    }

    @Override
    public Float getRandomValue() {
        return random.nextFloat();
    }
}
