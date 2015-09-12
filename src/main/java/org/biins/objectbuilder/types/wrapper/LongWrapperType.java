package org.biins.objectbuilder.types.wrapper;

import org.biins.objectbuilder.ConstantPool;

/**
 * @author Martin Janys
 */
public class LongWrapperType extends WrapperType<Long> {

    public LongWrapperType() {
        super(Long.class, ConstantPool.LONG_WRAPPER_DEFAULT, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    @Override
    public Long getRandomValue() {
        return random.nextLong();
    }
}
