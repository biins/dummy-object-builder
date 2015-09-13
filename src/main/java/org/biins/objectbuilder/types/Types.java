package org.biins.objectbuilder.types;

/**
 * @author Martin Janys
 */
public class Types<TYPE> {

    private final Class<TYPE> type;

    private Types<?> next;
    private Types<?> last;

    private Types(Class<TYPE> type) {
        this.type = type;
        this.last = this;
    }

    public Types<TYPE> of(Class<?> type) {
        Types<?> next = new Types<>(type);
        this.last.next = next;
        this.last = this.last.next;
        return this;
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
