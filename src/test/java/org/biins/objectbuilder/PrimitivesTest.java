package org.biins.objectbuilder;

import org.biins.objectbuilder.builder.strategy.PrimitiveGeneratorStrategy;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static org.biins.objectbuilder.ConstantPool.*;

/**
 * @author Martin Janys
 */
public class PrimitivesTest {

    @DataProvider(name = "buildStrategy")
    public static Object[][] buildStrategy() {
        return new Object[][] {
                {PrimitiveGeneratorStrategy.DEFAULT},
                {PrimitiveGeneratorStrategy.MIN},
                {PrimitiveGeneratorStrategy.MAX},
                {PrimitiveGeneratorStrategy.RANDOM}
        };
    }

    @Test(dataProvider = "buildStrategy")
    public void primitiveObject(PrimitiveGeneratorStrategy buildStrategy) {
        byte b = GenerateObject.forType(byte.class)
                .onPrimitiveProperty().setGeneratorStrategy(buildStrategy)
                .build();
        short s = GenerateObject.forType(short.class)
                .onPrimitiveProperty().setGeneratorStrategy(buildStrategy)
                .build();
        int i = GenerateObject.forType(int.class)
                .onPrimitiveProperty().setGeneratorStrategy(buildStrategy)
                .build();
        long l = GenerateObject.forType(long.class)
                .onPrimitiveProperty().setGeneratorStrategy(buildStrategy)
                .build();
        float f = GenerateObject.forType(float.class)
                .onPrimitiveProperty().setGeneratorStrategy(buildStrategy)
                .build();
        double d = GenerateObject.forType(double.class)
                .onPrimitiveProperty().setGeneratorStrategy(buildStrategy)
                .build();
        char c = GenerateObject.forType(char.class)
                .onPrimitiveProperty().setGeneratorStrategy(buildStrategy)
                .build();
        boolean bool = GenerateObject.forType(boolean.class)
                .onPrimitiveProperty().setGeneratorStrategy(buildStrategy)
                .build();

        switch (buildStrategy) {
            case DEFAULT:
                assertEquals(b, BYTE_DEFAULT);
                assertEquals(s, SHORT_DEFAULT);
                assertEquals(i, INT_DEFAULT);
                assertEquals(l, LONG_DEFAULT);
                assertEquals(f, FLOAT_DEFAULT);
                assertEquals(d, DOUBLE_DEFAULT);
                assertEquals(c, CHAR_DEFAULT);
                assertEquals(bool, BOOLEAN_DEFAULT);
                break;
            case MIN:
                assertEquals(b, Byte.MIN_VALUE);
                assertEquals(s, Short.MIN_VALUE);
                assertEquals(i, Integer.MIN_VALUE);
                assertEquals(l, Long.MIN_VALUE);
                assertEquals(f, Float.MIN_VALUE);
                assertEquals(d, Double.MIN_VALUE);
                assertEquals(c, Character.MIN_VALUE);
                assertEquals(bool, (boolean) Boolean.FALSE);
                break;
            case MAX:
                assertEquals(b, Byte.MAX_VALUE);
                assertEquals(s, Short.MAX_VALUE);
                assertEquals(i, Integer.MAX_VALUE);
                assertEquals(l, Long.MAX_VALUE);
                assertEquals(f, Float.MAX_VALUE);
                assertEquals(d, Double.MAX_VALUE);
                assertEquals(c, Character.MAX_VALUE);
                assertEquals(bool, (boolean) Boolean.TRUE);
                break;
            case RANDOM:
                break;
        }

    }
}
