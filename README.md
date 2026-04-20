# cl-tests

BDD test automation framework for [Craigslist](https://craigslist.org) housing pages, built with Java 17, Playwright, Cucumber, and Maven.

---

## Requirements

- Java 17+
- Maven 3.8+

---

## Setup

Install Playwright browsers once after cloning:

```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

---

## Running Tests

```bash
# Run all scenarios
mvn test

# Run scenarios for a specific tag
mvn test -Dcucumber.filter.tags="@smoke"

# Skip scenarios tagged @wip
mvn test -Dcucumber.filter.tags="not @wip"
```

---

## Configuration

Default settings are in `src/test/resources/config.properties`:

| Key           | Default                        | Description                        |
|---------------|--------------------------------|------------------------------------|
| `browser`     | `chromium`                     | Browser to use                     |
| `headless`    | `false`                        | Run without a visible browser window |
| `baseUrl`     | `https://{city}.craigslist.org/` | Base URL — `{city}` is substituted at runtime |
| `defaultCity` | `madrid`                       | City used when no city is specified |
| `defaultTimeout` | `30000`                     | Default Playwright timeout (ms)    |
| `slowMo`      | `0`                            | Delay between Playwright actions (ms) |

---

## Reports

After each run, an HTML report is generated at:

```
target/cucumber-reports/report.html
```

Screenshots of failed scenarios are embedded in the report. Video recordings are saved to `target/videos/`.
