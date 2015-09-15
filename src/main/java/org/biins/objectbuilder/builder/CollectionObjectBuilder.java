package org.biins.objectbuilder.builder;

import org.biins.objectbuilder.builder.strategy.CollectionGeneratorStrategy;
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
public class CollectionObjectBuilder<T> extends AbstractCompositeBuilder<T, CollectionObjectBuilder> implements Builder<T> {

    protected int[] size = new int[]{0};

    private Types<?> elementType;

    protected CollectionObjectBuilder(Class<T> cls) {
        super(cls);
    }

    static <T> CollectionObjectBuilder<T> forType(Class<T> cls) {
        return new CollectionObjectBuilder<>(cls);
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
        return buildCollectionInternal(collectionType.getType(), elementType, sizes);
    }

    private T buildCollectionInternal(Class<T> collectionType, Types elementType, int ... sizes) {
        Collection collection = createCollection(collectionType, elementType, sizes);
        return (T) collection;
    }

    private Collection createCollection(Class<?> collectionType, Types elementType, int[] sizes) {
        int size = countSize(sizes);
        List list = new ArrayList(size);
        if (elementType != null) {
            for (int i = 0; i < size; i++) {
                Object value = createObject(elementType, sizes);
                list.add(i, value);
            }
        }

        return createCollectionOfType(collectionType, list);
    }


    protected Object createObject(Types type, int ... size) {
        if (ClassUtils.isComposite(type.getType())) {
            return createCompositeObject(type.getType(), type.next(), size);
        }
        else {
            return createRawObject(type.getType());
        }
    }

    protected Object createCompositeObject(Class<?> type, Types of, int ... size) {
        return ObjectBuilder.forType(type)
                .onArrayProperty()
                    .setGeneratorStrategy(primitiveStrategy)
                    .setGeneratorStrategy(wrapperStrategy)
                    .setGeneratorStrategy(stringGeneratorStrategy)
                    .setGeneratorStrategy(collectionGeneratorStrategy)
                    .setGeneratorStrategy(arrayStrategy)
                    .setSize(decreaseDimension(size))
                .onCollectionProperty()
                    .setGeneratorStrategy(primitiveStrategy)
                    .setGeneratorStrategy(wrapperStrategy)
                    .setGeneratorStrategy(stringGeneratorStrategy)
                    .setGeneratorStrategy(collectionGeneratorStrategy)
                    .setGeneratorStrategy(arrayStrategy)
                    .setSize(decreaseDimension(size))
                .and().collectionOf(of)
                .build();
    }

    private int countSize(int[] sizes) {
        return sizes.length > 0
                ? sizes[0]
                : (collectionGeneratorStrategy.equals(CollectionGeneratorStrategy.SINGLETON)
                    ? 1
                    : 0);
    }

    protected int[] decreaseDimension(int[] size) {
        return size.length > 1 ? Arrays.copyOfRange(size, 1, size.length) : new int[]{collectionGeneratorStrategy.equals(CollectionGeneratorStrategy.SINGLETON) ? 1 : 0};
    }

    private Collection createCollectionOfType(Class<?> collectionType, List values) {
        Class<? extends Collection> collectionCls = CollectionTypeRegistry.getDefaultImpl((Class<? extends Collection>) collectionType);
        return CollectionTypeRegistry.getNewCollection(collectionCls, values);
    }
}
