package com.tests.steps;

import io.cucumber.java.ParameterType;

public class ParameterTypes {

    @ParameterType("[A-Za-z]+")
    public String city(String city) {
        return city.toLowerCase();
    }
}
