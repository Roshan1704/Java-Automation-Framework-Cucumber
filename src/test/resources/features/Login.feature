Feature: User Login Functionality
  As a user
  I want to be able to login to the application
  So that I can access my account

  Background:
    Given I am on the login page

  @smoke @regression
  Scenario: Successful login with valid credentials
    When I enter username "testuser@example.com"
    And I enter password "Test@123"
    And I click on the login button
    Then I should be logged in successfully
    And I should see the welcome message

  @regression
  Scenario: Login fails with invalid username
    When I enter username "invalid@example.com"
    And I enter password "Test@123"
    And I click on the login button
    Then I should see an error message
    And the error message should contain "Invalid credentials"

  @regression
  Scenario: Login fails with invalid password
    When I enter username "testuser@example.com"
    And I enter password "wrongpassword"
    And I click on the login button
    Then I should see an error message
    And the error message should contain "Invalid credentials"

  @regression
  Scenario Outline: Login with multiple user types
    When I enter username "<username>"
    And I enter password "<password>"
    And I click on the login button
    Then I should see "<result>"

    Examples:
      | username              | password    | result  |
      | admin@example.com     | Admin@123   | success |
      | user@example.com      | User@123    | success |
      | guest@example.com     | Guest@123   | error   |
