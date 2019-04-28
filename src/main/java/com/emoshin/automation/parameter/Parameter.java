package com.emoshin.automation.parameter;

public interface Parameter<T> {

    ParameterType<T> getParameterType();

    T getValue();
}
