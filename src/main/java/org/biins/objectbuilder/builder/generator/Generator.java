package org.biins.objectbuilder.builder.generator;

/**
 * @author Martin Janys
 */
public interface Generator<T> {

    boolean isCyclic();
    void reset();
    boolean hasNext();
    T next();
}
