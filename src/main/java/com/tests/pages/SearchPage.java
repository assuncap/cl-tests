package com.tests.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.tests.utils.Keys;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SearchPage extends BasePage {

    protected static final String XPATH_SEARCH_BAR = "//div[contains(@class,'cl-query-bar')]//input";
    protected static final String XPATH_LISTINGS_RESULTS  = "//div[contains(@class,'results')]//div[@data-pid]";
    protected static final String XPATH_SORT_SELECTOR = "//div[contains(@class,'cl-search-sort-mode')]//button";

    public class Listing {
        public String pid;
        public String title;
        public double price;
        public String location;
        public LocalDate date;
        public String bedrooms;
        public String sqft;
        public String url;
    }

    public enum SortModeSelector {

        NEWEST     ("//button[contains(@class,'cl-search-sort-mode-newest')]"),
        OLDEST     ("//button[contains(@class,'cl-search-sort-mode-oldest')]"),
        PRICE_ASC  ("//button[contains(@class,'cl-search-sort-mode-price-asc')]"),
        PRICE_DESC ("//button[contains(@class,'cl-search-sort-mode-price-desc')]"),
        UPCOMING   ("//button[contains(@class,'cl-search-sort-mode-upcoming')]"),
        CONTAINER  ("//div[contains(@class,'bd-for-bd-combo-box') and contains(@class,'bd-list-box')]");

        private final String xpath;

        SortModeSelector(String xpath) {
            this.xpath = xpath;
        }

        public String xpath() { return xpath; }
    }


    public SearchPage(Page page, String city, String path) {
        super(page, city, path);
    }

    public void searchBy(String query) {
        var locator = page.locator(XPATH_SEARCH_BAR);
        locator.fill(query);
        locator.press(Keys.ENTER);
    }

    public void SortBy(SortModeSelector sortOption)
    {
        var sorLocator = page.locator(XPATH_SORT_SELECTOR);
        sorLocator.click();
        var optionLocator = page.locator(sortOption.xpath());
        optionLocator.click();

    }

    /**
     * Parses the listed
     * @return
     */
    public List<Listing> parseListings() {
        List<Listing> listings = new ArrayList<>();

        // Get all listing elements
        page.waitForSelector(XPATH_LISTINGS_RESULTS);
        Locator results = page.locator(XPATH_LISTINGS_RESULTS);
        int count = results.count();

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("dd/MM")
                .parseDefaulting(ChronoField.YEAR, Year.now().getValue())
                .toFormatter();

        for (int i = 0; i < count; i++) {
            var item = results.nth(i);

            Listing listing = new Listing();

            Pattern pricepattern = Pattern.compile("[\\d.]+");
            Matcher pricematcher = pricepattern.matcher(getTextOrEmpty(item, ".priceinfo"));
            if (pricematcher.find()) {
                listing.price = Double.parseDouble( pricematcher.group().replace(".", ""));
            }
            else {
                listing.price = 0;
            }
            Pattern datepattern = Pattern.compile("\\d{2}/\\d{2}");
            Matcher datematcher = datepattern.matcher(getTextOrEmpty(item, ".priceinfo"));
            if (datematcher.find()) {
                listing.date  = LocalDate.parse( datematcher.group());
            }
            else {
                listing.date = LocalDate.now();
            }

            listing.pid      = item.getAttribute("data-pid");
            listing.title    = item.getAttribute("title");
            listing.url      = item.locator("a.posting-title").getAttribute("href");
            listing.location = getTextOrEmpty(item, ".result-location");

            listing.bedrooms = getTextOrEmpty(item, ".post-bedrooms");
            listing.sqft     = getTextOrEmpty(item, ".post-sqft");

            listings.add(listing);
        }
        return listings;
    }

    // Helper to avoid errors on missing elements
    private String getTextOrEmpty(Locator parent, String selector) {
        Locator el = parent.locator(selector);
        return el.count() > 0 ? el.textContent().trim() : "";
    }

}


