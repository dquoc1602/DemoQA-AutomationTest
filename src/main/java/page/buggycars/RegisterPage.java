package page.buggycars;

import core.BasePage;
import locator.buggycars.RegisterLocators;
import static common.Constants.BUGGY_CARS_REGISTER_URL;

public class RegisterPage extends BasePage {

    // ================= NAVIGATION =================

    public RegisterPage() {
        super();
        openSite(BUGGY_CARS_REGISTER_URL);
    }

    // ================= INTERACTION METHODS =================

    public void fillRegistrationForm(String username, String firstName, String lastName, String password,
            String confirmPassword) {
        logger.info("Filling registration form for user: {}", username);
        enterText(RegisterLocators.USERNAME_INPUT, username);
        enterText(RegisterLocators.FIRSTNAME_INPUT, firstName);
        enterText(RegisterLocators.LASTNAME_INPUT, lastName);
        enterText(RegisterLocators.PASSWORD_INPUT, password);
        enterText(RegisterLocators.CONFIRM_PASSWORD_INPUT, confirmPassword);
    }

    public void clickRegister() {
        logger.info("Clicking Register button");
        clickButton(RegisterLocators.REGISTER_BUTTON);
    }

    public void register(String username, String firstName, String lastName, String password, String confirmPassword) {
        fillRegistrationForm(username, firstName, lastName, password, confirmPassword);
        clickRegister();
    }

    public boolean isRegisterButtonEnabled() {
        return findElement(RegisterLocators.REGISTER_BUTTON).isEnabled();
    }

    public String getSuccessMessage() {
        return getElementText(RegisterLocators.REGISTRATION_SUCCESS_MSG);
    }

    public boolean isRegistrationSuccessful() {
        try {
            return findElement(RegisterLocators.REGISTRATION_SUCCESS_MSG).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
