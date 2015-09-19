package org.biins.objectbuilder.builder;

import org.apache.commons.lang.Validate;
import org.biins.objectbuilder.types.Types;
import org.biins.objectbuilder.util.ClassUtils;

import java.util.Arrays;

/**
 * @author Martin Janys
 */
@SuppressWarnings("unchecked")
public abstract class AbstractCompositeBuilder extends AbstractBuilder {

    private ObjectBuilder objectBuilder;
    protected Boolean array = null;
    protected Boolean collection = null;

    public AbstractCompositeBuilder(ObjectBuilder objectBuilder) {
        this.objectBuilder = objectBuilder;
    }

    protected Object createCompositeObject(Types<?> types) {
        Class<?> type = types.getType();
        if (ClassUtils.isArray(type)) {
            if (collection != null && array != null) { // detect cycle
                Validate.isTrue(!(array && collection), "Cyclic generating of composite type");
            }
            array = array != null;
            return objectBuilder.onArray().buildArray(type);
        }
        if (ClassUtils.isCollection(type)) {
            if (collection != null && array != null) {
                Validate.isTrue(!(array && collection), "Cyclic generating of composite type");
            }
            collection = collection != null;
            return objectBuilder.onCollection().buildCollection(type);
        }
        else {
            return objectBuilder.build(type);
        }
    }

    protected Object createCompositeObject(Types<?> types, int ... size) {
        Class<?> type = types.getType();
        if (ClassUtils.isArray(type)) {
            return objectBuilder.onArray().buildArray(type, size);
        }
        if (ClassUtils.isCollection(type)) {
            return objectBuilder.onCollection().buildCollection(type, types.next(), size);
        }
        else {
            return objectBuilder.build(type);
        }
    }

    protected int[] decreaseDimension(int[] size) {
        return size.length > 1 ? Arrays.copyOfRange(size, 1, size.length) : new int[]{0};
    }

}
