package org.biins.objectbuilder.builder;

import org.biins.objectbuilder.builder.strategy.CommonObjectGeneratorStrategy;
import org.biins.objectbuilder.types.Types;
import org.biins.objectbuilder.util.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * @author Martin Janys
 */
public class CommonObjectBuilder extends AbstractBuilder implements Builder {

    private final Logger logger = Logger.getLogger(CommonObjectBuilder.class.getName());

    private final ObjectBuilder objectBuilder;
    private final Stack<Class<?>> typeStack;
    private CommonObjectGeneratorStrategy objectStrategy = CommonObjectGeneratorStrategy.DEFAULT;

    public CommonObjectBuilder(ObjectBuilder objectBuilder) {
        this.objectBuilder = objectBuilder;
        typeStack = new Stack<>();
    }

    public CommonObjectBuilder setGeneratorStrategy(CommonObjectGeneratorStrategy objectStrategy) {
        this.objectStrategy = objectStrategy;
        return this;
    }

    @Override
    public <T> T build(Class<T> type) {
        switch (objectStrategy) {
            case NULL:
                return null;
            case VALUE:
            case DEFAULT:
            default:
                if (typeStack.contains(type)) {
                    logger.warning("Detect cyclic reference of " + type + ". Return null");
                    return null;
                }
                typeStack.push(type);
                return buildObjectInternal(type);
        }
    }

    public  <T> T buildObject(Class<T> type) {
        return build(type);
    }

    @SuppressWarnings("unchecked")
    public  <T> T buildObjectInternal(Class<T> type) {
        Object o = newInstance(type);
        fillObject(o, type);
        return (T) o;
    }

    private <T> void fillObject(Object o, Class<T> type) {
        List<Field> fields = ClassUtils.getFields(type);
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            Object fieldValue;
            if (ClassUtils.isCollection(fieldType)) {
                Type genericType = field.getGenericType();
                Types types = genericType instanceof ParameterizedType ? Types.typeOf(((ParameterizedType)genericType).getActualTypeArguments()[0]) : null;
                fieldValue = objectBuilder.onCollection()
                        .of(types)
                        .build(fieldType);
            }
            else if (ClassUtils.isMap(fieldType)) {
                Type[] types = getGenericType(field);
                fieldValue = objectBuilder.onMap()
                        .ofKey(Types.typeOf(types[0]))
                        .ofValue(Types.typeOf(types[1]))
                        .build(fieldType);
            }
            else {
                fieldValue = objectBuilder.build(fieldType);
            }
            ClassUtils.setProperty(o, field, fieldValue);
        }
    }

    private Type[] getGenericType(Field field) {
        Type[] types = new Type[2];
        if (field.getGenericType() instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (types.length > 0) {
                types[0] = actualTypeArguments[0];
            }
            if (types.length > 1) {
                types[1] = actualTypeArguments[1];
            }
        }
        return types;
    }

    private <T> Object newInstance(Class<T> type) {
        T object = ClassUtils.newInstance(type);
        if (object != null) {
            return object;
        }
        else {
            Constructor<?>[] constructors = type.getConstructors();
            for (Constructor<?> constructor : constructors) {
                object = ClassUtils.newInstance(type, constructor, newInstances(constructor.getParameterTypes()));
                if (object != null) {
                    return object;
                }
            }
            return null;
        }
    }

    private Object[] newInstances(Class<?> ... types) {
        Object[] objects = new Object[types.length];
        for (int i = 0; i < objects.length; i++) {
            Class<?> type = types[i];
            Object build = objectBuilder.build(type);
            objects[i] = build;
        }
        return objects;
    }
}
