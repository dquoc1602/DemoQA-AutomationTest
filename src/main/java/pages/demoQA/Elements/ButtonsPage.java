package pages.demoQA.Elements;

import core.Basepage;
import locators.demoQA.elements.ButtonsLocators;
import org.openqa.selenium.By;

import static util.Constants.DEMOQA_BUTTONS_URL;

public class ButtonsPage extends Basepage {

    public ButtonsPage() {
        super();
        logger.info("Opening Buttons page: {}", DEMOQA_BUTTONS_URL);
        openSite(DEMOQA_BUTTONS_URL);
    }

    public ButtonsPage doubleClick() {
        logger.info("Performing double click on button");
        doubleClickButton(ButtonsLocators.DOUBLE_CLICK_BTN);
        return this;
    }

    public ButtonsPage rightClick() {
        logger.info("Performing right click on button");
        rightClickButton(ButtonsLocators.RIGHT_CLICK_BTN);
        return this;
    }

    public ButtonsPage dynamicClick() {
        logger.info("Performing dynamic click on button");
        clickButton(ButtonsLocators.DYNAMIC_CLICK_BTN);
        return this;
    }

    public String getDoubleClickMessage() {
        return getMessage(ButtonsLocators.DOUBLE_CLICK_MESSAGE);
    }

    public String getRightClickMessage() {
        return getMessage(ButtonsLocators.RIGHT_CLICK_MESSAGE);
    }

    public String getDynamicClickMessage() {
        return getMessage(ButtonsLocators.DYNAMIC_CLICK_MESSAGE);
    }

    private String getMessage(By locator) {
        try {
            String text = getElementText(locator);
            logger.info("Retrieved message: {}", text);
            return text;
        } catch (Exception e) {
            logger.warn("Message not found or element not visible: {}", locator);
            return "";
        }
    }
}
