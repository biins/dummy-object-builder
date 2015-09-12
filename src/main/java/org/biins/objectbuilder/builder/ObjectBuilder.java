package org.biins.objectbuilder.builder;

import org.biins.objectbuilder.builder.strategy.PrimitiveGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.WrapperGeneratorStrategy;
import org.biins.objectbuilder.util.ClassUtils;

/**
 * @author Martin Janys
 */
public class ObjectBuilder<T> extends AbstractBuilder<T> implements Builder<T> {

    private PrimitiveObjectBuilder primitiveObjectBuilder = new PrimitiveObjectBuilder();
    private WrapperObjectBuilder wrapperObjectBuilder = new WrapperObjectBuilder();

    protected ObjectBuilder(Class<T> cls) {
        super(cls);
    }

    public static <T> ObjectBuilder<T> forClass(Class<T> cls) {
        return new ObjectBuilder<T>(cls);
    }

    public PrimitiveObjectBuilder onPrimitiveProperty() {
        return primitiveObjectBuilder;
    }

    public WrapperObjectBuilder onWrapperProperty() {
        return wrapperObjectBuilder;
    }

    public T build() {
        if (ClassUtils.isPrimitive(cls)) {
            return primitiveObjectBuilder.buildPrimitive();
        }
        else if (ClassUtils.isWrapperClass(cls)) {
            return wrapperObjectBuilder.buildPrimitiveWrapper();
        }

        throw new IllegalStateException("Unknown type");
    }

    public class PrimitiveObjectBuilder implements Builder<T> {

        private final org.biins.objectbuilder.builder.PrimitiveObjectBuilder<T> builder;

        protected PrimitiveObjectBuilder() {
            builder = org.biins.objectbuilder.builder.PrimitiveObjectBuilder.forClass(ObjectBuilder.this.cls);
        }

        public WrapperObjectBuilder onWrapperProperty() {
            return ObjectBuilder.this.wrapperObjectBuilder;
        }

        public T build() {
            return ObjectBuilder.this.build();
        }

        public PrimitiveObjectBuilder setPrimitiveStrategy(PrimitiveGeneratorStrategy primitiveStrategy) {
            builder.setPrimitiveStrategy(primitiveStrategy);
            return this;
        }

        public T buildPrimitive() {
            return builder.buildPrimitive();
        }
    }

    public class WrapperObjectBuilder implements Builder<T> {

        private final org.biins.objectbuilder.builder.WrapperObjectBuilder<T> builder;

        protected WrapperObjectBuilder() {
            builder = org.biins.objectbuilder.builder.WrapperObjectBuilder.forClass(cls);
        }

        public PrimitiveObjectBuilder onPrimitiveProperty() {
            return ObjectBuilder.this.primitiveObjectBuilder;
        }

        @Override
        public T build() {
            return ObjectBuilder.this.build();
        }

        public WrapperObjectBuilder setWrapperStrategy(WrapperGeneratorStrategy wrapper) {
            builder.setWrapperStrategy(wrapper);
            return this;
        }

        public T buildPrimitiveWrapper() {
            return builder.buildPrimitiveWrapper();
        }
    }

}
