package com.tests.context;

import com.microsoft.playwright.*;
import com.tests.pages.BasePage;
import com.tests.pages.factory.CraigslistPageFactory;
import com.tests.pages.factory.PageFactory;
import com.tests.utils.ConfigManager;

import java.nio.file.Paths;

/**
 * Scenario-scoped state shared across step definition classes via PicoContainer.
 * One instance is created per scenario and injected by constructor into any step class that declares it.
 */
public class TestContext {

    private static Playwright playwright;
    private static Browser browser;

    public final Page page;
    private final BrowserContext context;

    public BasePage currentPage;
    public PageFactory pageFactory;

    private boolean screenshotOnStep;
    public TestContext() {
        context = getBrowser().newContext(new Browser.NewContextOptions()
                .setViewportSize(1280, 720)
                .setRecordVideoDir(Paths.get("target/videos/")));
        page = context.newPage();
        page.setDefaultTimeout(ConfigManager.getDefaultTimeout());
        pageFactory = new CraigslistPageFactory(page);
        screenshotOnStep = false;
    }

    public void initFactory(String city) {
        pageFactory = new CraigslistPageFactory(page, city);
    }

    public void close() {
        context.close();
    }

    public <T extends BasePage> T getCurrentPage() {
        return (T) currentPage;
    }

    public void setCurrentPage(BasePage page) {
        currentPage = page;
    }

    private static synchronized Browser getBrowser() {
        if (browser == null) {
            playwright = Playwright.create();
            BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                    .setHeadless(ConfigManager.isHeadless())
                    .setSlowMo(ConfigManager.getSlowMo());

            browser = switch (ConfigManager.getBrowser().toLowerCase()) {
                case "firefox" -> playwright.firefox().launch(options);
                case "webkit"  -> playwright.webkit().launch(options);
                default        -> playwright.chromium().launch(options);
            };

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                browser.close();
                playwright.close();
            }));
        }
        return browser;
    }

    public void setScreenshotOnStep(Boolean screenshotOnStep) {
        this.screenshotOnStep = screenshotOnStep;
    }

    public Boolean getScreenshotOnStep() {
        return screenshotOnStep;
    }
}
