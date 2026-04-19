package com.tests.pages;

import com.microsoft.playwright.Page;
import com.tests.utils.Keys;

public class SearchPage extends BasePage {

    public static final String XPATH_SEARCH_BAR = "//div[contains(@class,'cl-query-bar')]//input";
    public static final String XPATH_SORT_NEWEST     = "//button[contains(@class,'cl-search-sort-mode-newest')]";
    public static final String XPATH_SORT_OLDEST     = "//button[contains(@class,'cl-search-sort-mode-oldest')]";
    public static final String XPATH_SORT_PRICE_ASC  = "//button[contains(@class,'cl-search-sort-mode-price-asc')]";
    public static final String XPATH_SORT_PRICE_DESC = "//button[contains(@class,'cl-search-sort-mode-price-desc')]";
    public static final String XPATH_SORT_UPCOMING   = "//button[contains(@class,'cl-search-sort-mode-upcoming')]";
    public static final String XPATH_SORT_CONTAINER  = "//div[contains(@class,'bd-for-bd-combo-box') and contains(@class,'bd-list-box')]";

    public SearchPage(Page page, String city, String path) {
        super(page, city, path);
    }

    public void searchBy(String query) {
        var locator = page.locator(XPATH_SEARCH_BAR);
        locator.fill(query);
        locator.press(Keys.ENTER);
    }
}
