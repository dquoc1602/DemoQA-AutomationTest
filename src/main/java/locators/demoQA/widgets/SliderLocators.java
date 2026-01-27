package locators.demoQA.widgets;

import org.openqa.selenium.By;

public final class SliderLocators {

    public static final By SLIDER = By.cssSelector("input.range-slider");
    public static final By SLIDER_VALUE_INPUT = By.id("sliderValue");

    private SliderLocators() {
    }
}
