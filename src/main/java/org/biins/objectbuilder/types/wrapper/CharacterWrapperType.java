package org.biins.objectbuilder.types.wrapper;

import org.biins.objectbuilder.ConstantPool;

/**
 * @author Martin Janys
 */
public class CharacterWrapperType extends WrapperType<Character> {

    public CharacterWrapperType() {
        super(Character.class, ConstantPool.CHARACTER_WRAPPER_DEFAULT, Character.MIN_VALUE, Character.MAX_VALUE);
    }

    @Override
    public Character getRandomValue() {
        return (char) random.nextInt();
    }
}
