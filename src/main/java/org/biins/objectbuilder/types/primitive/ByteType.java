package org.biins.objectbuilder.types.primitive;

import org.biins.objectbuilder.ConstantPool;

/**
 * @author Martin Janys
 */
public class ByteType extends PrimitiveType<Byte> {

    public ByteType() {
        super(byte.class, ConstantPool.BYTE_DEFAULT, Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    @Override
    public Byte getRandomValue() {
        return (byte) random.nextInt();
    }
}