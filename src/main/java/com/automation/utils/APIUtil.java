package com.automation.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;

/**
 * Utility class for API testing operations
 */
public class APIUtil {
    
    private static LoggerUtil logger = LoggerUtil.getInstance();
    private static ConfigReader configReader = ConfigReader.getInstance();
    private static RequestSpecification request;

    public APIUtil() {
        String baseUri = configReader.getProperty("api.base.url");
        RestAssured.baseURI = baseUri;
        this.request = RestAssured.given();
        logger.info("API base URI set to: " + baseUri);
    }

    /**
     * Set headers for the request
     */
    public APIUtil setHeaders(Map<String, String> headers) {
        request.headers(headers);
        logger.info("Headers set: " + headers);
        return this;
    }

    /**
     * Set authentication token
     */
    public APIUtil setAuthToken(String token) {
        request.header("Authorization", "Bearer " + token);
        logger.info("Auth token set");
        return this;
    }

    /**
     * Set basic authentication
     */
    public APIUtil setBasicAuth(String username, String password) {
        request.auth().basic(username, password);
        logger.info("Basic auth set for user: " + username);
        return this;
    }

    /**
     * Set query parameters
     */
    public APIUtil setQueryParams(Map<String, String> queryParams) {
        request.queryParams(queryParams);
        logger.info("Query params set: " + queryParams);
        return this;
    }

    /**
     * Execute GET request
     */
    public static Response get(String endpoint) {
        logger.info("Executing GET request to: " + endpoint);
        Response response = request.contentType(ContentType.JSON).get(endpoint);
        logger.info("Response status: " + response.getStatusCode());
        return response;
    }

    /**
     * Execute POST request
     */
    public static Response post(String endpoint, Object body) {
        logger.info("Executing POST request to: " + endpoint);
        Response response = request.contentType(ContentType.JSON).body(body).post(endpoint);
        logger.info("Response status: " + response.getStatusCode());
        return response;
    }

    /**
     * Execute PUT request
     */
    public static Response put(String endpoint, Object body) {
        logger.info("Executing PUT request to: " + endpoint);
        Response response = request.contentType(ContentType.JSON).body(body).put(endpoint);
        logger.info("Response status: " + response.getStatusCode());
        return response;
    }

    /**
     * Execute PATCH request
     */
    public static Response patch(String endpoint, Object body) {
        logger.info("Executing PATCH request to: " + endpoint);
        Response response = request.contentType(ContentType.JSON).body(body).patch(endpoint);
        logger.info("Response status: " + response.getStatusCode());
        return response;
    }

    /**
     * Execute DELETE request
     */
    public static Response delete(String endpoint) {
        logger.info("Executing DELETE request to: " + endpoint);
        Response response = request.contentType(ContentType.JSON).delete(endpoint);
        logger.info("Response status: " + response.getStatusCode());
        return response;
    }

    /**
     * Validate response status code
     */
    public void validateStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        if (actualStatusCode == expectedStatusCode) {
            logger.info("Status code validation passed. Expected: " + expectedStatusCode + ", Actual: " + actualStatusCode);
        } else {
            logger.error("Status code validation failed. Expected: " + expectedStatusCode + ", Actual: " + actualStatusCode);
            throw new AssertionError("Expected status code " + expectedStatusCode + " but got " + actualStatusCode);
        }
    }

    /**
     * Get response time
     */
    public long getResponseTime(Response response) {
        long responseTime = response.getTime();
        logger.info("Response time: " + responseTime + " ms");
        return responseTime;
    }

    /**
     * Extract value from JSON response
     */
    public String extractFromJson(Response response, String jsonPath) {
        String value = response.jsonPath().getString(jsonPath);
        logger.info("Extracted value from JSON path '" + jsonPath + "': " + value);
        return value;
    }

    /**
     * Reset request specification
     */
    public void resetRequest() {
        this.request = RestAssured.given();
        logger.info("Request specification reset");
    }
}
