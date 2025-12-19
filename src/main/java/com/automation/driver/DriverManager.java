package com.automation.driver;

import com.automation.utils.CloudIntegrationUtil;
import com.automation.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Driver Manager to handle WebDriver instance creation and management
 */
public class DriverManager {
    
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ConfigReader configReader = ConfigReader.getInstance();

    /**
     * Initialize and return WebDriver based on browser type
     */
    public static WebDriver getDriver(String browserName) {
        if (driver.get() == null) {
            String executionMode = configReader.getProperty("execution.mode", "local");
            
            if ("browserstack".equalsIgnoreCase(executionMode)) {
                driver.set(CloudIntegrationUtil.getBrowserStackDriver());
            } else if ("saucelabs".equalsIgnoreCase(executionMode)) {
                driver.set(CloudIntegrationUtil.getSauceLabsDriver());
            } else {
                driver.set(createDriver(browserName));
            }
            setupDriver();
        }
        return driver.get();
    }

    /**
     * Create WebDriver instance based on browser type
     */
    private static WebDriver createDriver(String browserName) {
        WebDriver webDriver;
        boolean headless = Boolean.parseBoolean(configReader.getProperty("headless"));

        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless");
                }
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--window-size=1920,1080");
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("download.default_directory", System.getProperty("user.dir") + "/downloads");
                prefs.put("download.prompt_for_download", false);
                chromeOptions.setExperimentalOption("prefs", prefs);
                webDriver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                webDriver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless");
                }
                webDriver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new IllegalArgumentException("Browser not supported: " + browserName);
        }

        return webDriver;
    }

    /**
     * Setup driver with timeouts and configurations
     */
    private static void setupDriver() {
        int implicitWait = Integer.parseInt(configReader.getProperty("implicit.wait"));
        int pageLoadTimeout = Integer.parseInt(configReader.getProperty("page.load.timeout"));

        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        driver.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
    }

    /**
     * Quit driver and remove from ThreadLocal
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
