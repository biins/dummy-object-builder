package org.biins.objectbuilder.builder;

import org.apache.commons.lang.Validate;
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

    private Types<?> collectionElementType;

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

    public ArrayObjectBuilder onArrayProperty() {
        return arrayObjectBuilder;
    }
    public ArrayObjectBuilder onArrayProperty(ArrayGeneratorStrategy strategy) {
        return onArrayProperty().setGeneratorStrategy(strategy);
    }

    public StringObjectBuilder onStringProperty() {
        return stringObjectBuilder;
    }
    public StringObjectBuilder onStringProperty(StringGeneratorStrategy strategy) {
        return onStringProperty().setGeneratorStrategy(strategy);
    }

    public CollectionObjectBuilder onCollectionProperty() {
        return collectionObjectBuilder;
    }
    public CollectionObjectBuilder onCollectionProperty(CollectionGeneratorStrategy strategy) {
        return onCollectionProperty().setGeneratorStrategy(strategy);
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
            Validate.notNull(collectionElementType, "Generation collection without element type. Use ObjectBuilder#collectionOf() method to setup");
            return collectionObjectBuilder.of(collectionElementType).buildCollection();
        }

        throw new IllegalStateException("Unknown type");
    }

    public ObjectBuilder<T> collectionOf(Types<?> type) {
        this.collectionElementType = type;
        return this;
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

        public PrimitiveObjectBuilder onPrimitiveProperty() {
            return ObjectBuilder.this.primitiveObjectBuilder;
        }
        public PrimitiveObjectBuilder onPrimitiveProperty(PrimitiveGeneratorStrategy strategy) {
            return onPrimitiveProperty().setGeneratorStrategy(strategy);
        }

        public WrapperObjectBuilder onWrapperProperty() {
            return ObjectBuilder.this.wrapperObjectBuilder;
        }
        public WrapperObjectBuilder onWrapperProperty(WrapperGeneratorStrategy strategy) {
            return onWrapperProperty().setGeneratorStrategy(strategy);
        }

        public ArrayObjectBuilder onArrayProperty() {
            return ObjectBuilder.this.arrayObjectBuilder;
        }
        public ArrayObjectBuilder onArrayProperty(ArrayGeneratorStrategy strategy) {
            return onArrayProperty().setGeneratorStrategy(strategy);
        }

        public StringObjectBuilder onStringProperty() {
            return ObjectBuilder.this.stringObjectBuilder;
        }
        public StringObjectBuilder onStringProperty(StringGeneratorStrategy strategy) {
            return onStringProperty().setGeneratorStrategy(strategy);
        }

        public CollectionObjectBuilder onCollectionProperty() {
            return ObjectBuilder.this.collectionObjectBuilder;
        }
        public CollectionObjectBuilder onCollectionProperty(CollectionGeneratorStrategy strategy) {
            return onCollectionProperty().setGeneratorStrategy(strategy);
        }
    }


    @SuppressWarnings("unchecked")
    private abstract class AbstractCompositeTransitionsBuilder<TYPE extends AbstractCompositeBuilder, BUILDER> extends AbstractTransitionsBuilder {

        protected TYPE builder;

        public AbstractCompositeTransitionsBuilder(TYPE builder) {
            this.builder = builder;
        }

        public BUILDER setGeneratorStrategy(PrimitiveGeneratorStrategy strategy) {
            builder.setGeneratorStrategy(strategy);
            return (BUILDER) this;
        }

        public BUILDER setGeneratorStrategy(WrapperGeneratorStrategy strategy) {
            builder.setGeneratorStrategy(strategy);
            return (BUILDER) this;
        }

        public BUILDER setGeneratorStrategy(StringGeneratorStrategy strategy) {
            builder.setGeneratorStrategy(strategy);
            return (BUILDER) this;
        }

        public BUILDER setGeneratorStrategy(CollectionGeneratorStrategy strategy) {
            builder.setGeneratorStrategy(strategy);
            return (BUILDER) this;
        }

        public BUILDER setGeneratorStrategy(ArrayGeneratorStrategy strategy) {
            builder.setGeneratorStrategy(strategy);
            return (BUILDER) this;
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

    public class ArrayObjectBuilder extends AbstractCompositeTransitionsBuilder<org.biins.objectbuilder.builder.ArrayObjectBuilder, ArrayObjectBuilder> {

        protected ArrayObjectBuilder() {
            super(org.biins.objectbuilder.builder.ArrayObjectBuilder.forType(cls));
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

        @SuppressWarnings("unchecked")
        public T buildArray() {
            return (T) builder.buildArray();
        }
    }

    public class CollectionObjectBuilder extends AbstractCompositeTransitionsBuilder<org.biins.objectbuilder.builder.CollectionObjectBuilder, CollectionObjectBuilder> {

        protected CollectionObjectBuilder() {
            super(org.biins.objectbuilder.builder.CollectionObjectBuilder.forType(cls));
        }

        private CollectionObjectBuilder of(Types<?> types) {
            builder.of(types);
            return this;
        }

        @Override
        public T build() {
            return ObjectBuilder.this.build();
        }

        public CollectionObjectBuilder setSize(int size) {
            builder.setSize(size);
            return this;
        }

        public CollectionObjectBuilder setSize(int ... size) {
            builder.setSize(size);
            return this;
        }

        @SuppressWarnings("unchecked")
        public T buildCollection() {
            return (T) builder.buildCollection();
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
