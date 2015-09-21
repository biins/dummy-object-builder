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
public class CollectionObjectBuilder extends AbstractCompositeBuilder implements Builder {

    private CollectionGeneratorStrategy collectionGeneratorStrategy = CollectionGeneratorStrategy.DEFAULT;
    private int[] size = new int[]{0};

    private Types elementType;

    public CollectionObjectBuilder(ObjectBuilder objectBuilder) {
        super(objectBuilder);
    }

    public CollectionObjectBuilder setSize(int ... size) {
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

    public CollectionObjectBuilder setGeneratorStrategy(CollectionGeneratorStrategy collectionStrategy) {
        this.collectionGeneratorStrategy = collectionStrategy;
        return this;
    }

    public CollectionObjectBuilder of(Types elementType) {
        this.elementType = elementType;
        return this;
    }

    @Override
    public <T> T build(Class<T> type) {
        return buildCollection(type);
    }

    public <T> T buildCollection(Class<T> type) {
        CollectionType<T> collectionType = CollectionTypeRegistry.get(type);
        return buildCollection(collectionType, elementType, size);
    }

    public <T> T buildCollection(Class<T> type, Types elementType) {
        CollectionType<T> collectionType = CollectionTypeRegistry.get(type);
        return buildCollection(collectionType, elementType, size);
    }

    public  <T> T buildCollection(Class<T> type, Types elementType, int ... size) {
        CollectionType<T> collectionType = CollectionTypeRegistry.get(type);
        return buildCollection(collectionType, elementType, size);
    }

    private <T> T buildCollection(CollectionType<T> collectionType, Types elementType, int ... size) {
        switch (collectionGeneratorStrategy) {
            case NULL:
                return null;
            case VALUE:
                return buildCollectionInternal(collectionType.getType(), elementType, size);
            case SINGLETON:
                this.size = new int[]{1};
                return buildCollectionInternal(collectionType.getType(), elementType);
            case DEFAULT:
            default:
                return collectionType.getDefaultValue();
        }
    }

    private <T> T buildCollectionInternal(Class<T> collectionType, Types elementType) {
        return buildCollectionInternal(collectionType, elementType, size);
    }

    private <T> T buildCollectionInternal(Class<T> collectionType, Types elementType, int ... size) {
        Collection collection = createCollection(collectionType, elementType, size);
        return (T) collection;
    }

    private Collection createCollection(Class<?> collectionType, Types elementType, int ... size) {
        int collectionSize = countSize(size, 0);
        List list = new ArrayList(collectionSize);
        if (elementType != null) {
            for (int i = 0; i < collectionSize; i++) {
                Object value;
                if (ClassUtils.isSameCompositeType(collectionType, elementType.getType())) {
                    value = createCompositeObject(elementType, decreaseDimension(size));
                }
                else {
                    value = createCompositeObject(elementType);
                }
                list.add(i, value);
            }
        }

        return createCollectionOfType(collectionType, list);
    }

    private int countSize(int[] sizes, int at) {
        return sizes.length > at
                ? sizes[at]
                : (collectionGeneratorStrategy.equals(CollectionGeneratorStrategy.SINGLETON)
                    ? 1
                    : 0);
    }


    private Collection createCollectionOfType(Class<?> collectionType, List values) {
        Class<? extends Collection> collectionCls = CollectionTypeRegistry.getDefaultImpl((Class<? extends Collection>) collectionType);
        return CollectionTypeRegistry.getNewCollection(collectionCls, values);
    }
}
