package org.biins.objectbuilder.builder;

import org.biins.objectbuilder.builder.strategy.*;
import org.biins.objectbuilder.types.Types;
import org.biins.objectbuilder.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin Janys
 */
@SuppressWarnings("unused")
public class ObjectBuilder<T> extends AbstractBuilder<T> implements Builder<T> {

    private final PrimitiveObjectBuilder primitiveObjectBuilder;
    private final WrapperObjectBuilder wrapperObjectBuilder;
    private final ArrayObjectBuilder arrayObjectBuilder;
    private final StringObjectBuilder stringObjectBuilder;
    private final CollectionObjectBuilder collectionObjectBuilder;

    protected ObjectBuilder(Class<T> cls) {
        super(cls);
        primitiveObjectBuilder = new PrimitiveObjectBuilder();
        wrapperObjectBuilder = new WrapperObjectBuilder();
        arrayObjectBuilder = new ArrayObjectBuilder();
        stringObjectBuilder = new StringObjectBuilder();
        collectionObjectBuilder = new CollectionObjectBuilder();
    }

    public static <T> ObjectBuilder<T> forType(Class<T> cls) {
        return new ObjectBuilder<>(cls);
    }

    public PrimitiveObjectBuilder onPrimitiveProperty() {
        return primitiveObjectBuilder;
    }
    public PrimitiveObjectBuilder onPrimitiveProperty(PrimitiveGeneratorStrategy strategy) {
        return onPrimitiveProperty().setGeneratorStrategy(strategy);
    }

    public WrapperObjectBuilder onWrapperProperty() {
        return wrapperObjectBuilder;
    }
    public WrapperObjectBuilder onWrapperProperty(WrapperGeneratorStrategy strategy) {
        return onWrapperProperty().setGeneratorStrategy(strategy);
    }

    public ArrayObjectBuilder onArray() {
        return arrayObjectBuilder;
    }
    public ArrayObjectBuilder onArray(ArrayGeneratorStrategy strategy) {
        return onArray().setGeneratorStrategy(strategy);
    }

    public StringObjectBuilder onString() {
        return stringObjectBuilder;
    }
    public StringObjectBuilder onString(StringGeneratorStrategy strategy) {
        return onString().setGeneratorStrategy(strategy);
    }

    public CollectionObjectBuilder onCollection() {
        return collectionObjectBuilder;
    }
    public CollectionObjectBuilder onCollection(CollectionGeneratorStrategy strategy) {
        return onCollection().setGeneratorStrategy(strategy);
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
        else if (ClassUtils.isCollection(cls)) {
            return collectionObjectBuilder.buildCollection();
        }

        throw new IllegalStateException("Unknown type");
    }

    private abstract class AbstractTransitionsBuilder implements Builder<T> {

        @Override
        public List<T> build(int count) {
            List<T> list =  new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                list.add(build());
            }
            return list;
        }

        public ObjectBuilder<T> and() {
            return ObjectBuilder.this;
        }

        public PrimitiveObjectBuilder onPrimitive() {
            return ObjectBuilder.this.primitiveObjectBuilder;
        }
        public PrimitiveObjectBuilder onPrimitive(PrimitiveGeneratorStrategy strategy) {
            return onPrimitive().setGeneratorStrategy(strategy);
        }

        public WrapperObjectBuilder onWrapper() {
            return ObjectBuilder.this.wrapperObjectBuilder;
        }
        public WrapperObjectBuilder onWrapper(WrapperGeneratorStrategy strategy) {
            return onWrapper().setGeneratorStrategy(strategy);
        }

        public ArrayObjectBuilder onArray() {
            return ObjectBuilder.this.arrayObjectBuilder;
        }
        public ArrayObjectBuilder onArray(ArrayGeneratorStrategy strategy) {
            return onArray().setGeneratorStrategy(strategy);
        }

        public StringObjectBuilder onString() {
            return ObjectBuilder.this.stringObjectBuilder;
        }
        public StringObjectBuilder onString(StringGeneratorStrategy strategy) {
            return onString().setGeneratorStrategy(strategy);
        }

        public CollectionObjectBuilder onCollection() {
            return ObjectBuilder.this.collectionObjectBuilder;
        }
        public CollectionObjectBuilder onCollection(CollectionGeneratorStrategy strategy) {
            return onCollection().setGeneratorStrategy(strategy);
        }
    }

    public class PrimitiveObjectBuilder extends AbstractTransitionsBuilder {

        private final org.biins.objectbuilder.builder.PrimitiveObjectBuilder<T> builder;

        protected PrimitiveObjectBuilder() {
            builder = org.biins.objectbuilder.builder.PrimitiveObjectBuilder.forType(ObjectBuilder.this.cls);
        }

        public T build() {
            return ObjectBuilder.this.build();
        }

        public PrimitiveObjectBuilder setGeneratorStrategy(PrimitiveGeneratorStrategy strategy) {
            builder.setPrimitiveStrategy(strategy);
            return this;
        }

        public T buildPrimitive() {
            return builder.buildPrimitive();
        }
    }

    public class WrapperObjectBuilder extends AbstractTransitionsBuilder {

        private final org.biins.objectbuilder.builder.WrapperObjectBuilder<T> builder;

        protected WrapperObjectBuilder() {
            builder = org.biins.objectbuilder.builder.WrapperObjectBuilder.forType(cls);
        }

        @Override
        public T build() {
            return ObjectBuilder.this.build();
        }

        public WrapperObjectBuilder setGeneratorStrategy(WrapperGeneratorStrategy strategy) {
            builder.setWrapperStrategy(strategy);
            return this;
        }

        public T buildPrimitiveWrapper() {
            return builder.buildPrimitiveWrapper();
        }
    }

    public class ArrayObjectBuilder extends AbstractTransitionsBuilder {

        private final org.biins.objectbuilder.builder.ArrayObjectBuilder builder;

        protected ArrayObjectBuilder() {
            this.builder = org.biins.objectbuilder.builder.ArrayObjectBuilder.forType(cls);
        }

        @Override
        public T build() {
            return ObjectBuilder.this.build();
        }


        public ArrayObjectBuilder setGeneratorStrategy(PrimitiveGeneratorStrategy strategy) {
            builder.setGeneratorStrategy(strategy);
            return this;
        }

        public ArrayObjectBuilder setGeneratorStrategy(WrapperGeneratorStrategy strategy) {
            builder.setGeneratorStrategy(strategy);
            return this;
        }

        public ArrayObjectBuilder setGeneratorStrategy(StringGeneratorStrategy strategy) {
            builder.setGeneratorStrategy(strategy);
            return this;
        }

        public ArrayObjectBuilder setGeneratorStrategy(CollectionGeneratorStrategy strategy) {
            builder.setGeneratorStrategy(strategy);
            return this;
        }

        public ArrayObjectBuilder setGeneratorStrategy(ArrayGeneratorStrategy strategy) {
            builder.setGeneratorStrategy(strategy);
            return this;
        }

        public ArrayObjectBuilder setSize(int ... size) {
            builder.setSize(size);
            return this;
        }

        @SuppressWarnings("unchecked")
        public T buildArray() {
            return (T) builder.buildArray();
        }
    }

    public class CollectionObjectBuilder extends AbstractTransitionsBuilder {

        private final org.biins.objectbuilder.builder.CollectionObjectBuilder<T> builder;

        protected CollectionObjectBuilder() {
            this.builder = org.biins.objectbuilder.builder.CollectionObjectBuilder.forType(cls);
        }

        public CollectionObjectBuilder of(Types<?> types) {
            builder.of(types);
            return this;
        }

        public CollectionObjectBuilder setSize(int ... size) {
            builder.setSize(size);
            return this;
        }


        public CollectionObjectBuilder setGeneratorStrategy(PrimitiveGeneratorStrategy strategy) {
            builder.setGeneratorStrategy(strategy);
            return this;
        }

        public CollectionObjectBuilder setGeneratorStrategy(WrapperGeneratorStrategy strategy) {
            builder.setGeneratorStrategy(strategy);
            return this;
        }

        public CollectionObjectBuilder setGeneratorStrategy(StringGeneratorStrategy strategy) {
            builder.setGeneratorStrategy(strategy);
            return this;
        }

        public CollectionObjectBuilder setGeneratorStrategy(CollectionGeneratorStrategy strategy) {
            builder.setGeneratorStrategy(strategy);
            return this;
        }

        public CollectionObjectBuilder setGeneratorStrategy(ArrayGeneratorStrategy strategy) {
            builder.setGeneratorStrategy(strategy);
            return this;
        }

        @Override
        public T build() {
            return ObjectBuilder.this.build();
        }

        @SuppressWarnings("unchecked")
        public T buildCollection() {
            return builder.buildCollection();
        }
    }

    public class StringObjectBuilder extends AbstractTransitionsBuilder {

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

        public StringObjectBuilder setGeneratorStrategy(StringGeneratorStrategy strategy) {
            builder.setGeneratorStrategy(strategy);
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

        @SuppressWarnings("unchecked")
        public T buildString() {
            return (T) builder.buildString();
        }
    }

}
