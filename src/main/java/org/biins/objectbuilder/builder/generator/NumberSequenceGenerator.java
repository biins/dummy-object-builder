package org.biins.objectbuilder.builder.generator;

/**
 * @author Martin Janys
 */
public class NumberSequenceGenerator implements Generator<Number> {

    private final long min;
    private final long max;
    private long number;

    public NumberSequenceGenerator() {
        this(0, Integer.MAX_VALUE);
    }

    public NumberSequenceGenerator(long max) {
        this(0, max);
    }

    public NumberSequenceGenerator(Number min, Number max) {
        this.min = min.longValue();
        this.max = max.longValue();
        this.number = min.longValue();
    }

    @Override
    public boolean isCyclic() {
        return true;
    }

    @Override
    public void reset() {
        number = min;
    }

    @Override
    public boolean hasNext() {
        return number < max;
    }

    @Override
    public Number next() {
        if (number < max) {
            return number++;
        }
        else {
            throw new IllegalStateException();
        }
    }
}
