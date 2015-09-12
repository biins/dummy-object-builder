package org.biins.objectbuilder.types.primitive;

import org.biins.objectbuilder.ConstantPool;

/**
 * @author Martin Janys
 */
public class LongType extends PrimitiveType<Long> {

    public LongType() {
        super(long.class, ConstantPool.LONG_DEFAULT, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    @Override
    public Long getRandomValue() {
        return random.nextLong();
    }
}
