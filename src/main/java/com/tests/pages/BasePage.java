package com.tests.pages;

import com.microsoft.playwright.Page;
import com.tests.utils.ConfigManager;

public abstract class BasePage {

    protected final Page page;
    protected String pageUrl;

    public BasePage(Page page) {
        this(page, ConfigManager.getDefaultCity(), "");
    }

    public BasePage(Page page, String city) {
        this(page, city, "");
    }

    public BasePage(Page page, String city, String path) {
        this.page = page;
        page.setDefaultTimeout(ConfigManager.getDefaultTimeout());
        this.pageUrl = baseUrl().replace("{city}", city) + path;
    }

    protected String baseUrl() {
        return ConfigManager.getBaseUrl();
    }


    public String getTitle() {
        return page.title();
    }

    public void navigateTo() {
        page.navigate(pageUrl);
    }

    /**
     * Compares the current url with the expected url
     * @return
     */
    public boolean isPageLoaded()
    {
        return page.url().startsWith(pageUrl);
    }
}
