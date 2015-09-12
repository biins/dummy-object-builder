package org.biins.objectbuilder.types.wrapper;

import org.biins.objectbuilder.ConstantPool;

/**
 * @author Martin Janys
 */
public class FloatWrapperType extends WrapperType<Float> {

    public FloatWrapperType() {
        super(Float.class, ConstantPool.FLOAT_WRAPPER_DEFAULT, Float.MIN_VALUE, Float.MAX_VALUE);
    }

    @Override
    public Float getRandomValue() {
        return random.nextFloat();
    }
}
