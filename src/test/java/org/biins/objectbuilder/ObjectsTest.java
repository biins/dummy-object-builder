package org.biins.objectbuilder;

import org.biins.objectbuilder.builder.ObjectBuilder;
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
public class ObjectsTest {

    @DataProvider(name = "primitiveBuildStrategy")
    public static Object[][] primitiveBuildStrategy() {
        return new Object[][]{
                {PrimitiveGeneratorStrategy.DEFAULT},
                {PrimitiveGeneratorStrategy.MIN},
                {PrimitiveGeneratorStrategy.MAX}
        };
    }

    @Test(dataProvider = "primitiveBuildStrategy")
    public void primitiveObjectTest(PrimitiveGeneratorStrategy primitiveStrategy) {
        PrimitiveObject primitiveObject = new ObjectBuilder()
                .onPrimitive(primitiveStrategy)
                .build(PrimitiveObject.class);

        Object bool = ReflectionTestUtils.getField(primitiveObject, "bool");
        Object b = ReflectionTestUtils.getField(primitiveObject, "b");
        Object c = ReflectionTestUtils.getField(primitiveObject, "c");
        switch (primitiveStrategy) {
            case MIN:
                assertEquals(bool, Boolean.FALSE.booleanValue());
                assertEquals(b, Byte.MIN_VALUE);
                assertEquals(c, Character.MIN_VALUE);
                assertEquals(primitiveObject.d, Double.MIN_VALUE);
                assertEquals(primitiveObject.getF(), Float.MIN_VALUE);
                assertEquals(primitiveObject.getI(), Integer.MIN_VALUE);
                assertEquals(primitiveObject.getL(), Long.MIN_VALUE);
                break;
            case MAX:
                assertEquals(bool, Boolean.TRUE.booleanValue());
                assertEquals(b, Byte.MAX_VALUE);
                assertEquals(c, Character.MAX_VALUE);
                assertEquals(primitiveObject.d, Double.MAX_VALUE);
                assertEquals(primitiveObject.getF(), Float.MAX_VALUE);
                assertEquals(primitiveObject.getI(), Integer.MAX_VALUE);
                assertEquals(primitiveObject.getL(), Long.MAX_VALUE);
                break;
            case DEFAULT:
            default:
                assertEquals(bool, ConstantPool.BOOLEAN_DEFAULT);
                assertEquals(b, ConstantPool.BYTE_WRAPPER_DEFAULT);
                assertEquals(c, ConstantPool.CHAR_DEFAULT);
                assertEquals(primitiveObject.d, ConstantPool.DOUBLE_DEFAULT);
                assertEquals(primitiveObject.getF(), ConstantPool.FLOAT_DEFAULT);
                assertEquals(primitiveObject.getI(), ConstantPool.INT_DEFAULT);
                assertEquals(primitiveObject.getL(), ConstantPool.LONG_DEFAULT);
        }
    }

    @DataProvider(name = "wrapperBuildStrategy")
    public static Object[][] wrapperBuildStrategy() {
        return new Object[][]{
                {WrapperGeneratorStrategy.DEFAULT},
                {WrapperGeneratorStrategy.NULL},
                {WrapperGeneratorStrategy.MIN},
                {WrapperGeneratorStrategy.MAX}
        };
    }

    @Test(dataProvider = "wrapperBuildStrategy")
    public void wrapperObjectTest(WrapperGeneratorStrategy wrapperStrategy) {
        WrapperObject wrapperObject = new ObjectBuilder()
                .onWrapper(wrapperStrategy)
                .build(WrapperObject.class);

        Object bool = ReflectionTestUtils.getField(wrapperObject, "bool");
        Object b = ReflectionTestUtils.getField(wrapperObject, "b");
        Object c = ReflectionTestUtils.getField(wrapperObject, "c");
        switch (wrapperStrategy) {
            case MIN:
                assertEquals(bool, Boolean.FALSE.booleanValue());
                assertEquals(b, Byte.MIN_VALUE);
                assertEquals(c, Character.MIN_VALUE);
                assertEquals(wrapperObject.d, Double.MIN_VALUE);
                assertEquals(wrapperObject.getF(), Float.MIN_VALUE);
                assertEquals(wrapperObject.getI(), (Integer) Integer.MIN_VALUE);
                assertEquals(wrapperObject.getL(), (Long) Long.MIN_VALUE);
                break;
            case MAX:
                assertEquals(bool, Boolean.TRUE.booleanValue());
                assertEquals(b, Byte.MAX_VALUE);
                assertEquals(c, Character.MAX_VALUE);
                assertEquals(wrapperObject.d, Double.MAX_VALUE);
                assertEquals(wrapperObject.getF(), Float.MAX_VALUE);
                assertEquals(wrapperObject.getI(), (Integer) Integer.MAX_VALUE);
                assertEquals(wrapperObject.getL(), (Long) Long.MAX_VALUE);
                break;
            case NULL:
                assertNull(bool);
                assertNull(b);
                assertNull(c);
                assertNull(wrapperObject.d);
                assertNull(wrapperObject.getF());
                assertNull(wrapperObject.getI());
                assertNull(wrapperObject.getL());
                break;
            case DEFAULT:
            default:
                assertEquals(bool, ConstantPool.BOOLEAN_DEFAULT);
                assertEquals(b, ConstantPool.BYTE_WRAPPER_DEFAULT);
                assertEquals(c, ConstantPool.CHAR_DEFAULT);
                assertEquals(wrapperObject.d, ConstantPool.DOUBLE_DEFAULT);
                assertEquals(wrapperObject.getF(), ConstantPool.FLOAT_DEFAULT);
                assertEquals(wrapperObject.getI(), ConstantPool.INTEGER_WRAPPER_DEFAULT);
                assertEquals(wrapperObject.getL(), ConstantPool.LONG_WRAPPER_DEFAULT);
        }
    }

    @DataProvider(name = "arrayBuildStrategy")
    public static Object[][] arrayBuildStrategy() {
        return new Object[][]{
                {ArrayGeneratorStrategy.DEFAULT},
                {ArrayGeneratorStrategy.NULL},
                {ArrayGeneratorStrategy.VALUE}
        };
    }

    @Test(dataProvider = "arrayBuildStrategy")
    public void arrayObjectTest(ArrayGeneratorStrategy arrayStrategy) {
        ArrayObject arrayObject = new ObjectBuilder()
                .onArray(arrayStrategy).setSize(3, 2, 1)
                .build(ArrayObject.class);

        Object bool = ReflectionTestUtils.getField(arrayObject, "bool");
        Object b = ReflectionTestUtils.getField(arrayObject, "b");
        Object c = ReflectionTestUtils.getField(arrayObject, "c");
        switch (arrayStrategy) {
            case VALUE:
                assertEquals(bool, new Boolean[]{false, false, false});
                assertEquals(b, new byte[][]{
                        new byte[]{0, 0},
                        new byte[]{0, 0},
                        new byte[]{0, 0}
                });
                assertEquals(c, new Character[][][]{
                        new Character[][]{
                                new Character[]{0},
                                new Character[]{0}
                        },
                        new Character[][]{
                                new Character[]{0},
                                new Character[]{0}
                        },
                        new Character[][]{
                                new Character[]{0},
                                new Character[]{0}
                        }
                });
                assertEquals(arrayObject.d, new double[][]{
                        new double[]{0d, 0d},
                        new double[]{0d, 0d},
                        new double[]{0d, 0d}
                });
                assertEquals(arrayObject.f, new Float[]{0f, 0f, 0f});
                assertEquals(arrayObject.i, new int[][]{
                        new int[]{0, 0},
                        new int[]{0, 0},
                        new int[]{0, 0}
                });
                assertEquals(arrayObject.l, new Long[][][]{
                        new Long[][]{
                                new Long[]{0l},
                                new Long[]{0l}
                        },
                        new Long[][]{
                                new Long[]{0l},
                                new Long[]{0l}
                        },
                        new Long[][]{
                                new Long[]{0l},
                                new Long[]{0l}
                        }
                });
                assertEquals(arrayObject.list, new List[]{
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList()
                });
                break;
            case NULL:
                assertNull(bool);
                assertNull(b);
                assertNull(c);
                assertNull(arrayObject.d);
                assertNull(arrayObject.f);
                assertNull(arrayObject.i);
                assertNull(arrayObject.l);
                break;
            case DEFAULT:
            default:
                assertEquals(bool, new Boolean[0]);
                assertEquals(b, new byte[0][]);
                assertEquals(c, new Character[0][][]);
                assertEquals(arrayObject.d, new double[0][]);
                assertEquals(arrayObject.f, new Float[0]);
                assertEquals(arrayObject.i, new int[0][]);
                assertEquals(arrayObject.l, new Long[0][][]);
        }
    }

    @DataProvider(name = "collectionBuildStrategy")
    public static Object[][] collectionBuildStrategy() {
        return new Object[][]{
//                {CollectionGeneratorStrategy.DEFAULT},
//                {CollectionGeneratorStrategy.SINGLETON},
//                {CollectionGeneratorStrategy.NULL},
                {CollectionGeneratorStrategy.VALUE}
        };
    }

    @Test(dataProvider = "collectionBuildStrategy")
    public void collectionObjectTest(CollectionGeneratorStrategy collectionStrategy) {
        CollectionObject collectionObject = new ObjectBuilder()
                .onCollection(collectionStrategy).setSize(1, 1, 1)
                .build(CollectionObject.class);

        List bool = (List) ReflectionTestUtils.getField(collectionObject, "bool");
        Set b = (Set) ReflectionTestUtils.getField(collectionObject, "b");
        List c = (List) ReflectionTestUtils.getField(collectionObject, "c");
        switch (collectionStrategy) {
            case SINGLETON:
            case VALUE:
                assertEquals(bool, Collections.singletonList(false));
                assertEquals(b, Collections.singleton(Collections.singletonList((byte) 0)));
                assertEquals(c, Collections.singletonList(Collections.singleton(Collections.singletonList((char) 0))));
                assertEquals(collectionObject.d, Collections.singletonList(Collections.singleton(0d)));
                assertEquals(collectionObject.f, Collections.singletonList(0f));
                assertEquals(collectionObject.i, Collections.singletonList(new int[0]));
                assertEquals(collectionObject.l, Collections.singletonList(new Long[0][]));
                assertEquals(collectionObject.set, Collections.emptySet());
                break;
            case NULL:
                assertNull(bool);
                assertNull(b);
                assertNull(c);
                assertNull(collectionObject.d);
                assertNull(collectionObject.f);
                assertNull(collectionObject.i);
                assertNull(collectionObject.l);
                assertNull(collectionObject.set);
                break;
            case DEFAULT:
            default:
                assertEquals(bool, Collections.emptyList());
                assertEquals(b, Collections.emptySet());
                assertEquals(c, Collections.emptyList());
                assertEquals(collectionObject.d, Collections.emptyList());
                assertEquals(collectionObject.f, Collections.emptyList());
                assertEquals(collectionObject.i, Collections.emptyList());
                assertEquals(collectionObject.l, Collections.emptySet());
                assertEquals(collectionObject.set, Collections.emptySet());
        }
    }

    @DataProvider(name = "stringBuildStrategy")
    public static Object[][] stringBuildStrategy() {
        return new Object[][]{
                {StringGeneratorStrategy.DEFAULT},
                {StringGeneratorStrategy.EMPTY},
                {StringGeneratorStrategy.NULL},
                {StringGeneratorStrategy.VALUE}
        };
    }

    @Test(dataProvider = "stringBuildStrategy")
    public void collectionObjectTest(StringGeneratorStrategy stringStrategy) {
        StringObject stringObject = new ObjectBuilder()
                .onString(stringStrategy).setSize(3)
                .onArray(ArrayGeneratorStrategy.VALUE).setSize(1)
                .onCollection(CollectionGeneratorStrategy.VALUE).setSize(1)
                .build(StringObject.class);

        switch (stringStrategy) {
            case VALUE:
                assertEquals(stringObject.string.length(), 3);
                assertEquals(stringObject.array.length, 1);
                assertEquals(stringObject.array[0].length(), 3);
                assertEquals(stringObject.list.size(), 1);
                assertEquals(stringObject.list.get(0).length(), 3);
                break;
            case NULL:
                assertNull(stringObject.string);
                assertEquals(stringObject.array, new String[]{null});
                assertEquals(stringObject.list, Collections.singletonList(null));
                break;
            case EMPTY:
            case DEFAULT:
            default:
                assertEquals(stringObject.string, "");
                assertEquals(stringObject.array, new String[]{""});
                assertEquals(stringObject.list, Collections.singletonList(""));

        }
    }

    @Test
    public void objectTest() {
        CommonObject o = new ObjectBuilder()
                .onPrimitive(PrimitiveGeneratorStrategy.MIN)
                .onWrapper(WrapperGeneratorStrategy.MAX)
                .onArray(ArrayGeneratorStrategy.VALUE).setSize(1)
                .onCollection(CollectionGeneratorStrategy.VALUE).setSize(1)
                .onString(StringGeneratorStrategy.VALUE).setSize(1)
                .build(CommonObject.class);

        assertNotNull(o);
        assertNotNull(o.primitiveObject);
        assertNotNull(o.wrapperObject);
        assertNotNull(o.stringObject);
        assertNotNull(o.collectionObject);
        assertNotNull(o.arrayObject);

        Object bool = ReflectionTestUtils.getField(o.arrayObject, "bool");
        Object b = ReflectionTestUtils.getField(o.arrayObject, "b");
        Object c = ReflectionTestUtils.getField(o.arrayObject, "c");
        assertEquals(bool, new Boolean[]{true});
        assertEquals(b, new byte[][]{new byte[0]});
        assertEquals(c, new Character[][][]{new Character[0][]});
        assertEquals(o.arrayObject.d, new double[][]{new double[0]});
        assertEquals(o.arrayObject.f, new Float[]{Float.MAX_VALUE});
        assertEquals(o.arrayObject.i, new int[][]{new int[0]});
        assertEquals(o.arrayObject.l, new Long[][][]{new Long[0][]});
        assertEquals(o.arrayObject.list, new List[]{Collections.emptyList()});

        bool = ReflectionTestUtils.getField(o.collectionObject, "bool");
        b = ReflectionTestUtils.getField(o.collectionObject, "b");
        c = ReflectionTestUtils.getField(o.collectionObject, "c");
        assertEquals((List)bool, Collections.singletonList(true));
        assertEquals((Set)b, Collections.singleton(Collections.emptyList()));
        assertEquals((List)c, Collections.singletonList(Collections.emptySet()));
        assertEquals(o.collectionObject.d, Collections.singletonList(Collections.emptySet()));
        assertEquals(o.collectionObject.f, Collections.singletonList((Float) Float.MAX_VALUE));
        assertEquals(o.collectionObject.i, Collections.singletonList(new int[]{Integer.MIN_VALUE}));
        assertEquals(o.collectionObject.l.size(), 1);
        assertEquals(o.collectionObject.l.iterator().next(), new Long[][]{new Long[0]});

        bool = ReflectionTestUtils.getField(o.primitiveObject, "bool");
        b = ReflectionTestUtils.getField(o.primitiveObject, "b");
        c = ReflectionTestUtils.getField(o.primitiveObject, "c");
        assertEquals(bool, false);
        assertEquals(b, Byte.MIN_VALUE);
        assertEquals(c, Character.MIN_VALUE);
        assertEquals(o.primitiveObject.d, Double.MIN_VALUE);
        assertEquals(o.primitiveObject.getF(), Float.MIN_VALUE);
        assertEquals(o.primitiveObject.getI(), Integer.MIN_VALUE);
        assertEquals(o.primitiveObject.getL(), Long.MIN_VALUE);

        bool = ReflectionTestUtils.getField(o.wrapperObject, "bool");
        b = ReflectionTestUtils.getField(o.wrapperObject, "b");
        c = ReflectionTestUtils.getField(o.wrapperObject, "c");
        assertEquals(bool, true);
        assertEquals(b, Byte.MAX_VALUE);
        assertEquals(c, Character.MAX_VALUE);
        assertEquals(o.wrapperObject.d, Double.MAX_VALUE);
        assertEquals(o.wrapperObject.getF(), Float.MAX_VALUE);
        assertEquals(o.wrapperObject.getI(), (Integer)Integer.MAX_VALUE);
        assertEquals(o.wrapperObject.getL(), (Long)Long.MAX_VALUE);

        assertEquals(o.stringObject.string.length(), 1);
        assertEquals(o.stringObject.array.length, 1);
        assertEquals(o.stringObject.array[0].length(), 1);
        assertEquals(o.stringObject.list.size(), 1);
        assertEquals(o.stringObject.list.get(0).length(), 1);

    }

    // todo - custom value
    // todo - custom custom generator -> map key
    // todo - set strategy, with string
    // todo - class with T field;+

    @Test
    public void realObjectTest() {
        Page page = new ObjectBuilder()
                .onPrimitive(PrimitiveGeneratorStrategy.MIN)
                .onWrapper(WrapperGeneratorStrategy.MAX)
                .onArray(ArrayGeneratorStrategy.VALUE).setSize(1)
                .onCollection(CollectionGeneratorStrategy.VALUE).setSize(1)
                .onString(StringGeneratorStrategy.VALUE).setSize(1)
                .onEnum(EnumGeneratorStrategy.FIRST)
                .build(Page.class);

        assertNotNull(page);

        assertEquals(page.getId(), (Long) Long.MAX_VALUE);
        assertEquals(page.isRoot(), false);
        assertNull(page.getParent()); // cyclic
        assertEquals(page.getChilds().size(), 1);
        assertNull(page.getChilds().get(0)); // cyclic

        assertEquals(page.getTitle().length(), 1);
        assertEquals(page.getFriendlyUrl().length(), 1);
        assertEquals(page.getOrder(), (Integer)Integer.MAX_VALUE);
        assertEquals(page.getArticles().size(), 1);
        Article article = page.getArticles().get(0);
        assertEquals(article.getId(), (Long)Long.MAX_VALUE);
        assertEquals(article.getArticleType().length(), 1);
        assertEquals(article.getOrder(), (Integer)Integer.MAX_VALUE);
        assertEquals(article.getTitle().length(), 1);
        assertEquals(article.getContent().length(), 1);
        assertEquals(article.getAction().length(), 1);
        assertEquals(article.getDisplayType(), Article.Type.A);
        assertEquals(article.getAuthor().length(), 1);
        assertEquals(article.getPageId(), (Long)Long.MAX_VALUE);
        assertEquals(page.getLayout().length(), 1);
        assertEquals(page.getArticleLayout().length(), 1);
        assertEquals(page.isVisible(), false);

    }

    @Test
    public void resolvableTypes() {
        ResolvableTypesObject object = new ObjectBuilder().build(ResolvableTypesObject.class);
        assertEquals(object.iterable, Collections.emptyList());
        assertEquals(object.iterator, Collections.emptyIterator());
        assertEquals(object.enumeration, Collections.emptyEnumeration());
    }

    @DataProvider(name = "mapBuildStrategy")
    public static Object[][] mapBuildStrategy() {
        return new Object[][]{
                {MapGeneratorStrategy.DEFAULT},
                {MapGeneratorStrategy.NULL},
                {MapGeneratorStrategy.SINGLETON},
                {MapGeneratorStrategy.VALUE}
        };
    }

    @Test(dataProvider = "mapBuildStrategy")
    public void mapObjects(MapGeneratorStrategy generatorStrategy) {
        MapObject maps = new ObjectBuilder()
                .onCollection(CollectionGeneratorStrategy.SINGLETON)
                .onMap(generatorStrategy).setSize(1)
                .build(MapObject.class);

        switch (generatorStrategy) {
            case NULL:
                assertNull(maps.map1);
                assertNull(maps.map2);
                assertNull(maps.map3);
                assertNull(maps.map);
                break;
            case SINGLETON:
            case VALUE:
                assertEquals(maps.map1, new HashMap<String, Integer>(){{
                    put("", 0);
                }});
                assertEquals(maps.map2, new HashMap<List<String>, Integer>(){{
                    put(Collections.singletonList(""), 0);
                }});
                assertEquals(maps.map3, new HashMap<String, List<Integer>>(){{
                    put("", Collections.singletonList(0));
                }});
                assertEquals(maps.map, Collections.emptyMap());
                break;
            case DEFAULT:
            default:
                assertEquals(maps.map1, Collections.emptyMap());
                assertEquals(maps.map2, Collections.emptyMap());
                assertEquals(maps.map2, Collections.emptyMap());
                assertEquals(maps.map, Collections.emptyMap());
        }

    }
}