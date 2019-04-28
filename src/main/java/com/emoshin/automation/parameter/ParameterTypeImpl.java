package com.emoshin.automation.parameter;

import java.util.Objects;

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
        if (getClass() != o.getClass()) {
            return false;
        }
        ParameterType<T> newObj = (ParameterType<T>) o;
        return Objects.equals(this.getType(), newObj.getType());
    }

    @Override
    public String toString() {
        return type.getSimpleName();
    }

    public static <T> ParameterType<T> of(Class<T> type) {
        return new ParameterTypeImpl<>(type);
    }
}
