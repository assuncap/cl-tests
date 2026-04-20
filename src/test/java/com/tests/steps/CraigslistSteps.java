package com.tests.steps;

import com.tests.context.TestContext;
import com.tests.pages.CityHomePage;
import com.tests.pages.SearchPage;
import io.cucumber.datatable.DataTable;
import java.util.LinkedHashMap;
import java.util.Map;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;



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

    @When("Sort by '{option}'")
    public void sortListings(SearchPage.SortModeSelector option) {
       SearchPage searchPage = ctx.getCurrentPage();
       searchPage.SortBy(option);
    }

    @Then("Validate that listings are sorted by '{option}'")
    public void validateSearchPageSorting(SearchPage.SortModeSelector sortOption) {
        SearchPage searchPage = ctx.getCurrentPage();
        var listings = searchPage.parseListings();
        assertThat(listings.size() > 0)
                .as("At least 1 listing needs to be displayed")
                .isTrue();
        assertThat(listings.size() > 1)
                .as("Sorting functionality needs at least 2 listings to be validated")
                .isTrue();

        for (int i = 0; i < listings.size() -1; i++) {
            SearchPage.Listing firstListing = listings.get(i);
            SearchPage.Listing nextListing = listings.get(i+1);
            switch (sortOption){
                case NEWEST -> assertThat(firstListing.date.isBefore(nextListing.date) || firstListing.date.isEqual(nextListing.date))
                                .as(String.format("listing date '%s' needs to be before '%s'", firstListing.date, nextListing.date))
                                .isTrue();
                case OLDEST -> assertThat(firstListing.date.isAfter(nextListing.date) || firstListing.date.isEqual(nextListing.date))
                                .as(String.format("listing date '%s' needs to be after '%s'", firstListing.date, nextListing.date))
                                .isTrue();
                case PRICE_DESC -> assertThat(firstListing.price >= nextListing.price)
                                .as(String.format("listing price %s needs to be higher than %s", firstListing.price, nextListing.price))
                                .isTrue();
                case PRICE_ASC -> assertThat(firstListing.price <= nextListing.price)
                        .as(String.format("listing price %s needs to be lower than %s", firstListing.price, nextListing.price))
                        .isTrue();
                }
        }



    }


    @Then("Validate displayed sort options:")
    public void validateDisplayedSortOptions(DataTable table) {
        Map<String, SearchPage.SortModeSelector> expectedOptions = new LinkedHashMap<>();
        table.asList().forEach(value -> {
            SearchPage.SortModeSelector sortMode = SearchPage.SortModeSelector.valueOf(value.toUpperCase());
            expectedOptions.put(value, sortMode);
        });

        SearchPage searchPage = ctx.getCurrentPage();
        var options = searchPage.getAvailableSortOptions();

        assertThat(options)
                .as("Displayed sort options do not match expected")
                .containsExactlyInAnyOrderElementsOf(expectedOptions.values());

        




    }
}
