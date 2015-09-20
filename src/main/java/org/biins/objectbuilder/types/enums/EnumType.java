package org.biins.objectbuilder.types.enums;

import org.biins.objectbuilder.types.Type;

import java.util.Random;

/**
 * @author Martin Janys
 */
public class EnumType extends Type {

    private static Random random = new Random(System.currentTimeMillis());

    @SuppressWarnings("unchecked")
    public EnumType(Class<?> type) {
        super(type, null);
    }

    public int size() {
        Object[] enumConstants = getType().getEnumConstants();
        if (enumConstants == null)
            return 0;
        return enumConstants.length;
    }

    public Object generate() {
        return generate(random.nextInt(size()));
    }
    public Object generate(int position) {
        Object[] enumConstants = getType().getEnumConstants();
        if (enumConstants == null || enumConstants.length == 0)
            return null;
        return enumConstants[position];
    }
}
