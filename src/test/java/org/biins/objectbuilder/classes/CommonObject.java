package org.biins.objectbuilder.classes;

/**
 * @author Martin Janys
 */
public class CommonObject {

    public ArrayObject arrayObject;
    public CollectionObject collectionObject;
    public PrimitiveObject primitiveObject;
    public StringObject stringObject;
    public WrapperObject wrapperObject;

    public StaticInner staticInner;
    public Outer outer;

    public static final class StaticInner {
        private int i;
    }

    public class Inner {
        public Inner() {
        }

        private short s;
    }

}

class Outer {
    private long l;
}