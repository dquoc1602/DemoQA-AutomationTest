package alerts_frame_windows;

import core.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.demoQA.alerts_frame_windows.BrowserWindowsPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Browser Windows Tests")
public class BrowserWindowsTest extends BaseTest {

    private BrowserWindowsPage page;

    @BeforeEach
    void setup() {
        page = new BrowserWindowsPage();
    }

    @Test
    @DisplayName("Verify New Tab functionality")
    void testNewTab() {
        page.clickNewTab();
        page.switchToNewWindowOrTab();

        // Assert we are on the new page
        String heading = page.getHeadingText();
        assertTrue(heading.contains("This is a sample page"), "New Tab content mismatch");

        page.closeAndSwitchBack();
    }

    @Test
    @DisplayName("Verify New Window functionality")
    void testNewWindow() {
        page.clickNewWindow();
        page.switchToNewWindowOrTab();

        // Assert we are on the new window
        String heading = page.getHeadingText();
        assertTrue(heading.contains("This is a sample page"), "New Window content mismatch");

        page.closeAndSwitchBack();
    }

    @Test
    @DisplayName("Verify New Window Message functionality")
    void testNewMessageWindow() {
        page.clickNewWindowMessage();
        page.switchToNewWindowOrTab();

        // The message window contains specific text about sharing knowledge
        String body = page.getBodyText();
        assertTrue(body.contains("Knowledge increases by sharing but not by saving"),
                "Message Window content mismatch. Got: " + body);

        page.closeAndSwitchBack();
    }
}
