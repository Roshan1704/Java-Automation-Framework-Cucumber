package com.automation.stepdefinitions;

import com.automation.driver.DriverManager;
import com.automation.pages.LoginPage;
import com.automation.utils.ConfigReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Step Definitions for Login feature
 */
public class LoginSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private ConfigReader configReader;

    public LoginSteps() {
        this.driver = DriverManager.getDriver("chrome");
        this.loginPage = new LoginPage(driver);
        this.configReader = ConfigReader.getInstance();
    }

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        String baseUrl = configReader.getProperty("base.url");
        driver.get(baseUrl + "/login");
    }

    @When("I enter username {string}")
    public void iEnterUsername(String username) {
        loginPage.enterUsername(username);
    }

    @And("I enter password {string}")
    public void iEnterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @And("I click on the login button")
    public void iClickOnTheLoginButton() {
        loginPage.clickLogin();
    }

    @Then("I should be logged in successfully")
    public void iShouldBeLoggedInSuccessfully() {
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login was not successful");
    }

    @And("I should see the welcome message")
    public void iShouldSeeTheWelcomeMessage() {
        String welcomeMessage = loginPage.getWelcomeMessage();
        Assert.assertFalse(welcomeMessage.isEmpty(), "Welcome message is not displayed");
    }

    @Then("I should see an error message")
    public void iShouldSeeAnErrorMessage() {
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed");
    }

    @And("the error message should contain {string}")
    public void theErrorMessageShouldContain(String expectedText) {
        String actualErrorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(actualErrorMessage.contains(expectedText),
                "Expected error message to contain: " + expectedText + ", but got: " + actualErrorMessage);
    }

    @Then("I should see {string}")
    public void iShouldSee(String result) {
        if (result.equalsIgnoreCase("success")) {
            Assert.assertTrue(loginPage.isLoginSuccessful(), "Login should be successful");
        } else {
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
        }
    }
}
