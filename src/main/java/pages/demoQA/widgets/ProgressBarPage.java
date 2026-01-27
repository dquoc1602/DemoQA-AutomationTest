package pages.demoQA.widgets;

import core.Basepage;
import locators.demoQA.widgets.ProgressBarLocators;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static util.Constants.DEMOQA_PROGRESS_BAR_URL;

public class ProgressBarPage extends Basepage {

    public ProgressBarPage() {
        super();
        logger.info("Opening Progress Bar page: {}", DEMOQA_PROGRESS_BAR_URL);
        openSite(DEMOQA_PROGRESS_BAR_URL);
    }

    public void clickStartStop() {
        logger.info("Clicking Start/Stop button");
        clickButton(ProgressBarLocators.START_STOP_BUTTON);
    }

    public void clickReset() {
        logger.info("Clicking Reset button");
        clickButton(ProgressBarLocators.RESET_BUTTON);
    }

    public int getProgressValue() {
        String value = getElementAttribute(ProgressBarLocators.PROGRESS_BAR, "aria-valuenow");
        return Integer.parseInt(value);
    }

    public void waitForProgress(int targetPercentage) {
        logger.info("Waiting for progress to reach {}%", targetPercentage);
        getWait(30).until(driver -> getProgressValue() >= targetPercentage);
    }

    public void waitForComplete() {
        logger.info("Waiting for progress bar to complete (100%)");
        getWait(60).until(ExpectedConditions.attributeToBe(ProgressBarLocators.PROGRESS_BAR, "aria-valuenow", "100"));
    }

    public boolean isResetButtonVisible() {
        try {
            return findElementPresent(ProgressBarLocators.RESET_BUTTON).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
