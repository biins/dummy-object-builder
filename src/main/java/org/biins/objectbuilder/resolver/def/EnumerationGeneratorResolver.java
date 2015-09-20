package org.biins.objectbuilder.resolver.def;

import org.biins.objectbuilder.builder.ObjectBuilder;
import org.biins.objectbuilder.resolver.TypeGeneratorResolver;

import java.util.Collections;
import java.util.Enumeration;

/**
 * @author Martin Janys
 */
public class EnumerationGeneratorResolver implements TypeGeneratorResolver<Enumeration> {

    @Override
    public boolean canResolve(Class<Enumeration> type) {
        return Enumeration.class.equals(type);
    }

    @Override
    public Enumeration resolve(Class<Enumeration> type, ObjectBuilder builder) {
        return Collections.emptyEnumeration();
    }
}
