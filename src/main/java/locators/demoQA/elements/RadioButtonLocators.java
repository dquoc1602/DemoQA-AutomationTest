package locators.demoQA.elements;

import org.openqa.selenium.By;

public final class RadioButtonLocators {

    public static final By QUESTION_TEXT = By.cssSelector("div.mb-3");

    public static final By RESULT_TEXT = By.cssSelector("p.mt-3 span.text-success");

    public static final By ALL_RADIOS = By.cssSelector("input[type='radio'][name='like']");

    public static final String RADIO_BY_ID = "input[type='radio'][id='%s']";

    public static final String LABEL_BY_FOR = "label[for='%s']";

    private RadioButtonLocators() {
    }
}
