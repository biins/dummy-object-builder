package org.biins.objectbuilder.resolver.def;

import org.biins.objectbuilder.builder.ObjectBuilder;
import org.biins.objectbuilder.resolver.TypeGeneratorResolver;

import java.util.Collections;

/**
 * @author Martin Janys
 */
public class IterableGeneratorResolver implements TypeGeneratorResolver<Iterable> {

    @Override
    public boolean canResolve(Class<Iterable> type) {
        return Iterable.class.equals(type);
    }

    @Override
    public Iterable resolve(Class<Iterable> type, ObjectBuilder builder) {
        return Collections.emptyList();
    }
}
