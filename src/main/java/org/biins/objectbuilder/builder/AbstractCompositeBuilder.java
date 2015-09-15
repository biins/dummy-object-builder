package org.biins.objectbuilder.builder;

import org.biins.objectbuilder.builder.strategy.*;
import org.biins.objectbuilder.util.ClassUtils;

import java.util.Arrays;

/**
 * @author Martin Janys
 */
@SuppressWarnings("unchecked")
public abstract class AbstractCompositeBuilder<T, BUILDER> extends AbstractBuilder<T> {

    protected PrimitiveGeneratorStrategy primitiveStrategy = PrimitiveGeneratorStrategy.DEFAULT;
    protected WrapperGeneratorStrategy wrapperStrategy = WrapperGeneratorStrategy.DEFAULT;
    protected StringGeneratorStrategy stringGeneratorStrategy = StringGeneratorStrategy.DEFAULT;
    protected CollectionGeneratorStrategy collectionGeneratorStrategy = CollectionGeneratorStrategy.DEFAULT;
    protected ArrayGeneratorStrategy arrayStrategy = ArrayGeneratorStrategy.DEFAULT;

    protected AbstractCompositeBuilder(Class<T> cls) {
        super(cls);
    }

    public BUILDER setGeneratorStrategy(CollectionGeneratorStrategy collectionGeneratorStrategy) {
        this.collectionGeneratorStrategy = collectionGeneratorStrategy;
        return (BUILDER) this;
    }

    public BUILDER setGeneratorStrategy(ArrayGeneratorStrategy arrayStrategy) {
        this.arrayStrategy = arrayStrategy;
        return (BUILDER) this;
    }

    public BUILDER setGeneratorStrategy(PrimitiveGeneratorStrategy primitiveStrategy) {
        this.primitiveStrategy = primitiveStrategy;
        return (BUILDER) this;
    }

    public BUILDER setGeneratorStrategy(WrapperGeneratorStrategy wrapperStrategy) {
        this.wrapperStrategy = wrapperStrategy;
        return (BUILDER) this;
    }

    public BUILDER setGeneratorStrategy(StringGeneratorStrategy stringGeneratorStrategy) {
        this.stringGeneratorStrategy = stringGeneratorStrategy;
        return (BUILDER) this;
    }

    protected Object createCompositeObject(Class<?> type, int ... size) {
        return ObjectBuilder.forType(type)
                .onArray()
                    .setGeneratorStrategy(primitiveStrategy)
                    .setGeneratorStrategy(wrapperStrategy)
                    .setGeneratorStrategy(stringGeneratorStrategy)
                    .setGeneratorStrategy(collectionGeneratorStrategy)
                    .setGeneratorStrategy(arrayStrategy)
                .setSize(decreaseDimension(size))
                .onCollection()
                    .setGeneratorStrategy(primitiveStrategy)
                    .setGeneratorStrategy(wrapperStrategy)
                    .setGeneratorStrategy(stringGeneratorStrategy)
                    .setGeneratorStrategy(collectionGeneratorStrategy)
                    .setGeneratorStrategy(arrayStrategy)
                    .setSize(decreaseDimension(size))
                .build();
    }

    protected int[] decreaseDimension(int[] size) {
        return size.length > 1 ? Arrays.copyOfRange(size, 1, size.length) : new int[]{0};
    }

    protected Object createObject(Class<?> type, int ... size) {
        if (ClassUtils.isComposite(type)) {
            return createCompositeObject(type, size);
        }
        else {
            return createRawObject(type);
        }
    }

    protected Object createRawObject(Class<?> type) {
        return ObjectBuilder.forType(type)
                .onPrimitiveProperty().setGeneratorStrategy(primitiveStrategy)
                .onWrapper().setGeneratorStrategy(wrapperStrategy)
                .onString().setGeneratorStrategy(stringGeneratorStrategy)
                .build();
    }

}
