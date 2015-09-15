package org.biins.objectbuilder;

import org.biins.objectbuilder.builder.strategy.StringGeneratorStrategy;
import org.biins.objectbuilder.builder.strategy.WrapperGeneratorStrategy;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.*;

/**
 * @author Martin Janys
 */
public class StringsTest {

    @DataProvider(name = "buildStrategy")
    public static Object[][] buildStrategy() {
        return new Object[][] {
                {StringGeneratorStrategy.DEFAULT},
                {StringGeneratorStrategy.NULL},
                {StringGeneratorStrategy.EMPTY},
                {StringGeneratorStrategy.UUID},
                {StringGeneratorStrategy.VALUE}
        };
    }

    @Test(dataProvider = "buildStrategy")
    public void string(StringGeneratorStrategy buildStrategy) {
        String def = GenerateObject.forType(String.class)
                .onWrapperProperty().setGeneratorStrategy(WrapperGeneratorStrategy.DEFAULT)
                .onString().setGeneratorStrategy(buildStrategy)
                .build();
        String size5 = GenerateObject.forType(String.class)
                .onString().setGeneratorStrategy(buildStrategy).setSize(5)
                .onWrapper().setGeneratorStrategy(WrapperGeneratorStrategy.DEFAULT)
                .build();
        String numeric = GenerateObject.forType(String.class)
                .onString().setGeneratorStrategy(buildStrategy).setNumeric(true)
                .build();
        String alpha = GenerateObject.forType(String.class)
                .onString().setGeneratorStrategy(buildStrategy).setAlpha(true)
                .build();
        String alphaLower = GenerateObject.forType(String.class)
                .onString().setGeneratorStrategy(buildStrategy).setAttributes(true, true, false)
                .onWrapper().setGeneratorStrategy(WrapperGeneratorStrategy.DEFAULT)
                .build();
        String alphaNumericLower = GenerateObject.forType(String.class)
                .onString().setGeneratorStrategy(buildStrategy).setLower(true)
                .onWrapper().setGeneratorStrategy(WrapperGeneratorStrategy.DEFAULT)
                .build();

        switch (buildStrategy) {
            case VALUE:
                assertEquals(def.length(), 10);
                assertTrue(def.matches("[\\p{Lu}\\p{Digit}]+"));
                assertEquals(size5.length(), 5);
                assertTrue(size5.matches("[\\p{Lu}\\p{Digit}]+"));
                assertEquals(numeric.length(), 10);
                assertTrue(numeric.matches("[\\p{Digit}]+"));
                assertEquals(alpha.length(), 10);
                assertTrue(alpha.matches("[\\p{Lu}]+"));
                assertEquals(alphaLower.length(), 10);
                assertTrue(alphaLower.matches("[\\p{Ll}]+"));
                assertEquals(alphaNumericLower.length(), 10);
                assertTrue(alphaNumericLower.matches("[\\p{Ll}\\p{Digit}]+"));
                break;
            case EMPTY:
            case DEFAULT:
                assertEquals(def, "");
                assertEquals(size5, "");
                assertEquals(numeric, "");
                assertEquals(alpha, "");
                assertEquals(alphaLower, "");
                assertEquals(alphaNumericLower, "");
                break;
            case NULL:
                assertNull(def);
                assertNull(size5);
                assertNull(numeric);
                assertNull(alpha);
                assertNull(alphaLower);
                assertNull(alphaNumericLower);
                break;
            case UUID:
                assertTrue(isUUID(def));
                assertTrue(isUUID(size5));
                assertTrue(isUUID(numeric));
                assertTrue(isUUID(alpha));
                assertTrue(isUUID(alphaLower));
                assertTrue(isUUID(alphaNumericLower));
                break;
        }
    }

    public boolean isUUID(String string) {
        try {
            UUID.fromString(string);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
