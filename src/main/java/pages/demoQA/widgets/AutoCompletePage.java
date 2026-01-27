package pages.demoQA.widgets;

import core.Basepage;
import locators.demoQA.widgets.AutoCompleteLocators;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static util.Constants.DEMOQA_AUTO_COMPLETE_URL;

public class AutoCompletePage extends Basepage {

    public AutoCompletePage() {
        super();
        logger.info("Opening Auto Complete page: {}", DEMOQA_AUTO_COMPLETE_URL);
        openSite(DEMOQA_AUTO_COMPLETE_URL);
    }

    public AutoCompletePage typeAndSelectMultiple(String... colors) {
        for (String color : colors) {
            logger.info("Typing and selecting multiple color: {}", color);
            WebElement input = findElement(AutoCompleteLocators.MULTIPLE_INPUT);
            input.sendKeys(color);
            input.sendKeys(Keys.ENTER);
        }
        return this;
    }

    public AutoCompletePage typeAndSelectSingle(String color) {
        logger.info("Typing and selecting single color: {}", color);
        WebElement input = findElement(AutoCompleteLocators.SINGLE_INPUT);
        input.sendKeys(color);
        input.sendKeys(Keys.ENTER);
        return this;
    }

    public List<String> getMultipleValues() {
        List<WebElement> elements = findElements(AutoCompleteLocators.MULTI_VALUE_LABELS);
        return elements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public String getSingleValue() {
        try {
            return getElementText(AutoCompleteLocators.SINGLE_VALUE_LABEL);
        } catch (Exception e) {
            return "";
        }
    }

    public AutoCompletePage removeMultipleValue(String color) {
        logger.info("Removing color: {}", color);
        List<WebElement> values = findElements(AutoCompleteLocators.MULTI_VALUE_LABELS);
        List<WebElement> removes = findElements(AutoCompleteLocators.MULTI_VALUE_REMOVE);

        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).getText().equalsIgnoreCase(color)) {
                removes.get(i).click();
                break;
            }
        }
        return this;
    }

    public AutoCompletePage clearAllMultiple() {
        logger.info("Clearing all multiple values");
        clickButton(AutoCompleteLocators.MULTI_CLEAR_BUTTON);
        return this;
    }
}
