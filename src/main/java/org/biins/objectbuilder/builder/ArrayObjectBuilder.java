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
                if (ClassUtils.isPrimitive(componentType)) {
                    subArray = fillPrimitiveArray(subArray, componentType, subArraySize);
                }
                else if (ClassUtils.isWrapperClass(componentType)) {
                    subArray = fillWrapperArray(subArray, componentType, subArraySize);
                }
                else if (ClassUtils.isArray(componentType)) {
                    subArray = fillArrayArray(subArray, decreaseDimension(sizes));
                }
                else {
                    throw new IllegalStateException("Unknown array component type");
                }

                Array.set(array, i, subArray);
            }
        }
        else {
            if (ClassUtils.isPrimitive(arrayType.getComponentType())) {
                array = fillPrimitiveArray(array, componentType, arraySize);
            }
            else if (ClassUtils.isWrapperClass(arrayType.getComponentType())) {
                array = fillWrapperArray(array, arrayType.getComponentType(), arraySize);
            }
            else if (ClassUtils.isArray(arrayType.getComponentType())) {
                array = fillArrayArray(array, decreaseDimension(sizes));
            }
            else {
                throw new IllegalStateException("Unknown array component type");
            }
        }

        return (T) array;
    }

    private Object fillWrapperArray(Object array, Class<?> componentType, int index) {
        for (int i = 0; i < index; i++) {
            Object value =  WrapperObjectBuilder.forType(componentType).setWrapperStrategy(wrapperStrategy).build();
            Array.set(array, i, value);
        }

        return array;

    }

    private Object fillPrimitiveArray(Object array, Class<?> componentType, int index) {
        for (int i = 0; i < index; i++) {
            Object value = PrimitiveObjectBuilder.forType(componentType).setPrimitiveStrategy(primitiveStrategy).build();
            Array.set(array, i, value);
        }

        return array;
    }

    private Object fillArrayArray(Object array, int ... sizes) {
        int maxIndex = sizes.length > 0 ? sizes[0] : 0;
        for (int i = 0; i < maxIndex; i++) {
            Class<?> componentType = array.getClass().getComponentType();
            Object value = null;
            if (ClassUtils.isArray(componentType)) {
                value = buildArray(new ArrayType(componentType), decreaseDimension(sizes));
            }
            else {
                if (ClassUtils.isPrimitive(componentType)) {
                    value = PrimitiveObjectBuilder.forType(componentType).setPrimitiveStrategy(primitiveStrategy).build();
                }
                else if (ClassUtils.isWrapperClass(componentType)) {
                    value = WrapperObjectBuilder.forType(componentType).setWrapperStrategy(wrapperStrategy).build();
                }
            }
            Array.set(array, i, value);
        }

        return array;
    }

    private int[] decreaseDimension(int[] size) {
        return size.length > 1 ? Arrays.copyOfRange(size, 1, size.length) : new int[]{0};
    }

    private Object createArray(Class<?> type, int size) {
        return Array.newInstance(type, size);
    }
}
