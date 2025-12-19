package com.automation.tests.ui;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for Login functionality (TestNG style)
 */
public class LoginTest extends BaseTest {

    @Test(priority = 1, description = "Verify successful login with valid credentials")
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.login("testuser@example.com", "Test@123");
        
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login should be successful");
        Assert.assertFalse(loginPage.getWelcomeMessage().isEmpty(), "Welcome message should be displayed");
    }

    @Test(priority = 2, description = "Verify login fails with invalid credentials")
    public void testLoginWithInvalidCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.login("invalid@example.com", "wrongpassword");
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Invalid credentials"));
    }

    @Test(priority = 3, description = "Verify login page title")
    public void testLoginPageTitle() {
        LoginPage loginPage = new LoginPage(driver);
        
        String pageTitle = loginPage.getPageTitle();
        Assert.assertTrue(pageTitle.contains("Login"), "Page title should contain 'Login'");
    }
}
