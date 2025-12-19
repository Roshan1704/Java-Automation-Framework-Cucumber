package com.automation.pages;

import com.automation.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object for Dashboard Page
 */
public class DashboardPage extends BasePage {

    public DashboardPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "h1.dashboard-title")
    private WebElement dashboardTitle;

    @FindBy(id = "user-profile")
    private WebElement userProfile;

    @FindBy(css = "button.logout-btn")
    private WebElement logoutButton;

    @FindBy(css = ".welcome-message")
    private WebElement welcomeMessage;

    @FindBy(id = "notifications")
    private WebElement notificationsIcon;

    /**
     * Get dashboard title
     */
    public String getDashboardTitle() {
        waitForElementVisible(dashboardTitle);
        String title = getText(dashboardTitle);
        logger.info("Dashboard title: " + title);
        return title;
    }

    /**
     * Get welcome message
     */
    public String getWelcomeMessage() {
        waitForElementVisible(welcomeMessage);
        return getText(welcomeMessage);
    }

    /**
     * Click user profile
     */
    public void clickUserProfile() {
        click(userProfile);
        logger.info("Clicked on user profile");
    }

    /**
     * Click logout button
     */
    public void clickLogout() {
        click(logoutButton);
        logger.info("Clicked logout button");
    }

    /**
     * Check if notifications icon is displayed
     */
    public boolean isNotificationsIconDisplayed() {
        return isElementDisplayed(notificationsIcon);
    }

    /**
     * Verify dashboard is loaded
     */
    public boolean isDashboardLoaded() {
        return isElementDisplayed(dashboardTitle) && 
               isElementDisplayed(userProfile);
    }
}
