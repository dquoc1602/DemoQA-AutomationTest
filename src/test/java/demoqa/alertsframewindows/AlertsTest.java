package demoqa.alertsframewindows;

import core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.demoqa.alertsframewindows.AlertsPage;

public class AlertsTest extends BaseTest {

    private AlertsPage page;

    @BeforeMethod
    void setup() {
        page = new AlertsPage();
    }

    @Test(description = "Verify simple alert")
    void testSimpleAlert() {
        page.clickSimpleAlertButton();
        String alertText = page.waitForAlert().getText();
        Assert.assertEquals(alertText, "You clicked a button");
        page.acceptAlert();
    }

    @Test(description = "Verify timer alert (5 seconds)")
    void testTimerAlert() {
        page.clickTimerAlertButton();
        String alertText = page.waitForAlert().getText();
        Assert.assertEquals(alertText, "This alert appeared after 5 seconds");
        page.acceptAlert();
    }

    @Test(description = "Verify confirm box - OK")
    void testConfirmOk() {
        page.clickConfirmButton();
        page.acceptAlert();
        Assert.assertEquals(page.getConfirmResult(), "You selected Ok");
    }

    @Test(description = "Verify confirm box - Cancel")
    void testConfirmCancel() {
        page.clickConfirmButton();
        page.dismissAlert();
        Assert.assertEquals(page.getConfirmResult(), "You selected Cancel");
    }

    @Test(description = "Verify prompt box")
    void testPromptValue() {
        String inputName = "Antigravity";
        page.clickPromptButton();
        page.sendTextToPrompt(inputName);
        Assert.assertTrue(page.getPromptResult().contains(inputName));
    }
}
