package demoqa.alertsframewindows;

import core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.demoqa.alertsframewindows.BrowserWindowsPage;

public class BrowserWindowsTest extends BaseTest {

    private BrowserWindowsPage page;

    @BeforeMethod
    void setup() {
        page = new BrowserWindowsPage();
    }

    @Test(description = "Verify New Tab functionality")
    void testNewTab() {
        page.clickNewTab();
        page.switchToNewWindowOrTab();

        // Assert we are on the new page
        String heading = page.getHeadingText();
        Assert.assertTrue(heading.contains("This is a sample page"), "New Tab content mismatch");

        page.closeAndSwitchBack();
    }

    @Test(description = "Verify New Window functionality")
    void testNewWindow() {
        page.clickNewWindow();
        page.switchToNewWindowOrTab();

        // Assert we are on the new window
        String heading = page.getHeadingText();
        Assert.assertTrue(heading.contains("This is a sample page"), "New Window content mismatch");

        page.closeAndSwitchBack();
    }

    @Test(description = "Verify New Window Message functionality")
    void testNewMessageWindow() {
        page.clickNewWindowMessage();
        page.switchToNewWindowOrTab();

        // The message window contains specific text about sharing knowledge
        String body = page.getBodyText();
        Assert.assertTrue(body.contains("Knowledge increases by sharing but not by saving"),
                "Message Window content mismatch. Got: " + body);

        page.closeAndSwitchBack();
    }
}
