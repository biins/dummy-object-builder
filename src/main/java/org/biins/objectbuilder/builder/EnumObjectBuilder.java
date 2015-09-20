package org.biins.objectbuilder.builder;

import org.apache.commons.lang.Validate;
import org.biins.objectbuilder.builder.strategy.EnumGeneratorStrategy;
import org.biins.objectbuilder.types.enums.EnumType;
import org.biins.objectbuilder.types.enums.EnumTypeRegistry;

/**
 * @author Martin Janys
 */
public class EnumObjectBuilder extends AbstractBuilder implements Builder {

    protected EnumGeneratorStrategy enumStrategy = EnumGeneratorStrategy.DEFAULT;

    public EnumObjectBuilder setGeneratorStrategy(EnumGeneratorStrategy enumStrategy) {
        this.enumStrategy = enumStrategy;
        return this;
    }

    public String build() {
        return build(String.class);
    }

    @Override
    public <T> T build(Class<T> type) {
        return buildEnum(type);
    }

    @SuppressWarnings("unchecked")
    public <T> T buildEnum(Class<T> type) {
        Validate.isTrue(Enum.class.isAssignableFrom(type));
        EnumType enumType = EnumTypeRegistry.get(type);
        switch (enumStrategy) {
            case FIRST:
                return (T) enumType.generate(0);
            case LAST:
                return (T) enumType.generate(enumType.size() - 1);
            case RANDOM:
                return (T) enumType.generate();
            case NULL:
            case DEFAULT:
            default:
                return (T) enumType.getDefaultValue();
        }
    }
}
