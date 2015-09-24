package org.biins.objectbuilder.builder.generator;

/**
 * @author Martin Janys
 */
public class AlphabetGenerator implements Generator<String> {

    private final boolean isUpperCase;
    private StringBuilder builder = new StringBuilder();

    public AlphabetGenerator() {
        this(false);
    }

    public AlphabetGenerator(boolean isUpperCase) {
        this.isUpperCase = isUpperCase;
    }

    private char format(char ch) {
        return isUpperCase ? Character.toUpperCase(ch) : Character.toLowerCase(ch);
    }

    @Override
    public boolean isCyclic() {
        return true;
    }

    @Override
    public void reset() {
        builder = new StringBuilder();
    }

    @Override
    public boolean hasNext() {
        return builder.length() > 0 && builder.charAt(builder.length() - 1) != 'z';
    }

    @Override
    public String next() {
        if (builder.length() > 0) {
            int last = builder.length() - 1;
            char c = builder.charAt(last);
            if (c >= 'a' && c <= 'z') {
                c++;
                builder.setCharAt(last, c);
            } else {
                builder.append(format('a'));
            }
        }
        else {
            builder.append(format('a'));
        }

        return builder.toString();
    }
}
