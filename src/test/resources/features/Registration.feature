Feature: User Registration
  As a new user
  I want to register an account
  So that I can access the application

  Background:
    Given User navigates to registration page

  @smoke @regression
  Scenario: Successful user registration with valid details
    When User enters valid registration details
      | firstName | John              |
      | lastName  | Doe               |
      | email     | john.doe@test.com |
      | password  | Test@12345        |
      | phone     | 1234567890        |
    And User clicks on register button
    Then User should see registration success message
    And User should be redirected to login page

  @regression
  Scenario: Registration with existing email
    When User enters email that already exists
    And User clicks on register button
    Then User should see error message "Email already registered"

  @regression
  Scenario Outline: Registration with invalid data
    When User enters "<firstName>" as first name
    And User enters "<email>" as email
    And User enters "<password>" as password
    And User clicks on register button
    Then User should see error message "<errorMessage>"

    Examples:
      | firstName | email              | password   | errorMessage               |
      |           | john@test.com      | Test@12345 | First name is required     |
      | John      | invalidemail       | Test@12345 | Invalid email format       |
      | John      | john@test.com      | 123        | Password must be strong    |

  @dataDriven
  Scenario: Register multiple users from Excel
    When User registers multiple users from test data file
    Then All registrations should be successful
