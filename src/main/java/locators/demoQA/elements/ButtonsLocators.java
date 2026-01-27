package locators.demoQA.elements;

import org.openqa.selenium.By;

public final class ButtonsLocators {

    public static final By DOUBLE_CLICK_BTN = By.id("doubleClickBtn");
    public static final By RIGHT_CLICK_BTN = By.id("rightClickBtn");
    // This button has a dynamic ID, using text-based locator
    public static final By DYNAMIC_CLICK_BTN = By.xpath("//button[text()='Click Me']");

    public static final By DOUBLE_CLICK_MESSAGE = By.id("doubleClickMessage");
    public static final By RIGHT_CLICK_MESSAGE = By.id("rightClickMessage");
    public static final By DYNAMIC_CLICK_MESSAGE = By.id("dynamicClickMessage");

    private ButtonsLocators() {
    }
}
