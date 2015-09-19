package org.biins.objectbuilder.builder;

import org.biins.objectbuilder.builder.strategy.PrimitiveGeneratorStrategy;
import org.biins.objectbuilder.types.primitive.PrimitiveType;
import org.biins.objectbuilder.types.primitive.PrimitiveTypeRegistry;

/**
 * @author Martin Janys
 */
public class PrimitiveObjectBuilder extends AbstractBuilder implements Builder {

    protected PrimitiveGeneratorStrategy primitiveStrategy = PrimitiveGeneratorStrategy.DEFAULT;

    public PrimitiveObjectBuilder setGeneratorStrategy(PrimitiveGeneratorStrategy primitiveStrategy) {
        this.primitiveStrategy = primitiveStrategy;
        return this;
    }

    @Override
    public <T> T build(Class<T> type) {
        return buildPrimitive(type);
    }

    public <T> T buildPrimitive(Class<T> type) {
        PrimitiveType<T> primitiveType = PrimitiveTypeRegistry.get(type);
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
