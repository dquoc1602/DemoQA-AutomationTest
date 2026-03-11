package page.demoqa.alertsframewindows;

import core.BasePage;
import locator.demoqa.alertsframewindows.BrowserWindowsLocators;

import static common.Constants.DEMOQA_BROWSER_WINDOW_URL;

public class BrowserWindowsPage extends BasePage {

    public BrowserWindowsPage() {
        super();
        logger.info("Opening Browser Windows page: {}", DEMOQA_BROWSER_WINDOW_URL);
        openSite(DEMOQA_BROWSER_WINDOW_URL);
    }

    public void clickNewTab() {
        clickButton(BrowserWindowsLocators.NEW_TAB_BUTTON);
    }

    public void clickNewWindow() {
        clickButton(BrowserWindowsLocators.NEW_WINDOW_BUTTON);
    }

    public void clickNewWindowMessage() {
        clickButton(BrowserWindowsLocators.NEW_WINDOW_MESSAGE_BUTTON);
    }

    public String getHeadingText() {
        return getElementText(BrowserWindowsLocators.HEADING);
    }

    public String getBodyText() {
        // The message window is very minimal (often about:blank with raw text).
        // Using getPageSource is more resilient for raw text windows where
        // findElement(body) might fail.
        logger.info("Retrieving content from message window via page source...");

        return getWait(30).until(d -> {
            try {
                String source = d.getPageSource();
                return (source != null && source.length() > 30) ? source : null;
            } catch (Exception e) {
                return null;
            }
        });
    }

    // Switch methods derived from Basepage
    public void switchToNewWindowOrTab() {
        swithToNewWindow();
    }

    public void closeAndSwitchBack() {
        switchBackToOriginalWindow();
    }
}
