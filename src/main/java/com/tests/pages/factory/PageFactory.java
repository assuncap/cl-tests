package com.tests.pages.factory;

import com.tests.pages.CityHomePage;
import com.tests.pages.SearchPage;

public interface PageFactory {

    CityHomePage createCityHomePage();

    SearchPage createSearchPage(String path);
}
