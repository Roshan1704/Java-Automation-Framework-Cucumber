package com.automation.pages;

import com.automation.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object for Registration Page
 */
public class RegistrationPage extends BasePage {

    public RegistrationPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "firstName")
    private WebElement firstNameField;

    @FindBy(id = "lastName")
    private WebElement lastNameField;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "confirmPassword")
    private WebElement confirmPasswordField;

    @FindBy(id = "phone")
    private WebElement phoneField;

    @FindBy(css = "button[type='submit']")
    private WebElement registerButton;

    @FindBy(css = ".success-message")
    private WebElement successMessage;

    @FindBy(css = ".error-message")
    private WebElement errorMessage;

    /**
     * Register new user
     */
    public void registerUser(String firstName, String lastName, String email, 
                            String password, String phone) {
        enterText(firstNameField, firstName);
        enterText(lastNameField, lastName);
        enterText(emailField, email);
        enterText(passwordField, password);
        enterText(confirmPasswordField, password);
        enterText(phoneField, phone);
        click(registerButton);
        logger.info("Submitted registration form for user: " + email);
    }

    /**
     * Get success message
     */
    public String getSuccessMessage() {
        waitForElementVisible(successMessage);
        return getText(successMessage);
    }

    /**
     * Get error message
     */
    public String getErrorMessage() {
        waitForElementVisible(errorMessage);
        return getText(errorMessage);
    }

    /**
     * Check if registration was successful
     */
    public boolean isRegistrationSuccessful() {
        return isElementDisplayed(successMessage);
    }
}
