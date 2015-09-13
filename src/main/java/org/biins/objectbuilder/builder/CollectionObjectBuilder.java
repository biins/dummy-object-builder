package org.biins.objectbuilder.builder;

import org.biins.objectbuilder.builder.strategy.ArrayGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.CollectionGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.PrimitiveGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.WrapperGeneratorStrategy;
import org.biins.objectbuilder.types.Types;
import org.biins.objectbuilder.types.collection.CollectionType;
import org.biins.objectbuilder.types.collection.CollectionTypeRegistry;
import org.biins.objectbuilder.util.ClassUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Martin Janys
 */
@SuppressWarnings("unchecked")
public class CollectionObjectBuilder<T> extends AbstractBuilder<T> implements Builder<T> {

    protected CollectionGeneratorStrategy collectionGeneratorStrategy = CollectionGeneratorStrategy.DEFAULT;
    protected ArrayGeneratorStrategy arrayStrategy = ArrayGeneratorStrategy.DEFAULT;
    protected PrimitiveGeneratorStrategy primitiveStrategy = PrimitiveGeneratorStrategy.DEFAULT;
    protected WrapperGeneratorStrategy wrapperStrategy = WrapperGeneratorStrategy.DEFAULT;
    protected int[] size = new int[]{0};

    private Types<?> elementType;

    protected CollectionObjectBuilder(Class<T> cls) {
        super(cls);
    }

    static <T> CollectionObjectBuilder<T> forType(Class<T> cls) {
        return new CollectionObjectBuilder<>(cls);
    }

    public void setCollectionStrategy(CollectionGeneratorStrategy collectionGeneratorStrategy) {
        this.collectionGeneratorStrategy = collectionGeneratorStrategy;
    }

    public CollectionObjectBuilder<T> setCollectionStrategy(ArrayGeneratorStrategy arrayStrategy) {
        this.arrayStrategy = arrayStrategy;
        return this;
    }

    public CollectionObjectBuilder<T> setCollectionStrategy(PrimitiveGeneratorStrategy primitiveStrategy) {
        this.primitiveStrategy = primitiveStrategy;
        return this;
    }

    public CollectionObjectBuilder<T> setCollectionStrategy(WrapperGeneratorStrategy wrapperStrategy) {
        this.wrapperStrategy = wrapperStrategy;
        return this;
    }

    public CollectionObjectBuilder<T> setCollectionStrategy(CollectionGeneratorStrategy collectionStrategy, PrimitiveGeneratorStrategy primitiveStrategy, WrapperGeneratorStrategy wrapperStrategy, ArrayGeneratorStrategy arrayStrategy) {
        setCollectionStrategy(collectionStrategy);
        setCollectionStrategy(arrayStrategy);
        setCollectionStrategy(primitiveStrategy);
        setCollectionStrategy(wrapperStrategy);
        return this;
    }

    public CollectionObjectBuilder<T> setSize(int size) {
        this.size = new int[]{size};
        validateSize();
        return this;
    }

    public CollectionObjectBuilder<T> setSize(int ... size) {
        this.size = size;
        validateSize();
        return this;
    }

    private void validateSize() {
        for (int s : size) {
            if (s < 0) {
                throw new IllegalArgumentException("Size must be positive");
            }
        }
    }


    public CollectionObjectBuilder<T> of(Types<?> elementType) {
        this.elementType = elementType;
        return this;
    }

    @Override
    public T build() {
        return buildCollection();
    }

    public T buildCollection() {
        CollectionType<T> collectionType = CollectionTypeRegistry.get(cls);
        return buildCollection(collectionType, elementType, size);
    }

    private T buildCollection(CollectionType<T> collectionType, Types elementType, int ... size) {
        switch (collectionGeneratorStrategy) {
            case NULL:
                return null;
            case VALUE:
                return buildCollectionInternal(collectionType, elementType, size);
            case SINGLETON:
                return buildCollectionInternal(collectionType, elementType);
            case DEFAULT:
            default:
                return collectionType.getDefaultValue();
        }
    }

    private T buildCollectionInternal(CollectionType<T> collectionType, Types elementType, int ... sizes) {
        int collectionSize = countSize(sizes, 0);
        int subCollectionSize = countSize(sizes, 1);

        Collection collection;

        if (ClassUtils.isCollection(elementType.getType())) {
            // element is collection
            ArrayList list = new ArrayList(collectionSize);
            for (int i = 0; i < collectionSize; i++) {
                Collection subCollection;
                if (ClassUtils.isCollection(elementType.next().getType())) {
                    subCollection = createCollectionWithCollection(elementType, elementType.next(), decreaseDimension(sizes));
                }
                else {
                    subCollection = createCollectionWithValues(elementType.getType(), elementType.next(), subCollectionSize);
                }

                list.set(i, subCollection);
            }
            collection = createCollection(elementType.getType(), list);
        }
        else {
            // element is value
            if (elementType.next() != null && ClassUtils.isCollection(elementType.next().getType())) {
                collection = createCollectionWithCollection(elementType, elementType.next(), decreaseDimension(sizes));
            }
            else {
                collection = createCollectionWithValues(collectionType.getType(), elementType, collectionSize);
            }
        }

        return (T) collection;
    }

    private int countSize(int[] sizes, int index) {
        return sizes.length > index
                ? sizes[index]
                : (collectionGeneratorStrategy.equals(CollectionGeneratorStrategy.SINGLETON)
                    ? 1
                    : 0);
    }

    private Collection createCollectionWithValues(Class<?> collectionType, Types<?> elementType, int maxIndex) {
        List list = new ArrayList(maxIndex);
        for (int i = 0; i < maxIndex; i++) {
            Object value =  objectForType(elementType.getType());
            list.add(i, value);
        }
        return createCollection(collectionType, list);
    }

    private Collection createCollectionWithCollection(Types<?> collectionType, Types<?> elementType, int ... sizes) {
        List list = new ArrayList();
        int maxIndex = countSize(sizes, 0);

        for (int i = 0; i < maxIndex; i++) {
            Object value;
            if (ClassUtils.isCollection(elementType.getType())) {
                value = buildCollection(new CollectionType(collectionType.getType()), elementType, decreaseDimension(sizes));
            }
            else {
                value = objectForType(elementType.getType());
            }
            list.set(i, value);
        }

        return createCollection(collectionType.getType(), list);
    }

    private Object objectForType(Class<?> type) {
        return ObjectBuilder.forType(type)
                .onPrimitiveProperty().setPrimitiveStrategy(primitiveStrategy)
                .onWrapperProperty().setWrapperStrategy(wrapperStrategy)
                .build();
    }

    private int[] decreaseDimension(int[] size) {
        return size.length > 1 ? Arrays.copyOfRange(size, 1, size.length) : new int[]{0};
    }

    private Collection createCollection(Class<?> collectionType, List values) {
        Class<? extends Collection> collectionCls = CollectionTypeRegistry.getDefaultImpl((Class<? extends Collection>) collectionType);
        return CollectionTypeRegistry.getNewCollection(collectionCls, values);
    }
}
