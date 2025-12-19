package com.automation.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retry analyzer for failed test cases
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    
    private static LoggerUtil logger = LoggerUtil.getInstance();
    private static ConfigReader configReader = ConfigReader.getInstance();
    private int retryCount = 0;
    private int maxRetryCount;

    public RetryAnalyzer() {
        this.maxRetryCount = Integer.parseInt(
            configReader.getProperty("retry.count", "1")
        );
    }

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            logger.info("Retrying test " + result.getName() + " for the " + retryCount + " time");
            return true;
        }
        return false;
    }
}
