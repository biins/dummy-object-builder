package org.biins.objectbuilder.types.map;

import java.util.*;

/**
 * @author Martin Janys
 */
public class MapTypeRegistry {

    private static final Map<Class<? extends Map>, Class<? extends Map>> MAP_IMPL_TYPES = new LinkedHashMap<>();
    static {
        MAP_IMPL_TYPES.put(SortedMap.class, TreeMap.class);
        MAP_IMPL_TYPES.put(Map.class, HashMap.class);
    }

    private static final Map<Class<? extends Map>, Map> MAP_DEFAULT = new LinkedHashMap<>();
    static {
        MAP_DEFAULT.put(SortedMap.class, Collections.emptyMap());
        MAP_DEFAULT.put(Map.class, Collections.emptyMap());
    }

    @SuppressWarnings("unchecked")
    public static MapType get(Class<Map> type) {
        return new MapType(type);
    }

    public static Map getDefault(Class<? extends Map> type) {
        for (Class<? extends Map> collectionType : MAP_DEFAULT.keySet()) {
            if (type.isAssignableFrom(collectionType)) {
                return MAP_DEFAULT.get(collectionType);
            }
        }

        throw new IllegalStateException("Unknown collection type " + type);
    }

    public static Class<? extends Map> getDefaultImpl(Class<? extends Map> type) {
        for (Class<? extends Map> collectionType : MAP_IMPL_TYPES.keySet()) {
            if (type.equals(collectionType)) {
                return MAP_IMPL_TYPES.get(collectionType);
            }
        }

        if (Map.class.isAssignableFrom(type)) {
            return HashMap.class;
        }

        throw new IllegalStateException("Unknown collection type " + type);

    }

    @SuppressWarnings("unchecked")
    public static Map getNewMap(Class<? extends Map> collectionType, Map map) {
        if (HashMap.class.isAssignableFrom(collectionType)) {
            return new HashMap(map);
        }
        else if (TreeMap.class.isAssignableFrom(collectionType)) {
            return new TreeMap<>(map);
        }
        else {
            throw new IllegalArgumentException("Not supported collection impl class");
        }
    }

}
