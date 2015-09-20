package org.biins.objectbuilder.builder;

import org.apache.commons.lang.Validate;
import org.biins.objectbuilder.ConstantPool;
import org.biins.objectbuilder.builder.strategy.StringGeneratorStrategy;
import org.biins.objectbuilder.types.string.StringType;
import org.biins.objectbuilder.types.string.StringTypeRegistry;

/**
 * @author Martin Janys
 */
public class StringObjectBuilder extends AbstractBuilder implements Builder {

    protected StringGeneratorStrategy stringStrategy = StringGeneratorStrategy.DEFAULT;

    private int size = 10;
    private Boolean lower = null;
    private Boolean alpha = null;
    private Boolean numeric = null;

    public StringObjectBuilder setGeneratorStrategy(StringGeneratorStrategy stringStrategy) {
        this.stringStrategy = stringStrategy;
        return this;
    }

    public StringObjectBuilder setSize(int size) {
        this.size = size;
        return this;
    }

    public StringObjectBuilder setLower(boolean lower) {
        this.lower = lower;
        return this;
    }

    public StringObjectBuilder setAlpha(boolean alpha) {
        this.alpha = alpha;
        return this;
    }

    public StringObjectBuilder setNumeric(boolean numeric) {
        this.numeric = numeric;
        return this;
    }

    public StringObjectBuilder setAttributes(boolean lower, boolean alpha, boolean numeric) {
        this.lower = lower;
        this.alpha = alpha;
        this.numeric = numeric;
        return this;
    }

    public String build() {
        return build(String.class);
    }

    @Override
    public <T> T build(Class<T> type) {
        Validate.isTrue(String.class.isAssignableFrom(type));
        return buildString();
    }

    @SuppressWarnings("unchecked")
    public <T> T buildString() {
        StringType stringType = StringTypeRegistry.get();
        switch (stringStrategy) {
            case EMPTY:
                return (T) ConstantPool.EMPTY_STRING;
            case NULL:
                return null;
            case UUID:
                return (T) stringType.generateUUID();
            case VALUE:
                return (T) stringType.generate(size, valueOrElse(lower, false), valueOrElse(alpha, !isAttributesSet()), valueOrElse(numeric, !isAttributesSet()));
            case DEFAULT:
            default:
                return (T) stringType.getDefaultValue();
        }
    }

    private boolean valueOrElse(Boolean b, boolean val) {
        if (b != null) {
            return b;
        }
        else {
            return val;
        }
    }

    public boolean isAttributesSet() {
        return alpha != null || numeric != null;
    }
}
