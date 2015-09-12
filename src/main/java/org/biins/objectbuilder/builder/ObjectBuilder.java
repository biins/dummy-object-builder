package org.biins.objectbuilder.builder;

import org.biins.objectbuilder.builder.strategy.ArrayGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.PrimitiveGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.WrapperGeneratorStrategy;
import org.biins.objectbuilder.util.ClassUtils;

/**
 * @author Martin Janys
 */
public class ObjectBuilder<T> extends AbstractBuilder<T> implements Builder<T> {

    private final PrimitiveObjectBuilder primitiveObjectBuilder;
    private final WrapperObjectBuilder wrapperObjectBuilder;
    private final ArrayObjectBuilder arrayObjectBuilder;

    protected ObjectBuilder(Class<T> cls) {
        super(cls);
        primitiveObjectBuilder = new PrimitiveObjectBuilder();
        wrapperObjectBuilder = new WrapperObjectBuilder();
        arrayObjectBuilder = new ArrayObjectBuilder();
    }

    public static <T> ObjectBuilder<T> forType(Class<T> cls) {
        return new ObjectBuilder<T>(cls);
    }

    public PrimitiveObjectBuilder onPrimitiveProperty() {
        return primitiveObjectBuilder;
    }

    public WrapperObjectBuilder onWrapperProperty() {
        return wrapperObjectBuilder;
    }

    public ArrayObjectBuilder onArrayProperty() {
        return arrayObjectBuilder;
    }

    public T build() {
        if (ClassUtils.isPrimitive(cls)) {
            return primitiveObjectBuilder.buildPrimitive();
        }
        else if (ClassUtils.isWrapperClass(cls)) {
            return wrapperObjectBuilder.buildPrimitiveWrapper();
        }
        else if (ClassUtils.isArray(cls)) {
            return arrayObjectBuilder.buildArray();
        }

        throw new IllegalStateException("Unknown type");
    }

    private abstract class BuilderTransitions {
        public PrimitiveObjectBuilder onPrimitiveProperty() {
            return ObjectBuilder.this.primitiveObjectBuilder;
        }

        public WrapperObjectBuilder onWrapperProperty() {
            return ObjectBuilder.this.wrapperObjectBuilder;
        }

        public ArrayObjectBuilder onArrayProperty() {
            return ObjectBuilder.this.arrayObjectBuilder;
        }
    }

    public class PrimitiveObjectBuilder extends BuilderTransitions implements Builder<T> {

        private final org.biins.objectbuilder.builder.PrimitiveObjectBuilder<T> builder;

        protected PrimitiveObjectBuilder() {
            builder = org.biins.objectbuilder.builder.PrimitiveObjectBuilder.forType(ObjectBuilder.this.cls);
        }

        public T build() {
            return ObjectBuilder.this.build();
        }

        public PrimitiveObjectBuilder setPrimitiveStrategy(PrimitiveGeneratorStrategy strategy) {
            builder.setPrimitiveStrategy(strategy);
            return this;
        }

        public T buildPrimitive() {
            return builder.buildPrimitive();
        }
    }

    public class WrapperObjectBuilder extends BuilderTransitions implements Builder<T> {

        private final org.biins.objectbuilder.builder.WrapperObjectBuilder<T> builder;

        protected WrapperObjectBuilder() {
            builder = org.biins.objectbuilder.builder.WrapperObjectBuilder.forType(cls);
        }

        @Override
        public T build() {
            return ObjectBuilder.this.build();
        }

        public WrapperObjectBuilder setWrapperStrategy(WrapperGeneratorStrategy strategy) {
            builder.setWrapperStrategy(strategy);
            return this;
        }

        public T buildPrimitiveWrapper() {
            return builder.buildPrimitiveWrapper();
        }
    }

    public class ArrayObjectBuilder extends BuilderTransitions implements Builder<T> {

        private final org.biins.objectbuilder.builder.ArrayObjectBuilder<T> builder;

        protected ArrayObjectBuilder() {
            builder = org.biins.objectbuilder.builder.ArrayObjectBuilder.forType(cls);
        }

        @Override
        public T build() {
            return ObjectBuilder.this.build();
        }

        public ArrayObjectBuilder setSize(int size) {
            builder.setSize(size);
            return this;
        }

        public ArrayObjectBuilder setSize(int ... size) {
            builder.setSize(size);
            return this;
        }

        public ArrayObjectBuilder setArrayStrategy(ArrayGeneratorStrategy strategy) {
            builder.setArrayStrategy(strategy);
            return this;
        }

        public ArrayObjectBuilder setArrayStrategy(PrimitiveGeneratorStrategy strategy) {
            builder.setArrayStrategy(strategy);
            return this;
        }

        public ArrayObjectBuilder setArrayStrategy(WrapperGeneratorStrategy strategy) {
            builder.setArrayStrategy(strategy);
            return this;
        }

        public ArrayObjectBuilder setArrayStrategy(ArrayGeneratorStrategy arrayStrategy, PrimitiveGeneratorStrategy primitiveStrategy, WrapperGeneratorStrategy wrapperStrategy) {
            builder.setArrayStrategy(arrayStrategy, primitiveStrategy, wrapperStrategy);
            return this;
        }

        public T buildArray() {
            return builder.buildArray();
        }
    }

}
