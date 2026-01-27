package pages.demoQA.Elements;

import core.Basepage;
import locators.demoQA.elements.DynamicPropertiesLocators;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static util.Constants.DEMOQA_DYNAMIC_PROPERTIES_URL;

public class DynamicPropertiesPage extends Basepage {

    public DynamicPropertiesPage() {
        super();
        logger.info("Opening Dynamic Properties page: {}", DEMOQA_DYNAMIC_PROPERTIES_URL);
        openSite(DEMOQA_DYNAMIC_PROPERTIES_URL);
    }

    public boolean isEnableButtonEnabled() {
        return findElementPresent(DynamicPropertiesLocators.ENABLE_AFTER_BTN).isEnabled();
    }

    public void waitForEnableButton() {
        logger.info("Waiting for button to be enabled (up to 10s)");
        getWait(10).until(ExpectedConditions.elementToBeClickable(DynamicPropertiesLocators.ENABLE_AFTER_BTN));
    }

    public void waitForVisibleButton() {
        logger.info("Waiting for button to be visible (up to 10s)");
        findElement(DynamicPropertiesLocators.VISIBLE_AFTER_BTN);
    }

    public boolean isVisibleButtonDisplayed() {
        try {
            return driver.findElement(DynamicPropertiesLocators.VISIBLE_AFTER_BTN).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getColorButtonClass() {
        return getElementAttribute(DynamicPropertiesLocators.COLOR_CHANGE_BTN, "class");
    }

    public void waitForColorChange(String expectedClassPart) {
        logger.info("Waiting for color change (class to contain '{}')", expectedClassPart);
        waitForAttributeContains(DynamicPropertiesLocators.COLOR_CHANGE_BTN, "class", expectedClassPart);
    }
}
