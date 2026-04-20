package com.tests.pages;

import com.microsoft.playwright.Page;
import com.tests.pages.factory.PageFactory;

public class CityHomePage extends BasePage {

    private static final String HOUSING_LINK = "a[data-cat='hhh']";
    private static final String HOUSING_PATH = "/search/hhh";

    protected final String city;
    private final PageFactory factory;

    public CityHomePage(Page page, String city, PageFactory factory) {
        super(page, city);
        this.city = city;
        this.factory = factory;
    }

    public SearchPage clickHousing() {
        page.locator(HOUSING_LINK).click();
        return factory.createSearchPage(HOUSING_PATH);
    }
}
