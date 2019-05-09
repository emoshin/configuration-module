package com.emoshin.automation.parameter;

public class ParameterTypeImpl<T> implements ParameterType<T> {

    private final Class<T> type;

    private ParameterTypeImpl(Class<T> type) {
        this.type = type;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        return getClass() == o.getClass();
    }

    @Override
    public String toString() {
        return type.getSimpleName();
    }

    public static <T> ParameterType<T> of(Class<T> type) {
        return new ParameterTypeImpl<>(type);
    }
}
