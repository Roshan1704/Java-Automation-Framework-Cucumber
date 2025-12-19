package com.automation.tests.ui;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import com.automation.utils.ExcelDataProvider;
import com.automation.utils.PerformanceUtil;
import com.automation.utils.RetryAnalyzer;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Advanced UI Test examples with data-driven testing, performance checks, and retry logic
 */
@Epic("UI Testing")
@Feature("Advanced UI Tests")
public class AdvancedUITest extends BaseTest {

    @Test(priority = 1, description = "Verify page load performance", groups = {"performance"})
    @Severity(SeverityLevel.NORMAL)
    @Description("Test to verify page load time is within acceptable limits")
    public void testPageLoadPerformance() {
        logger.info("Starting page load performance test");
        
        long maxLoadTime = 5000; // 5 seconds
        boolean isWithinThreshold = PerformanceUtil.verifyPageLoadTime(driver, maxLoadTime);
        
        Assert.assertTrue(isWithinThreshold, "Page load time exceeds threshold");
        logger.info("Page load performance test passed");
    }

    @Test(priority = 2, description = "Login with valid credentials", 
          retryAnalyzer = RetryAnalyzer.class, groups = {"smoke", "regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test login functionality with valid user credentials")
    @Story("User Login")
    public void testLoginWithValidCredentials() {
        logger.info("Testing login with valid credentials");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("testuser@example.com", "Password123!");
        
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login should be successful");
        logger.info("Login test passed successfully");
    }

    @Test(priority = 3, description = "Login with invalid credentials", groups = {"regression"})
    @Severity(SeverityLevel.NORMAL)
    @Description("Test login functionality with invalid user credentials")
    @Story("User Login")
    public void testLoginWithInvalidCredentials() {
        logger.info("Testing login with invalid credentials");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("invalid@example.com", "wrongpassword");
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertTrue(errorMsg.contains("Invalid"), "Error message should contain 'Invalid'");
        logger.info("Invalid login test passed");
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][] {
            {"user1@test.com", "Pass123", false},
            {"user2@test.com", "Pass456", false},
            {"admin@test.com", "Admin123", true}
        };
    }

    @Test(priority = 4, dataProvider = "loginData", description = "Data-driven login test", groups = {"regression"})
    @Severity(SeverityLevel.NORMAL)
    @Description("Test login with multiple sets of credentials")
    public void testLoginDataDriven(String username, String password, boolean shouldSucceed) {
        logger.info("Testing login with credentials: " + username);
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        
        if (shouldSucceed) {
            Assert.assertTrue(loginPage.isLoginSuccessful(), "Login should succeed for: " + username);
        } else {
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error should be shown for: " + username);
        }
    }
}
