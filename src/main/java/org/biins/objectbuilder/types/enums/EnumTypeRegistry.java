package org.biins.objectbuilder.types.enums;

/**
 * @author Martin Janys
 */
public class EnumTypeRegistry {

    @SuppressWarnings("unchecked")
    public static EnumType get(Class<?> type) {
        return new EnumType(type);
    }
}
