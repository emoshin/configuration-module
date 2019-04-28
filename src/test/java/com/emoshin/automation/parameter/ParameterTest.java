package com.emoshin.automation.parameter;

import org.junit.jupiter.api.Test;

public class ParameterTest {
    @Test
    public void simpleParameter() {
        Parameter<String> SITE_URL = new SimpleParameter<>("www.some.test.com");
        Parameter<String> NEW_URL = new SimpleParameter<>("www.tut.by");
        ParameterType<String> STRING_DATA_TYPE = ParameterTypeImpl.of(String.class);
        ParameterType<Double> DOUBLE_DATA_TYPE = ParameterTypeImpl.of(Double.class);
        System.out.println(SITE_URL.getParameterType().equals(STRING_DATA_TYPE));
        System.out.println(SITE_URL.getParameterType().equals(DOUBLE_DATA_TYPE));
        System.out.println(SITE_URL.getValue());
    }
}
