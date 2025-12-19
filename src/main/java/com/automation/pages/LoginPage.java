package com.automation.pages;

import com.automation.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for Login Page
 */
public class LoginPage extends BasePage {

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = ".error-message")
    private WebElement errorMessage;

    @FindBy(css = ".welcome-message")
    private WebElement welcomeMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Enter username
     */
    public LoginPage enterUsername(String username) {
        type(usernameField, username);
        return this;
    }

    /**
     * Enter password
     */
    public LoginPage enterPassword(String password) {
        type(passwordField, password);
        return this;
    }

    /**
     * Click login button
     */
    public void clickLogin() {
        click(loginButton);
    }

    /**
     * Complete login with username and password
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    /**
     * Check if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    /**
     * Get error message text
     */
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    /**
     * Check if login was successful
     */
    public boolean isLoginSuccessful() {
        return isDisplayed(welcomeMessage);
    }

    /**
     * Get welcome message
     */
    public String getWelcomeMessage() {
        return getText(welcomeMessage);
    }
}
