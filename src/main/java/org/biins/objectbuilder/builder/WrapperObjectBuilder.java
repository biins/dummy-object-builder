package org.biins.objectbuilder.builder;

import org.biins.objectbuilder.builder.strategy.WrapperGeneratorStrategy;
import org.biins.objectbuilder.types.wrapper.WrapperType;
import org.biins.objectbuilder.types.wrapper.WrapperTypeRegistry;

/**
 * @author Martin Janys
 */
public class WrapperObjectBuilder extends AbstractBuilder implements Builder {

    protected WrapperGeneratorStrategy wrapperStrategy = WrapperGeneratorStrategy.DEFAULT;

    public WrapperObjectBuilder setGeneratorStrategy(WrapperGeneratorStrategy wrapper) {
        this.wrapperStrategy = wrapper;
        return this;
    }

    @Override
    public <T> T build(Class<T> type) {
        return buildPrimitiveWrapper(type);
    }

    public <T> T buildPrimitiveWrapper(Class<T> type) {
        WrapperType<T> wrapperType = WrapperTypeRegistry.get(type);

        switch (wrapperStrategy) {
            case NULL:
                return null;
            case MIN:
                return wrapperType.getMinValue();
            case MAX:
                return wrapperType.getMaxValue();
            case RANDOM:
                return wrapperType.getRandomValue();
            case DEFAULT:
            default:
                return wrapperType.getDefaultValue();
        }
    }
}
