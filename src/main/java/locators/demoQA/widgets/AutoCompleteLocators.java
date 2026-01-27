package locators.demoQA.widgets;

import org.openqa.selenium.By;

public final class AutoCompleteLocators {

    public static final By MULTIPLE_INPUT = By.id("autoCompleteMultipleInput");
    public static final By SINGLE_INPUT = By.id("autoCompleteSingleInput");

    public static final By MULTI_VALUE_LABELS = By.className("auto-complete__multi-value__label");
    public static final By SINGLE_VALUE_LABEL = By.className("auto-complete__single-value");

    public static final By MULTI_VALUE_REMOVE = By.className("auto-complete__multi-value__remove");
    public static final By MULTI_CLEAR_BUTTON = By.className("auto-complete__clear-indicator");

    // Using a dynamic locator for the options in the dropdown
    public static final String DROPDOWN_OPTION_XPATH = "//div[contains(@class, 'auto-complete__option') and text()='%s']";

    private AutoCompleteLocators() {
    }
}
