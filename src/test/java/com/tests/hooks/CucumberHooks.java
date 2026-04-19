package com.tests.hooks;

import com.tests.context.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class CucumberHooks {

    private final TestContext ctx;

    public CucumberHooks(TestContext ctx) {
        this.ctx = ctx;
    }

    @Before
    public void setUp() {
        // Page is already created by TestContext constructor; nothing extra needed.
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ctx.page.screenshot();
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
        ctx.close();
    }
}
