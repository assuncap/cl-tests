Feature: Testing CraigList's Madrid Housing page
  Background:
    Given I open Madrid CraigList
    When On homepage click on housing
    Then Validate that Madrid Housing page has been loaded

  Scenario Outline: Show Madrid housing page and sort listings
    When Sort by '<sort-by-first>'
    Then Validate that listings are sorted by '<sort-by-first>'
    When Sort by '<sort-by-after>'
    Then Validate that listings are sorted by '<sort-by-after>'

    Examples:
      | sort-by-first | sort-by-after |
      | PrIcE_ASC     | NEWEST        |
      | oldest        | price_desc    |
      | price_desc    | price_asc     |

  Scenario: Check Sorting Options
    And Validate displayed sort options:
      | price_desc |
      | price_asc  |
      | newest     |
    When Sort by 'price_desc'
    Then Validate displayed sort options:
      | price_desc |
      | price_asc  |
      | newest     |
      | upcoming   |
      | relevant   |