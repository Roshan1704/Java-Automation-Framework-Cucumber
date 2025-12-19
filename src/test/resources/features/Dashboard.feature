Feature: Dashboard Functionality
  As a logged in user
  I want to access dashboard
  So that I can view my account information

  Background:
    Given User is logged in to the application

  @smoke
  Scenario: Verify dashboard elements
    When User navigates to dashboard
    Then User should see dashboard title
    And User should see welcome message
    And User should see user profile icon
    And User should see logout button

  @regression
  Scenario: User logout from dashboard
    When User is on dashboard
    And User clicks on logout button
    Then User should be logged out
    And User should be redirected to login page

  @regression
  Scenario: View user profile from dashboard
    When User is on dashboard
    And User clicks on user profile icon
    Then User profile page should be displayed
    And User should see personal information
