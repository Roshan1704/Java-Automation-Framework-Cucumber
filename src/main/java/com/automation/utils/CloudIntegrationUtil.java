package com.automation.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for cloud platform integrations (BrowserStack, Sauce Labs)
 */
public class CloudIntegrationUtil {
    
    private static LoggerUtil logger = LoggerUtil.getInstance();
    private static ConfigReader configReader = ConfigReader.getInstance();

    /**
     * Get BrowserStack RemoteWebDriver
     */
    public static WebDriver getBrowserStackDriver() {
        try {
            String username = configReader.getProperty("browserstack.username");
            String accessKey = configReader.getProperty("browserstack.accesskey");
            String url = "https://" + username + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub";

            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("os", configReader.getProperty("browserstack.os", "Windows"));
            caps.setCapability("os_version", configReader.getProperty("browserstack.os_version", "10"));
            caps.setCapability("browser", configReader.getProperty("browser", "Chrome"));
            caps.setCapability("browser_version", configReader.getProperty("browserstack.browser_version", "latest"));
            caps.setCapability("name", "Automation Test");
            
            Map<String, Object> browserstackOptions = new HashMap<>();
            browserstackOptions.put("sessionName", "Test Session");
            browserstackOptions.put("projectName", "UI Automation Framework");
            browserstackOptions.put("buildName", "Build " + System.currentTimeMillis());
            caps.setCapability("bstack:options", browserstackOptions);

            logger.info("Initializing BrowserStack driver");
            return new RemoteWebDriver(new URL(url), caps);
        } catch (Exception e) {
            logger.error("Failed to initialize BrowserStack driver: " + e.getMessage());
            throw new RuntimeException("BrowserStack initialization failed", e);
        }
    }

    /**
     * Get Sauce Labs RemoteWebDriver
     */
    public static WebDriver getSauceLabsDriver() {
        try {
            String username = configReader.getProperty("saucelabs.username");
            String accessKey = configReader.getProperty("saucelabs.accesskey");
            String url = "https://" + username + ":" + accessKey + "@ondemand.us-west-1.saucelabs.com:443/wd/hub";

            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("platformName", configReader.getProperty("saucelabs.platform", "Windows 10"));
            caps.setCapability("browserName", configReader.getProperty("browser", "chrome"));
            caps.setCapability("browserVersion", configReader.getProperty("saucelabs.browser_version", "latest"));
            
            Map<String, Object> sauceOptions = new HashMap<>();
            sauceOptions.put("name", "Automation Test");
            sauceOptions.put("build", "Build " + System.currentTimeMillis());
            caps.setCapability("sauce:options", sauceOptions);

            logger.info("Initializing Sauce Labs driver");
            return new RemoteWebDriver(new URL(url), caps);
        } catch (Exception e) {
            logger.error("Failed to initialize Sauce Labs driver: " + e.getMessage());
            throw new RuntimeException("Sauce Labs initialization failed", e);
        }
    }
}
