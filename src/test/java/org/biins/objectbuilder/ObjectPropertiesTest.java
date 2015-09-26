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

    @Test
    public void ignoredFieldsTest() {
        Page page = new ObjectBuilder()
                .setStrategyForAll("VALUE")
                .onString().setGeneratorStrategy(StringGeneratorStrategy.EMPTY)
                .onObject().onProperty("title", null, null)
                .build(Page.class);

        assertNull(page.getTitle());
        assertNotNull(page.getArticleLayout());
        assertNotNull(page.getFriendlyUrl());
        assertNotNull(page.getLayout());

        page = new ObjectBuilder()
                .setStrategyForAll("VALUE")
                .onString().setGeneratorStrategy(StringGeneratorStrategy.EMPTY)
                .onObject().ignoreProperty("title")
                .build(Page.class);

        assertNull(page.getTitle());
        assertNotNull(page.getArticleLayout());
        assertNotNull(page.getFriendlyUrl());
        assertNotNull(page.getLayout());

        page = new ObjectBuilder().addIgnoredType(String.class, Article.class)
                .setStrategyForAll("VALUE")
                .onString().setGeneratorStrategy(StringGeneratorStrategy.EMPTY)
                .onCollection().setSize(1)
                .build(Page.class);

        assertNull(page.getTitle());
        assertNull(page.getArticleLayout());
        assertNull(page.getFriendlyUrl());
        assertNull(page.getLayout());
        assertEquals(page.getArticles(), Collections.emptyList());
    }

    @Test
    public void fieldValuesTest() {
        List<CommonObject> objs = new ObjectBuilder()
                .setStrategyForAll("VALUE")
                .onObject()
                    .onProperty("primitiveObject.f", 123f)
                    .onProperty("primitiveObject.i", 123)
                    .onProperty("primitiveObject.l", 123l)
                .build(CommonObject.class, 5);

        for (CommonObject o : objs) {
            assertEquals(o.primitiveObject.getF(), 123f);
            assertEquals(o.primitiveObject.getI(), 123);
            assertEquals(o.primitiveObject.getL(), 123l);
        }
    }

    @Test
    public void fieldValuesTest2() {
        List<CommonObjects> objs = new ObjectBuilder()
                .setStrategyForAll("VALUE")
                .onObject()
                    .onProperty("a.primitiveObject.f", 123f)
                    .onProperty("b.primitiveObject.i", 123)
                    .onProperty("c.primitiveObject.l", 123l)
                    .onProperty("a.stringObject.string", "A")
                    .onProperty("b.stringObject.string", "B")
                    .onProperty("c.stringObject.string", "C")
                    .onProperty("d", new Object[]{null})
                .build(CommonObjects.class, 5);

        for (CommonObjects o : objs) {
            assertEquals(o.a.primitiveObject.getF(), 123f);
            assertEquals(o.b.primitiveObject.getI(), 123);
            assertEquals(o.c.primitiveObject.getL(), 123l);
            assertEquals(o.a.stringObject.string, "A");
            assertEquals(o.b.stringObject.string, "B");
            assertEquals(o.c.stringObject.string, "C");
            assertNull(o.d);
        }
    }

    @Test
    public void ignoredFieldValuesTest() {
        List<CommonObjects> objs = new ObjectBuilder()
                .setStrategyForAll("VALUE")
                .onPrimitive(PrimitiveGeneratorStrategy.MAX)
                .onObject().ignoreProperty(
                        "a.primitiveObject.f",
                        "b.primitiveObject.i",
                        "c.primitiveObject.l",
                        "a.stringObject.string",
                        "b.stringObject.string",
                        "c.stringObject.string",
                        "d"
                )
                .build(CommonObjects.class, 5);

        for (CommonObjects o : objs) {
            assertEquals(o.a.primitiveObject.getF(), 0f);
            assertEquals(o.b.primitiveObject.getI(), 0);
            assertEquals(o.c.primitiveObject.getL(), 0l);
            assertEquals(o.a.stringObject.string, null);
            assertEquals(o.b.stringObject.string, null);
            assertEquals(o.c.stringObject.string, null);
            assertNull(o.d);
        }
    }
}