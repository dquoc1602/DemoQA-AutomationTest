package locator.buggycars;

import org.openqa.selenium.By;

public class RegisterLocators {
    public static final By USERNAME_INPUT = By.id("username");
    public static final By FIRSTNAME_INPUT = By.id("firstName");
    public static final By LASTNAME_INPUT = By.id("lastName");
    public static final By PASSWORD_INPUT = By.id("password");
    public static final By CONFIRM_PASSWORD_INPUT = By.id("confirmPassword");
    public static final By REGISTER_BUTTON = By.xpath("//button[text()='Register']");
    public static final By CANCEL_LINK = By.linkText("Cancel");

    // Success message
    public static final By REGISTRATION_SUCCESS_MSG = By
            .xpath("//div[contains(@class,'alert-success') and contains(text(),'Registration is successful')]");

    
}
