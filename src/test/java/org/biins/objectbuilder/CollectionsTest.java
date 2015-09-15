package org.biins.objectbuilder;

import org.biins.objectbuilder.builder.ObjectBuilder;
import org.biins.objectbuilder.builder.strategy.ArrayGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.CollectionGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.PrimitiveGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.WrapperGeneratorStrategy;
import org.biins.objectbuilder.types.Types;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

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
        new ObjectBuilder()
                .onCollection().setGeneratorStrategy(buildStrategy).setSize(-1)
                .build(ArrayList.class);
    }

    @Test(dataProvider = "buildStrategy", expectedExceptions = IllegalArgumentException.class)
    public void collectionSizeIllegalArgument2(CollectionGeneratorStrategy buildStrategy) {
        new ObjectBuilder()
                .onCollection().setGeneratorStrategy(buildStrategy).setSize(1, 2, -1)
                .build(List.class);
    }

    @Test(dataProvider = "buildStrategy")
    public void collectionSizeIllegalArgumentMissingType(CollectionGeneratorStrategy buildStrategy) {
        List<String> list = new ObjectBuilder()
                .onCollection().setGeneratorStrategy(buildStrategy).setSize(1)
                .build(List.class);

        switch (buildStrategy) {
            case NULL:
                assertNull(list);
                break;
            default:
                assertEquals(list, Collections.emptyList());
        }
    }

    @Test(dataProvider = "buildStrategy")
    public void collectionOfCollectionTypeMishmashObject(CollectionGeneratorStrategy buildStrategy) {
        List<String> list = new ObjectBuilder()
                .onCollection().setGeneratorStrategy(buildStrategy).setSize(1).of(Types.typeOf(Integer.class))
                .build(List.class);

        switch (buildStrategy) {
            case DEFAULT:
                assertEquals(list, Collections.emptyList());
                break;
            case SINGLETON:
            case VALUE:
                assertEquals(list.size(), 1);
                assertEquals(list, Collections.singleton(0));
                try {
                    String s = list.get(0);
                    fail();
                }
                catch (ClassCastException ignored) {
                }
                break;
            case NULL:
                assertNull(list);
                break;
        }
    }

    @Test(dataProvider = "buildStrategy")
    public void collectionOfCollectionTypeMissingObject(CollectionGeneratorStrategy buildStrategy) {
        List<String> list = new ObjectBuilder()
                .onCollection().setGeneratorStrategy(buildStrategy).setSize(1)
                .build(List.class);

        List<Collection<Integer>> listOfCollections = new ObjectBuilder()
                .onCollection().setGeneratorStrategy(buildStrategy).setSize(1).of(Types.typeOf(Collection.class))
                .build(List.class);

        switch (buildStrategy) {
            case SINGLETON:
            case VALUE:
                assertEquals(list, Collections.emptyList());
                assertEquals(listOfCollections, Collections.singletonList(Collections.emptyList()));
                break;
            case DEFAULT:
                assertEquals(list, Collections.emptyList());
                assertEquals(listOfCollections, Collections.emptyList());
                break;
            case NULL:
                assertNull(list);
                assertNull(listOfCollections);
                break;
        }

    }

    @Test(dataProvider = "buildStrategy")
    public void collectionObject(CollectionGeneratorStrategy buildStrategy) {
        List<String> list = new ObjectBuilder()
                .onCollection().setGeneratorStrategy(buildStrategy).of(Types.typeOf(String.class))
                .onPrimitive().setGeneratorStrategy(PrimitiveGeneratorStrategy.DEFAULT)
                .onWrapper().setGeneratorStrategy(WrapperGeneratorStrategy.DEFAULT)
                .onArray().setGeneratorStrategy(ArrayGeneratorStrategy.DEFAULT)
                .build(List.class);
        Set<Integer> set = new ObjectBuilder()
                .onWrapperProperty().setGeneratorStrategy(WrapperGeneratorStrategy.DEFAULT)
                .onCollection().setGeneratorStrategy(buildStrategy).setSize(2).of(Types.typeOf(Integer.class))
                .onPrimitive().setGeneratorStrategy(PrimitiveGeneratorStrategy.DEFAULT)
                .onArray().setGeneratorStrategy(ArrayGeneratorStrategy.DEFAULT)
                .build(Set.class);
        Queue<Double> queue = new ObjectBuilder()
                .onWrapperProperty().setGeneratorStrategy(WrapperGeneratorStrategy.DEFAULT)
                .onPrimitive().setGeneratorStrategy(PrimitiveGeneratorStrategy.DEFAULT)
                .onCollection().setGeneratorStrategy(buildStrategy).setSize(3).of(Types.typeOf(Double.class))
                .onArray().setGeneratorStrategy(ArrayGeneratorStrategy.DEFAULT)
                .build(Queue.class);
        Queue<Boolean> dequeue = new ObjectBuilder()
                .onWrapperProperty().setGeneratorStrategy(WrapperGeneratorStrategy.DEFAULT)
                .onPrimitive().setGeneratorStrategy(PrimitiveGeneratorStrategy.DEFAULT)
                .onCollection().setGeneratorStrategy(buildStrategy).setSize(2).of(Types.typeOf(Boolean.class))
                .onArray().setGeneratorStrategy(ArrayGeneratorStrategy.DEFAULT)
                .build(Queue.class);
        Collection<Short> collection = new ObjectBuilder()
                .onWrapperProperty().setGeneratorStrategy(WrapperGeneratorStrategy.DEFAULT)
                .onPrimitive().setGeneratorStrategy(PrimitiveGeneratorStrategy.DEFAULT)
                .onCollection().setGeneratorStrategy(buildStrategy).setSize(3).of(Types.typeOf(Short.class))
                .onArray().setGeneratorStrategy(ArrayGeneratorStrategy.DEFAULT)
                .build(Collection.class);

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
        List<List<String>> list = new ObjectBuilder()
                .onCollection().setGeneratorStrategy(buildStrategy).of(Types.typeOf(List.class).of(String.class))
                .build(List.class);
        Set<Set<Integer>> set = new ObjectBuilder()
                .onCollection().setGeneratorStrategy(buildStrategy).of(Types.typeOf(Set.class).of(Integer.class))
                .build(Set.class);
        Queue<Queue<Double>> queue = new ObjectBuilder()
                .onCollection().setGeneratorStrategy(buildStrategy).of(Types.typeOf(Queue.class).of(Double.class))
                .build(Queue.class);
        List<Set<Float>> listSet = new ObjectBuilder()
                .onCollection().setGeneratorStrategy(buildStrategy).of(Types.typeOf(Set.class).of(Float.class))
                .build(List.class);
        Collection<List<Collection<Boolean>>> collectionOfCollection = new ObjectBuilder()
                .onCollection().setGeneratorStrategy(buildStrategy).of(Types.typeOf(List.class).of(Collection.class).of(Boolean.class))
                .build(List.class);

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
