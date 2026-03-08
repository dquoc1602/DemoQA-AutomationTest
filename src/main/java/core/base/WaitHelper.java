package core.base;

import core.TestSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.BooleanSupplier;

/**
 * All wait / synchronisation strategies.
 * Covers explicit waits for element states, page states, AJAX, URL changes, and custom conditions.
 * Reusable across web, app, and mobile projects.
 */
public class WaitHelper extends ElementFinder {

    // ───────────────────── WAIT FACTORY ─────────────────────

    /**
     * Creates a WebDriverWait with the given timeout.
     *
     * @param waitTime timeout in seconds
     * @return WebDriverWait instance
     */
    public WebDriverWait getWait(long waitTime) {
        return new WebDriverWait(this.driver, Duration.ofSeconds(waitTime));
    }

    // ───────────────────── ELEMENT VISIBILITY / CLICKABILITY ─────────────────────

    protected void waitForElementInvisible(By selector) {
        getWait(TestSettings.WAIT_ELEMENT)
                .until(ExpectedConditions.invisibilityOfElementLocated(selector));
    }

    protected void waitForElementInvisible(By selector, long timeoutSeconds) {
        getWait(timeoutSeconds)
                .until(ExpectedConditions.invisibilityOfElementLocated(selector));
    }

    protected WebElement waitForElementClickable(By selector) {
        return getWait(TestSettings.WAIT_ELEMENT)
                .until(ExpectedConditions.elementToBeClickable(selector));
    }

    protected WebElement waitForElementClickable(By selector, long timeoutSeconds) {
        return getWait(timeoutSeconds)
                .until(ExpectedConditions.elementToBeClickable(selector));
    }

    // ───────────────────── ELEMENT NOT PRESENT (removed from DOM) ─────────────────────

    /**
     * Waits until no element matching the selector is present in the DOM.
     *
     * @param selector element locator
     */
    protected void waitForElementNotPresent(By selector) {
        waitForElementNotPresent(selector, TestSettings.WAIT_ELEMENT);
    }

    protected void waitForElementNotPresent(By selector, long timeoutSeconds) {
        logger.debug("Waiting for element {} to be removed from DOM (timeout: {}s)", selector, timeoutSeconds);
        getWait(timeoutSeconds).until(d -> d.findElements(selector).isEmpty());
    }

    // ───────────────────── CUSTOM CONDITION ─────────────────────

    /**
     * Generic wait for any boolean condition.
     * Use for state-based waiting (checked, expanded, text changed, etc.).
     *
     * @param condition supplier returning true when condition is met
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

    // ───────────────────── TEXT / ATTRIBUTE WAITS ─────────────────────

    protected void waitForTextEquals(WebElement element, String expected) {
        waitForCondition(() -> expected.equals(element.getText().trim()));
    }

    protected void waitForClassContains(WebElement element, String className) {
        waitForCondition(() -> {
            String clazz = element.getDomAttribute("class");
            return clazz != null && clazz.contains(className);
        });
    }

    /**
     * Waits until an element's attribute contains a specific value.
     * Essential for verifying "expanded", "checked", etc.
     */
    protected void waitForAttributeContains(By selector, String attribute, String value) {
        logger.info("Waiting for element {} to have attribute '{}' containing '{}'", selector, attribute, value);
        try {
            getWait(TestSettings.WAIT_ELEMENT)
                    .until(ExpectedConditions.attributeContains(selector, attribute, value));
        } catch (Exception e) {
            logger.error("Timeout waiting for attribute '{}' to contain '{}' on element {}", attribute, value, selector);
            throw e;
        }
    }

    /**
     * Waits until the element's text contains the expected substring.
     *
     * @param selector element locator
     * @param text     expected substring
     */
    protected void waitForTextPresent(By selector, String text) {
        logger.debug("Waiting for text '{}' in element {}", text, selector);
        getWait(TestSettings.WAIT_ELEMENT)
                .until(ExpectedConditions.textToBePresentInElementLocated(selector, text));
    }

    /**
     * Waits until the element has non-empty text.
     *
     * @param selector element locator
     */
    protected void waitForTextNotEmpty(By selector) {
        logger.debug("Waiting for non-empty text in element {}", selector);
        getWait(TestSettings.WAIT_ELEMENT)
                .until(d -> {
                    String text = d.findElement(selector).getText();
                    return text != null && !text.trim().isEmpty();
                });
    }

    // ───────────────────── STALENESS ─────────────────────

    /**
     * Waits until the element is stale (removed from DOM or re-rendered).
     * Useful after actions that cause page/component refresh.
     *
     * @param element the element to watch
     */
    protected void waitForElementStaleness(WebElement element) {
        logger.debug("Waiting for element to become stale");
        getWait(TestSettings.WAIT_ELEMENT)
                .until(ExpectedConditions.stalenessOf(element));
    }

    // ───────────────────── COUNT WAITS ─────────────────────

    /**
     * Waits until exactly N elements matching the selector are present.
     *
     * @param selector      element locator
     * @param expectedCount expected number of elements
     */
    protected void waitForNumberOfElements(By selector, int expectedCount) {
        logger.debug("Waiting for {} elements matching {}", expectedCount, selector);
        getWait(TestSettings.WAIT_ELEMENT)
                .until(ExpectedConditions.numberOfElementsToBe(selector, expectedCount));
    }

    /**
     * Waits until at least N elements matching the selector are present.
     *
     * @param selector element locator
     * @param minCount minimum number of elements
     */
    protected void waitForNumberOfElementsMoreThan(By selector, int minCount) {
        logger.debug("Waiting for more than {} elements matching {}", minCount, selector);
        getWait(TestSettings.WAIT_ELEMENT)
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(selector, minCount));
    }

    /**
     * Waits until fewer than N elements matching the selector are present.
     *
     * @param selector element locator
     * @param maxCount maximum number of elements
     */
    protected void waitForNumberOfElementsLessThan(By selector, int maxCount) {
        logger.debug("Waiting for fewer than {} elements matching {}", maxCount, selector);
        getWait(TestSettings.WAIT_ELEMENT)
                .until(ExpectedConditions.numberOfElementsToBeLessThan(selector, maxCount));
    }

    // ───────────────────── PAGE / URL / TITLE WAITS ─────────────────────

    /**
     * Waits until the current URL contains the given text.
     *
     * @param urlFragment expected URL substring
     */
    protected void waitForUrlContains(String urlFragment) {
        logger.debug("Waiting for URL to contain '{}'", urlFragment);
        getWait(TestSettings.WAIT_ELEMENT)
                .until(ExpectedConditions.urlContains(urlFragment));
    }

    /**
     * Waits until the current URL exactly equals the given value.
     *
     * @param url expected full URL
     */
    protected void waitForUrlToBe(String url) {
        logger.debug("Waiting for URL to be '{}'", url);
        getWait(TestSettings.WAIT_ELEMENT)
                .until(ExpectedConditions.urlToBe(url));
    }

    /**
     * Waits until the page title contains the given text.
     *
     * @param titleFragment expected title substring
     */
    protected void waitForTitleContains(String titleFragment) {
        logger.debug("Waiting for title to contain '{}'", titleFragment);
        getWait(TestSettings.WAIT_ELEMENT)
                .until(ExpectedConditions.titleContains(titleFragment));
    }

    /**
     * Waits until the page title exactly equals the given value.
     *
     * @param title expected exact title
     */
    protected void waitForTitleIs(String title) {
        logger.debug("Waiting for title to be '{}'", title);
        getWait(TestSettings.WAIT_ELEMENT)
                .until(ExpectedConditions.titleIs(title));
    }

    /**
     * Waits until document.readyState is "complete".
     */
    protected void waitForPageLoadComplete() {
        waitForPageLoadComplete(TestSettings.PAGE_LOAD_TIMEOUT);
    }

    protected void waitForPageLoadComplete(long timeoutSeconds) {
        logger.debug("Waiting for page load complete (timeout: {}s)", timeoutSeconds);
        getWait(timeoutSeconds)
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Waits until jQuery AJAX requests are complete.
     * Silently returns if jQuery is not present on the page.
     */
    protected void waitForAjaxComplete() {
        waitForAjaxComplete(TestSettings.WAIT_ELEMENT);
    }

    protected void waitForAjaxComplete(long timeoutSeconds) {
        logger.debug("Waiting for AJAX complete (timeout: {}s)", timeoutSeconds);
        try {
            getWait(timeoutSeconds)
                    .until(d -> {
                        try {
                            return (Boolean) ((JavascriptExecutor) d)
                                    .executeScript("return (typeof jQuery === 'undefined') || (jQuery.active === 0)");
                        } catch (Exception e) {
                            return true; // jQuery not available — treat as complete
                        }
                    });
        } catch (TimeoutException e) {
            logger.warn("AJAX did not complete within {}s", timeoutSeconds);
        }
    }

    // ───────────────────── STATIC SLEEP (DEBUG ONLY) ─────────────────────

    /**
     * Hard sleep — use ONLY for debugging, never in production tests.
     *
     * @param milliseconds time to sleep
     */
    @SuppressWarnings("java:S2925") // intentional Thread.sleep
    protected void sleep(long milliseconds) {
        logger.warn("Using Thread.sleep({}ms) — this should not be in production tests!", milliseconds);
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Sleep interrupted", e);
        }
    }
}
