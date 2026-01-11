package pages.demoQA.Elements;

import core.Basepage;
import models.TextboxFormData;
import org.openqa.selenium.By;

import static util.Constants.DEMOQA_TEXTBOX_URL;

/**
 * Page Object Model for the DemoQA Textbox page.
 * Implements the Page Object Model pattern following Selenium best practices.
 * 
 * @author Automation Team
 */
public class TextboxPage extends Basepage {
    
    /**
     * Private static inner class containing all page element selectors.
     * Encapsulates locators to prevent direct access and improve maintainability.
     */
    private static final class Selectors {
        // Input field selectors
        static final By FULL_NAME_INPUT = By.id("userName");
        static final By EMAIL_INPUT = By.id("userEmail");
        static final By CURRENT_ADDRESS_TEXTAREA = By.id("currentAddress");
        static final By PERMANENT_ADDRESS_TEXTAREA = By.id("permanentAddress");
        
        // Action button selector
        static final By SUBMIT_BUTTON = By.id("submit");
        
        // Output container and field selectors
        static final By OUTPUT_CONTAINER = By.id("output");
        static final By OUTPUT_FULL_NAME = By.id("name");
        static final By OUTPUT_EMAIL = By.id("email");
        static final By OUTPUT_CURRENT_ADDRESS = By.id("currentAddress");
        static final By OUTPUT_PERMANENT_ADDRESS = By.id("permanentAddress");
        
        // Private constructor to prevent instantiation
        private Selectors() {
            throw new UnsupportedOperationException("Utility class cannot be instantiated");
        }
    }

    /**
     * Constructor initializes the page and navigates to the Textbox page URL.
     */
    public TextboxPage() {
        super();
        logger.info("Initializing TextboxPage and navigating to: {}", DEMOQA_TEXTBOX_URL);
        openSite(DEMOQA_TEXTBOX_URL);
    }

    // ================= ACTION METHODS =================

    /**
     * Enters the full name into the full name input field.
     * 
     * @param fullName The full name to enter
     * @return This page instance for method chaining (fluent interface)
     */
    public TextboxPage enterFullName(String fullName) {
        logger.info("Entering Full Name: {}", fullName);
        enterText(Selectors.FULL_NAME_INPUT, fullName);
        return this;
    }

    /**
     * Enters the email address into the email input field.
     * 
     * @param email The email address to enter
     * @return This page instance for method chaining (fluent interface)
     */
    public TextboxPage enterEmail(String email) {
        logger.info("Entering Email: {}", email);
        enterText(Selectors.EMAIL_INPUT, email);
        return this;
    }

    /**
     * Enters the current address into the current address textarea.
     * 
     * @param currentAddress The current address to enter
     * @return This page instance for method chaining (fluent interface)
     */
    public TextboxPage enterCurrentAddress(String currentAddress) {
        logger.info("Entering Current Address: {}", currentAddress);
        enterText(Selectors.CURRENT_ADDRESS_TEXTAREA, currentAddress);
        return this;
    }

    /**
     * Enters the permanent address into the permanent address textarea.
     * 
     * @param permanentAddress The permanent address to enter
     * @return This page instance for method chaining (fluent interface)
     */
    public TextboxPage enterPermanentAddress(String permanentAddress) {
        logger.info("Entering Permanent Address: {}", permanentAddress);
        enterText(Selectors.PERMANENT_ADDRESS_TEXTAREA, permanentAddress);
        return this;
    }

    /**
     * Fills the entire form with the provided form data.
     * This method encapsulates the form filling logic for better maintainability.
     * 
     * @param formData The TextboxFormData object containing all form field values
     * @return This page instance for method chaining (fluent interface)
     */
    public TextboxPage fillForm(TextboxFormData formData) {
        logger.info("Filling form with data: {}", formData);
        enterFullName(formData.getFullName())
            .enterEmail(formData.getEmail())
            .enterCurrentAddress(formData.getCurrentAddress())
            .enterPermanentAddress(formData.getPermanentAddress());
        return this;
    }

    /**
     * Clicks the submit button to submit the form.
     * 
     * @return This page instance for method chaining (fluent interface)
     */
    public TextboxPage clickSubmit() {
        logger.info("Clicking Submit button");
        clickButton(Selectors.SUBMIT_BUTTON);
        return this;
    }

    /**
     * Submits the form by clicking the submit button.
     * This is an alias method for better readability in test scenarios.
     * 
     * @return This page instance for method chaining (fluent interface)
     */
    public TextboxPage submitForm() {
        return clickSubmit();
    }

    // ================= VERIFICATION METHODS =================

    /**
     * Verifies that the output container is visible after form submission.
     * 
     * @return This page instance for method chaining (fluent interface)
     */
    public TextboxPage verifyOutputContainerVisible() {
        logger.info("Verifying output container is visible after submit");
        verifyElementVisible(
                Selectors.OUTPUT_CONTAINER,
                "Output container is not visible after submit"
        );
        return this;
    }

    /**
     * Verifies the output name matches the expected value.
     * 
     * @param expectedValue The expected full name value
     * @return This page instance for method chaining (fluent interface)
     */
    public TextboxPage verifyOutputName(String expectedValue) {
        String actualValue = getValueAfterColon(Selectors.OUTPUT_FULL_NAME);
        logger.info("Verifying Output Name. Expected: {}, Actual: {}", expectedValue, actualValue);
        verifyEquals(expectedValue, actualValue, 
                String.format("Output Name value is incorrect. Expected: %s, Actual: %s", expectedValue, actualValue));
        return this;
    }

    /**
     * Verifies the output email matches the expected value.
     * 
     * @param expectedValue The expected email value
     * @return This page instance for method chaining (fluent interface)
     */
    public TextboxPage verifyOutputEmail(String expectedValue) {
        String actualValue = getValueAfterColon(Selectors.OUTPUT_EMAIL);
        logger.info("Verifying Output Email. Expected: {}, Actual: {}", expectedValue, actualValue);
        verifyEquals(expectedValue, actualValue, 
                String.format("Output Email value is incorrect. Expected: %s, Actual: %s", expectedValue, actualValue));
        return this;
    }

    /**
     * Verifies the output current address matches the expected value.
     * 
     * @param expectedValue The expected current address value
     * @return This page instance for method chaining (fluent interface)
     */
    public TextboxPage verifyOutputCurrentAddress(String expectedValue) {
        String actualValue = getValueAfterColon(Selectors.OUTPUT_CURRENT_ADDRESS);
        logger.info("Verifying Output Current Address. Expected: {}, Actual: {}", expectedValue, actualValue);
        verifyEquals(expectedValue, actualValue, 
                String.format("Output Current Address value is incorrect. Expected: %s, Actual: %s", expectedValue, actualValue));
        return this;
    }

    /**
     * Verifies the output permanent address matches the expected value.
     * 
     * @param expectedValue The expected permanent address value
     * @return This page instance for method chaining (fluent interface)
     */
    public TextboxPage verifyOutputPermanentAddress(String expectedValue) {
        String actualValue = getValueAfterColon(Selectors.OUTPUT_PERMANENT_ADDRESS);
        logger.info("Verifying Output Permanent Address. Expected: {}, Actual: {}", expectedValue, actualValue);
        verifyEquals(expectedValue, actualValue, 
                String.format("Output Permanent Address value is incorrect. Expected: %s, Actual: %s", expectedValue, actualValue));
        return this;
    }

    /**
     * Verifies all output fields match the provided form data.
     * This method encapsulates the verification logic for better maintainability.
     * 
     * @param expectedData The TextboxFormData object containing expected output values
     * @return This page instance for method chaining (fluent interface)
     */
    public TextboxPage verifyAllOutputFields(TextboxFormData expectedData) {
        logger.info("Verifying all output fields match expected data: {}", expectedData);
        verifyOutputContainerVisible()
            .verifyOutputName(expectedData.getFullName())
            .verifyOutputEmail(expectedData.getEmail())
            .verifyOutputCurrentAddress(expectedData.getCurrentAddress())
            .verifyOutputPermanentAddress(expectedData.getPermanentAddress());
        return this;
    }
}
