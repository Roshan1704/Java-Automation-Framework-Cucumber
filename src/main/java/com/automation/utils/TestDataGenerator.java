package com.automation.utils;

import com.github.javafaker.Faker;
import java.util.Locale;
import java.util.Random;

/**
 * Utility class for generating test data using JavaFaker
 */
public class TestDataGenerator {
    
    private static Faker faker = new Faker(new Locale("en-US"));
    private static Random random = new Random();

    public static String generateEmail() {
        return faker.internet().emailAddress();
    }

    public static String generateFirstName() {
        return faker.name().firstName();
    }

    public static String generateLastName() {
        return faker.name().lastName();
    }

    public static String generateFullName() {
        return faker.name().fullName();
    }

    public static String generatePhoneNumber() {
        return faker.phoneNumber().phoneNumber();
    }

    public static String generateAddress() {
        return faker.address().fullAddress();
    }

    public static String generateCity() {
        return faker.address().city();
    }

    public static String generateZipCode() {
        return faker.address().zipCode();
    }

    public static String generateCompanyName() {
        return faker.company().name();
    }

    public static String generatePassword(int length) {
        return faker.internet().password(length, length + 5, true, true, true);
    }

    public static String generateText(int wordCount) {
        return faker.lorem().sentence(wordCount);
    }

    public static String generateNumeric(int length) {
        return faker.number().digits(length);
    }

    public static String generateAlphanumeric(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static String generateCreditCardNumber() {
        return faker.business().creditCardNumber();
    }

    public static String generateUrl() {
        return faker.internet().url();
    }
}
