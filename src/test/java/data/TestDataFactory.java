package data;

import models.TextboxFormData;

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
                .withFullName("John Doe")
                .withEmail("john.doe@example.com")
                .withCurrentAddress("123 Main Street, City, State 12345")
                .withPermanentAddress("456 Oak Avenue, City, State 67890")
                .build();
    }
    
    /**
     * Creates invalid test data with invalid email format.
     * 
     * @return TextboxFormData with invalid email
     */
    public static TextboxFormData createInvalidEmailData() {
        return TextboxFormData.builder()
                .withFullName("Jane Smith")
                .withEmail("invalid-email-format")
                .withCurrentAddress("789 Pine Road")
                .withPermanentAddress("321 Elm Street")
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
                .withFullName("ABC")
                .withEmail("abc@gmail.com")
                .withCurrentAddress("abc")
                .withPermanentAddress("abc")
                .build();
    }
}

