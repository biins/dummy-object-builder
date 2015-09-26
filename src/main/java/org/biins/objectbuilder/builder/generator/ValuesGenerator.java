package org.biins.objectbuilder.builder.generator;

/**
 * @author Martin Janys
 */
public class ValuesGenerator<T> implements Generator<T> {

    private final T[] values;
    private int index = 0;

    public ValuesGenerator(T ... values) {
        this.values = values;
    }

    @Override
    public boolean isCyclic() {
        return false;
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
