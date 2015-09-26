package org.biins.objectbuilder.builder.generator;

/**
 * @author Martin Janys
 */
public class CyclicValuesGenerator<T> implements Generator<T> {

    private final T[] values;
    private int index = 0;

    public CyclicValuesGenerator(T... values) {
        this.values = values;
    }

    @Override
    public boolean isCyclic() {
        return true;
    }

    @Override
    public void reset() {
        index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < values.length;
    }

    @Override
    public T next() {
        if (index < values.length) {
            try {
                return values[index];
            }
            finally {
                index++;
            }
        }
        else {
            throw new IllegalStateException();
        }
    }
}
