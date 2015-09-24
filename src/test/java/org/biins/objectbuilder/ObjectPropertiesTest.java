package org.biins.objectbuilder;

import org.biins.objectbuilder.builder.ObjectBuilder;
import org.biins.objectbuilder.builder.generator.CyclicValuesGenerator;
import org.biins.objectbuilder.builder.generator.Generator;
import org.biins.objectbuilder.builder.generator.ValuesGenerator;
import org.biins.objectbuilder.builder.strategy.*;
import org.biins.objectbuilder.classes.*;
import org.biins.objectbuilder.classes.real.Article;
import org.biins.objectbuilder.classes.real.Page;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.*;

/**
 * @author Martin Janys
 */
public class ObjectPropertiesTest {

    @Test
    public void simplePropertiesTest() {
        List<Page> pages = new ObjectBuilder()
                .setStrategyForAll("NULL")
                .onObject().onProperty("title", "A", "B", "C")
                .build(Page.class, 4);

        assertEquals(pages.size(), 4);

        assertNotNull(pages.get(0));
        assertNotNull(pages.get(1));
        assertNotNull(pages.get(2));
        assertNotNull(pages.get(3));

        assertEquals(pages.get(0).getTitle(), "A");
        assertEquals(pages.get(1).getTitle(), "B");
        assertEquals(pages.get(2).getTitle(), "C");
        assertNull(pages.get(3).getTitle());
    }

    @Test
    public void simplePropertiesGeneratorTest() {
        List<Page> pages = new ObjectBuilder()
                .setStrategyForAll("NULL")
                .onObject().onProperty("title", new ValuesGenerator<>("A", "B", "C"))
                .build(Page.class, 4);

        assertEquals(pages.size(), 4);

        assertNotNull(pages.get(0));
        assertNotNull(pages.get(1));
        assertNotNull(pages.get(2));
        assertNotNull(pages.get(3));

        assertEquals(pages.get(0).getTitle(), "A");
        assertEquals(pages.get(1).getTitle(), "B");
        assertEquals(pages.get(2).getTitle(), "C");
        assertNull(pages.get(3).getTitle());

    }
    @Test
    public void simplePropertiesGeneratorCyclicTest() {
        List<Page> pages = new ObjectBuilder()
                .setStrategyForAll("NULL")
                .onObject().onProperty("title", new CyclicValuesGenerator<Object>("A", "B", "C"))
                .build(Page.class, 4);

        assertEquals(pages.size(), 4);

        assertNotNull(pages.get(0));
        assertNotNull(pages.get(1));
        assertNotNull(pages.get(2));
        assertNotNull(pages.get(3));

        assertEquals(pages.get(0).getTitle(), "A");
        assertEquals(pages.get(1).getTitle(), "B");
        assertEquals(pages.get(2).getTitle(), "C");
        assertEquals(pages.get(3).getTitle(), "A");

    }

}