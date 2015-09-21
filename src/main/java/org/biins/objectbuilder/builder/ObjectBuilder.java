package org.biins.objectbuilder.builder;

import org.biins.objectbuilder.builder.strategy.*;
import org.biins.objectbuilder.resolver.TypeGeneratorResolver;
import org.biins.objectbuilder.resolver.def.EnumerationGeneratorResolver;
import org.biins.objectbuilder.resolver.def.IterableGeneratorResolver;
import org.biins.objectbuilder.resolver.def.IteratorGeneratorResolver;
import org.biins.objectbuilder.types.Types;
import org.biins.objectbuilder.util.ClassUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Martin Janys
 */
@SuppressWarnings("unused")
public class ObjectBuilder extends AbstractBuilder implements Builder {

    private final PrimitiveObjectBuilder primitiveObjectBuilder;
    private final WrapperObjectBuilder wrapperObjectBuilder;
    private final ArrayObjectBuilder arrayObjectBuilder;
    private final StringObjectBuilder stringObjectBuilder;
    private final CollectionObjectBuilder collectionObjectBuilder;
    private final CommonObjectBuilder commonObjectBuilder;
    private final EnumObjectBuilder enumObjectBuilder;
    private final MapObjectBuilder mapObjectBuilder;

    private final List<TypeGeneratorResolver<?>> generatorResolvers = new ArrayList<>();

    public ObjectBuilder() {
        primitiveObjectBuilder = new PrimitiveObjectBuilder();
        wrapperObjectBuilder = new WrapperObjectBuilder();
        arrayObjectBuilder = new ArrayObjectBuilder();
        stringObjectBuilder = new StringObjectBuilder();
        collectionObjectBuilder = new CollectionObjectBuilder();
        commonObjectBuilder = new CommonObjectBuilder();
        enumObjectBuilder = new EnumObjectBuilder();
        mapObjectBuilder = new MapObjectBuilder();
    }

    public ObjectBuilder addGeneratorResolver(TypeGeneratorResolver resolver) {
        generatorResolvers.add(resolver);
        return this;
    }

    public PrimitiveObjectBuilder onPrimitive() {
        return primitiveObjectBuilder;
    }
    public PrimitiveObjectBuilder onPrimitive(PrimitiveGeneratorStrategy strategy) {
        return onPrimitive().setGeneratorStrategy(strategy);
    }

    public WrapperObjectBuilder onWrapper() {
        return wrapperObjectBuilder;
    }
    public WrapperObjectBuilder onWrapper(WrapperGeneratorStrategy strategy) {
        return onWrapper().setGeneratorStrategy(strategy);
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

    public EnumObjectBuilder onEnum() {
        return ObjectBuilder.this.enumObjectBuilder;
    }
    public EnumObjectBuilder onEnum(EnumGeneratorStrategy strategy) {
        return onEnum().setGeneratorStrategy(strategy);
    }

    public MapObjectBuilder onMap() {
        return ObjectBuilder.this.mapObjectBuilder;
    }
    public MapObjectBuilder onMap(MapGeneratorStrategy strategy) {
        return onMap().setGeneratorStrategy(strategy);
    }

    @Override
    public <T> T build(Class<T> type) {
        TypeGeneratorResolver<T>  resolver = getResolver(type);
        if (resolver != null) {
            return resolver.resolve(type, this);
        }
        else if (ClassUtils.isPrimitive(type)) {
            return primitiveObjectBuilder.buildPrimitive(type);
        }
        else if (ClassUtils.isWrapperClass(type)) {
            return wrapperObjectBuilder.buildPrimitiveWrapper(type);
        }
        else if (ClassUtils.isArray(type)) {
            return arrayObjectBuilder.buildArray(type);
        }
        else if (ClassUtils.isString(type)) {
            return stringObjectBuilder.buildString();
        }
        else if (ClassUtils.isCollection(type)) {
            return collectionObjectBuilder.buildCollection(type);
        }
        else if (ClassUtils.isMap(type)) {
            return mapObjectBuilder.buildMap(type);
        }
        else if (ClassUtils.isEnum(type)) {
            return enumObjectBuilder.buildEnum(type);
        }
        else {
            return commonObjectBuilder.buildObject(type);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> TypeGeneratorResolver<T> getResolver(Class<T> type) {
        for (TypeGeneratorResolver resolver : getAllResolvers()) {
            if (resolver.canResolve(type)) {
                return resolver;
            }
        }

        return null;
    }

    private <T> List<TypeGeneratorResolver<?>> getAllResolvers() {
        List<TypeGeneratorResolver<?>> resolvers = new ArrayList<>();
        resolvers.addAll(generatorResolvers);
        resolvers.addAll(Arrays.asList(
                new EnumerationGeneratorResolver(),
                new IteratorGeneratorResolver(),
                new IterableGeneratorResolver()
        ));
        return resolvers;
    }

    private abstract class AbstractTransitionsBuilder implements Builder {

        @Override
        public <T> List<T> build(Class<T> type, int count) {
            List<T> list =  new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                list.add(build(type));
            }
            return list;
        }

        public ObjectBuilder and() {
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

        public EnumObjectBuilder onEnum() {
            return ObjectBuilder.this.enumObjectBuilder;
        }
        public EnumObjectBuilder onEnum(EnumGeneratorStrategy strategy) {
            return onEnum().setGeneratorStrategy(strategy);
        }

        public MapObjectBuilder onMap() {
            return ObjectBuilder.this.mapObjectBuilder;
        }
        public MapObjectBuilder onMap(MapGeneratorStrategy strategy) {
            return onMap().setGeneratorStrategy(strategy);
        }

    }

    public class PrimitiveObjectBuilder extends AbstractTransitionsBuilder {

        private final org.biins.objectbuilder.builder.PrimitiveObjectBuilder builder;

        protected PrimitiveObjectBuilder() {
            builder = new org.biins.objectbuilder.builder.PrimitiveObjectBuilder();
        }

        public <T> T build(Class<T> type) {
            return ObjectBuilder.this.build(type);
        }

        public PrimitiveObjectBuilder setGeneratorStrategy(PrimitiveGeneratorStrategy strategy) {
            builder.setGeneratorStrategy(strategy);
            return this;
        }

        public <T> T buildPrimitive(Class<T> type) {
            return builder.buildPrimitive(type);
        }
    }

    public class WrapperObjectBuilder extends AbstractTransitionsBuilder {

        private final org.biins.objectbuilder.builder.WrapperObjectBuilder builder;

        protected WrapperObjectBuilder() {
            builder = new org.biins.objectbuilder.builder.WrapperObjectBuilder();
        }

        @Override
        public <T> T build(Class<T> type) {
            return ObjectBuilder.this.build(type);
        }

        public WrapperObjectBuilder setGeneratorStrategy(WrapperGeneratorStrategy strategy) {
            builder.setGeneratorStrategy(strategy);
            return this;
        }

        public <T> T buildPrimitiveWrapper(Class<T> type) {
            return builder.buildPrimitiveWrapper(type);
        }
    }

    public class EnumObjectBuilder extends AbstractTransitionsBuilder {

        private final org.biins.objectbuilder.builder.EnumObjectBuilder builder;

        protected EnumObjectBuilder() {
            builder = new org.biins.objectbuilder.builder.EnumObjectBuilder();
        }

        @Override
        public <T> T build(Class<T> type) {
            return ObjectBuilder.this.build(type);
        }

        public EnumObjectBuilder setGeneratorStrategy(EnumGeneratorStrategy strategy) {
            builder.setGeneratorStrategy(strategy);
            return this;
        }

        public <T> T buildEnum(Class<T> type) {
            return builder.buildEnum(type);
        }
    }

    public class ArrayObjectBuilder extends AbstractTransitionsBuilder {

        private final org.biins.objectbuilder.builder.ArrayObjectBuilder builder;

        protected ArrayObjectBuilder() {
            this.builder = new org.biins.objectbuilder.builder.ArrayObjectBuilder(ObjectBuilder.this);
        }

        @Override
        public <T> T build(Class<T> type) {
            return ObjectBuilder.this.build(type);
        }

        public ArrayObjectBuilder setGeneratorStrategy(ArrayGeneratorStrategy strategy) {
            builder.setGeneratorStrategy(strategy);
            return this;
        }

        public ArrayObjectBuilder setSize(int ... size) {
            builder.setSize(size);
            return this;
        }

        <T> T buildArray(Class<T> type) {
            return builder.buildArray(type);
        }

        <T> T buildArray(Class<T> type, int ... size) {
            return builder.buildArray(type, size);
        }
    }

    public class CollectionObjectBuilder extends AbstractTransitionsBuilder {

        private final org.biins.objectbuilder.builder.CollectionObjectBuilder builder;

        protected CollectionObjectBuilder() {
            this.builder = new org.biins.objectbuilder.builder.CollectionObjectBuilder(ObjectBuilder.this);
        }

        public CollectionObjectBuilder of(Types types) {
            builder.of(types);
            return this;
        }

        public CollectionObjectBuilder setSize(int ... size) {
            builder.setSize(size);
            return this;
        }

        public CollectionObjectBuilder setGeneratorStrategy(CollectionGeneratorStrategy strategy) {
            builder.setGeneratorStrategy(strategy);
            return this;
        }

        @Override
        public <T> T build(Class<T> type) {
            return ObjectBuilder.this.build(type);
        }

        <T> T buildCollection(Class<T> type) {
            return builder.buildCollection(type);
        }

        <T> T buildCollection(Class<T> type, Types elementType) {
            return builder.buildCollection(type, elementType);
        }

        <T> T buildCollection(Class<T> type, Types elementType, int ... size) {
            return builder.buildCollection(type, elementType, size);
        }
    }

    public class StringObjectBuilder extends AbstractTransitionsBuilder {

        private final org.biins.objectbuilder.builder.StringObjectBuilder builder;

        protected StringObjectBuilder() {
            builder = new org.biins.objectbuilder.builder.StringObjectBuilder();
        }

        @Override
        public <T> T build(Class<T> type) {
            return ObjectBuilder.this.build(type);
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
        public <T> T buildString() {
            return (T) builder.buildString();
        }
    }

    public class CommonObjectBuilder extends AbstractBuilder {

        private final org.biins.objectbuilder.builder.CommonObjectBuilder builder;

        protected CommonObjectBuilder() {
            builder = new org.biins.objectbuilder.builder.CommonObjectBuilder(ObjectBuilder.this);
        }

        public <T> T build(Class<T> type) {
            return ObjectBuilder.this.build(type);
        }

        public CommonObjectBuilder setGeneratorStrategy(CommonObjectGeneratorStrategy strategy) {
            builder.setGeneratorStrategy(strategy);
            return this;
        }

        public <T> T buildObject(Class<T> type) {
            return builder.buildObject(type);
        }
    }

    public class MapObjectBuilder extends AbstractBuilder {

        private final org.biins.objectbuilder.builder.MapObjectBuilder builder;

        protected MapObjectBuilder() {
            builder = new org.biins.objectbuilder.builder.MapObjectBuilder(ObjectBuilder.this);
        }

        public <T> T build(Class<T> type) {
            return ObjectBuilder.this.build(type);
        }

        public MapObjectBuilder setGeneratorStrategy(MapGeneratorStrategy strategy) {
            builder.setGeneratorStrategy(strategy);
            return this;
        }

        public <T> T buildMap(Class<T> type) {
            return builder.buildMap(type);
        }

        public MapObjectBuilder setSize(int size) {
            builder.setSize(size);
            return this;
        }

        public MapObjectBuilder ofKey(Types keyType) {
            builder.ofKey(keyType);
            return this;
        }

        public MapObjectBuilder ofValue(Types valueType) {
            builder.ofValue(valueType);
            return this;
        }
    }
}
