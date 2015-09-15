package org.biins.objectbuilder;

import org.biins.objectbuilder.builder.ObjectBuilder;
import org.biins.objectbuilder.builder.strategy.ArrayGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.CollectionGeneratorStrategy;
import org.biins.objectbuilder.types.Types;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.fail;

/**
 * @author Martin Janys
 */
public class ArrayCollectionsTest {

    @DataProvider(name = "buildStrategy")
    public static Object[][] buildStrategy() {
        return new Object[][] {
                {CollectionGeneratorStrategy.DEFAULT, ArrayGeneratorStrategy.DEFAULT},
                {CollectionGeneratorStrategy.NULL, ArrayGeneratorStrategy.NULL},
                {CollectionGeneratorStrategy.VALUE, ArrayGeneratorStrategy.VALUE}
        };
    }

    @SuppressWarnings("unchecked")
    @Test(dataProvider = "buildStrategy")
    public void arrayOfCollections(CollectionGeneratorStrategy collectionStrategy, ArrayGeneratorStrategy arrayStrategy) {
        List<Integer>[] arrayOfLists = new ObjectBuilder()
                .onCollection().setGeneratorStrategy(collectionStrategy).setSize(1).of(Types.typeOf(Integer.class))
                .onArray().setGeneratorStrategy(arrayStrategy).setSize(2)
                .build(List[].class);

        List<String[]> listOfArrays = new ObjectBuilder()
                .onCollection().setGeneratorStrategy(collectionStrategy).setSize(1).of(Types.typeOf(String[].class))
                .onArray().setGeneratorStrategy(arrayStrategy).setSize(2)
                .build(List.class);

        switch (collectionStrategy) {
            case DEFAULT:
                assertEquals(arrayOfLists, new List[]{});
                break;
            case VALUE:
                assertEquals(arrayOfLists, new List[]{
                        Collections.singletonList(0),
                        Collections.singletonList(0)
                });
                break;
            case NULL:
                assertNull(arrayOfLists);
                assertNull(listOfArrays);
                break;
        }
    }
}
