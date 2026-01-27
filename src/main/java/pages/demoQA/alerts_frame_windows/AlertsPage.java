package pages.demoQA.alerts_frame_windows;

import core.Basepage;
import locators.demoQA.alerts_frame_windows.AlertsLocators;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static util.Constants.DEMOQA_ALERTS_URL;

public class AlertsPage extends Basepage {

    public AlertsPage() {
        super();
        logger.info("Opening Alerts page: {}", DEMOQA_ALERTS_URL);
        openSite(DEMOQA_ALERTS_URL);
    }

    public AlertsPage clickSimpleAlertButton() {
        clickButton(AlertsLocators.SIMPLE_ALERT_BUTTON);
        return this;
    }

    public AlertsPage clickTimerAlertButton() {
        clickButton(AlertsLocators.TIMER_ALERT_BUTTON);
        return this;
    }

    public AlertsPage clickConfirmButton() {
        clickButton(AlertsLocators.CONFIRM_BUTTON);
        return this;
    }

    public AlertsPage clickPromptButton() {
        clickButton(AlertsLocators.PROMPT_BUTTON);
        return this;
    }

    public Alert waitForAlert() {
        logger.info("Waiting for alert to be present");
        return getWait(10).until(ExpectedConditions.alertIsPresent());
    }

    public String getConfirmResult() {
        return getElementText(AlertsLocators.CONFIRM_RESULT);
    }

    public String getPromptResult() {
        return getElementText(AlertsLocators.PROMPT_RESULT);
    }

    public void acceptAlert() {
        Alert alert = switchToAlert();
        logger.info("Accepting alert with text: {}", alert.getText());
        alert.accept();
    }

    public void dismissAlert() {
        Alert alert = switchToAlert();
        logger.info("Dismissing alert with text: {}", alert.getText());
        alert.dismiss();
    }

    public void sendTextToPrompt(String text) {
        Alert alert = switchToAlert();
        logger.info("Sending text '{}' to prompt", text);
        alert.sendKeys(text);
        alert.accept();
    }
}
