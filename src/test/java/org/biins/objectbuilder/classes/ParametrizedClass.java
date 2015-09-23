package org.biins.objectbuilder.classes;

/**
 * @author Martin Janys
 */
public class ParametrizedClass {
    public static class Parent<T> {
        public T field;
    }

    public static class Child extends Parent<String> {
    }
}
