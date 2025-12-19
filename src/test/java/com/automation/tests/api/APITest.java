package com.automation.tests.api;

import com.automation.utils.ConfigReader;
import com.automation.utils.LoggerUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 * Sample API Test using RestAssured
 */
public class APITest {

    private ConfigReader configReader;
    private LoggerUtil logger;
    private String baseUrl;

    @BeforeClass
    public void setup() {
        configReader = ConfigReader.getInstance();
        logger = LoggerUtil.getInstance();
        baseUrl = configReader.getProperty("api.base.url");
        RestAssured.baseURI = baseUrl;
    }

    @Test(priority = 1, description = "Verify GET request returns 200 status")
    public void testGetRequest() {
        logger.info("Testing GET request");

        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .get("/users")
                .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        logger.info("GET request successful with status: " + response.getStatusCode());
    }

    @Test(priority = 2, description = "Verify POST request creates user")
    public void testPostRequest() {
        logger.info("Testing POST request");

        String requestBody = "{\n" +
                "  \"name\": \"Test User\",\n" +
                "  \"email\": \"testuser@example.com\",\n" +
                "  \"password\": \"Test@123\"\n" +
                "}";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 201, "Status code should be 201");
        logger.info("POST request successful with status: " + response.getStatusCode());
    }

    @Test(priority = 3, description = "Verify API response time")
    public void testResponseTime() {
        logger.info("Testing API response time");

        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .get("/users")
                .then()
                .extract().response();

        long responseTime = response.getTime();
        Assert.assertTrue(responseTime < 3000, "Response time should be less than 3 seconds");
        logger.info("API response time: " + responseTime + "ms");
    }
}
