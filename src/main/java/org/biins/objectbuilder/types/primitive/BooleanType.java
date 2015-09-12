package org.biins.objectbuilder.types.primitive;

import org.biins.objectbuilder.ConstantPool;

/**
 * @author Martin Janys
 */
public class BooleanType extends PrimitiveType<Boolean> {

    public BooleanType() {
        super(boolean.class, ConstantPool.BOOLEAN_DEFAULT, Boolean.FALSE, Boolean.TRUE);
    }

    @Override
    public Boolean getRandomValue() {
        return random.nextInt() % 2 == 0;
    }
}
