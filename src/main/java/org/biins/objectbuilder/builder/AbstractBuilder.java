package org.biins.objectbuilder.builder;

/**
 * @author Martin Janys
 */
public abstract class AbstractBuilder<T> implements Builder<T> {

    protected final Class<T> cls;

    protected AbstractBuilder(Class<T> cls) {
        this.cls = cls;
    }

}
