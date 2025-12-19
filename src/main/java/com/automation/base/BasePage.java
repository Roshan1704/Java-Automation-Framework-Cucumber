package com.automation.base;

import com.automation.utils.LoggerUtil;
import com.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Base Page class containing common methods for all page objects
 */
public class BasePage {
    
    protected WebDriver driver;
    protected WaitUtils waitUtils;
    protected LoggerUtil logger;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        this.logger = LoggerUtil.getInstance();
        PageFactory.initElements(driver, this);
    }

    /**
     * Click on an element
     */
    protected void click(WebElement element) {
        waitUtils.waitForElementToBeClickable(element);
        element.click();
        logger.info("Clicked on element: " + element.toString());
    }

    /**
     * Type text into an input field
     */
    protected void type(WebElement element, String text) {
        waitUtils.waitForElementToBeVisible(element);
        element.clear();
        element.sendKeys(text);
        logger.info("Typed '" + text + "' into element: " + element.toString());
    }

    /**
     * Enter text into an input field (alias for type)
     */
    protected void enterText(WebElement element, String text) {
        type(element, text);
    }

    /**
     * Get text from an element
     */
    protected String getText(WebElement element) {
        waitUtils.waitForElementToBeVisible(element);
        String text = element.getText();
        logger.info("Retrieved text '" + text + "' from element: " + element.toString());
        return text;
    }

    /**
     * Check if element is displayed
     */
    protected boolean isDisplayed(WebElement element) {
        try {
            waitUtils.waitForElementToBeVisible(element);
            boolean displayed = element.isDisplayed();
            logger.info("Element displayed status: " + displayed);
            return displayed;
        } catch (Exception e) {
            logger.error("Element not displayed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check if element is displayed (alias for isDisplayed)
     */
    protected boolean isElementDisplayed(WebElement element) {
        return isDisplayed(element);
    }

    /**
     * Wait for element to be visible
     */
    protected void waitForElementVisible(WebElement element) {
        waitUtils.waitForElementToBeVisible(element);
    }

    /**
     * Wait for element to be clickable
     */
    protected void waitForElementClickable(WebElement element) {
        waitUtils.waitForElementToBeClickable(element);
    }

    /**
     * Wait for element with timeout
     */
    protected void waitForElementVisible(WebElement element, int timeoutInSeconds) {
        waitUtils.waitForElementToBeVisible(element, timeoutInSeconds);
    }

    /**
     * Click using JavaScript
     */
    protected void clickUsingJS(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
        logger.info("Clicked using JavaScript on element: " + element.toString());
    }

    /**
     * Scroll to element
     */
    protected void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        logger.info("Scrolled to element: " + element.toString());
    }

    /**
     * Get attribute value
     */
    protected String getAttribute(WebElement element, String attributeName) {
        waitUtils.waitForElementToBeVisible(element);
        String value = element.getAttribute(attributeName);
        logger.info("Retrieved attribute '" + attributeName + "' with value '" + value + "'");
        return value;
    }

    /**
     * Check if element is enabled
     */
    protected boolean isEnabled(WebElement element) {
        try {
            boolean enabled = element.isEnabled();
            logger.info("Element enabled status: " + enabled);
            return enabled;
        } catch (Exception e) {
            logger.error("Error checking element enabled status: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check if element is selected
     */
    protected boolean isSelected(WebElement element) {
        try {
            boolean selected = element.isSelected();
            logger.info("Element selected status: " + selected);
            return selected;
        } catch (Exception e) {
            logger.error("Error checking element selected status: " + e.getMessage());
            return false;
        }
    }

    /**
     * Select from dropdown by visible text
     */
    protected void selectByVisibleText(WebElement element, String text) {
        waitUtils.waitForElementToBeVisible(element);
        Select select = new Select(element);
        select.selectByVisibleText(text);
        logger.info("Selected '" + text + "' from dropdown");
    }

    /**
     * Select from dropdown by value
     */
    protected void selectByValue(WebElement element, String value) {
        waitUtils.waitForElementToBeVisible(element);
        Select select = new Select(element);
        select.selectByValue(value);
        logger.info("Selected value '" + value + "' from dropdown");
    }

    /**
     * Select from dropdown by index
     */
    protected void selectByIndex(WebElement element, int index) {
        waitUtils.waitForElementToBeVisible(element);
        Select select = new Select(element);
        select.selectByIndex(index);
        logger.info("Selected index " + index + " from dropdown");
    }

    /**
     * Get all options from dropdown
     */
    protected List<WebElement> getAllDropdownOptions(WebElement element) {
        Select select = new Select(element);
        return select.getOptions();
    }

    /**
     * Clear text field
     */
    protected void clear(WebElement element) {
        waitUtils.waitForElementToBeVisible(element);
        element.clear();
        logger.info("Cleared element: " + element.toString());
    }

    /**
     * Get page title
     */
    public String getPageTitle() {
        String title = driver.getTitle();
        logger.info("Page title: " + title);
        return title;
    }

    /**
     * Get current URL
     */
    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        logger.info("Current URL: " + url);
        return url;
    }

    /**
     * Navigate to URL
     */
    public void navigateTo(String url) {
        driver.get(url);
        logger.info("Navigated to: " + url);
    }

    /**
     * Refresh page
     */
    protected void refreshPage() {
        driver.navigate().refresh();
        logger.info("Page refreshed");
    }

    /**
     * Navigate back
     */
    protected void navigateBack() {
        driver.navigate().back();
        logger.info("Navigated back");
    }

    /**
     * Navigate forward
     */
    protected void navigateForward() {
        driver.navigate().forward();
        logger.info("Navigated forward");
    }

    /**
     * Switch to frame by index
     */
    protected void switchToFrame(int index) {
        driver.switchTo().frame(index);
        logger.info("Switched to frame with index: " + index);
    }

    /**
     * Switch to frame by name or ID
     */
    protected void switchToFrame(String nameOrId) {
        driver.switchTo().frame(nameOrId);
        logger.info("Switched to frame: " + nameOrId);
    }

    /**
     * Switch to frame by WebElement
     */
    protected void switchToFrame(WebElement element) {
        driver.switchTo().frame(element);
        logger.info("Switched to frame element");
    }

    /**
     * Switch to default content
     */
    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
        logger.info("Switched to default content");
    }

    /**
     * Accept alert
     */
    protected void acceptAlert() {
        driver.switchTo().alert().accept();
        logger.info("Alert accepted");
    }

    /**
     * Dismiss alert
     */
    protected void dismissAlert() {
        driver.switchTo().alert().dismiss();
        logger.info("Alert dismissed");
    }

    /**
     * Get alert text
     */
    protected String getAlertText() {
        String text = driver.switchTo().alert().getText();
        logger.info("Alert text: " + text);
        return text;
    }

    /**
     * Send text to alert
     */
    protected void sendTextToAlert(String text) {
        driver.switchTo().alert().sendKeys(text);
        logger.info("Sent text to alert: " + text);
    }
}
