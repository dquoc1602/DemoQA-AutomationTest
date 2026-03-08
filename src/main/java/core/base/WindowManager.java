package core.base;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Manages browser-level operations: alerts, windows/tabs, frames, cookies,
 * screenshots, and navigation.
 * Reusable across web, app, and mobile projects.
 */
public class WindowManager extends JavaScriptHelper {

    /** Stores the original window handle for switching back later. */
    private String crrWindow;

    // ═══════════════════ ALERT HANDLING ═══════════════════

    /**
     * Switches focus to the current browser alert/confirm/prompt.
     *
     * @return the Alert object
     */
    protected Alert switchToAlert() {
        logger.info("Switching to alert");
        return this.driver.switchTo().alert();
    }

    protected void acceptAlertAction(Alert alert) {
        logger.info("Accepting alert");
        alert.accept();
    }

    protected void dismissAlertAction(Alert alert) {
        logger.info("Dismissing alert");
        alert.dismiss();
    }

    /**
     * Returns the text displayed in the current alert.
     *
     * @return alert text
     */
    protected String getAlertText() {
        logger.info("Getting alert text");
        return driver.switchTo().alert().getText();
    }

    /**
     * Sends text to a prompt-type alert.
     *
     * @param text text to enter
     */
    protected void sendAlertText(String text) {
        logger.info("Sending text '{}' to alert", text);
        Alert alert = driver.switchTo().alert();
        alert.sendKeys(text);
    }

    /**
     * Checks whether an alert is currently present.
     *
     * @return true if an alert is open
     */
    protected boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    /**
     * Accepts the alert if present, otherwise does nothing.
     */
    protected void acceptAlertIfPresent() {
        if (isAlertPresent()) {
            driver.switchTo().alert().accept();
            logger.info("Alert accepted");
        }
    }

    // ═══════════════════ WINDOW / TAB MANAGEMENT ═══════════════════

    /**
     * Switches to the newest window/tab (keeps original misspelled name for
     * backward compatibility).
     *
     * @return the WebDriver instance
     */
    protected WebDriver swithToNewWindow() {
        logger.info("Switching to new window");
        this.crrWindow = this.driver.getWindowHandle();
        logger.info("Current window: {}", this.crrWindow);
        for (String windowHandle : this.driver.getWindowHandles()) {
            if (!windowHandle.equals(this.crrWindow)) {
                this.driver.switchTo().window(windowHandle);
                logger.info("Switched to new window: {}", windowHandle);
                return this.driver;
            }
        }
        logger.warn("No new window found to switch to");
        return this.driver;
    }

    /**
     * Alias with correct spelling. Delegates to {@link #swithToNewWindow()}.
     *
     * @return the WebDriver instance
     */
    protected WebDriver switchToNewWindow() {
        return swithToNewWindow();
    }

    public void switchBackToOriginalWindow() {
        logger.info("Switching back to original window: {}", this.crrWindow);
        Set<String> arrString = this.driver.getWindowHandles();
        for (String windowHandle : arrString) {
            if (!windowHandle.equals(this.crrWindow)) {
                this.driver.switchTo().window(windowHandle);
                logger.info("Closing window: {}", windowHandle);
                this.driver.close();
            }
        }
        this.driver.switchTo().window(this.crrWindow);
    }

    /**
     * Switches to a window by its title.
     *
     * @param title exact window title to match
     * @throws IllegalStateException if no matching window found
     */
    protected void switchToWindowByTitle(String title) {
        logger.info("Switching to window with title: {}", title);
        String current = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            if (driver.getTitle().equals(title)) {
                logger.info("Found and switched to window: {}", title);
                return;
            }
        }
        // Restore original if not found
        driver.switchTo().window(current);
        throw new IllegalStateException("No window with title: " + title);
    }

    /**
     * Switches to a window/tab by its zero-based index.
     *
     * @param index window index
     */
    protected void switchToWindowByIndex(int index) {
        logger.info("Switching to window at index {}", index);
        List<String> handles = new ArrayList<>(driver.getWindowHandles());
        if (index < 0 || index >= handles.size()) {
            throw new IndexOutOfBoundsException(
                    "Window index " + index + " out of range (total: " + handles.size() + ")");
        }
        driver.switchTo().window(handles.get(index));
    }

    /**
     * Closes the current tab/window and switches to the first remaining one.
     */
    protected void closeCurrentWindow() {
        logger.info("Closing current window");
        driver.close();
        List<String> handles = new ArrayList<>(driver.getWindowHandles());
        if (!handles.isEmpty()) {
            driver.switchTo().window(handles.get(0));
        }
    }

    /**
     * Returns the number of open windows/tabs.
     *
     * @return window count
     */
    protected int getWindowHandleCount() {
        return driver.getWindowHandles().size();
    }

    // ═══════════════════ FRAME MANAGEMENT ═══════════════════

    protected void switchToDefaultContent() {
        logger.info("Switching back to default content");
        this.driver.switchTo().defaultContent();
    }

    protected void switchToFrame(By selector) {
        logger.info("Switching to frame {}", selector);
        WebElement frameElement = findElementPresent(selector);
        this.driver.switchTo().frame(frameElement);
    }

    protected void switchToFrame(String idOrName) {
        logger.info("Switching to frame by ID/Name: {}", idOrName);
        this.driver.switchTo().frame(idOrName);
    }

    /**
     * Switches to a frame by its zero-based index.
     *
     * @param index frame index
     */
    protected void switchToFrameByIndex(int index) {
        logger.info("Switching to frame at index {}", index);
        this.driver.switchTo().frame(index);
    }

    /**
     * Switches to the parent frame (one level up).
     */
    protected void switchToParentFrame() {
        logger.info("Switching to parent frame");
        this.driver.switchTo().parentFrame();
    }

    // ═══════════════════ NAVIGATION ═══════════════════

    /**
     * Navigates the browser back.
     */
    protected void navigateBack() {
        logger.info("Navigating back");
        driver.navigate().back();
    }

    /**
     * Navigates the browser forward.
     */
    protected void navigateForward() {
        logger.info("Navigating forward");
        driver.navigate().forward();
    }

    /**
     * Refreshes the current page.
     */
    protected void refreshPage() {
        logger.info("Refreshing page");
        driver.navigate().refresh();
    }

    /**
     * Returns the current page URL.
     *
     * @return URL string
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Returns the current page title.
     *
     * @return title string
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Returns the page source HTML.
     *
     * @return page source
     */
    protected String getPageSource() {
        return driver.getPageSource();
    }

    // ═══════════════════ WINDOW SIZE ═══════════════════

    /**
     * Maximizes the browser window.
     */
    protected void maximizeWindow() {
        logger.info("Maximizing window");
        driver.manage().window().maximize();
    }

    /**
     * Minimizes the browser window.
     */
    protected void minimizeWindow() {
        logger.info("Minimizing window");
        driver.manage().window().minimize();
    }

    /**
     * Sets the browser window to fullscreen.
     */
    protected void fullscreenWindow() {
        logger.info("Setting window to fullscreen");
        driver.manage().window().fullscreen();
    }

    /**
     * Sets the browser window size.
     *
     * @param width  width in pixels
     * @param height height in pixels
     */
    protected void setWindowSize(int width, int height) {
        logger.info("Setting window size to {}x{}", width, height);
        driver.manage().window().setSize(new Dimension(width, height));
    }

    /**
     * Returns the current window size.
     *
     * @return Dimension
     */
    protected Dimension getWindowSize() {
        return driver.manage().window().getSize();
    }

    // ═══════════════════ COOKIE MANAGEMENT ═══════════════════

    /**
     * Adds a cookie.
     *
     * @param name  cookie name
     * @param value cookie value
     */
    protected void addCookie(String name, String value) {
        logger.info("Adding cookie: {}={}", name, value);
        driver.manage().addCookie(new Cookie(name, value));
    }

    /**
     * Gets a cookie by name.
     *
     * @param name cookie name
     * @return Cookie or null if not found
     */
    protected Cookie getCookie(String name) {
        return driver.manage().getCookieNamed(name);
    }

    /**
     * Deletes a cookie by name.
     *
     * @param name cookie name
     */
    protected void deleteCookie(String name) {
        logger.info("Deleting cookie: {}", name);
        driver.manage().deleteCookieNamed(name);
    }

    /**
     * Deletes all cookies.
     */
    protected void deleteAllCookies() {
        logger.info("Deleting all cookies");
        driver.manage().deleteAllCookies();
    }

    /**
     * Returns all cookies.
     *
     * @return set of Cookie objects
     */
    protected Set<Cookie> getAllCookies() {
        return driver.manage().getCookies();
    }

    // ═══════════════════ SCREENSHOTS ═══════════════════

    /**
     * Takes a full-page screenshot and saves it.
     *
     * @param filePath absolute file path for the screenshot
     * @return the saved File
     */
    protected File takeScreenshot(String filePath) {
        logger.info("Taking screenshot: {}", filePath);
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path destPath = Paths.get(filePath);
            Files.createDirectories(destPath.getParent());
            Files.copy(srcFile.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
            logger.info("Screenshot saved to: {}", filePath);
            return destPath.toFile();
        } catch (IOException e) {
            logger.error("Failed to save screenshot to {}", filePath, e);
            throw new RuntimeException("Screenshot save failed", e);
        }
    }

    /**
     * Takes a screenshot of a specific element.
     *
     * @param selector element locator
     * @param filePath absolute file path
     * @return the saved File
     */
    protected File takeElementScreenshot(By selector, String filePath) {
        logger.info("Taking element screenshot: {}", filePath);
        try {
            WebElement element = findElement(selector);
            File srcFile = element.getScreenshotAs(OutputType.FILE);
            Path destPath = Paths.get(filePath);
            Files.createDirectories(destPath.getParent());
            Files.copy(srcFile.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
            logger.info("Element screenshot saved to: {}", filePath);
            return destPath.toFile();
        } catch (IOException e) {
            logger.error("Failed to save element screenshot to {}", filePath, e);
            throw new RuntimeException("Element screenshot save failed", e);
        }
    }

    /**
     * Returns a full-page screenshot as a Base64-encoded string.
     *
     * @return Base64 screenshot string
     */
    protected String takeScreenshotAsBase64() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }
}
