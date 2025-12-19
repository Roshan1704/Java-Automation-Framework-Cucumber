package com.automation.tests.api;

import com.automation.utils.APIUtil;
import com.automation.utils.JsonUtil;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Advanced API Test examples demonstrating comprehensive API testing capabilities
 */
@Epic("API Testing")
@Feature("Advanced API Tests")
public class AdvancedAPITest {

    private static final String BASE_URI = "https://jsonplaceholder.typicode.com";

    @Test(priority = 1, description = "Test GET request with path parameters", groups = {"api", "smoke"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify GET request returns correct user data")
    @Story("User API")
    public void testGetUserById() {
        Allure.step("Send GET request to fetch user by ID");
        
        Response response = APIUtil.get(BASE_URI + "/users/1");
        
        Allure.step("Verify response status code is 200");
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        
        Allure.step("Verify response contains user data");
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        Assert.assertEquals(jsonResponse.getInt("id"), 1, "User ID should be 1");
        Assert.assertNotNull(jsonResponse.getString("name"), "Name should not be null");
        
        Allure.addAttachment("Response", "application/json", response.getBody().asString());
    }

    @Test(priority = 2, description = "Test POST request with JSON body", groups = {"api", "regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify POST request creates new resource")
    @Story("User API")
    public void testCreateNewPost() {
        Allure.step("Prepare POST request body");
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", "Test Post");
        requestBody.put("body", "This is a test post body");
        requestBody.put("userId", 1);
        
        Allure.step("Send POST request to create new post");
        Response response = APIUtil.post(BASE_URI + "/posts", requestBody);
        
        Allure.step("Verify response status code is 201");
        Assert.assertEquals(response.getStatusCode(), 201, "Status code should be 201");
        
        Allure.step("Verify response contains created post data");
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        Assert.assertEquals(jsonResponse.getString("title"), "Test Post");
        Assert.assertNotNull(jsonResponse.get("id"), "Post ID should be generated");
        
        Allure.addAttachment("Request Body", "application/json", JsonUtil.toJson(requestBody));
        Allure.addAttachment("Response Body", "application/json", response.getBody().asString());
    }

    @Test(priority = 3, description = "Test PUT request to update resource", groups = {"api", "regression"})
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify PUT request updates existing resource")
    @Story("User API")
    public void testUpdatePost() {
        Allure.step("Prepare PUT request body");
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", 1);
        requestBody.put("title", "Updated Post Title");
        requestBody.put("body", "Updated post body");
        requestBody.put("userId", 1);
        
        Allure.step("Send PUT request to update post");
        Response response = APIUtil.put(BASE_URI + "/posts/1", requestBody);
        
        Allure.step("Verify response status code is 200");
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        
        Allure.step("Verify response contains updated data");
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        Assert.assertEquals(jsonResponse.getString("title"), "Updated Post Title");
        
        Allure.addAttachment("Response", "application/json", response.getBody().asString());
    }

    @Test(priority = 4, description = "Test DELETE request", groups = {"api", "regression"})
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify DELETE request removes resource")
    @Story("User API")
    public void testDeletePost() {
        Allure.step("Send DELETE request to remove post");
        
        Response response = APIUtil.delete(BASE_URI + "/posts/1");
        
        Allure.step("Verify response status code is 200");
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
    }

    @Test(priority = 5, description = "Test API response time", groups = {"api", "performance"})
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify API response time is within acceptable limits")
    @Story("Performance")
    public void testAPIResponseTime() {
        Allure.step("Send GET request and measure response time");
        
        Response response = APIUtil.get(BASE_URI + "/users");
        long responseTime = response.getTime();
        
        Allure.step("Verify response time is less than 2000ms");
        Assert.assertTrue(responseTime < 2000, "Response time should be less than 2000ms, actual: " + responseTime + "ms");
        
        Allure.addAttachment("Response Time", responseTime + "ms");
    }
}
