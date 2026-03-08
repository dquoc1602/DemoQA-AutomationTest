package common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Locale;

public class Helper {
    protected final static Logger logger = LogManager.getLogger("demoQA");

    public Helper() {
        try {
            logger.debug("Environment configuration loaded from .env file");
        } catch (Exception e) {
            logger.error("Failed to load .env configuration", e);
            throw new RuntimeException("Environment configuration failed", e);
        }
    }

    /**
     * Load JSON file and store as JSONObject or JSONArray
     * 
     * @param filePath path to JSON file
     */
    public static JSONObject loadJsonFile(String filePath) {
        try {
            String content = Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);
            return new JSONObject(content);
        } catch (Exception e) {
            logger.error("Failed to load JSON file: {}", filePath, e);
            return null;
        }
    }

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);

    public static LocalDate convertStringtoDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DATE_TIME_FORMATTER);
        } catch (Exception e) {
            logger.error("Failed to parse date string: {}", dateStr, e);
            throw new IllegalArgumentException("Invalid date format" + dateStr, e);
        }
    }

    public int convertMonthNameToNumber(String monthName) {
        try {
            DateTimeFormatter moTimeFormatter = DateTimeFormatter.ofPattern("MMMM", Locale.US);
            return moTimeFormatter.parse(monthName).get(ChronoField.MONTH_OF_YEAR);
        } catch (Exception e) {
            logger.error("Failed to convert month name '{}' to number", monthName, e);
            throw new IllegalArgumentException("Invalid month name: " + monthName, e);
        }
    }

    public String readFileContent(String filePath) {
        try {
            return Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("Failed to read file content from: {}", filePath, e);
            return null;
        }
    }

    // ================= RANDOM DATA GENERATION =================

    public static String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static String generateRandomNumber(int length) {
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder();
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static String generateRandomPassword(int length) {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String nums = "0123456789";
        String special = "!@#$%^&*";

        String all = upper + lower + nums + special;
        java.security.SecureRandom random = new java.security.SecureRandom();
        StringBuilder sb = new StringBuilder();

        // Ensure at least one of each type
        sb.append(upper.charAt(random.nextInt(upper.length())));
        sb.append(lower.charAt(random.nextInt(lower.length())));
        sb.append(nums.charAt(random.nextInt(nums.length())));
        sb.append(special.charAt(random.nextInt(special.length())));

        for (int i = 4; i < length; i++) {
            sb.append(all.charAt(random.nextInt(all.length())));
        }

        // Shuffle to avoid predictable pattern
        String password = sb.toString();
        char[] characters = password.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            int randomIndex = random.nextInt(characters.length);
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }

        return new String(characters);
    }

}
