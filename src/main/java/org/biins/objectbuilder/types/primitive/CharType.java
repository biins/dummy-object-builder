package org.biins.objectbuilder.types.primitive;

import org.biins.objectbuilder.ConstantPool;

/**
 * @author Martin Janys
 */
public class CharType extends PrimitiveType<Character> {

    public CharType() {
        super(char.class, ConstantPool.CHAR_DEFAULT, Character.MIN_VALUE, Character.MAX_VALUE);
    }

    @Override
    public Character getRandomValue() {
        return (char) random.nextInt();
    }
}
