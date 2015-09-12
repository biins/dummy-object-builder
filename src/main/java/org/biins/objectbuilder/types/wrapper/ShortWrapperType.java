package org.biins.objectbuilder.types.wrapper;

import org.biins.objectbuilder.ConstantPool;

/**
 * @author Martin Janys
 */
public class ShortWrapperType extends WrapperType<Short> {

    public ShortWrapperType() {
        super(Short.class, ConstantPool.SHORT_WRAPPER_DEFAULT, Short.MIN_VALUE, Short.MAX_VALUE);
    }

    @Override
    public Short getRandomValue() {
        return (short) random.nextInt();
    }
}
