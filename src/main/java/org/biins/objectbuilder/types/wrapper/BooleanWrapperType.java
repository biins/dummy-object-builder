package org.biins.objectbuilder.types.wrapper;

import org.biins.objectbuilder.ConstantPool;

/**
 * @author Martin Janys
 */
public class BooleanWrapperType extends WrapperType<Boolean> {

    public BooleanWrapperType() {
        super(Boolean.class, ConstantPool.BOOLEAN_WRAPPER_DEFAULT, Boolean.FALSE, Boolean.TRUE);
    }

    @Override
    public Boolean getRandomValue() {
        return random.nextInt() % 2 == 0;
    }
}
