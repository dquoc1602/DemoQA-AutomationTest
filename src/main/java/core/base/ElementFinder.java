package core.base;

import core.DriverManager;
import core.TestSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import common.Helper;

import java.time.Duration;
import java.util.List;

/**
 * Foundation class for element discovery and existence checks.
 * Provides all methods to locate elements in the DOM — by visibility, presence, or raw lookup.
 * Reusable across web, app, and mobile projects.
 */
public class ElementFinder extends Helper {

    protected WebDriver driver;

    public ElementFinder() {
        this.driver = DriverManager.getDriver();
    }

    // ───────────────────── SINGLE ELEMENT FINDERS ─────────────────────

    /**
     * Finds an element waiting for it to be visible.
     *
     * @param selector element locator
     * @return the visible WebElement
     */
    public WebElement findElement(By selector) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(TestSettings.WAIT_ELEMENT))
                    .until(ExpectedConditions.visibilityOfElementLocated(selector));
        } catch (org.openqa.selenium.TimeoutException e) {
            try {
                java.io.File srcFile = ((org.openqa.selenium.TakesScreenshot) driver).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
                java.nio.file.Files.copy(srcFile.toPath(), new java.io.File("target/error_screenshot.png").toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                logger.error("Timeout waiting for element. Screenshot saved to target/error_screenshot.png", selector);
            } catch (Exception ex) {
                logger.error("Failed to take screenshot on timeout", ex);
            }
            throw e;
        }
    }

    /**
     * Finds an element waiting only for DOM presence (may be hidden).
     *
     * @param selector element locator
     * @return the present WebElement
     */
    public WebElement findElementPresent(By selector) {
        logger.debug("Finding element by presence: {}", selector);
        return new WebDriverWait(driver, Duration.ofSeconds(TestSettings.WAIT_ELEMENT))
                .until(ExpectedConditions.presenceOfElementLocated(selector));
    }

    // ───────────────────── MULTIPLE ELEMENT FINDERS ─────────────────────

    /**
     * Finds all elements matching the selector.
     * Returns an empty list if none are found — never throws.
     *
     * @param selector element locator
     * @return list of matching WebElements (may be empty)
     */
    protected List<WebElement> findElements(By selector) {
        logger.debug("Finding elements by selector: {}", selector);
        try {
            List<WebElement> elements = driver.findElements(selector);
            logger.debug("Found {} elements for selector {}", elements.size(), selector);
            return elements;
        } catch (Exception e) {
            logger.error("Error while finding elements by selector {}", selector, e);
            return List.of();
        }
    }

    /**
     * Finds all visible elements matching the selector (waits for at least one).
     *
     * @param selector element locator
     * @return list of visible WebElements
     */
    protected List<WebElement> findElementsVisible(By selector) {
        logger.debug("Finding visible elements by selector: {}", selector);
        return new WebDriverWait(driver, Duration.ofSeconds(TestSettings.WAIT_ELEMENT))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(selector));
    }

    /**
     * Finds all present elements matching the selector (waits for at least one).
     *
     * @param selector element locator
     * @return list of present WebElements
     */
    protected List<WebElement> findElementsPresent(By selector) {
        logger.debug("Finding present elements by selector: {}", selector);
        return new WebDriverWait(driver, Duration.ofSeconds(TestSettings.WAIT_ELEMENT))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(selector));
    }

    // ───────────────────── CHILD / RELATIVE ELEMENT FINDERS ─────────────────────

    /**
     * Finds a child element within a parent.
     *
     * @param parent  the parent WebElement
     * @param selector child locator (relative to parent)
     * @return the child WebElement
     */
    protected WebElement findChildElement(WebElement parent, By selector) {
        logger.debug("Finding child element {} within parent", selector);
        return parent.findElement(selector);
    }

    /**
     * Finds multiple child elements within a parent.
     *
     * @param parent  the parent WebElement
     * @param selector child locator (relative to parent)
     * @return list of child WebElements (may be empty)
     */
    protected List<WebElement> findChildElements(WebElement parent, By selector) {
        logger.debug("Finding child elements {} within parent", selector);
        return parent.findElements(selector);
    }

    // ───────────────────── ELEMENT STATE CHECKS ─────────────────────

    /**
     * Checks if an element is currently displayed on the page.
     *
     * @param selector element locator
     * @return true if visible, false otherwise
     */
    protected boolean isElementDisplayed(By selector) {
        try {
            return driver.findElement(selector).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    /**
     * Checks if an element exists in the DOM (visible or hidden).
     *
     * @param selector element locator
     * @return true if present in DOM
     */
    protected boolean isElementPresent(By selector) {
        try {
            driver.findElement(selector);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Checks if an element is enabled (not disabled).
     *
     * @param selector element locator
     * @return true if enabled
     */
    protected boolean isElementEnabled(By selector) {
        try {
            return driver.findElement(selector).isEnabled();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    /**
     * Checks if an element (checkbox, radio) is selected.
     *
     * @param selector element locator
     * @return true if selected/checked
     */
    protected boolean isElementSelected(By selector) {
        try {
            return driver.findElement(selector).isSelected();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    /**
     * Returns the count of elements matching the selector.
     *
     * @param selector element locator
     * @return number of matching elements
     */
    protected int getElementCount(By selector) {
        return driver.findElements(selector).size();
    }
}
