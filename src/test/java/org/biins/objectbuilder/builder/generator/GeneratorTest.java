package org.biins.objectbuilder.builder.generator;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Martin Janys
 */
public class GeneratorTest {

    @Test
    public void alphabeticalGeneratorTest() throws Exception {
        Generator<String> g = new AlphabetGenerator();

        for (char i = 'a'; i <= 'z'; i++) {
            assertEquals(String.valueOf(i), g.next());
        }
        assertFalse(g.hasNext());
        g.reset();

        char c = 'a';
        while (g.hasNext()) {
            assertEquals(String.valueOf(c++), g.next());
        }
        assertFalse(g.hasNext());
        g.reset();
        for (char i = 'a'; i <= 'z'; i++) {
            assertEquals(String.valueOf(i), g.next());
        }
        assertFalse(g.hasNext());
        g.reset();
    }

    @Test
    public void numberSequenceGeneratorTest() {
        NumberSequenceGenerator g = new NumberSequenceGenerator(10, 20);
        long i;
        for (i = 10; i < 20; i++) {
             assertEquals(i, g.next().longValue());
        }

        g = new NumberSequenceGenerator(Short.MAX_VALUE);
        i = 0;
        while (g.hasNext()) {
            assertEquals(i++, g.next().longValue());
        }
    }

    @Test
    public void valuesGeneratorTest() {
        Generator<String> g = new ValuesGenerator<>("A", "B", "C");
        assertEquals(g.next(), "A");
        assertEquals(g.next(), "B");
        assertEquals(g.next(), "C");
        assertFalse(g.hasNext());
        try {
            g.next();
            fail();
        }
        catch (IllegalStateException ignored) {
        }

        g = new CyclicValuesGenerator<>("A", "B", "C");
        assertEquals(g.next(), "A");
        assertEquals(g.next(), "B");
        assertEquals(g.next(), "C");
        assertFalse(g.hasNext());
        g.reset();
        assertTrue(g.hasNext());
        assertEquals(g.next(), "A");
        assertEquals(g.next(), "B");
        assertEquals(g.next(), "C");
    }

}