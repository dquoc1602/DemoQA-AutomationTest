package locators.demoQA.elements;

import org.openqa.selenium.By;

public final class DynamicPropertiesLocators {

    public static final By RANDOM_ID_TEXT = By.xpath("//p[text()='This text has random Id']");
    public static final By ENABLE_AFTER_BTN = By.id("enableAfter");
    public static final By COLOR_CHANGE_BTN = By.id("colorChange");
    public static final By VISIBLE_AFTER_BTN = By.id("visibleAfter");

    private DynamicPropertiesLocators() {
    }
}
