package com.automation.utils;

import java.sql.*;
import java.util.*;

/**
 * Utility class for database operations
 */
public class DatabaseUtil {
    
    private static LoggerUtil logger = LoggerUtil.getInstance();
    private static ConfigReader configReader = ConfigReader.getInstance();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    /**
     * Establish database connection
     */
    public void connect() {
        try {
            String dbUrl = configReader.getProperty("db.url");
            String dbUser = configReader.getProperty("db.username");
            String dbPassword = configReader.getProperty("db.password");
            
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            statement = connection.createStatement();
            logger.info("Database connection established successfully");
        } catch (SQLException e) {
            logger.error("Failed to connect to database: " + e.getMessage());
            throw new RuntimeException("Database connection failed", e);
        }
    }

    /**
     * Execute SELECT query and return results as List of Maps
     */
    public List<Map<String, String>> executeQuery(String query) {
        List<Map<String, String>> results = new ArrayList<>();
        try {
            resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            while (resultSet.next()) {
                Map<String, String> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String value = resultSet.getString(i);
                    row.put(columnName, value);
                }
                results.add(row);
            }
            logger.info("Query executed successfully: " + query);
        } catch (SQLException e) {
            logger.error("Failed to execute query: " + e.getMessage());
        }
        return results;
    }

    /**
     * Execute INSERT, UPDATE, DELETE queries
     */
    public int executeUpdate(String query) {
        int rowsAffected = 0;
        try {
            rowsAffected = statement.executeUpdate(query);
            logger.info("Update executed successfully. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            logger.error("Failed to execute update: " + e.getMessage());
        }
        return rowsAffected;
    }

    /**
     * Get single cell value
     */
    public String getCellValue(String query, String columnName) {
        String value = null;
        try {
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                value = resultSet.getString(columnName);
            }
        } catch (SQLException e) {
            logger.error("Failed to get cell value: " + e.getMessage());
        }
        return value;
    }

    /**
     * Close database connection
     */
    public void disconnect() {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
            logger.info("Database connection closed successfully");
        } catch (SQLException e) {
            logger.error("Failed to close database connection: " + e.getMessage());
        }
    }

    /**
     * Execute batch queries
     */
    public int[] executeBatch(List<String> queries) {
        int[] results = null;
        try {
            for (String query : queries) {
                statement.addBatch(query);
            }
            results = statement.executeBatch();
            logger.info("Batch execution completed successfully");
        } catch (SQLException e) {
            logger.error("Failed to execute batch: " + e.getMessage());
        }
        return results;
    }
}
