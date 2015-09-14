package org.biins.objectbuilder.builder;

import org.biins.objectbuilder.builder.strategy.ArrayGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.CollectionGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.PrimitiveGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.WrapperGeneratorStrategy;

/**
 * @author Martin Janys
 */
@SuppressWarnings("unchecked")
public abstract class AbstractCompositeBuilder<T, BUILDER> extends AbstractBuilder<T> {

    protected CollectionGeneratorStrategy collectionGeneratorStrategy = CollectionGeneratorStrategy.DEFAULT;
    protected ArrayGeneratorStrategy arrayStrategy = ArrayGeneratorStrategy.DEFAULT;
    protected PrimitiveGeneratorStrategy primitiveStrategy = PrimitiveGeneratorStrategy.DEFAULT;
    protected WrapperGeneratorStrategy wrapperStrategy = WrapperGeneratorStrategy.DEFAULT;

    protected AbstractCompositeBuilder(Class<T> cls) {
        super(cls);
    }

    public void setGeneratorStrategy(CollectionGeneratorStrategy collectionGeneratorStrategy) {
        this.collectionGeneratorStrategy = collectionGeneratorStrategy;
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

}
