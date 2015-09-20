package org.biins.objectbuilder.resolver.def;

import org.biins.objectbuilder.builder.ObjectBuilder;
import org.biins.objectbuilder.resolver.TypeGeneratorResolver;

import java.util.Collections;
import java.util.Iterator;

/**
 * @author Martin Janys
 */
public class IteratorGeneratorResolver implements TypeGeneratorResolver<Iterator> {

    @Override
    public boolean canResolve(Class<Iterator> type) {
        return Iterator.class.equals(type);
    }

    @Override
    public Iterator resolve(Class<Iterator> type, ObjectBuilder builder) {
        return Collections.emptyIterator();
    }
}
