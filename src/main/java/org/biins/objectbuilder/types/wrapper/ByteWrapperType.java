package org.biins.objectbuilder.types.wrapper;

import org.biins.objectbuilder.ConstantPool;

/**
 * @author Martin Janys
 */
public class ByteWrapperType extends WrapperType<Byte> {

    public ByteWrapperType() {
        super(Byte.class, ConstantPool.BYTE_WRAPPER_DEFAULT, Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    @Override
    public Byte getRandomValue() {
        return (byte) random.nextInt();
    }
}