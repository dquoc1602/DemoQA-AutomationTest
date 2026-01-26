package locators.demoQA.elements;

import org.openqa.selenium.By;

public final class TextboxLocators {
    // Input field selectors
    public static final By FULL_NAME_INPUT = By.id("userName");
    public static final By EMAIL_INPUT = By.id("userEmail");
    public static final By CURRENT_ADDRESS_TEXTAREA = By.id("currentAddress");
    public static final By PERMANENT_ADDRESS_TEXTAREA = By.id("permanentAddress");

    // Action button selector
    public static final By SUBMIT_BUTTON = By.id("submit");

    // Output container and field selectors
    public static final By OUTPUT_CONTAINER = By.id("output");
    public static final By OUTPUT_FULL_NAME = By.id("name");
    public static final By OUTPUT_EMAIL = By.id("email");
    public static final By OUTPUT_CURRENT_ADDRESS = By.id("currentAddress");
    public static final By OUTPUT_PERMANENT_ADDRESS = By.id("permanentAddress");

    private TextboxLocators() {
    }
}
