package locators.demoQA.widgets;

import org.openqa.selenium.By;

public final class ProgressBarLocators {

    public static final By PROGRESS_BAR = By.xpath("//div[@role='progressbar']");
    public static final By START_STOP_BUTTON = By.id("startStopButton");
    public static final By RESET_BUTTON = By.id("resetButton");

    private ProgressBarLocators() {
    }
}
