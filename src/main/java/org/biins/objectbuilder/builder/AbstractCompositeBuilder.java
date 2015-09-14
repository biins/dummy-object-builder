package org.biins.objectbuilder.builder;

import org.biins.objectbuilder.builder.strategy.*;
import org.biins.objectbuilder.util.ClassUtils;

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

    protected abstract Object createCompositeObject(Class<?> type);

    protected Object createObject(Class<?> type) {
        if (ClassUtils.isComposite(type)) {
            return createCompositeObject(type);
        }
        else {
            return createRawObject(type);
        }
    }

    protected Object createRawObject(Class<?> type) {
        return ObjectBuilder.forType(type)
                .onPrimitiveProperty().setGeneratorStrategy(primitiveStrategy)
                .onWrapperProperty().setGeneratorStrategy(wrapperStrategy)
                .onStringProperty().setGeneratorStrategy(stringGeneratorStrategy)
                .build();
    }

}
