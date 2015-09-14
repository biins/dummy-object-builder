package org.biins.objectbuilder.builder;

import java.util.List;

/**
 * @author Martin Janys
 */
public interface Builder<T> {

    T build();

    List<T> build(int count);
    
}
