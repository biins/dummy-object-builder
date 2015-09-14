package org.biins.objectbuilder.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin Janys
 */
public abstract class AbstractBuilder<T> implements Builder<T> {

    protected final Class<T> cls;

    protected AbstractBuilder(Class<T> cls) {
        this.cls = cls;
    }


    @Override
    public List<T> build(int count) {
        List<T> list =  new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(build());
        }
        return list;
    }
}
