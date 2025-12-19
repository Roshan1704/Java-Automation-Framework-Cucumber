package com.automation.stepdefinitions;

import com.automation.pages.RegistrationPage;
import com.automation.utils.TestDataGenerator;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.Map;

/**
 * Step definitions for Registration feature
 */
public class RegistrationSteps {

    private RegistrationPage registrationPage;

    @Given("User navigates to registration page")
    public void userNavigatesToRegistrationPage() {
        // Implementation
    }

    @When("User enters valid registration details")
    public void userEntersValidRegistrationDetails(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        registrationPage.registerUser(
            data.get("firstName"),
            data.get("lastName"),
            data.get("email"),
            data.get("password"),
            data.get("phone")
        );
    }

    @And("User clicks on register button")
    public void userClicksOnRegisterButton() {
        // Already handled in registerUser method
    }

    @Then("User should see registration success message")
    public void userShouldSeeRegistrationSuccessMessage() {
        Assert.assertTrue(registrationPage.isRegistrationSuccessful(),
            "Registration success message not displayed");
    }

    @And("User should be redirected to login page")
    public void userShouldBeRedirectedToLoginPage() {
        // Verify URL contains login
        // Assert.assertTrue(driver.getCurrentUrl().contains("login"));
    }

    @When("User enters email that already exists")
    public void userEntersEmailThatAlreadyExists() {
        registrationPage.registerUser(
            "John", "Doe", "existing@test.com", "Test@12345", "1234567890"
        );
    }

    @Then("User should see error message {string}")
    public void userShouldSeeErrorMessage(String expectedMessage) {
        String actualMessage = registrationPage.getErrorMessage();
        Assert.assertTrue(actualMessage.contains(expectedMessage),
            "Expected error message not found");
    }

    @When("User registers multiple users from test data file")
    public void userRegistersMultipleUsersFromTestDataFile() {
        // Generate test data using TestDataGenerator
        for (int i = 0; i < 5; i++) {
            String firstName = TestDataGenerator.generateFirstName();
            String lastName = TestDataGenerator.generateLastName();
            String email = TestDataGenerator.generateEmail();
            String password = TestDataGenerator.generatePassword(8);
            String phone = TestDataGenerator.generatePhoneNumber();
            
            registrationPage.registerUser(firstName, lastName, email, password, phone);
        }
    }

    @Then("All registrations should be successful")
    public void allRegistrationsShouldBeSuccessful() {
        Assert.assertTrue(registrationPage.isRegistrationSuccessful());
    }
}
