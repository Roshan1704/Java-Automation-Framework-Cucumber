package com.automation.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Allure;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for performance testing and metrics collection
 */
public class PerformanceUtil {
    
    private static final LoggerUtil logger = LoggerUtil.getInstance();
    
    /**
     * Get page load time
     */
    public static long getPageLoadTime(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long loadTime = (Long) js.executeScript(
            "return performance.timing.loadEventEnd - performance.timing.navigationStart;"
        );
        logger.info("Page load time: " + loadTime + "ms");
        Allure.addAttachment("Page Load Time", loadTime + "ms");
        return loadTime;
    }
    
    /**
     * Get DOM content loaded time
     */
    public static long getDOMContentLoadedTime(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long domTime = (Long) js.executeScript(
            "return performance.timing.domContentLoadedEventEnd - performance.timing.navigationStart;"
        );
        logger.info("DOM content loaded time: " + domTime + "ms");
        return domTime;
    }
    
    /**
     * Get all performance metrics
     */
    public static Map<String, Long> getAllPerformanceMetrics(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Map<String, Long> metrics = new HashMap<>();
        
        metrics.put("navigationStart", (Long) js.executeScript("return performance.timing.navigationStart;"));
        metrics.put("loadEventEnd", (Long) js.executeScript("return performance.timing.loadEventEnd;"));
        metrics.put("domContentLoadedEventEnd", (Long) js.executeScript("return performance.timing.domContentLoadedEventEnd;"));
        metrics.put("responseEnd", (Long) js.executeScript("return performance.timing.responseEnd;"));
        
        long pageLoadTime = metrics.get("loadEventEnd") - metrics.get("navigationStart");
        long domLoadTime = metrics.get("domContentLoadedEventEnd") - metrics.get("navigationStart");
        long responseTime = metrics.get("responseEnd") - metrics.get("navigationStart");
        
        metrics.put("pageLoadTime", pageLoadTime);
        metrics.put("domLoadTime", domLoadTime);
        metrics.put("responseTime", responseTime);
        
        logger.info("Performance Metrics: " + metrics);
        Allure.addAttachment("Performance Metrics", metrics.toString());
        
        return metrics;
    }
    
    /**
     * Verify page load time is within threshold
     */
    public static boolean verifyPageLoadTime(WebDriver driver, long maxLoadTimeMs) {
        long actualLoadTime = getPageLoadTime(driver);
        boolean isWithinThreshold = actualLoadTime <= maxLoadTimeMs;
        
        if (!isWithinThreshold) {
            logger.warn("Page load time " + actualLoadTime + "ms exceeds threshold " + maxLoadTimeMs + "ms");
        }
        
        return isWithinThreshold;
    }
}
