package org.biins.objectbuilder.types;

/**
 * @author Martin Janys
 */
public class Types<TYPE> {

    private final Class<TYPE> type;

    private Types<?> next;

    private Types(Class<TYPE> type) {
        this.type = type;
    }

    public <T> Types<T> of(Class<T> type) {
        Types<T> next = new Types<>(type);
        this.next = next;
        return next;
    }

    public Class<TYPE> getType() {
        return type;
    }

    public Types next() {
        return next;
    }

    public static <T> Types<T> typeOf(Class<T> type) {
        return new Types<>(type);
    }

}
