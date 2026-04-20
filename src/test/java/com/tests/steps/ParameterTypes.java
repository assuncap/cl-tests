package com.tests.steps;

import com.tests.pages.SearchPage;
import io.cucumber.java.ParameterType;

public class ParameterTypes {

    @ParameterType("[A-Za-z]+")
    public String city(String city) {
        return city.toLowerCase();
    }

    @ParameterType("[A-Za-z_]+")
    public SearchPage.SortModeSelector option(String value) {
        return SearchPage.SortModeSelector.valueOf(value.toUpperCase());
    }
}
