package testdata;

import model.TextboxFormData;
import common.Helper;

/**
 * Factory class for creating test data objects.
 * Centralizes test data creation following the Factory pattern.
 * 
 * @author Automation Team
 */
public class TestDataFactory {

    /**
     * Creates valid test data for textbox form submission.
     * 
     * @return TextboxFormData with valid test values
     */
    public static TextboxFormData createValidTextboxData() {
        return TextboxFormData.builder()
                .withFullName("User " + Helper.generateRandomString(6))
                .withEmail(Helper.generateRandomString(8) + "@example.com")
                .withCurrentAddress(Helper.generateRandomString(10) + " St, City, " + Helper.generateRandomNumber(5))
                .withPermanentAddress(Helper.generateRandomString(10) + " Ave, City, " + Helper.generateRandomNumber(5))
                .build();
    }

    /**
     * Creates invalid test data with invalid email format.
     * 
     * @return TextboxFormData with invalid email
     */
    public static TextboxFormData createInvalidEmailData() {
        return TextboxFormData.builder()
                .withFullName("User " + Helper.generateRandomString(5))
                .withEmail("invalid-email-" + Helper.generateRandomString(5))
                .withCurrentAddress(Helper.generateRandomString(8))
                .withPermanentAddress(Helper.generateRandomString(8))
                .build();
    }

    /**
     * Creates test data with empty fields.
     * 
     * @return TextboxFormData with empty values
     */
    public static TextboxFormData createEmptyFormData() {
        return TextboxFormData.builder()
                .withFullName("")
                .withEmail("")
                .withCurrentAddress("")
                .withPermanentAddress("")
                .build();
    }

    /**
     * Creates test data with minimal required fields.
     * 
     * @return TextboxFormData with minimal data
     */
    public static TextboxFormData createMinimalTextboxData() {
        return TextboxFormData.builder()
                .withFullName(Helper.generateRandomString(5))
                .withEmail(Helper.generateRandomString(5) + "@gmail.com")
                .withCurrentAddress(Helper.generateRandomString(5))
                .withPermanentAddress(Helper.generateRandomString(5))
                .build();
    }
}
