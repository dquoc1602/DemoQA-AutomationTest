package page.demoqa.widgets;

import core.BasePage;
import locator.demoqa.widgets.SliderLocators;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static common.Constants.DEMOQA_SLIDER_URL;

public class SliderPage extends BasePage {

    public SliderPage() {
        super();
        logger.info("Opening Slider page: {}", DEMOQA_SLIDER_URL);
        openSite(DEMOQA_SLIDER_URL);
    }

    public int getSliderValue() {
        String value = getElementAttribute(SliderLocators.SLIDER_VALUE_INPUT, "value");
        return Integer.parseInt(value);
    }

    public void moveSliderToValue(int targetValue) {
        logger.info("Moving slider to value: {}", targetValue);
        WebElement slider = findElement(SliderLocators.SLIDER);
        int currentValue = getSliderValue();

        Keys moveKey = (targetValue > currentValue) ? Keys.ARROW_RIGHT : Keys.ARROW_LEFT;
        int steps = Math.abs(targetValue - currentValue);

        for (int i = 0; i < steps; i++) {
            slider.sendKeys(moveKey);
        }
    }
}
