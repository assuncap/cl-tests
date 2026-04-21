package com.tests.pages.factory;

import com.microsoft.playwright.Page;
import com.tests.pages.CityHomePage;
import com.tests.pages.SearchPage;
import com.tests.utils.ConfigManager;

public class CraigslistPageFactory implements PageFactory {

    private final Page page;
    private final String city;

    public CraigslistPageFactory(Page page, String city) {
        this.page = page;
        this.city = city;
    }

    public CraigslistPageFactory(Page page) {
        this(page, ConfigManager.getDefaultCity());
    }

    @Override
    public CityHomePage createCityHomePage() {
        return new CityHomePage(page, city, this);
    }

    @Override
    public SearchPage createSearchPage(String path) {
        return new SearchPage(page, city, path);
    }
}
