package core;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.Helper;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

public class Basepage extends Helper {
    private String crrWindow;
    protected WebDriver driver;

    public Basepage() {
        driver = DriverManager.getDriver();
    }

    public void openSite() {
        logger.info("Navigating to URL: {}", TestSettings.BASE_URL);
        this.driver.get(TestSettings.BASE_URL);
        logger.info("Navigation to URL: {} completed", TestSettings.BASE_URL);
    }

    public void openSite(String url){
        logger.info("Navigating to URL: {}", url);
        this.driver.get(url);
        logger.info("Navigation to URL: {} completed", url);
    }

    public WebElement findElement(By selector){
        return getWait(TestSettings.WAIT_ELEMENT).until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

    /**
     * Finds all elements matching selector.
     * Returns empty list if none found (NO exception).
     * Used for tree traversal, radio groups, dynamic lists.
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

    public WebElement findElementPresent(By selector) {
        logger.debug("Finding element by presence: {}", selector);
        return getWait(TestSettings.WAIT_ELEMENT)
                .until(ExpectedConditions.presenceOfElementLocated(selector));
    }


    public WebDriverWait getWait(long waitTime) {
        return new WebDriverWait(this.driver, Duration.ofSeconds(waitTime));
    }

    protected void waitForElementInvisible(By selector) {
        getWait(TestSettings.WAIT_ELEMENT).until(ExpectedConditions.invisibilityOfElementLocated(selector));
    }

    private WebElement waitForElementClickable(By selector) {
        return getWait(TestSettings.WAIT_ELEMENT).until(ExpectedConditions.elementToBeClickable(selector));
    }

    /**
     * Generic wait for custom condition.
     * Used for state-based waiting (checked, expanded, text changed, etc.)
     */
    protected void waitForCondition(BooleanSupplier condition) {
        waitForCondition(condition, TestSettings.WAIT_ELEMENT);
    }

    protected void waitForCondition(BooleanSupplier condition, long timeoutSeconds) {
        logger.debug("Waiting for custom condition (timeout: {}s)", timeoutSeconds);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(d -> {
                        try {
                            return condition.getAsBoolean();
                        } catch (Exception e) {
                            logger.trace("Condition evaluation failed, retrying...", e);
                            return false;
                        }
                    });
            logger.debug("Condition satisfied");
        } catch (TimeoutException e) {
            logger.error("Timeout waiting for custom condition");
            throw e;
        }
    }

    protected void waitForTextEquals(WebElement element, String expected) {
        waitForCondition(() -> expected.equals(element.getText().trim()));
    }

    protected void waitForClassContains(WebElement element, String className) {
        waitForCondition(() -> {
            String clazz = element.getDomAttribute("class");
            return clazz != null && clazz.contains(className);
        });
    }


    protected void enterText(By selector, String text){
        logger.info("Entering text {}", text);
        findElement(selector).sendKeys(text);
    }

    protected void replaceText(By selector, String text) {
        logger.info("Replacing text '{}' in element {}", text, selector);
        WebElement element = findElement(selector);
        element.clear();
        element.sendKeys(text);
        logger.info("Text replaced successfully");
    }

    protected String getValueAfterColon(By selector) {
        String rawText = getElementText(selector);
        logger.info("Raw output text: {}", rawText);

        if (!rawText.contains(":")) {
            return rawText.trim();
        }

        return rawText.substring(rawText.indexOf(":") + 1).trim();
    }

    protected void enterTextWithoutWait(By selector, String text){
        logger.info("Entering text: {}", text);
        this.driver.findElement(selector).sendKeys(text);
    }

    protected String getElementAttribute(By selector, String attributeName) {
        logger.info("Getting attribute {} from element {}", attributeName, selector);
        return findElement(selector).getDomAttribute(attributeName);
    }

    protected String getElementValue(By selector){
        logger.info("Getting value from element {}", selector);
        WebElement element = findElement(selector);
        return element.getText().isEmpty() ? element.getDomProperty("value") : element.getText();
    }

    protected String getElementText(By selector){
        String text = findElement(selector).getText();
        logger.info("Retrieved text '{}' from element {}", text, selector);
        return text;
    }

    protected void clickButton(By selector){
        logger.info("Clicking button {}", selector);
        waitForElementClickable(selector).click();
    }

    protected void executeJavaScript(String script) {
        logger.info("Executing JavaScript: {}", script);
        JavascriptExecutor js = (JavascriptExecutor) this.driver;
        js.executeScript(script);
    }

    protected void verifyTrue(boolean condition, String message) {
        logger.info("Verifying condition is true");
        assertTrue(condition, message);
    }

    protected void verifyFalse(boolean condition, String message) {
        logger.info("Verifying condition is false");
        assertFalse(condition, message);
    }

    protected void verifyEquals(Object expected, Object actual, String message) {
        logger.info("Verifying equality of expected and actual values");
        assertEquals(expected, actual, message);
    }

    public void verifyTitle(String expectedTitle) {
        logger.info("Verifying page title is: {}", expectedTitle);
        String actualTitle = this.driver.getTitle();
        verifyEquals(expectedTitle, actualTitle, String.format("Expected title '%s' but found '%s'", expectedTitle, actualTitle));
    }

    protected void verifyElementVisible(By selector, String errorMessage) {
        logger.info("Verifying visibility of element {}", selector);
        try {
            getWait(TestSettings.WAIT_ELEMENT).until(ExpectedConditions.visibilityOfElementLocated(selector));
            logger.info("Element {} is visible", selector);
        } catch (Exception e) {
            logger.error("Element {} is not visible: {}", selector, errorMessage);
            throw new AssertionError(errorMessage);
        }
    }

    protected void hoverElement(By selector) {
        logger.info("Hovering over element {}", selector);
        WebElement element = findElement(selector);
        // Init action object
        Actions actions = new Actions(this.driver);

        // Perform hover action
        actions.moveToElement(element).perform();
    }

    protected void dragAndDrop(By sourceEleBy, By targetEleBy) {
        logger.info("Dragging element from {} to {}", sourceEleBy, targetEleBy);
        WebElement sourceElement = findElement(sourceEleBy);
        WebElement targetElement = findElement(targetEleBy);

        // Init action object
        Actions actions = new Actions(this.driver);

        // Perform drag and drop action
        actions.dragAndDrop(sourceElement, targetElement).perform();
    }

    protected void acceptAlertAction(Alert alert) {
        logger.info("Accepting alert");
        alert.accept();
    }
    protected void dismissAlertAction(Alert alert) {
        logger.info("Dismissing alert");
        alert.dismiss();
    }

    protected Alert switchToAlert() {
        logger.info("Switching to alert");
        return this.driver.switchTo().alert();
    }

    protected WebDriver swithToNewWindow(){
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

    public void switchBackToOriginalWindow() {
        logger.info("Switching back to original window: {}", this.crrWindow);
        Set<String> arrString = this.driver.getWindowHandles();
        for (String windowHandle : arrString) {
            if (!windowHandle.equals(this.crrWindow)) {
                this.driver.switchTo().window(windowHandle);
                logger.info("Switched to new window: {}", windowHandle);
                this.driver.close();
            }
        }
        this.driver.switchTo().window(this.crrWindow);
    }

    /**
     * Waits for an element's attribute to contain a specific value.
     * Essential for verifying state changes like "expanded" or "checked" in dynamic components.
     */
    protected void waitForAttributeContains(By selector, String attribute, String value) {
        logger.info("Waiting for element {} to have attribute '{}' containing '{}'", selector, attribute, value);
        try {
            getWait(TestSettings.WAIT_ELEMENT).until(ExpectedConditions.attributeContains(selector, attribute, value));
        } catch (Exception e) {
            logger.error("Timeout waiting for attribute '{}' to contain '{}' on element {}", attribute, value, selector);
            throw e;
        }
    }

    /**
     * Explicitly scrolls to an element using JavaScript.
     * Useful for large trees where nodes might be out of viewport.
     */
    protected void scrollToElement(By selector) {
        logger.info("Scrolling to element {}", selector);
    
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(selector));
    
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    /**
     * Helper to create a dynamic XPath By object based on a template.
     * Reduces boilerplate code in Page classes.
     */
    protected By getDynamicXpath(String xpathTemplate, String... args) {
        String xpath = String.format(xpathTemplate, (Object[]) args);
        return By.xpath(xpath);
    }

    // ================= WEBELEMENT HELPER METHODS =================

    /**
     * Scrolls to a WebElement using JavaScript with default center alignment.
     * Used when working with WebElement objects directly instead of By selectors.
     * 
     * @param element The WebElement to scroll to
     */
    protected void scrollToWebElement(WebElement element) {
        scrollToWebElement(element, "center", "center");
    }

    /**
     * Scrolls to a WebElement using JavaScript with custom alignment options.
     * 
     * @param element The WebElement to scroll to
     * @param blockVertical Vertical alignment: "start", "center", "end", or "nearest"
     * @param inlineHorizontal Horizontal alignment: "start", "center", "end", or "nearest"
     */
    protected void scrollToWebElement(WebElement element, String blockVertical, String inlineHorizontal) {
        logger.info("Scrolling to WebElement with block: '{}', inline: '{}'", blockVertical, inlineHorizontal);
        String script = String.format("arguments[0].scrollIntoView({block:'%s', inline:'%s'});", 
                blockVertical, inlineHorizontal);
        ((JavascriptExecutor) driver).executeScript(script, element);
    }

    /**
     * Waits for a WebElement's attribute to contain a specific value.
     * Uses default wait time from TestSettings.WAIT_ELEMENT.
     * 
     * @param element The WebElement to wait for
     * @param attribute The attribute name to check
     * @param value The value that should be contained in the attribute
     */
    protected void waitForWebElementAttributeContains(WebElement element, String attribute, String value) {
        waitForWebElementAttributeContains(element, attribute, value, TestSettings.WAIT_ELEMENT);
    }

    /**
     * Waits for a WebElement's attribute to contain a specific value with custom timeout.
     * 
     * @param element The WebElement to wait for
     * @param attribute The attribute name to check
     * @param value The value that should be contained in the attribute
     * @param timeoutSeconds Custom timeout in seconds
     */
    protected void waitForWebElementAttributeContains(WebElement element, String attribute, String value, long timeoutSeconds) {
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

    /**
     * Waits for a WebElement's attribute to not contain a specific value.
     * Uses default wait time from TestSettings.WAIT_ELEMENT.
     * 
     * @param element The WebElement to wait for
     * @param attribute The attribute name to check
     * @param value The value that should not be contained in the attribute
     */
    protected void waitForWebElementAttributeNotContains(WebElement element, String attribute, String value) {
        waitForWebElementAttributeNotContains(element, attribute, value, TestSettings.WAIT_ELEMENT);
    }

    /**
     * Waits for a WebElement's attribute to not contain a specific value with custom timeout.
     * 
     * @param element The WebElement to wait for
     * @param attribute The attribute name to check
     * @param value The value that should not be contained in the attribute
     * @param timeoutSeconds Custom timeout in seconds
     */
    protected void waitForWebElementAttributeNotContains(WebElement element, String attribute, String value, long timeoutSeconds) {
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

    /**
     * Waits for a WebElement to be visible.
     * Uses default wait time from TestSettings.WAIT_ELEMENT.
     * 
     * @param element The WebElement to wait for
     * @return The visible WebElement
     */
    protected WebElement waitForWebElementVisible(WebElement element) {
        return waitForWebElementVisible(element, TestSettings.WAIT_ELEMENT);
    }

    /**
     * Waits for a WebElement to be visible with custom timeout.
     * 
     * @param element The WebElement to wait for
     * @param timeoutSeconds Custom timeout in seconds
     * @return The visible WebElement
     */
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

    /**
     * Waits for a WebElement to be clickable.
     * Uses default wait time from TestSettings.WAIT_ELEMENT.
     * 
     * @param element The WebElement to wait for
     * @return The clickable WebElement
     */
    protected WebElement waitForWebElementClickable(WebElement element) {
        return waitForWebElementClickable(element, TestSettings.WAIT_ELEMENT);
    }

    /**
     * Waits for a WebElement to be clickable with custom timeout.
     * 
     * @param element The WebElement to wait for
     * @param timeoutSeconds Custom timeout in seconds
     * @return The clickable WebElement
     */
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

    /**
     * Waits for a WebElement to be invisible.
     * Uses default wait time from TestSettings.WAIT_ELEMENT.
     * 
     * @param element The WebElement to wait for
     */
    protected void waitForWebElementInvisible(WebElement element) {
        waitForWebElementInvisible(element, TestSettings.WAIT_ELEMENT);
    }

    /**
     * Waits for a WebElement to be invisible with custom timeout.
     * 
     * @param element The WebElement to wait for
     * @param timeoutSeconds Custom timeout in seconds
     */
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

    /**
     * Gets a WebElement's attribute value using getDomAttribute (non-deprecated method).
     * 
     * @param element The WebElement to get attribute from
     * @param attributeName The name of the attribute
     * @return The attribute value, or null if not found
     */
    protected String getWebElementAttribute(WebElement element, String attributeName) {
        logger.info("Getting attribute '{}' from WebElement", attributeName);
        String value = element.getDomAttribute(attributeName);
        logger.info("Retrieved attribute '{}' value: '{}'", attributeName, value);
        return value;
    }

    /**
     * Gets a WebElement's text content.
     * 
     * @param element The WebElement to get text from
     * @return The text content of the element
     */
    protected String getWebElementText(WebElement element) {
        logger.info("Getting text from WebElement");
        String text = element.getText();
        logger.info("Retrieved text: '{}'", text);
        return text;
    }

    /**
     * Clicks a WebElement after ensuring it's visible and clickable.
     * Scrolls to element first if needed.
     * 
     * @param element The WebElement to click
     */
    protected void clickWebElement(WebElement element) {
        logger.info("Clicking WebElement");
        scrollToWebElement(element);
        WebElement clickableElement = waitForWebElementClickable(element);
        clickableElement.click();
        logger.info("WebElement clicked successfully");
    }

    /**
     * Enters text into a WebElement after ensuring it's visible.
     * Scrolls to element first if needed.
     * 
     * @param element The WebElement to enter text into
     * @param text The text to enter
     */
    protected void enterTextToWebElement(WebElement element, String text) {
        logger.info("Entering text '{}' into WebElement", text);
        scrollToWebElement(element);
        WebElement visibleElement = waitForWebElementVisible(element);
        visibleElement.clear();
        visibleElement.sendKeys(text);
        logger.info("Text entered successfully");
    }
}
