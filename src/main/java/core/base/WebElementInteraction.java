package core.base;

import core.TestSettings;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Direct interaction methods that operate on a {@link WebElement} instance
 * (as opposed to a {@link org.openqa.selenium.By} selector).
 * Useful when elements are already discovered, e.g. iterating over a list or working with
 * FindBy-annotated fields.
 * Reusable across web, app, and mobile projects.
 */
public class WebElementInteraction extends WindowManager {

    // ───────────────────── SCROLL ─────────────────────

    protected void scrollToWebElement(WebElement element) {
        scrollToWebElement(element, "center", "center");
    }

    protected void scrollToWebElement(WebElement element, String blockVertical, String inlineHorizontal) {
        logger.info("Scrolling to WebElement with block: '{}', inline: '{}'", blockVertical, inlineHorizontal);
        String script = String.format("arguments[0].scrollIntoView({block:'%s', inline:'%s'});",
                blockVertical, inlineHorizontal);
        ((JavascriptExecutor) driver).executeScript(script, element);
    }

    // ───────────────────── WAIT HELPERS ─────────────────────

    protected void waitForWebElementAttributeContains(WebElement element, String attribute, String value) {
        waitForWebElementAttributeContains(element, attribute, value, TestSettings.WAIT_ELEMENT);
    }

    protected void waitForWebElementAttributeContains(WebElement element, String attribute, String value,
            long timeoutSeconds) {
        logger.info("Waiting for WebElement to have attribute '{}' containing '{}' (timeout: {}s)",
                attribute, value, timeoutSeconds);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.attributeContains(element, attribute, value));
            logger.info("WebElement attribute '{}' now contains '{}'", attribute, value);
        } catch (Exception e) {
            logger.error("Timeout waiting for attribute '{}' to contain '{}' on WebElement", attribute, value);
            throw e;
        }
    }

    protected void waitForWebElementAttributeNotContains(WebElement element, String attribute, String value) {
        waitForWebElementAttributeNotContains(element, attribute, value, TestSettings.WAIT_ELEMENT);
    }

    protected void waitForWebElementAttributeNotContains(WebElement element, String attribute, String value,
            long timeoutSeconds) {
        logger.info("Waiting for WebElement to not have attribute '{}' containing '{}' (timeout: {}s)",
                attribute, value, timeoutSeconds);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.not(ExpectedConditions.attributeContains(element, attribute, value)));
            logger.info("WebElement attribute '{}' no longer contains '{}'", attribute, value);
        } catch (Exception e) {
            logger.error("Timeout waiting for attribute '{}' to not contain '{}' on WebElement", attribute, value);
            throw e;
        }
    }

    protected WebElement waitForWebElementVisible(WebElement element) {
        return waitForWebElementVisible(element, TestSettings.WAIT_ELEMENT);
    }

    protected WebElement waitForWebElementVisible(WebElement element, long timeoutSeconds) {
        logger.info("Waiting for WebElement to be visible (timeout: {}s)", timeoutSeconds);
        try {
            WebElement visibleElement = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.visibilityOf(element));
            logger.info("WebElement is now visible");
            return visibleElement;
        } catch (Exception e) {
            logger.error("Timeout waiting for WebElement to be visible");
            throw e;
        }
    }

    protected WebElement waitForWebElementClickable(WebElement element) {
        return waitForWebElementClickable(element, TestSettings.WAIT_ELEMENT);
    }

    protected WebElement waitForWebElementClickable(WebElement element, long timeoutSeconds) {
        logger.info("Waiting for WebElement to be clickable (timeout: {}s)", timeoutSeconds);
        try {
            WebElement clickableElement = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.elementToBeClickable(element));
            logger.info("WebElement is now clickable");
            return clickableElement;
        } catch (Exception e) {
            logger.error("Timeout waiting for WebElement to be clickable");
            throw e;
        }
    }

    protected void waitForWebElementInvisible(WebElement element) {
        waitForWebElementInvisible(element, TestSettings.WAIT_ELEMENT);
    }

    protected void waitForWebElementInvisible(WebElement element, long timeoutSeconds) {
        logger.info("Waiting for WebElement to be invisible (timeout: {}s)", timeoutSeconds);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.invisibilityOf(element));
            logger.info("WebElement is now invisible");
        } catch (Exception e) {
            logger.error("Timeout waiting for WebElement to be invisible");
            throw e;
        }
    }

    // ───────────────────── PROPERTY GETTERS ─────────────────────

    protected String getWebElementAttribute(WebElement element, String attributeName) {
        logger.info("Getting attribute '{}' from WebElement", attributeName);
        String value = element.getDomAttribute(attributeName);
        logger.info("Retrieved attribute '{}' value: '{}'", attributeName, value);
        return value;
    }

    protected String getWebElementText(WebElement element) {
        logger.info("Getting text from WebElement");
        String text = element.getText();
        logger.info("Retrieved text: '{}'", text);
        return text;
    }

    /**
     * Returns the element's CSS property value.
     *
     * @param element      WebElement
     * @param propertyName CSS property (e.g. "color", "display")
     * @return computed CSS value
     */
    protected String getWebElementCssValue(WebElement element, String propertyName) {
        return element.getCssValue(propertyName);
    }

    /**
     * Returns the element's text; falls back to the "value" property if text is empty.
     *
     * @param element WebElement
     * @return text or value
     */
    protected String getWebElementValue(WebElement element) {
        String text = element.getText();
        return text.isEmpty() ? element.getDomProperty("value") : text;
    }

    /**
     * Checks whether the element is selected (checkbox / radio / option).
     *
     * @param element WebElement
     * @return true if selected
     */
    protected boolean isWebElementSelected(WebElement element) {
        return element.isSelected();
    }

    /**
     * Checks whether the element is enabled.
     *
     * @param element WebElement
     * @return true if enabled
     */
    protected boolean isWebElementEnabled(WebElement element) {
        return element.isEnabled();
    }

    /**
     * Checks whether the element is displayed.
     *
     * @param element WebElement
     * @return true if displayed
     */
    protected boolean isWebElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ───────────────────── INTERACTIONS ─────────────────────

    protected void clickWebElement(WebElement element) {
        logger.info("Clicking WebElement");
        scrollToWebElement(element);
        WebElement clickableElement = waitForWebElementClickable(element);
        clickableElement.click();
        logger.info("WebElement clicked successfully");
    }

    protected void enterTextToWebElement(WebElement element, String text) {
        logger.info("Entering text '{}' into WebElement", text);
        scrollToWebElement(element);
        WebElement visibleElement = waitForWebElementVisible(element);
        visibleElement.clear();
        visibleElement.sendKeys(text);
        logger.info("Text entered successfully");
    }

    /**
     * Double-clicks a WebElement.
     *
     * @param element WebElement
     */
    protected void doubleClickWebElement(WebElement element) {
        logger.info("Double clicking WebElement");
        scrollToWebElement(element);
        new Actions(driver).doubleClick(element).perform();
    }

    /**
     * Right-clicks (context click) a WebElement.
     *
     * @param element WebElement
     */
    protected void rightClickWebElement(WebElement element) {
        logger.info("Right clicking WebElement");
        scrollToWebElement(element);
        new Actions(driver).contextClick(element).perform();
    }

    /**
     * Hovers over a WebElement.
     *
     * @param element WebElement
     */
    protected void hoverWebElement(WebElement element) {
        logger.info("Hovering over WebElement");
        scrollToWebElement(element);
        new Actions(driver).moveToElement(element).perform();
    }

    /**
     * Drags one WebElement to another.
     *
     * @param source source WebElement
     * @param target target WebElement
     */
    protected void dragAndDropWebElement(WebElement source, WebElement target) {
        logger.info("Dragging WebElement");
        new Actions(driver).dragAndDrop(source, target).perform();
    }
}
