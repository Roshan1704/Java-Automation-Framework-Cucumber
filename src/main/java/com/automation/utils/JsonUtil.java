package com.automation.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONObject;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Utility class for JSON operations
 */
public class JsonUtil {
    
    private static LoggerUtil logger = LoggerUtil.getInstance();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Read JSON file and return as JSONObject
     */
    public static JSONObject readJsonFile(String filePath) {
        try {
            FileReader reader = new FileReader(filePath);
            StringBuilder sb = new StringBuilder();
            int i;
            while ((i = reader.read()) != -1) {
                sb.append((char) i);
            }
            reader.close();
            return new JSONObject(sb.toString());
        } catch (IOException e) {
            logger.error("Failed to read JSON file: " + e.getMessage());
            return null;
        }
    }

    /**
     * Write JSON to file
     */
    public static void writeJsonFile(String filePath, JSONObject jsonObject) {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonObject.toString(4));
            logger.info("JSON written to file successfully: " + filePath);
        } catch (IOException e) {
            logger.error("Failed to write JSON file: " + e.getMessage());
        }
    }

    /**
     * Convert object to JSON string
     */
    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    /**
     * Convert JSON string to object
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    /**
     * Parse JSON string to JsonObject
     */
    public static JsonObject parseJson(String jsonString) {
        return JsonParser.parseString(jsonString).getAsJsonObject();
    }

    /**
     * Get value from JSON by key
     */
    public static String getValueFromJson(String jsonString, String key) {
        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.optString(key, null);
    }
}
