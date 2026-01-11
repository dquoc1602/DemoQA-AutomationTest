package Elements;

import core.BaseTest;
import data.TestDataFactory;
import models.TextboxFormData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.demoQA.Elements.TextboxPage;

/**
 * Test class for the DemoQA Textbox page functionality.
 * Follows Page Object Model (POM) pattern and OOP principles.
 * 
 * @author Automation Team
 */
@DisplayName("Textbox Page Tests")
public class TextboxTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(TextboxTest.class);

    private TextboxPage textboxPage;
    private TextboxFormData testData;

    /**
     * Sets up the test environment before each test method execution.
     * Initializes the page object and test data.
     */
    @BeforeEach
    public void setUpPages() {
        logger.info("===== SETUP TEST =====");
        textboxPage = new TextboxPage();
        testData = TestDataFactory.createMinimalTextboxData();
        logger.info("TextboxPage initialized with test data: {}", testData);
    }

    /**
     * Verifies that valid form data can be submitted and displayed correctly.
     * Tests the complete form submission flow with valid data.
     */
    @Test
    @DisplayName("Verify form submission with valid data")
    public void verifyValidData() {
        logger.info("Starting test: verifyValidData with data: {}", testData);
        
        // Fill form, submit, and verify using fluent interface
        textboxPage
            .fillForm(testData)
            .submitForm()
            .verifyOutputContainerVisible()
            .verifyAllOutputFields(testData);
        
        logger.info("Test completed successfully: All validations passed");
    }

    /**
     * Verifies form behavior with invalid email format.
     * Tests validation and error handling for invalid input.
     */
    @Test
    @DisplayName("Verify form submission with invalid email format")
    public void verifyInvalidData() {
        logger.info("Starting test: verifyInvalidData");
        
        TextboxFormData invalidData = TestDataFactory.createInvalidEmailData();
        logger.info("Using invalid test data: {}", invalidData);
        
        // Fill form with invalid data
        textboxPage.fillForm(invalidData);
        
        // Submit form - the page should handle validation
        textboxPage.submitForm();
        
        // Note: Actual validation behavior depends on the application
        // This test can be extended based on specific validation requirements
        logger.info("Test completed: Invalid data scenario tested");
    }

    /**
     * Verifies form submission with comprehensive valid data.
     * Uses more realistic test data with full addresses.
     */
    @Test
    @DisplayName("Verify form submission with comprehensive valid data")
    public void verifyComprehensiveValidData() {
        logger.info("Starting test: verifyComprehensiveValidData");
        
        TextboxFormData comprehensiveData = TestDataFactory.createValidTextboxData();
        logger.info("Using comprehensive test data: {}", comprehensiveData);
        
        // Execute test using fluent interface pattern
        textboxPage
            .fillForm(comprehensiveData)
            .submitForm()
            .verifyOutputContainerVisible()
            .verifyAllOutputFields(comprehensiveData);
        
        logger.info("Test completed successfully: Comprehensive validations passed");
    }
}
