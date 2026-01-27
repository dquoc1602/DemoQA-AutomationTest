package alerts_frame_windows;

import core.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.demoQA.alerts_frame_windows.AlertsPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Alerts Tests")
public class AlertsTest extends BaseTest {

    private AlertsPage page;

    @BeforeEach
    void setup() {
        page = new AlertsPage();
    }

    @Test
    @DisplayName("Verify simple alert")
    void testSimpleAlert() {
        page.clickSimpleAlertButton();
        String alertText = page.waitForAlert().getText();
        assertEquals("You clicked a button", alertText);
        page.acceptAlert();
    }

    @Test
    @DisplayName("Verify timer alert (5 seconds)")
    void testTimerAlert() {
        page.clickTimerAlertButton();
        String alertText = page.waitForAlert().getText();
        assertEquals("This alert appeared after 5 seconds", alertText);
        page.acceptAlert();
    }

    @Test
    @DisplayName("Verify confirm box - OK")
    void testConfirmOk() {
        page.clickConfirmButton();
        page.acceptAlert();
        assertEquals("You selected Ok", page.getConfirmResult());
    }

    @Test
    @DisplayName("Verify confirm box - Cancel")
    void testConfirmCancel() {
        page.clickConfirmButton();
        page.dismissAlert();
        assertEquals("You selected Cancel", page.getConfirmResult());
    }

    @Test
    @DisplayName("Verify prompt box")
    void testPromptValue() {
        String inputName = "Antigravity";
        page.clickPromptButton();
        page.sendTextToPrompt(inputName);
        assertTrue(page.getPromptResult().contains(inputName));
    }
}
