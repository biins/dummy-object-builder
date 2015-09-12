package org.biins.objectbuilder;

import org.biins.objectbuilder.builder.strategy.ArrayGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.PrimitiveGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.WrapperGeneratorStrategy;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.biins.objectbuilder.ConstantPool.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Martin Janys
 */
public class ArraysTest {

    @DataProvider(name = "buildStrategy")
    public static Object[][] buildStrategy() {
        return new Object[][] {
                {ArrayGeneratorStrategy.DEFAULT},
                {ArrayGeneratorStrategy.NULL},
                {ArrayGeneratorStrategy.VALUE}
        };
    }

    @DataProvider(name = "buildStrategyForValue")
    public static Object[][] buildStrategyForValue() {
        return new Object[][] {
                {ArrayGeneratorStrategy.VALUE, PrimitiveGeneratorStrategy.DEFAULT, WrapperGeneratorStrategy.DEFAULT},
                {ArrayGeneratorStrategy.VALUE, PrimitiveGeneratorStrategy.MIN, WrapperGeneratorStrategy.MIN},
                {ArrayGeneratorStrategy.VALUE, PrimitiveGeneratorStrategy.MAX, WrapperGeneratorStrategy.MAX}
        };
    }

    @Test(dataProvider = "buildStrategy", expectedExceptions = IllegalArgumentException.class)
    public void arraySizeIllegalArgument(ArrayGeneratorStrategy buildStrategy) {
        DummyObject.forType(Byte[].class)
                .onArrayProperty().setArrayStrategy(buildStrategy).setSize(-1)
                .build();
    }

    @Test(dataProvider = "buildStrategy", expectedExceptions = IllegalArgumentException.class)
    public void arraySizeIllegalArgument2(ArrayGeneratorStrategy buildStrategy) {
        DummyObject.forType(Byte[].class)
                .onArrayProperty().setArrayStrategy(buildStrategy).setSize(1, 2, -1)
                .build();
    }

    @Test(dataProvider = "buildStrategy")
    public void arrayPrimitiveObject(ArrayGeneratorStrategy buildStrategy) {
        byte[] b = DummyObject.forType(byte[].class)
                .onPrimitiveProperty().setPrimitiveStrategy(PrimitiveGeneratorStrategy.DEFAULT)
                .onWrapperProperty().setWrapperStrategy(WrapperGeneratorStrategy.DEFAULT)
                .onArrayProperty().setArrayStrategy(buildStrategy)
                .build();
        short[] s = DummyObject.forType(short[].class)
                .onWrapperProperty().setWrapperStrategy(WrapperGeneratorStrategy.DEFAULT)
                .onPrimitiveProperty().setPrimitiveStrategy(PrimitiveGeneratorStrategy.DEFAULT)
                .onArrayProperty().setArrayStrategy(buildStrategy)
                .build();
        int[] i = DummyObject.forType(int[].class)
                .onWrapperProperty().setWrapperStrategy(WrapperGeneratorStrategy.DEFAULT)
                .onPrimitiveProperty().setPrimitiveStrategy(PrimitiveGeneratorStrategy.DEFAULT)
                .onArrayProperty().setArrayStrategy(buildStrategy)
                .build();
        long[] l = DummyObject.forType(long[].class)
                .onArrayProperty().setArrayStrategy(buildStrategy)
                .build();
        float[] f = DummyObject.forType(float[].class)
                .onArrayProperty().setArrayStrategy(buildStrategy)
                .build();
        double[] d = DummyObject.forType(double[].class)
                .onArrayProperty().setArrayStrategy(buildStrategy)
                .build();
        char[] c = DummyObject.forType(char[].class)
                .onArrayProperty().setArrayStrategy(buildStrategy)
                .build();
        boolean[] bool = DummyObject.forType(boolean[].class)
                .onArrayProperty().setArrayStrategy(buildStrategy)
                .build();

        switch (buildStrategy) {
            case DEFAULT:
            case VALUE:
                assertTrue(Arrays.equals(b, new byte[0]));
                assertEquals(b.length, 0);
                assertTrue(Arrays.equals(s, new short[0]));
                assertEquals(s.length, 0);
                assertTrue(Arrays.equals(i, new int[0]));
                assertEquals(i.length, 0);
                assertTrue(Arrays.equals(l, new long[0]));
                assertEquals(l.length, 0);
                assertTrue(Arrays.equals(f, new float[0]));
                assertEquals(f.length, 0);
                assertTrue(Arrays.equals(d, new double[0]));
                assertEquals(d.length, 0);
                assertTrue(Arrays.equals(c, new char[0]));
                assertEquals(c.length, 0);
                assertTrue(Arrays.equals(bool, new boolean[0]));
                assertEquals(bool.length, 0);
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
        }

    }

    @Test(dataProvider = "buildStrategy")
    public void arrayWrapperObject(ArrayGeneratorStrategy buildStrategy) {
        Byte[] b = DummyObject.forType(Byte[].class)
                .onArrayProperty().setArrayStrategy(buildStrategy)
                .build();
        Short[] s = DummyObject.forType(Short[].class)
                .onArrayProperty().setArrayStrategy(buildStrategy)
                .build();
        Integer[] i = DummyObject.forType(Integer[].class)
                .onArrayProperty().setArrayStrategy(buildStrategy)
                .build();
        Long[] l = DummyObject.forType(Long[].class)
                .onArrayProperty().setArrayStrategy(buildStrategy)
                .build();
        Float[] f = DummyObject.forType(Float[].class)
                .onArrayProperty().setArrayStrategy(buildStrategy)
                .build();
        Double[] d = DummyObject.forType(Double[].class)
                .onArrayProperty().setArrayStrategy(buildStrategy)
                .build();
        Character[] c = DummyObject.forType(Character[].class)
                .onArrayProperty().setArrayStrategy(buildStrategy)
                .build();
        Boolean bool[] = DummyObject.forType(Boolean[].class)
                .onArrayProperty().setArrayStrategy(buildStrategy)
                .build();

        switch (buildStrategy) {
            case DEFAULT:
            case VALUE:
                assertTrue(Arrays.equals(b, new Byte[0]));
                assertEquals(b.length, 0);
                assertTrue(Arrays.equals(s, new Short[0]));
                assertEquals(s.length, 0);
                assertTrue(Arrays.equals(i, new Integer[0]));
                assertEquals(i.length, 0);
                assertTrue(Arrays.equals(l, new Long[0]));
                assertEquals(l.length, 0);
                assertTrue(Arrays.equals(f, new Float[0]));
                assertEquals(f.length, 0);
                assertTrue(Arrays.equals(d, new Double[0]));
                assertEquals(d.length, 0);
                assertTrue(Arrays.equals(c, new Character[0]));
                assertEquals(c.length, 0);
                assertTrue(Arrays.equals(bool, new Boolean[0]));
                assertEquals(bool.length, 0);
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
        }
    }

    @Test(dataProvider = "buildStrategyForValue")
    public void arrayWrapperObjectValue(ArrayGeneratorStrategy buildStrategy, PrimitiveGeneratorStrategy primitiveGeneratorStrategy, WrapperGeneratorStrategy wrapperGeneratorStrategy) {
        Byte[] b = DummyObject.forType(Byte[].class)
                .onArrayProperty().setArrayStrategy(buildStrategy).setArrayStrategy(primitiveGeneratorStrategy).setArrayStrategy(wrapperGeneratorStrategy)
                .setSize(1)
                .build();
        short[] s = DummyObject.forType(short[].class)
                .onArrayProperty().setArrayStrategy(buildStrategy, primitiveGeneratorStrategy, wrapperGeneratorStrategy).setSize(2)
                .build();
        Integer[] i = DummyObject.forType(Integer[].class)
                .onArrayProperty().setArrayStrategy(buildStrategy, primitiveGeneratorStrategy, wrapperGeneratorStrategy).setSize(3)
                .build();
        long[] l = DummyObject.forType(long[].class)
                .onArrayProperty().setArrayStrategy(buildStrategy, primitiveGeneratorStrategy, wrapperGeneratorStrategy).setSize(2)
                .build();
        Float[] f = DummyObject.forType(Float[].class)
                .onArrayProperty().setArrayStrategy(buildStrategy, primitiveGeneratorStrategy, wrapperGeneratorStrategy).setSize(1)
                .build();
        double[] d = DummyObject.forType(double[].class)
                .onArrayProperty().setArrayStrategy(buildStrategy, primitiveGeneratorStrategy, wrapperGeneratorStrategy).setSize(0)
                .build();
        Character[] c = DummyObject.forType(Character[].class)
                .onArrayProperty().setArrayStrategy(buildStrategy, primitiveGeneratorStrategy, wrapperGeneratorStrategy).setSize(1)
                .build();
        boolean bool[] = DummyObject.forType(boolean[].class)
                .onArrayProperty().setArrayStrategy(buildStrategy, primitiveGeneratorStrategy, wrapperGeneratorStrategy).setSize(2)
                .build();

        switch (buildStrategy) {
            case VALUE:
                switch (wrapperGeneratorStrategy) {
                    case DEFAULT:
                        assertTrue(Arrays.equals(b, new Byte[]{BYTE_WRAPPER_DEFAULT}));
                        assertTrue(Arrays.equals(s, new short[]{SHORT_DEFAULT, SHORT_DEFAULT}));
                        assertTrue(Arrays.equals(i, new Integer[]{INTEGER_WRAPPER_DEFAULT, INTEGER_WRAPPER_DEFAULT, INTEGER_WRAPPER_DEFAULT}));
                        assertTrue(Arrays.equals(l, new long[]{LONG_DEFAULT, LONG_DEFAULT}));
                        assertTrue(Arrays.equals(f, new Float[]{FLOAT_DEFAULT}));
                        assertTrue(Arrays.equals(d, new double[]{}));
                        assertTrue(Arrays.equals(c, new Character[]{CHAR_DEFAULT}));
                        assertTrue(Arrays.equals(bool, new boolean[]{BOOLEAN_DEFAULT, BOOLEAN_DEFAULT}));
                        break;
                    case MIN:
                        assertTrue(Arrays.equals(b, new Byte[]{Byte.MIN_VALUE}));
                        assertTrue(Arrays.equals(s, new short[]{Short.MIN_VALUE, Short.MIN_VALUE}));
                        assertTrue(Arrays.equals(i, new Integer[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE}));
                        assertTrue(Arrays.equals(l, new long[]{Long.MIN_VALUE, Long.MIN_VALUE}));
                        assertTrue(Arrays.equals(f, new Float[]{Float.MIN_VALUE}));
                        assertTrue(Arrays.equals(d, new double[]{}));
                        assertTrue(Arrays.equals(c, new Character[]{Character.MIN_VALUE}));
                        assertTrue(Arrays.equals(bool, new boolean[]{Boolean.FALSE, Boolean.FALSE}));
                        break;
                    case MAX:
                        assertTrue(Arrays.equals(b, new Byte[]{Byte.MAX_VALUE}));
                        assertTrue(Arrays.equals(s, new short[]{Short.MAX_VALUE, Short.MAX_VALUE}));
                        assertTrue(Arrays.equals(i, new Integer[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE}));
                        assertTrue(Arrays.equals(l, new long[]{Long.MAX_VALUE, Long.MAX_VALUE}));
                        assertTrue(Arrays.equals(f, new Float[]{Float.MAX_VALUE}));
                        assertTrue(Arrays.equals(d, new double[]{}));
                        assertTrue(Arrays.equals(c, new Character[]{Character.MAX_VALUE}));
                        assertTrue(Arrays.equals(bool, new boolean[]{Boolean.TRUE, Boolean.TRUE}));
                        break;
                }
                switch (primitiveGeneratorStrategy) {
                    case DEFAULT:
                        break;
                    case MIN:
                        break;
                    case MAX:
                        break;
                }
                break;
        }
    }

    @Test(dataProvider = "buildStrategyForValue")
    public void arrayMultidimensional(ArrayGeneratorStrategy buildStrategy, PrimitiveGeneratorStrategy primitiveGeneratorStrategy, WrapperGeneratorStrategy wrapperGeneratorStrategy) {
        Byte[][] b = DummyObject.forType(Byte[][].class)
                .onArrayProperty().setArrayStrategy(buildStrategy, primitiveGeneratorStrategy, wrapperGeneratorStrategy)
                .setSize(0)
                .build();
        assertTrue(Arrays.equals(b, new Byte[0][]));

        short[][] s = DummyObject.forType(short[][].class)
                .onArrayProperty().setArrayStrategy(buildStrategy, primitiveGeneratorStrategy, wrapperGeneratorStrategy)
                .setSize(2)
                .build();

        Integer[][] i = DummyObject.forType(Integer[][].class)
                .onArrayProperty().setArrayStrategy(buildStrategy, primitiveGeneratorStrategy, wrapperGeneratorStrategy)
                .setSize(2, 1)
                .build();

        // asserts
        switch (primitiveGeneratorStrategy) {
            case DEFAULT:
                assertTrue(Arrays.deepEquals(s, new short[][]{
                        new short[]{},
                        new short[]{}
                }));
                assertTrue(Arrays.deepEquals(i, new Integer[][]{
                        new Integer[]{ConstantPool.INTEGER_WRAPPER_DEFAULT},
                        new Integer[]{ConstantPool.INTEGER_WRAPPER_DEFAULT}
                }));
                break;
            case MIN:
                assertTrue(Arrays.deepEquals(s, new short[][]{
                        new short[]{},
                        new short[]{}
                }));
                assertTrue(Arrays.deepEquals(i, new Integer[][]{
                        new Integer[]{Integer.MIN_VALUE},
                        new Integer[]{Integer.MIN_VALUE}
                }));
                break;
            case MAX:
                assertTrue(Arrays.deepEquals(s, new short[][]{
                        new short[]{},
                        new short[]{}
                }));
                assertTrue(Arrays.deepEquals(i, new Integer[][]{
                        new Integer[]{Integer.MAX_VALUE},
                        new Integer[]{Integer.MAX_VALUE}
                }));
                break;
        }

        switch (wrapperGeneratorStrategy) {
            case DEFAULT:
                break;
            case MIN:
                break;
            case MAX:
                break;
        }

    }
}
