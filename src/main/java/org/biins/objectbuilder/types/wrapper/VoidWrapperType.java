package org.biins.objectbuilder.types.wrapper;

/**
 * @author Martin Janys
 */
public class VoidWrapperType extends WrapperType<Void> {

    public VoidWrapperType() {
        super(Void.class, null, null, null);
    }

    @Override
    public Void getRandomValue() {
        return null;
    }
}
