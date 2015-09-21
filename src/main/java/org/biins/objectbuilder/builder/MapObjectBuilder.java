package org.biins.objectbuilder.builder;

import org.apache.commons.lang.Validate;
import org.biins.objectbuilder.builder.strategy.MapGeneratorStrategy;
import org.biins.objectbuilder.types.Types;
import org.biins.objectbuilder.types.map.MapType;
import org.biins.objectbuilder.types.map.MapTypeRegistry;
import org.biins.objectbuilder.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Martin Janys
 */
@SuppressWarnings("unchecked")
public class MapObjectBuilder extends AbstractBuilder implements Builder {

    private final ObjectBuilder objectBuilder;

    private MapGeneratorStrategy mapGeneratorStrategy = MapGeneratorStrategy.DEFAULT;
    private int size = 0;

    private Types keyType;
    private Types valueType;

    public MapObjectBuilder(ObjectBuilder objectBuilder) {
        this.objectBuilder = objectBuilder;
    }

    public MapObjectBuilder setSize(int size) {
        this.size = size;
        validateSize();
        return this;
    }

    private void validateSize() {
        if (size < 0) {
            throw new IllegalArgumentException("Size must be positive");
        }
    }

    public MapObjectBuilder setGeneratorStrategy(MapGeneratorStrategy mapGeneratorStrategy) {
        this.mapGeneratorStrategy = mapGeneratorStrategy;
        return this;
    }

    public MapObjectBuilder ofKey(Types keyType) {
        this.keyType = keyType;
        return this;
    }

    public MapObjectBuilder ofValue(Types valueType) {
        this.valueType = valueType;
        return this;
    }

    @Override
    public <T> T build(Class<T> type) {
        return buildMap(type);
    }

    public <T> T buildMap(Class<T> type) {
        return buildMap(type, keyType, valueType, size);
    }

    private <T> T buildMap(Class<T> type,  Types keyType, Types valueType, int size) {
        Validate.isTrue(Map.class.isAssignableFrom(type), "Map is required");
        MapType mapType = MapTypeRegistry.get((Class<Map>) type);
        return buildMap(mapType, keyType, valueType, size);
    }

    private <T> T buildMap(MapType mapType, Types keyType, Types valueType, int size) {
        switch (mapGeneratorStrategy) {
            case NULL:
                return null;
            case VALUE:
                return (T) buildMapInternal(mapType.getType(), keyType, valueType, size);
            case SINGLETON:
                return (T) buildMapInternal(mapType.getType(), keyType, valueType, 1);
            case DEFAULT:
            default:
                return (T) mapType.getDefaultValue();
        }
    }

    private Map buildMapInternal(Class<?> mapType, Types keyType, Types valueType, int size) {
        return createMap(mapType, keyType, valueType, size);
    }

    private Map createMap(Class<?> mapType, Types keyType, Types valueType, int size) {
        int mapSize = countSize(size);
        Map map = new HashMap<>();
        if (keyType != null) {
            for (int i = 0; i < mapSize; i++) {
                Object key = createObject(keyType);
                Object value = createObject(valueType);
                map.put(key, value);
            }
        }

        return createMapOfType(mapType, map);
    }

    private Object createObject(Types type) {
        if (type == null) {
            return null;
        }
        if (ClassUtils.isCollection(type.getType())) {
            return objectBuilder.onCollection().buildCollection(type.getType(), type.next());
        }
        else {
            return objectBuilder.build(type.getType());
        }
    }

    private int countSize(int size) {
        return mapGeneratorStrategy.equals(MapGeneratorStrategy.SINGLETON) ? 1 : size;
    }


    private Map createMapOfType(Class mapType, Map values) {
        Class<? extends Map> mapCls = MapTypeRegistry.getDefaultImpl((Class<? extends Map>) mapType);
        return MapTypeRegistry.getNewMap(mapCls, values);
    }
}
