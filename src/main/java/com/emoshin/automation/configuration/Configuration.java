package com.emoshin.automation.configuration;

import com.emoshin.automation.parameter.Parameter;

public interface Configuration {

    <T> Parameter<T> getParameter(String paramName);

    void put(String paramName, Parameter<?> parameter);

    int size();
}
