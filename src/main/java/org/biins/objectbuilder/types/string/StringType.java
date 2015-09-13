package org.biins.objectbuilder.types.string;

import org.biins.objectbuilder.ConstantPool;
import org.biins.objectbuilder.types.Type;

import java.lang.reflect.Array;
import java.util.Random;
import java.util.UUID;

/**
 * @author Martin Janys
 */
public class StringType extends Type<String> {

    private static final String NUMERIC = "0123456789";
    private static final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA_LOWER = ALPHA.toLowerCase();

    private Random random;

    @SuppressWarnings("unchecked")
    public StringType() {
        super(String.class, ConstantPool.STRING_DEFAULT);
        random = new Random(System.currentTimeMillis());
    }

    public String generate(int length, boolean lower, boolean alpha, boolean numeric) {
        if (!alpha && !numeric) {
            return ConstantPool.EMPTY_STRING;
        }
        else {
            String charValues = charValues(lower, alpha, numeric);
            int charValuesLen = charValues.length();
            char[] result = new char[length];
            for (int i = 0; i < result.length; i++) {
                result[i] = charValues.charAt(random.nextInt(charValuesLen));
            }
            return new String(result);
        }
    }

    private String charValues(boolean lower, boolean alpha, boolean numeric) {
        StringBuilder sb = new StringBuilder();
        if (alpha)
            sb.append(lower ? ALPHA_LOWER : ALPHA);
        if (numeric)
            sb.append(NUMERIC);
        return sb.toString();
    }

    public String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
