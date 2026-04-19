package com.tests.steps;

import com.tests.context.TestContext;
import com.tests.pages.CityHomePage;
import com.tests.pages.SearchPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class CraigslistSteps {

    private final TestContext ctx;

    public CraigslistSteps(TestContext ctx) {
        this.ctx = ctx;
    }

    @Given("I open {city} CraigList")
    public void iOpenCityHomepage(String city) {
        ctx.initFactory(city);
        CityHomePage homePage = ctx.pageFactory.createCityHomePage();
        homePage.navigateTo();
        ctx.setCurrentPage(homePage);
    }

    @When("On homepage click on housing")
    public void clickHousing() {
        CityHomePage homePage = ctx.getCurrentPage();
        assertThat(homePage).isInstanceOf(CityHomePage.class);
        ctx.setCurrentPage(homePage.clickHousing());
    }

    @Then("Validate that {city} Housing page has been loaded")
    public void validateHousingPageLoaded(String city) {
        SearchPage searchPage = ctx.getCurrentPage();
        assertThat(searchPage.isPageLoaded())
                .as("Expected housing page for %s to be loaded", city)
                .isTrue();
    }
}
