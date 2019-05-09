package com.emoshin.automation;

public class ParameterNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Parameter [%s] was not found in configuration!";

    public ParameterNotFoundException(String paramName) {
        super(String.format(MESSAGE, paramName));
    }
}
