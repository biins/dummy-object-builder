package org.biins.objectbuilder;

import org.biins.objectbuilder.builder.strategy.PrimitiveGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.WrapperGeneratorStrategy;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.biins.objectbuilder.ConstantPool.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * @author Martin Janys
 */
public class PrimitiveWrappersTest {

    @DataProvider(name = "buildStrategy")
    public static Object[][] buildStrategy() {
        return new Object[][] {
                {WrapperGeneratorStrategy.MIN},
                {WrapperGeneratorStrategy.DEFAULT},
                {WrapperGeneratorStrategy.MAX},
                {WrapperGeneratorStrategy.NULL},
                {WrapperGeneratorStrategy.RANDOM}
        };
    }

    @Test(dataProvider = "buildStrategy")
    public void wrapperObject(WrapperGeneratorStrategy buildStrategy) {
        Byte b = DummyObject.forType(Byte.class)
                .onPrimitiveProperty().setPrimitiveStrategy(PrimitiveGeneratorStrategy.DEFAULT)
                .onWrapperProperty().setWrapperStrategy(buildStrategy)
                .build();
        Short s = DummyObject.forType(Short.class)
                .onPrimitiveProperty().setPrimitiveStrategy(PrimitiveGeneratorStrategy.DEFAULT)
                .onWrapperProperty().setWrapperStrategy(buildStrategy)
                .build();
        Integer i = DummyObject.forType(Integer.class)
                .onWrapperProperty().setWrapperStrategy(buildStrategy)
                .build();
        Long l = DummyObject.forType(Long.class)
                .onWrapperProperty().setWrapperStrategy(buildStrategy)
                .build();
        Float f = DummyObject.forType(Float.class)
                .onWrapperProperty().setWrapperStrategy(buildStrategy)
                .build();
        Double d = DummyObject.forType(Double.class)
                .onWrapperProperty().setWrapperStrategy(buildStrategy)
                .build();
        Character c = DummyObject.forType(Character.class)
                .onWrapperProperty().setWrapperStrategy(buildStrategy)
                .build();
        Boolean bool = DummyObject.forType(Boolean.class)
                .onWrapperProperty().setWrapperStrategy(buildStrategy)
                .build();

        switch (buildStrategy) {
            case DEFAULT:
                assertEquals(b, BYTE_WRAPPER_DEFAULT);
                assertEquals(s, SHORT_WRAPPER_DEFAULT);
                assertEquals(i, INTEGER_WRAPPER_DEFAULT);
                assertEquals(l, LONG_WRAPPER_DEFAULT);
                assertEquals(f, FLOAT_WRAPPER_DEFAULT);
                assertEquals(d, DOUBLE_WRAPPER_DEFAULT);
                assertEquals(c, CHARACTER_WRAPPER_DEFAULT);
                assertEquals(bool, BOOLEAN_WRAPPER_DEFAULT);
                break;
            case MIN:
                assertEquals(b, (Byte) Byte.MIN_VALUE);
                assertEquals(s, (Short) Short.MIN_VALUE);
                assertEquals(i, (Integer) Integer.MIN_VALUE);
                assertEquals(l, (Long) Long.MIN_VALUE);
                assertEquals(f, (Float) Float.MIN_VALUE);
                assertEquals(d, (Double) Double.MIN_VALUE);
                assertEquals(c, (Character) Character.MIN_VALUE);
                assertEquals(bool, (Boolean) Boolean.FALSE);
                break;
            case MAX:
                assertEquals(b, (Byte) Byte.MAX_VALUE);
                assertEquals(s, (Short) Short.MAX_VALUE);
                assertEquals(i, (Integer) Integer.MAX_VALUE);
                assertEquals(l, (Long) Long.MAX_VALUE);
                assertEquals(f, (Float) Float.MAX_VALUE);
                assertEquals(d, (Double) Double.MAX_VALUE);
                assertEquals(c, (Character) Character.MAX_VALUE);
                assertEquals(bool, (Boolean) Boolean.TRUE);
                break;
            case NULL:
                assertNull(b);
                assertNull(s);
                assertNull(i);
                assertNull(l);
                assertNull(f);
                assertNull(d);
                assertNull(c);
                assertNull(bool);
                break;
            case RANDOM:
                break;
        }

    }
}
