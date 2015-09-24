package org.biins.objectbuilder.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin Janys
 */
public abstract class AbstractBuilder implements Builder {

    @Override
    public <T> List<T> build(Class<T> type, int count) {
        List<T> list =  new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(build(type));
        }
        return list;
    }


}
