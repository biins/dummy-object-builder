package org.biins.objectbuilder.builder;

import org.biins.objectbuilder.types.array.ArrayType;
import org.biins.objectbuilder.types.array.ArrayTypeRegistry;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author Martin Janys
 */
@SuppressWarnings("unchecked")
public class ArrayObjectBuilder<T> extends AbstractCompositeBuilder<T, ArrayObjectBuilder> implements Builder<T> {

    protected int[] size = new int[]{0};

    protected ArrayObjectBuilder(Class<T> cls) {
        super(cls);
    }

    static <T> ArrayObjectBuilder<T> forType(Class<T> cls) {
        return new ArrayObjectBuilder<T>(cls);
    }

    public ArrayObjectBuilder<T> setSize(int size) {
        this.size = new int[]{size};
        validateSize();
        return this;
    }

    public ArrayObjectBuilder<T> setSize(int ... size) {
        this.size = size;
        validateSize();
        return this;
    }

    private void validateSize() {
        for (int s : size) {
            if (s < 0) {
                throw new IllegalArgumentException("Size must be positive");
            }
        }
    }

    @Override
    public T build() {
        return buildArray();
    }

    public T buildArray() {
        ArrayType<T> arrayType = ArrayTypeRegistry.get(cls);
        return buildArray(arrayType, size);
    }

    private T buildArray(ArrayType<T> arrayType, int ... size) {
        switch (arrayStrategy) {
            case NULL:
                return null;
            case VALUE:
                return buildArrayInternal(arrayType, size);
            case DEFAULT:
            default:
                return arrayType.getDefaultValue();
        }
    }

    private T buildArrayInternal(ArrayType<T> arrayType, int ... sizes) {
        int arraySize = countSize(sizes);

        Class<?> componentType = arrayType.getComponentType();
        Object array = createArray(componentType, arraySize);
        array = fillArray(array, componentType, sizes);

        return (T) array;
    }

    private Object fillArray(Object array, Class<?> componentType, int ... sizes) {
        int maxIndex = countSize(sizes);
        for (int i = 0; i < maxIndex; i++) {
            Object value = createObject(componentType, sizes);
            Array.set(array, i, value);
        }

        return array;
    }

    private int countSize(int[] sizes) {
        return sizes.length > 0 ? sizes[0] : 0;
    }

    private Object createArray(Class<?> type, int size) {
        return Array.newInstance(type, size);
    }
}
