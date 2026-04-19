Feature: Testing CraigList's Madrid Housing page

  Scenario Outline: Show Madrid housing page
    Given I open Madrid CraigList
    When On homepage click on housing
    Then Validate that Madrid Housing page has been loaded
    When Sort by '<sort-by-first>'
    Then Validate that listings are sorted by '<sort-by-first>'
    When Sort by '<sort-by-after>'
    Then Validate that listings are sorted by '<sort-by-after>'


    Examples:
      | sort-by-first | sort-by-after |
      | PrIcE_ASC     | NEWEST     |
      | oldest        | price_desc    |
      | price_desc    | price_asc     |

