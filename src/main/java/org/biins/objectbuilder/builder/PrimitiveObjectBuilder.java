package org.biins.objectbuilder.builder;

import org.biins.objectbuilder.builder.strategy.PrimitiveGeneratorStrategy;
import org.biins.objectbuilder.types.primitive.PrimitiveType;
import org.biins.objectbuilder.types.primitive.PrimitiveTypeRegistry;

/**
 * @author Martin Janys
 */
public class PrimitiveObjectBuilder<T> extends AbstractBuilder<T> implements Builder<T> {

    protected PrimitiveGeneratorStrategy primitiveStrategy = PrimitiveGeneratorStrategy.DEFAULT;

    protected PrimitiveObjectBuilder(Class<T> cls) {
        super(cls);
    }

    static <T> PrimitiveObjectBuilder<T> forType(Class<T> cls) {
        return new PrimitiveObjectBuilder<T>(cls);
    }

    public PrimitiveObjectBuilder<T> setPrimitiveStrategy(PrimitiveGeneratorStrategy primitiveStrategy) {
        this.primitiveStrategy = primitiveStrategy;
        return this;
    }

    @Override
    public T build() {
        return buildPrimitive();
    }

    public T buildPrimitive() {
        PrimitiveType<T> primitiveType = PrimitiveTypeRegistry.get(cls);
        switch (primitiveStrategy) {
            case MIN:
                return primitiveType.getMinValue();
            case MAX:
                return primitiveType.getMaxValue();
            case RANDOM:
                return primitiveType.getRandomValue();
            case DEFAULT:
            default:
                return primitiveType.getDefaultValue();
        }
    }
}
