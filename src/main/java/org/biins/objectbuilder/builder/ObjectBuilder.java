package org.biins.objectbuilder.builder;

import org.biins.objectbuilder.builder.strategy.ArrayGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.PrimitiveGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.StringGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.WrapperGeneratorStrategy;
import org.biins.objectbuilder.util.ClassUtils;

/**
 * @author Martin Janys
 */
public class ObjectBuilder<T> extends AbstractBuilder<T> implements Builder<T> {

    private final PrimitiveObjectBuilder primitiveObjectBuilder;
    private final WrapperObjectBuilder wrapperObjectBuilder;
    private final ArrayObjectBuilder arrayObjectBuilder;
    private final StringObjectBuilder stringObjectBuilder;

    protected ObjectBuilder(Class<T> cls) {
        super(cls);
        primitiveObjectBuilder = new PrimitiveObjectBuilder();
        wrapperObjectBuilder = new WrapperObjectBuilder();
        arrayObjectBuilder = new ArrayObjectBuilder();
        stringObjectBuilder = new StringObjectBuilder();
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

    public StringObjectBuilder onStringProperty() {
        return stringObjectBuilder;
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
        else if (ClassUtils.isString(cls)) {
            return stringObjectBuilder.buildString();
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

        public StringObjectBuilder onStringProperty() {
            return ObjectBuilder.this.stringObjectBuilder;
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

    public class StringObjectBuilder extends BuilderTransitions implements Builder<T> {

        private final org.biins.objectbuilder.builder.StringObjectBuilder builder;

        protected StringObjectBuilder() {
            builder = org.biins.objectbuilder.builder.StringObjectBuilder.forType();
        }

        @Override
        public T build() {
            return ObjectBuilder.this.build();
        }

        public StringObjectBuilder setSize(int size) {
            builder.setSize(size);
            return this;
        }


        public StringObjectBuilder setStringStrategy(StringGeneratorStrategy strategy) {
            builder.setStringStrategy(strategy);
            return this;
        }

        public StringObjectBuilder setLower(boolean lower) {
            builder.setLower(lower);
            return this;
        }

        public StringObjectBuilder setAlpha(boolean alpha) {
            builder.setAlpha(alpha);
            return this;
        }

        public StringObjectBuilder setNumeric(boolean numeric) {
            builder.setNumeric(numeric);
            return this;
        }

        public StringObjectBuilder setAttributes(boolean lower, boolean alpha, boolean numeric) {
            builder.setAttributes(lower, alpha, numeric);
            return this;
        }

        public T buildString() {
            return (T) builder.buildString();
        }
    }

}
