package com.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.io.File;

/**
 * Singleton class for managing ExtentReports
 */
public class ExtentReportManager {
    
    private static ExtentReports extentReports;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static ConfigReader configReader = ConfigReader.getInstance();

    /**
     * Initialize ExtentReports
     */
    public static ExtentReports getInstance() {
        if (extentReports == null) {
            createInstance();
        }
        return extentReports;
    }

    private static void createInstance() {
        String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
        
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Test Execution Results");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
        
        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        
        extentReports.setSystemInfo("Application", configReader.getProperty("app.name", "Test Application"));
        extentReports.setSystemInfo("Environment", configReader.getProperty("environment", "QA"));
        extentReports.setSystemInfo("Browser", configReader.getProperty("browser", "chrome"));
        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("User", System.getProperty("user.name"));
    }

    /**
     * Create test in ExtentReports
     */
    public static ExtentTest createTest(String testName, String description) {
        ExtentTest test = getInstance().createTest(testName, description);
        extentTest.set(test);
        return test;
    }

    /**
     * Get current test
     */
    public static ExtentTest getTest() {
        return extentTest.get();
    }

    /**
     * Flush reports
     */
    public static void flushReports() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }

    /**
     * Attach screenshot to report
     */
    public static void attachScreenshot(String screenshotPath) {
        getTest().addScreenCaptureFromPath(screenshotPath);
    }
}
