package org.biins.objectbuilder.builder;

import org.biins.objectbuilder.builder.strategy.WrapperGeneratorStrategy;
import org.biins.objectbuilder.types.wrapper.WrapperType;
import org.biins.objectbuilder.types.wrapper.WrapperTypeRegistry;

/**
 * @author Martin Janys
 */
public class WrapperObjectBuilder<T> extends AbstractBuilder<T> implements Builder<T> {

    protected WrapperGeneratorStrategy wrapperStrategy = WrapperGeneratorStrategy.DEFAULT;

    protected WrapperObjectBuilder(Class<T> cls) {
        super(cls);
    }

    static <T> WrapperObjectBuilder<T> forClass(Class<T> cls) {
        return new WrapperObjectBuilder<T>(cls);
    }

    public WrapperObjectBuilder<T> setWrapperStrategy(WrapperGeneratorStrategy wrapper) {
        this.wrapperStrategy = wrapper;
        return this;
    }

    @Override
    public T build() {
        return buildPrimitiveWrapper();
    }

    public T buildPrimitiveWrapper() {
        WrapperType<T> wrapperType = WrapperTypeRegistry.get(cls);

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
