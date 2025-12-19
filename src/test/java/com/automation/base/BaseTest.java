package com.automation.base;

import com.automation.driver.DriverManager;
import com.automation.utils.ConfigReader;
import com.automation.utils.ExtentReportManager;
import com.automation.utils.LoggerUtil;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

/**
 * Base Test class for all test classes
 */
public class BaseTest {
    
    protected WebDriver driver;
    protected ConfigReader configReader;
    protected LoggerUtil logger;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        logger = LoggerUtil.getInstance();
        configReader = ConfigReader.getInstance();
        
        logger.info("Starting test execution on browser: " + browser);
        Allure.parameter("Browser", browser);
        Allure.parameter("Environment", configReader.getProperty("environment", "QA"));
        
        driver = DriverManager.getDriver(browser);
        driver.manage().window().maximize();
        
        String baseUrl = configReader.getProperty("base.url");
        driver.get(baseUrl);
        logger.info("Navigated to base URL: " + baseUrl);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            logger.info("Closing browser");
            DriverManager.quitDriver();
        }
    }
    
    protected void navigateTo(String url) {
        driver.get(url);
        logger.info("Navigated to: " + url);
    }
    
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    protected String getTitle() {
        return driver.getTitle();
    }
}
