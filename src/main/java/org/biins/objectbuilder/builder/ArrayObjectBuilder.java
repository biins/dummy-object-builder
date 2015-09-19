package org.biins.objectbuilder.builder;

import org.biins.objectbuilder.builder.strategy.ArrayGeneratorStrategy;
import org.biins.objectbuilder.types.Types;
import org.biins.objectbuilder.types.array.ArrayType;
import org.biins.objectbuilder.types.array.ArrayTypeRegistry;
import org.biins.objectbuilder.util.ClassUtils;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author Martin Janys
 */
@SuppressWarnings("unchecked")
public class ArrayObjectBuilder extends AbstractCompositeBuilder implements Builder {

    private ArrayGeneratorStrategy arrayStrategy;
    private int[] size = new int[]{0};

    public ArrayObjectBuilder(ObjectBuilder objectBuilder) {
        super(objectBuilder);
        array = true;
    }

    public ArrayObjectBuilder setSize(int ... size) {
        this.size = size;
        validateSize();
        return this;
    }

    public ArrayObjectBuilder setGeneratorStrategy(ArrayGeneratorStrategy arrayStrategy) {
        this.arrayStrategy = arrayStrategy;
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
    public <T> T build(Class<T> type) {
        return buildArray(type);
    }

    public <T> T buildArray(Class<T> type) {
        return buildArray(type, size);
    }

    public <T> T buildArray(Class<T> type, int ... size) {
        ArrayType<T> arrayType = ArrayTypeRegistry.get(type);
        return buildArray(arrayType, size);
    }

    public <T> T buildArray(ArrayType<T> arrayType, int ... size) {
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

    private <T> T buildArrayInternal(ArrayType<T> arrayType, int ... size) {
        int arraySize = countSize(size, 0);

        Class<?> componentType = arrayType.getComponentType();
        Object array = createArray(componentType, arraySize);
        array = fillArray(array, componentType, size);

        return (T) array;
    }

    private Object fillArray(Object array, Class<?> componentType, int ... size) {
        int maxIndex = countSize(size, 0);
        for (int i = 0; i < maxIndex; i++) {
            Object value;
            if (ClassUtils.isSameCompositeType(array.getClass(), componentType)) {
                value = createCompositeObject(Types.typeOf(componentType), decreaseDimension(size));
            }
            else {
                value = createCompositeObject(Types.typeOf(componentType));
            }
            Array.set(array, i, value);
        }

        return array;
    }

    private int countSize(int[] sizes, int at) {
        return sizes.length > at ? sizes[at] : 0;
    }

    private Object createArray(Class<?> type, int size) {
        return Array.newInstance(type, size);
    }
}
