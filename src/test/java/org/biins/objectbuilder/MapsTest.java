package org.biins.objectbuilder;

import org.biins.objectbuilder.builder.ObjectBuilder;
import org.biins.objectbuilder.builder.strategy.MapGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.StringGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.WrapperGeneratorStrategy;
import org.biins.objectbuilder.types.Types;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import static org.testng.Assert.*;

/**
 * @author Martin Janys
 */
@SuppressWarnings("unchecked")
public class MapsTest {

    public boolean isUUID(String string) {
        try {
            UUID.fromString(string);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @DataProvider(name = "buildStrategy")
    public static Object[][] buildStrategy() {
        return new Object[][] {
                {MapGeneratorStrategy.DEFAULT},
                {MapGeneratorStrategy.NULL},
                {MapGeneratorStrategy.SINGLETON},
                {MapGeneratorStrategy.VALUE}
        };
    }

    @Test(dataProvider = "buildStrategy")
    public void string(MapGeneratorStrategy buildStrategy) {
        Map<String, Integer> map = new ObjectBuilder()
                .onString(StringGeneratorStrategy.UUID)
                .onWrapper(WrapperGeneratorStrategy.MIN)
                .onMap(buildStrategy).setSize(2)
                    .ofKey(Types.typeOf(String.class))
                    .ofValue(Types.typeOf(Integer.class))
                .build(Map.class);

        switch (buildStrategy) {
            case SINGLETON:
                assertEquals(map.size(), 1);
                String key = map.keySet().iterator().next();
                assertTrue(isUUID(key));
                assertEquals(map.get(key), (Integer) Integer.MIN_VALUE);
                break;
            case NULL:
                assertNull(map);
                break;
            case VALUE:
                assertEquals(map.size(), 2);
                Iterator<String> iterator = map.keySet().iterator();
                String key1 = iterator.next();
                String key2 = iterator.next();
                assertTrue(isUUID(key1));
                assertTrue(isUUID(key2));
                assertEquals(map.get(key1), (Integer) Integer.MIN_VALUE);
                assertEquals(map.get(key2), (Integer) Integer.MIN_VALUE);
                break;
            case DEFAULT:
            default:
                assertEquals(map, Collections.emptyMap());
        }
    }
}
