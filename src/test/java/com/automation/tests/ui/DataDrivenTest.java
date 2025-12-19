package com.automation.tests.ui;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import com.automation.utils.ExcelDataProvider;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Data-driven test examples using Excel
 */
public class DataDrivenTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        String excelPath = System.getProperty("user.dir") + "/src/test/resources/testdata/LoginData.xlsx";
        ExcelDataProvider excelDataProvider = new ExcelDataProvider(excelPath, "LoginData");
        return excelDataProvider.getData();
    }

    @Test(dataProvider = "loginData", description = "Login with multiple data sets from Excel")
    public void testLoginWithExcelData(String username, String password, String expectedResult) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        if (expectedResult.equalsIgnoreCase("pass")) {
            Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"),
                "Login should be successful");
        } else {
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed");
        }
    }
}
