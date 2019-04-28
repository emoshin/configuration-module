package com.emoshin.automation.parameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

public class SimpleParameter<T> implements Parameter<T> {

    private final Logger logger = LoggerFactory.getLogger(Parameter.class);

    private final T paramValue;
    private final Class<T> type;

    public SimpleParameter(T paramValue) {
        this.paramValue = paramValue;
        this.type = getGenericClass();
    }

    @Override
    public T getValue() {
        return paramValue;
    }

    @Override
    public ParameterType<T> getParameterType() {
        return ParameterTypeImpl.of(type);
    }

    @Override
    public String toString() {
        return String.valueOf(paramValue);
    }

    @SuppressWarnings("unchecked")
    private Class<T> getGenericClass() {
        Class<T> result = null;
        Type mySuperclass = paramValue.getClass();
        String className = mySuperclass.getTypeName();
        try {
            result = (Class<T>) Class.forName(className);
        } catch (ClassNotFoundException ex) {
            logger.error("Class {} was not found", className);
        }
        return result;
    }
}
