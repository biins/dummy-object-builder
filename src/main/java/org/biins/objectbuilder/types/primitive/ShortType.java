package org.biins.objectbuilder.types.primitive;

import org.biins.objectbuilder.ConstantPool;

/**
 * @author Martin Janys
 */
public class ShortType extends PrimitiveType<Short> {

    public ShortType() {
        super(short.class, ConstantPool.SHORT_DEFAULT, Short.MIN_VALUE, Short.MAX_VALUE);
    }

    @Override
    public Short getRandomValue() {
        return (short) random.nextInt();
    }
}
