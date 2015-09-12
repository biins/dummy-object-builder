package org.biins.objectbuilder.types.primitive;

import org.biins.objectbuilder.ConstantPool;

/**
 * @author Martin Janys
 */
public class IntType extends PrimitiveType<Integer> {

    public IntType() {
        super(int.class, ConstantPool.INT_DEFAULT, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public Integer getRandomValue() {
        return random.nextInt();
    }
}
