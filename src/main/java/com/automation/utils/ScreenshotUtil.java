package com.automation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for taking screenshots
 */
public class ScreenshotUtil {
    
    private static ConfigReader configReader = ConfigReader.getInstance();
    private static LoggerUtil logger = LoggerUtil.getInstance();

    /**
     * Capture screenshot and save to file
     */
    public static String captureScreenshot(WebDriver driver, String testName) {
        String screenshotPath = configReader.getProperty("screenshot.path");
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = testName + "_" + timestamp + ".png";
        String fullPath = screenshotPath + fileName;

        try {
            // Create directory if it doesn't exist
            Files.createDirectories(Paths.get(screenshotPath));

            // Capture screenshot
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            File destFile = new File(fullPath);

            // Copy to destination
            Files.copy(srcFile.toPath(), destFile.toPath());

            logger.info("Screenshot captured: " + fullPath);
            return fullPath;

        } catch (IOException e) {
            logger.error("Failed to capture screenshot", e);
            return null;
        }
    }

    /**
     * Get screenshot as Base64 string for reporting
     */
    public static String getBase64Screenshot(WebDriver driver) {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        return screenshot.getScreenshotAs(OutputType.BASE64);
    }
}
