package com.automation.listeners;

import com.automation.driver.DriverManager;
import com.automation.utils.ExtentReportManager;
import com.automation.utils.LoggerUtil;
import com.automation.utils.ScreenshotUtil;
import com.aventstack.extentreports.Status;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

/**
 * TestNG Listener for test execution events
 */
public class TestListener implements ITestListener {

    private LoggerUtil logger = LoggerUtil.getInstance();

    @Override
    public void onStart(ITestContext context) {
        logger.info("Test Suite started: " + context.getName());
        ExtentReportManager.getInstance();
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test Suite finished: " + context.getName());
        logger.info("Total tests run: " + context.getAllTestMethods().length);
        logger.info("Passed: " + context.getPassedTests().size());
        logger.info("Failed: " + context.getFailedTests().size());
        logger.info("Skipped: " + context.getSkippedTests().size());
        ExtentReportManager.flushReports();
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Test started: " + result.getMethod().getMethodName());
        String description = result.getMethod().getDescription() != null ? 
            result.getMethod().getDescription() : "Test Execution";
        ExtentReportManager.createTest(result.getMethod().getMethodName(), description);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test passed: " + result.getMethod().getMethodName());
        ExtentReportManager.getTest().log(Status.PASS, "Test passed successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test failed: " + result.getMethod().getMethodName());
        logger.error("Failure reason: " + result.getThrowable().getMessage());
        
        try {
            String screenshotPath = ScreenshotUtil.captureScreenshot(
                DriverManager.getDriver("chrome"),
                result.getMethod().getMethodName()
            );
            
            // Attach to ExtentReports
            ExtentReportManager.getTest().log(Status.FAIL, "Test failed: " + result.getThrowable().getMessage());
            ExtentReportManager.attachScreenshot(screenshotPath);
            
            // Attach to Allure
            byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver("chrome")).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Failure Screenshot", new ByteArrayInputStream(screenshot));
            
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test skipped: " + result.getMethod().getMethodName());
        ExtentReportManager.getTest().log(Status.SKIP, "Test skipped: " + result.getSkipCausedBy());
    }
}
