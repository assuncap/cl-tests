package com.tests.steps;

import com.tests.context.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;

import java.util.Map;

/**
 *  Added this class as an example of what I usually include in my frameworks. In this case the ability to override config from the feature file. Can be very useful when particular setup is needed
 */
public class SettingsSteps {

    private final TestContext ctx;

    public SettingsSteps(TestContext ctx) {
        this.ctx = ctx;
    }

    @Given("I set viewport to {int} by {int}")
    public void setViewport(int width, int height) {
        ctx.page.setViewportSize(width, height);
    }

    @Given("I set default timeout to {int} milliseconds")
    public void setTimeout(int millis) {
        ctx.page.setDefaultTimeout(millis);
    }

    /**
     * Applies multiple settings from a two-column key/value table.
     *
     * Example usage in a feature file:
     *   Given I configure settings:
     *     | screenshotOnStep    | true |
     *     | width               | 1920      |
     *     | height              | 1080      |
     *     | timeout            | 15000     |
     */
    @Given("I configure settings:")
    public void configureSettings(DataTable table) {
        Map<String, String> settings = table.asMap(String.class, String.class);

        if (settings.containsKey("width") || settings.containsKey("height")) {
            int width  = Integer.parseInt(settings.getOrDefault("width",  "1280"));
            int height = Integer.parseInt(settings.getOrDefault("height", "720"));
            ctx.page.setViewportSize(width, height);
        }
        if (settings.containsKey("timeout")) {
            ctx.page.setDefaultTimeout(Integer.parseInt(settings.get("timeout")));
        }
        if (settings.containsKey("screenshotOnStep")) {
            ctx.setScreenshotOnStep(Boolean.valueOf(settings.get("screenshotOnStep")));
        }


    }
}
