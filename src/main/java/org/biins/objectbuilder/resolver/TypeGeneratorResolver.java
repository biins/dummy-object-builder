package org.biins.objectbuilder.resolver;

import org.biins.objectbuilder.builder.ObjectBuilder;

/**
 * @author Martin Janys
 */
public interface TypeGeneratorResolver<T> {
    boolean canResolve(Class<T> type);
    T resolve(Class<T> type, ObjectBuilder builder);
}
