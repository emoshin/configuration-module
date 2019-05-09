package com.emoshin.automation.parameter;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ParameterTest {
    private static final Logger logger = LoggerFactory.getLogger(ParameterTest.class);

    @Test
    public void simpleParameter() {
        Parameter<String> SITE_URL = new SimpleParameter<>("www.some.test.com");
        Parameter<String> NEW_URL = new SimpleParameter<>("www.tut.by");
        Parameter<List<String>> URL_LIST = new SimpleParameter<>(Arrays.asList("www.tut.by", "www.google.com", "www.onliner.by"));
        ParameterType<String> STRING_DATA_TYPE = ParameterTypeImpl.of(String.class);
        ParameterType<ArrayList> LIST_DATA_TYPE = ParameterTypeImpl.of(ArrayList.class);
        assertThat(SITE_URL.getParameterType()).as("String parameters has the same types").isEqualTo(STRING_DATA_TYPE);
        assertThat(SITE_URL.getParameterType()).as("Parameter types are the same").isEqualTo(NEW_URL.getParameterType());
        assertThat(URL_LIST.getValue()).as("URL list has 3 records inside").size().isEqualTo(3);
        assertThat(URL_LIST.getValue()).as("List contains the same value as NEW_URL variable").contains(NEW_URL.getValue());
        assertThat(URL_LIST.getParameterType()).as("URL_LIST has LIST parameter type").isEqualTo(LIST_DATA_TYPE);
    }
}
