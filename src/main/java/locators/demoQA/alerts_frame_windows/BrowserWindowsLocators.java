package locators.demoQA.alerts_frame_windows;

import org.openqa.selenium.By;

public final class BrowserWindowsLocators {

    public static final By NEW_TAB_BUTTON = By.id("tabButton");
    public static final By NEW_WINDOW_BUTTON = By.id("windowButton");
    public static final By NEW_WINDOW_MESSAGE_BUTTON = By.id("messageWindowButton");

    // Some elements on the new page to verify content
    public static final By HEADING = By.id("sampleHeading");
    public static final By BODY = By.tagName("body");

    private BrowserWindowsLocators() {
    }
}
