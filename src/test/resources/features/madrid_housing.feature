Feature: Testing CraigList's Madrid Housing page

  Scenario: Show Madrid housing page
    Given I open Madrid CraigList
    When On homepage click on housing
    Then Validate that Madrid Housing page has been loaded

#  Scenario: Main heading is visible
#    Given I am on the example home page
#    Then the main heading should be "Example Domain"
#
#  @wip
#  Scenario: More information link navigates away
#    Given I am on the example home page
#    When I click the more information link
#    Then the page title should contain "IANA"
