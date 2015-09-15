package org.biins.objectbuilder.builder;

import java.util.List;

/**
 * @author Martin Janys
 */
public interface Builder {

    <T> T build(Class<T> type);

    <T> List<T> build(Class<T> type, int count);
    
}
