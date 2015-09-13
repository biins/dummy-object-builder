package org.biins.objectbuilder.types.collection;

import java.util.*;

/**
 * @author Martin Janys
 */
public class CollectionTypeRegistry {

    private static final Map<Class<? extends Collection>, Class<? extends Collection>> COLLECTION_IMPL_TYPES = new LinkedHashMap<>();
    static {
        COLLECTION_IMPL_TYPES.put(List.class, ArrayList.class);
        COLLECTION_IMPL_TYPES.put(Set.class, LinkedHashSet.class);
        COLLECTION_IMPL_TYPES.put(Queue.class, LinkedList.class);
        COLLECTION_IMPL_TYPES.put(Deque.class, LinkedList.class);
        COLLECTION_IMPL_TYPES.put(Collection.class, ArrayList.class);
    }

    private static final Map<Class<? extends Collection>, Collection> COLLECTION_DEFAULT = new LinkedHashMap<>();
    static {
        COLLECTION_DEFAULT.put(List.class, Collections.emptyList());
        COLLECTION_DEFAULT.put(Set.class, Collections.emptySet());
        COLLECTION_DEFAULT.put(Queue.class, new LinkedList());
        COLLECTION_DEFAULT.put(Deque.class, new LinkedList());
        COLLECTION_DEFAULT.put(Collection.class, Collections.emptyList());
    }

    // todo iterator, iterable, enumeration

    @SuppressWarnings("unchecked")
    public static <T> CollectionType<T> get(Class<T> type) {
        return new CollectionType<>(type);
    }

    public static Collection getDefault(Class<? extends Collection> type) {
        for (Class<? extends Collection> collectionType : COLLECTION_DEFAULT.keySet()) {
            if (type.isAssignableFrom(collectionType)) {
                return COLLECTION_DEFAULT.get(collectionType);
            }
        }

        throw new IllegalStateException("Unknown collection type " + type);
    }

    public static Class<? extends Collection> getDefaultImpl(Class<? extends Collection> type) {
        for (Class<? extends Collection> collectionType : COLLECTION_IMPL_TYPES.keySet()) {
            if (type.isAssignableFrom(collectionType)) {
                return COLLECTION_IMPL_TYPES.get(collectionType);
            }
        }

        throw new IllegalStateException("Unknown collection type " + type);

    }

    @SuppressWarnings("unchecked")
    public static Collection<?> getNewCollection(Class<? extends Collection> collectionType, Collection values) {
        int size = values.size();

        if (ArrayList.class.isAssignableFrom(collectionType)) {
            List list = new ArrayList<>(size);
            list.addAll(values);
            return list;
        }
        else if (LinkedList.class.isAssignableFrom(collectionType)) {
            List list = new ArrayList<>(size);
            list.addAll(values);
            return new LinkedList<>(list);
        }
        else if (LinkedHashSet.class.isAssignableFrom(collectionType)) {
            ArrayList<Object> list = new ArrayList<>(size);
            list.addAll(values);
            return new LinkedHashSet<>(list);
        }
        else {
            throw new IllegalArgumentException("Not supported collection impl class");
        }
    }

}
