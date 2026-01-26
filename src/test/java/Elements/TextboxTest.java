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

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Textbox Page Tests")
public class TextboxTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(TextboxTest.class);

    private TextboxPage textboxPage;
    private TextboxFormData testData;

    @BeforeEach
    public void setUpPages() {
        logger.info("===== SETUP TEST =====");
        textboxPage = new TextboxPage();
        testData = TestDataFactory.createMinimalTextboxData();
        logger.info("TextboxPage initialized with test data: {}", testData);
    }

    @Test
    @DisplayName("Verify form submission with valid data")
    public void verifyValidData() {
        logger.info("Starting test: verifyValidData with data: {}", testData);

        textboxPage.fillForm(testData)
                .clickSubmit();

        assertTrue(textboxPage.isOutputContainerVisible(), "Output container should be visible");
        assertEquals(testData.getFullName(), textboxPage.getOutputName(), "Name mismatch");
        assertEquals(testData.getEmail(), textboxPage.getOutputEmail(), "Email mismatch");

        logger.info("Test completed successfully: All validations passed");
    }

    @Test
    @DisplayName("Verify form submission with invalid email format")
    public void verifyInvalidData() {
        logger.info("Starting test: verifyInvalidData");

        TextboxFormData invalidData = TestDataFactory.createInvalidEmailData();
        logger.info("Using invalid test data: {}", invalidData);

        textboxPage.fillForm(invalidData);
        textboxPage.clickSubmit();

        // When email is invalid, the output email (and usually container) is not shown
        // or the field gets a red border (class change), but here we check output
        // absence
        assertNotEquals(invalidData.getEmail(), textboxPage.getOutputEmail(),
                "Output should NOT contain invalid email validation success");

        logger.info("Test completed: Invalid data scenario tested");
    }

    @Test
    @DisplayName("Verify form submission with comprehensive valid data")
    public void verifyComprehensiveValidData() {
        logger.info("Starting test: verifyComprehensiveValidData");

        TextboxFormData comprehensiveData = TestDataFactory.createValidTextboxData();
        logger.info("Using comprehensive test data: {}", comprehensiveData);

        textboxPage.fillForm(comprehensiveData)
                .clickSubmit();

        assertTrue(textboxPage.isOutputContainerVisible(), "Output container should be visible");
        assertEquals(comprehensiveData.getFullName(), textboxPage.getOutputName(), "Name mismatch");
        assertEquals(comprehensiveData.getEmail(), textboxPage.getOutputEmail(), "Email mismatch");
        assertEquals(comprehensiveData.getCurrentAddress(), textboxPage.getOutputCurrentAddress(),
                "Current Address mismatch");
        assertEquals(comprehensiveData.getPermanentAddress(), textboxPage.getOutputPermanentAddress(),
                "Permanent Address mismatch");

        logger.info("Test completed successfully: Comprehensive validations passed");
    }
}
