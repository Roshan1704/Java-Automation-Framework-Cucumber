package com.automation.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * TestNG Cucumber Runner
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.automation.stepdefinitions"},
        tags = "@smoke or @regression",
        plugin = {
                "pretty",
                "html:test-output/reports/cucumber-reports.html",
                "json:test-output/reports/cucumber.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true,
        dryRun = false
)
public class TestNGCucumberRunner extends AbstractTestNGCucumberTests {
    // This class will be empty as AbstractTestNGCucumberTests handles the execution
}
