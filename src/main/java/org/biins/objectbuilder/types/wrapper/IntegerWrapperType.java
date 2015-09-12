package org.biins.objectbuilder.types.wrapper;

import org.biins.objectbuilder.ConstantPool;

/**
 * @author Martin Janys
 */
public class IntegerWrapperType extends WrapperType<Integer> {

    public IntegerWrapperType() {
        super(Integer.class, ConstantPool.INTEGER_WRAPPER_DEFAULT, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public Integer getRandomValue() {
        return random.nextInt();
    }
}
