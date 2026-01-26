package pages.demoQA.Elements;

import core.Basepage;
import locators.demoQA.elements.TextboxLocators;
import models.TextboxFormData;

import static util.Constants.DEMOQA_TEXTBOX_URL;

public class TextboxPage extends Basepage {

    public TextboxPage() {
        super();
        logger.info("Initializing TextboxPage and navigating to: {}", DEMOQA_TEXTBOX_URL);
        openSite(DEMOQA_TEXTBOX_URL);
    }

    // ================= ACTION METHODS =================

    public TextboxPage enterFullName(String fullName) {
        logger.info("Entering Full Name: {}", fullName);
        enterText(TextboxLocators.FULL_NAME_INPUT, fullName);
        return this;
    }

    public TextboxPage enterEmail(String email) {
        logger.info("Entering Email: {}", email);
        enterText(TextboxLocators.EMAIL_INPUT, email);
        return this;
    }

    public TextboxPage enterCurrentAddress(String currentAddress) {
        logger.info("Entering Current Address: {}", currentAddress);
        enterText(TextboxLocators.CURRENT_ADDRESS_TEXTAREA, currentAddress);
        return this;
    }

    public TextboxPage enterPermanentAddress(String permanentAddress) {
        logger.info("Entering Permanent Address: {}", permanentAddress);
        enterText(TextboxLocators.PERMANENT_ADDRESS_TEXTAREA, permanentAddress);
        return this;
    }

    public TextboxPage fillForm(TextboxFormData formData) {
        logger.info("Filling form with data: {}", formData);
        enterFullName(formData.getFullName())
                .enterEmail(formData.getEmail())
                .enterCurrentAddress(formData.getCurrentAddress())
                .enterPermanentAddress(formData.getPermanentAddress());
        return this;
    }

    public TextboxPage clickSubmit() {
        logger.info("Clicking Submit button");
        clickButton(TextboxLocators.SUBMIT_BUTTON);
        return this;
    }

    // ================= QUERY METHODS =================

    public boolean isOutputContainerVisible() {
        try {
            return findElement(TextboxLocators.OUTPUT_CONTAINER).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getOutputName() {
        return getValueAfterColon(TextboxLocators.OUTPUT_FULL_NAME);
    }

    public String getOutputEmail() {
        return getValueAfterColon(TextboxLocators.OUTPUT_EMAIL);
    }

    public String getOutputCurrentAddress() {
        return getValueAfterColon(TextboxLocators.OUTPUT_CURRENT_ADDRESS);
    }

    public String getOutputPermanentAddress() {
        return getValueAfterColon(TextboxLocators.OUTPUT_PERMANENT_ADDRESS);
    }
}
