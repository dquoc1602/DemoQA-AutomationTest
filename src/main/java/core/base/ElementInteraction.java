package core.base;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
// import core.TestSettings;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Element interaction methods using {@link By} selectors.
 * Covers text input, clicks, hover, drag-and-drop, keyboard actions, scroll, file upload,
 * and element property retrieval.
 * Reusable across web, app, and mobile projects.
 */
public class ElementInteraction extends WaitHelper {

    // ───────────────────── TEXT INPUT ─────────────────────

    protected void enterText(By selector, String text) {
        logger.info("Entering text '{}'", text);
        findElement(selector).sendKeys(text);
    }

    protected void replaceText(By selector, String text) {
        logger.info("Replacing text '{}' in element {}", text, selector);
        WebElement element = findElement(selector);
        element.clear();
        element.sendKeys(text);
        logger.info("Text replaced successfully");
    }

    protected void enterTextWithoutWait(By selector, String text) {
        logger.info("Entering text: {}", text);
        this.driver.findElement(selector).sendKeys(text);
    }

    /**
     * Clears the text field completely (handles edge cases where clear() alone fails).
     *
     * @param selector element locator
     */
    protected void clearField(By selector) {
        logger.info("Clearing field {}", selector);
        WebElement element = findElement(selector);
        element.clear();
        // Fallback: select-all + delete for stubborn fields
        if (!element.getDomProperty("value").isEmpty()) {
            element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        }
    }

    // ───────────────────── TEXT RETRIEVAL ─────────────────────

    protected String getElementText(By selector) {
        String text = findElement(selector).getText();
        logger.info("Retrieved text '{}' from element {}", text, selector);
        return text;
    }

    protected String getValueAfterColon(By selector) {
        String rawText = getElementText(selector);
        logger.info("Raw output text: {}", rawText);
        if (!rawText.contains(":")) {
            return rawText.trim();
        }
        return rawText.substring(rawText.indexOf(":") + 1).trim();
    }

    /**
     * Returns the element's text if available; otherwise falls back to the "value" DOM property.
     *
     * @param selector element locator
     * @return the element's text or value
     */
    protected String getElementValue(By selector) {
        logger.info("Getting value from element {}", selector);
        WebElement element = findElement(selector);
        return element.getText().isEmpty() ? element.getDomProperty("value") : element.getText();
    }

    /**
     * Collects the visible text of every element matching the selector.
     *
     * @param selector element locator
     * @return list of text strings
     */
    protected List<String> getElementsText(By selector) {
        logger.info("Getting text from all elements matching {}", selector);
        return findElements(selector).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    // ───────────────────── ATTRIBUTE / CSS / PROPERTY ─────────────────────

    protected String getElementAttribute(By selector, String attributeName) {
        logger.info("Getting attribute '{}' from element {}", attributeName, selector);
        return findElement(selector).getDomAttribute(attributeName);
    }

    /**
     * Returns the computed CSS value of a property (e.g. "color", "display").
     *
     * @param selector     element locator
     * @param propertyName CSS property name
     * @return computed CSS value
     */
    protected String getElementCssValue(By selector, String propertyName) {
        logger.info("Getting CSS value '{}' from element {}", propertyName, selector);
        return findElement(selector).getCssValue(propertyName);
    }

    /**
     * Returns the rendered size of the element.
     *
     * @param selector element locator
     * @return Dimension (width, height)
     */
    protected Dimension getElementSize(By selector) {
        return findElement(selector).getSize();
    }

    /**
     * Returns the location of the element on the page.
     *
     * @param selector element locator
     * @return Point (x, y)
     */
    protected Point getElementLocation(By selector) {
        return findElement(selector).getLocation();
    }

    /**
     * Returns the tag name of the element (e.g. "input", "div").
     *
     * @param selector element locator
     * @return tag name in lowercase
     */
    protected String getElementTagName(By selector) {
        return findElement(selector).getTagName();
    }

    // ───────────────────── CLICKS ─────────────────────

    protected void clickButton(By selector) {
        logger.info("Clicking button {}", selector);
        waitForElementClickable(selector).click();
    }

    /**
     * Clicks with a custom wait timeout.
     *
     * @param selector       element locator
     * @param timeoutSeconds wait timeout
     */
    protected void clickButton(By selector, long timeoutSeconds) {
        logger.info("Clicking button {} (timeout: {}s)", selector, timeoutSeconds);
        waitForElementClickable(selector, timeoutSeconds).click();
    }

    protected void doubleClickButton(By selector) {
        logger.info("Double clicking button {}", selector);
        WebElement element = waitForElementClickable(selector);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        new Actions(driver).moveToElement(element).doubleClick(element).perform();
    }

    protected void rightClickButton(By selector) {
        logger.info("Right clicking button {}", selector);
        WebElement element = waitForElementClickable(selector);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        new Actions(driver).moveToElement(element).contextClick(element).perform();
    }

    /**
     * Click-and-hold on an element (useful for sliders, drag handles).
     *
     * @param selector element locator
     */
    protected void clickAndHold(By selector) {
        logger.info("Click and hold on element {}", selector);
        WebElement element = findElement(selector);
        new Actions(driver).clickAndHold(element).perform();
    }

    /**
     * Releases a previously held mouse button.
     */
    protected void releaseClick() {
        logger.info("Releasing mouse button");
        new Actions(driver).release().perform();
    }

    // ───────────────────── HOVER / MOVE ─────────────────────

    protected void hoverElement(By selector) {
        logger.info("Hovering over element {}", selector);
        WebElement element = findElement(selector);
        new Actions(this.driver).moveToElement(element).perform();
    }

    /**
     * Moves to an element with an x,y offset.
     *
     * @param selector element locator
     * @param xOffset  horizontal offset from element centre
     * @param yOffset  vertical offset from element centre
     */
    protected void moveToElementWithOffset(By selector, int xOffset, int yOffset) {
        logger.info("Moving to element {} with offset ({},{})", selector, xOffset, yOffset);
        WebElement element = findElement(selector);
        new Actions(driver).moveToElement(element, xOffset, yOffset).perform();
    }

    // ───────────────────── DRAG & DROP ─────────────────────

    protected void dragAndDrop(By sourceEleBy, By targetEleBy) {
        logger.info("Dragging element from {} to {}", sourceEleBy, targetEleBy);
        WebElement source = findElement(sourceEleBy);
        WebElement target = findElement(targetEleBy);
        new Actions(this.driver).dragAndDrop(source, target).perform();
    }

    /**
     * Drags an element by a pixel offset.
     *
     * @param selector element locator
     * @param xOffset  horizontal offset
     * @param yOffset  vertical offset
     */
    protected void dragAndDropByOffset(By selector, int xOffset, int yOffset) {
        logger.info("Dragging element {} by offset ({},{})", selector, xOffset, yOffset);
        WebElement element = findElement(selector);
        new Actions(driver).dragAndDropBy(element, xOffset, yOffset).perform();
    }

    // ───────────────────── KEYBOARD ─────────────────────

    /**
     * Presses Enter on the element.
     *
     * @param selector element locator
     */
    protected void pressEnter(By selector) {
        logger.info("Pressing Enter on element {}", selector);
        findElement(selector).sendKeys(Keys.RETURN);
    }

    /**
     * Presses Tab on the element.
     *
     * @param selector element locator
     */
    protected void pressTab(By selector) {
        logger.info("Pressing Tab on element {}", selector);
        findElement(selector).sendKeys(Keys.TAB);
    }

    /**
     * Presses Escape (sent to the active element / body).
     */
    protected void pressEscape() {
        logger.info("Pressing Escape");
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
    }

    /**
     * Sends arbitrary key sequences to an element.
     *
     * @param selector element locator
     * @param keys     key sequences
     */
    protected void pressKeys(By selector, CharSequence... keys) {
        logger.info("Pressing keys on element {}", selector);
        findElement(selector).sendKeys(keys);
    }

    /**
     * Sends a global keyboard shortcut (no element focus required).
     *
     * @param keys key sequences (e.g. Keys.chord(Keys.CONTROL, "c"))
     */
    protected void sendGlobalKeys(CharSequence... keys) {
        logger.info("Sending global keys");
        new Actions(driver).sendKeys(keys).perform();
    }

    // ───────────────────── SCROLL ─────────────────────

    /**
     * Scrolls to bring the element into the viewport centre using JavaScript.
     *
     * @param selector element locator
     */
    protected void scrollToElement(By selector) {
        logger.info("Scrolling to element {}", selector);
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(selector));
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    // ───────────────────── FILE UPLOAD ─────────────────────

    /**
     * Uploads a file to an {@code <input type="file">} element.
     *
     * @param selector element locator (must be an input[type=file])
     * @param filePath absolute path to the file
     * @throws IllegalArgumentException if file does not exist
     */
    protected void uploadFile(By selector, String filePath) {
        logger.info("Uploading file: {}", filePath);
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }
        // Do NOT wait for visibility — file inputs are often hidden
        findElementPresent(selector).sendKeys(file.getAbsolutePath());
        logger.info("File uploaded successfully");
    }

    // ───────────────────── CHECKBOX ─────────────────────

    /**
     * Checks whether a checkbox/radio is selected.
     *
     * @param selector element locator
     * @return true if checked
     */
    protected boolean isCheckboxChecked(By selector) {
        return findElement(selector).isSelected();
    }

    /**
     * Sets a checkbox to the desired state (checked or unchecked).
     *
     * @param selector element locator
     * @param check    true = ensure checked, false = ensure unchecked
     */
    protected void setCheckbox(By selector, boolean check) {
        WebElement cb = findElement(selector);
        if (cb.isSelected() != check) {
            logger.info("{} checkbox {}", check ? "Checking" : "Unchecking", selector);
            cb.click();
        }
    }
}
