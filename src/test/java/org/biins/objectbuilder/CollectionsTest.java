package org.biins.objectbuilder;

import org.biins.objectbuilder.builder.strategy.ArrayGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.CollectionGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.PrimitiveGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.WrapperGeneratorStrategy;
import org.biins.objectbuilder.types.Type;
import org.biins.objectbuilder.types.Types;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

import static org.biins.objectbuilder.ConstantPool.*;
import static org.testng.Assert.*;

/**
 * @author Martin Janys
 */
@SuppressWarnings("unchecked")
public class CollectionsTest {

    @DataProvider(name = "buildStrategy")
    public static Object[][] buildStrategy() {
        return new Object[][] {
                {CollectionGeneratorStrategy.DEFAULT},
                {CollectionGeneratorStrategy.SINGLETON},
                {CollectionGeneratorStrategy.NULL},
                {CollectionGeneratorStrategy.VALUE}
        };
    }

    @DataProvider(name = "buildStrategyForValue")
    public static Object[][] buildStrategyForValue() {
        return new Object[][] {
                {CollectionGeneratorStrategy.VALUE, PrimitiveGeneratorStrategy.DEFAULT, WrapperGeneratorStrategy.DEFAULT},
                {CollectionGeneratorStrategy.VALUE, PrimitiveGeneratorStrategy.MIN, WrapperGeneratorStrategy.MIN},
                {CollectionGeneratorStrategy.VALUE, PrimitiveGeneratorStrategy.MAX, WrapperGeneratorStrategy.MAX}
        };
    }

    @Test(dataProvider = "buildStrategy", expectedExceptions = IllegalArgumentException.class)
    public void collectionSizeIllegalArgument(CollectionGeneratorStrategy buildStrategy) {
        GenerateObject.forType(ArrayList.class)
                .onCollectionProperty().setCollectionStrategy(buildStrategy).setSize(-1)
                .build();
    }

    @Test(dataProvider = "buildStrategy", expectedExceptions = IllegalArgumentException.class)
    public void collectionSizeIllegalArgument2(CollectionGeneratorStrategy buildStrategy) {
        GenerateObject.forType(List.class)
                .onCollectionProperty().setCollectionStrategy(buildStrategy).setSize(1, 2, -1)
                .build();
    }

    @Test(dataProvider = "buildStrategy", expectedExceptions = IllegalArgumentException.class)
    public void collectionSizeIllegalArgumentMissingType(CollectionGeneratorStrategy buildStrategy) {
        GenerateObject.forType(List.class)
                .onCollectionProperty().setCollectionStrategy(buildStrategy)
                .build();
    }

    public void collectionOfCollectionTypeMishmashObject(CollectionGeneratorStrategy buildStrategy) {
        // todo
    }

    @Test(dataProvider = "buildStrategy")
    public void collectionObject(CollectionGeneratorStrategy buildStrategy) {
        List<String> list = GenerateObject.forType(List.class).collectionOf(Types.typeOf(String.class))
                .onCollectionProperty().setCollectionStrategy(buildStrategy)
                .onPrimitiveProperty().setPrimitiveStrategy(PrimitiveGeneratorStrategy.DEFAULT)
                .onWrapperProperty().setWrapperStrategy(WrapperGeneratorStrategy.DEFAULT)
                .onArrayProperty().setArrayStrategy(ArrayGeneratorStrategy.DEFAULT)
                .build();
        Set<Integer> set = GenerateObject.forType(Set.class).collectionOf(Types.typeOf(Integer.class))
                .onWrapperProperty().setWrapperStrategy(WrapperGeneratorStrategy.DEFAULT)
                .onCollectionProperty().setCollectionStrategy(buildStrategy).setSize(2)
                .onPrimitiveProperty().setPrimitiveStrategy(PrimitiveGeneratorStrategy.DEFAULT)
                .onArrayProperty().setArrayStrategy(ArrayGeneratorStrategy.DEFAULT)
                .build();
        Queue<Double> queue = GenerateObject.forType(Queue.class).collectionOf(Types.typeOf(Double.class))
                .onWrapperProperty().setWrapperStrategy(WrapperGeneratorStrategy.DEFAULT)
                .onPrimitiveProperty().setPrimitiveStrategy(PrimitiveGeneratorStrategy.DEFAULT)
                .onCollectionProperty().setCollectionStrategy(buildStrategy).setSize(3)
                .onArrayProperty().setArrayStrategy(ArrayGeneratorStrategy.DEFAULT)
                .build();
        Queue<Boolean> dequeue = GenerateObject.forType(Queue.class).collectionOf(Types.typeOf(Boolean.class))
                .onWrapperProperty().setWrapperStrategy(WrapperGeneratorStrategy.DEFAULT)
                .onPrimitiveProperty().setPrimitiveStrategy(PrimitiveGeneratorStrategy.DEFAULT)
                .onCollectionProperty().setCollectionStrategy(buildStrategy).setSize(2)
                .onArrayProperty().setArrayStrategy(ArrayGeneratorStrategy.DEFAULT)
                .build();
        Collection<Short> collection = GenerateObject.forType(Collection.class).collectionOf(Types.typeOf(Short.class))
                .onWrapperProperty().setWrapperStrategy(WrapperGeneratorStrategy.DEFAULT)
                .onPrimitiveProperty().setPrimitiveStrategy(PrimitiveGeneratorStrategy.DEFAULT)
                .onCollectionProperty().setCollectionStrategy(buildStrategy).setSize(3)
                .onArrayProperty().setArrayStrategy(ArrayGeneratorStrategy.DEFAULT)
                .build();

        switch (buildStrategy) {
            case VALUE:
                assertEquals(list, Collections.emptyList());
                assertEquals(set, new HashSet<>(Arrays.asList(0, 0)));
                assertEquals(queue, new LinkedList<>(Arrays.asList(0d, 0d, 0d)));
                assertEquals(dequeue, new LinkedList<>(Arrays.asList(false, false)));
                assertEquals(collection, new ArrayList<>(Arrays.asList((short)0, (short)0, (short)0)));
                break;
            case SINGLETON:
                assertEquals(list, Collections.singletonList(""));
                assertEquals(set, Collections.singleton(0));
                assertEquals(queue, new LinkedList<>(Collections.singleton(0d)));
                assertEquals(dequeue, new LinkedList<>(Collections.singleton(false)));
                assertEquals(collection, Collections.singletonList((short)0));
                break;
            case DEFAULT:
                assertEquals(list, Collections.emptyList());
                assertEquals(set, Collections.emptySet());
                assertEquals(queue, new LinkedList<>());
                assertEquals(dequeue, new LinkedList<>());
                assertEquals(collection, Collections.emptyList());
                break;
            case NULL:
                assertNull(list);
                assertNull(set);
                assertNull(queue);
                assertNull(dequeue);
                assertNull(collection);
                break;
        }

    }

    @Test(dataProvider = "buildStrategy")
    public void collectionOfCollectionObject(CollectionGeneratorStrategy buildStrategy) {
        List<List<String>> list = GenerateObject.forType(List.class)
                .collectionOf(Types.typeOf(List.class).of(String.class))
                .onCollectionProperty().setCollectionStrategy(buildStrategy)
                .build();
        Set<Set<Integer>> set = GenerateObject.forType(Set.class)
                .collectionOf(Types.typeOf(Set.class).of(Integer.class))
                .onCollectionProperty().setCollectionStrategy(buildStrategy)
                .build();
        Queue<Queue<Double>> queue = GenerateObject.forType(Queue.class)
                .collectionOf(Types.typeOf(Queue.class).of(Double.class))
                .onCollectionProperty().setCollectionStrategy(buildStrategy)
                .build();
        List<Set<Float>> listSet = GenerateObject.forType(List.class)
                .collectionOf(Types.typeOf(Set.class).of(Float.class))
                .onCollectionProperty().setCollectionStrategy(buildStrategy)
                .build();
        Collection<List<Collection<Boolean>>> collectionOfCollection = GenerateObject.forType(List.class)
                .collectionOf(Types.typeOf(List.class).of(Collection.class).of(Boolean.class))
                .onCollectionProperty().setCollectionStrategy(buildStrategy)
                .build();

        switch (buildStrategy) {
            case DEFAULT:
                break;
            case VALUE:
                break;
            case SINGLETON:
                assertEquals(list, Collections.singletonList(
                        Collections.singletonList("")
                ));
                assertEquals(set, Collections.singleton(
                        Collections.singleton(0)
                ));
                assertEquals(queue, new LinkedList<>(
                        Arrays.asList(
                                new LinkedList<>(
                                        Arrays.asList(0d)
                                )
                        )
                ));
                assertEquals(listSet, Collections.singletonList(
                        Collections.singleton(0f)
                ));
                assertEquals(collectionOfCollection, Collections.singletonList(
                        Collections.singletonList(
                                Collections.singletonList(false)
                        )
                ));
                break;
            case NULL:
                assertNull(list);
                assertNull(set);
                assertNull(queue);
                assertNull(listSet);
                assertNull(collectionOfCollection);
                break;
        }

    }

}
