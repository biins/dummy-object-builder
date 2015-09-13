package org.biins.objectbuilder.builder;

import org.biins.objectbuilder.builder.strategy.ArrayGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.PrimitiveGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.WrapperGeneratorStrategy;
import org.biins.objectbuilder.types.array.ArrayType;
import org.biins.objectbuilder.types.array.ArrayTypeRegistry;
import org.biins.objectbuilder.util.ClassUtils;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author Martin Janys
 */
@SuppressWarnings("unchecked")
public class ArrayObjectBuilder<T> extends AbstractBuilder<T> implements Builder<T> {

    protected ArrayGeneratorStrategy arrayStrategy = ArrayGeneratorStrategy.DEFAULT;
    protected PrimitiveGeneratorStrategy primitiveStrategy = PrimitiveGeneratorStrategy.DEFAULT;
    protected WrapperGeneratorStrategy wrapperStrategy = WrapperGeneratorStrategy.DEFAULT;
    protected int[] size = new int[]{0};

    protected ArrayObjectBuilder(Class<T> cls) {
        super(cls);
    }

    static <T> ArrayObjectBuilder<T> forType(Class<T> cls) {
        return new ArrayObjectBuilder<T>(cls);
    }

    public ArrayObjectBuilder<T> setArrayStrategy(ArrayGeneratorStrategy arrayStrategy) {
        this.arrayStrategy = arrayStrategy;
        return this;
    }

    public ArrayObjectBuilder<T> setArrayStrategy(PrimitiveGeneratorStrategy primitiveStrategy) {
        this.primitiveStrategy = primitiveStrategy;
        return this;
    }

    public ArrayObjectBuilder<T> setArrayStrategy(WrapperGeneratorStrategy wrapperStrategy) {
        this.wrapperStrategy = wrapperStrategy;
        return this;
    }

    public ArrayObjectBuilder<T> setArrayStrategy(ArrayGeneratorStrategy arrayStrategy, PrimitiveGeneratorStrategy primitiveStrategy, WrapperGeneratorStrategy wrapperStrategy) {
        setArrayStrategy(arrayStrategy);
        setArrayStrategy(primitiveStrategy);
        setArrayStrategy(wrapperStrategy);
        return this;
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
        int arraySize = sizes.length > 0 ? sizes[0] : 0;
        int subArraySize = sizes.length > 1 ? sizes[1] : 0;

        Class<?> componentType = arrayType.getComponentType();
        Object array = createArray(componentType, arraySize);

        if (ClassUtils.isArray(componentType)) {
            for (int i = 0; i < arraySize; i++) {
                Object subArray = createArray(componentType.getComponentType(), subArraySize);
                if (ClassUtils.isArray(componentType)) {
                    subArray = fillArrayWithArray(subArray, decreaseDimension(sizes));
                }
                else {
                    subArray = fillArrayWithValues(subArray, componentType, subArraySize);
                }

                Array.set(array, i, subArray);
            }
        }
        else {
            if (ClassUtils.isArray(arrayType.getComponentType())) {
                // todo: unused ?
                array = fillArrayWithArray(array, decreaseDimension(sizes));
            }
            else {
                array = fillArrayWithValues(array, arrayType.getComponentType(), arraySize);
            }
        }

        return (T) array;
    }

    private Object fillArrayWithValues(Object array, Class<?> componentType, int index) {
        for (int i = 0; i < index; i++) {
            Object value =  objectForType(componentType);
            Array.set(array, i, value);
        }

        return array;

    }

    private Object fillArrayWithArray(Object array, int ... sizes) {
        int maxIndex = sizes.length > 0 ? sizes[0] : 0;
        for (int i = 0; i < maxIndex; i++) {
            Class<?> componentType = array.getClass().getComponentType();
            Object value;
            if (ClassUtils.isArray(componentType)) {
                value = buildArray(new ArrayType(componentType), decreaseDimension(sizes));
            }
            else {
                value = objectForType(componentType);
            }
            Array.set(array, i, value);
        }

        return array;
    }

    private Object objectForType(Class<?> type) {
        return ObjectBuilder.forType(type)
                .onPrimitiveProperty().setPrimitiveStrategy(primitiveStrategy)
                .onWrapperProperty().setWrapperStrategy(wrapperStrategy)
                .build();
    }

    private int[] decreaseDimension(int[] size) {
        return size.length > 1 ? Arrays.copyOfRange(size, 1, size.length) : new int[]{0};
    }

    private Object createArray(Class<?> type, int size) {
        return Array.newInstance(type, size);
    }
}
