package core.base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

/**
 * JavaScript execution helpers.
 * Provides raw JS execution plus convenience methods for scrolling, highlighting,
 * attribute manipulation, DOM modification, and clicking obscured elements.
 * Reusable across web, app, and mobile projects.
 */
public class JavaScriptHelper extends ElementInteraction {

    // ───────────────────── RAW EXECUTION ─────────────────────

    protected void executeJavaScript(String script) {
        logger.info("Executing JavaScript: {}", script);
        ((JavascriptExecutor) driver).executeScript(script);
    }

    protected void executeJavaScript(String script, Object... args) {
        logger.info("Executing JavaScript with {} arguments", args.length);
        ((JavascriptExecutor) driver).executeScript(script, args);
    }

    /**
     * Executes JavaScript and returns the result.
     *
     * @param script JS code
     * @param args   script arguments
     * @return script return value
     */
    @SuppressWarnings("unchecked")
    protected <T> T executeJavaScriptReturn(String script, Object... args) {
        logger.info("Executing JavaScript with return value");
        return (T) ((JavascriptExecutor) driver).executeScript(script, args);
    }

    // ───────────────────── JS CLICK (for obscured elements) ─────────────────────

    /**
     * Clicks an element via JavaScript — bypasses visibility and overlap issues.
     *
     * @param selector element locator
     */
    protected void jsClick(By selector) {
        logger.info("JS clicking element {}", selector);
        WebElement element = findElementPresent(selector);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    /**
     * Clicks a WebElement via JavaScript.
     *
     * @param element the WebElement to click
     */
    protected void jsClick(WebElement element) {
        logger.info("JS clicking WebElement");
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // ───────────────────── JS VALUE GET / SET ─────────────────────

    /**
     * Sets an input's value directly via JavaScript.
     * Useful for date-pickers or inputs that ignore sendKeys.
     *
     * @param selector element locator
     * @param value    value to set
     */
    protected void jsSetValue(By selector, String value) {
        logger.info("JS setting value '{}' on element {}", value, selector);
        WebElement element = findElementPresent(selector);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value=arguments[1];"
                        + "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));"
                        + "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));",
                element, value);
    }

    /**
     * Gets an input's value via JavaScript.
     *
     * @param selector element locator
     * @return the element's value property
     */
    protected String jsGetValue(By selector) {
        WebElement element = findElementPresent(selector);
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value;", element);
    }

    // ───────────────────── JS TEXT RETRIEVAL ─────────────────────

    /**
     * Returns innerText via JavaScript (strips HTML).
     *
     * @param selector element locator
     * @return the innerText
     */
    protected String jsGetInnerText(By selector) {
        WebElement element = findElementPresent(selector);
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerText;", element);
    }

    /**
     * Returns innerHTML via JavaScript (includes HTML).
     *
     * @param selector element locator
     * @return the innerHTML
     */
    protected String jsGetInnerHTML(By selector) {
        WebElement element = findElementPresent(selector);
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML;", element);
    }

    // ───────────────────── SCROLL ─────────────────────

    /**
     * Scrolls the page to the very top.
     */
    protected void jsScrollToTop() {
        logger.info("Scrolling to top of page");
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    /**
     * Scrolls the page to the very bottom.
     */
    protected void jsScrollToBottom() {
        logger.info("Scrolling to bottom of page");
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    /**
     * Scrolls by a given pixel amount.
     *
     * @param x horizontal pixels (positive = right)
     * @param y vertical pixels (positive = down)
     */
    protected void jsScrollByPixels(int x, int y) {
        logger.info("Scrolling by ({}, {}) pixels", x, y);
        ((JavascriptExecutor) driver).executeScript(String.format("window.scrollBy(%d, %d);", x, y));
    }

    // ───────────────────── ATTRIBUTE MANIPULATION ─────────────────────

    /**
     * Sets an HTML attribute via JavaScript.
     *
     * @param selector      element locator
     * @param attributeName attribute name
     * @param value         attribute value
     */
    protected void jsSetAttribute(By selector, String attributeName, String value) {
        logger.info("JS setting attribute '{}' = '{}' on element {}", attributeName, value, selector);
        WebElement element = findElementPresent(selector);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].setAttribute(arguments[1], arguments[2]);", element, attributeName, value);
    }

    /**
     * Removes an HTML attribute via JavaScript.
     *
     * @param selector      element locator
     * @param attributeName attribute name
     */
    protected void jsRemoveAttribute(By selector, String attributeName) {
        logger.info("JS removing attribute '{}' from element {}", attributeName, selector);
        WebElement element = findElementPresent(selector);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].removeAttribute(arguments[1]);", element, attributeName);
    }

    // ───────────────────── DOM MANIPULATION ─────────────────────

    /**
     * Removes an element from the DOM.
     *
     * @param selector element locator
     */
    protected void jsRemoveElement(By selector) {
        logger.info("JS removing element {} from DOM", selector);
        WebElement element = findElementPresent(selector);
        ((JavascriptExecutor) driver).executeScript("arguments[0].remove();", element);
    }

    /**
     * Triggers a DOM event on an element (e.g. "click", "change", "focus", "blur").
     *
     * @param selector  element locator
     * @param eventName DOM event name
     */
    protected void jsTriggerEvent(By selector, String eventName) {
        logger.info("JS triggering event '{}' on element {}", eventName, selector);
        WebElement element = findElementPresent(selector);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event(arguments[1], {bubbles: true}));",
                element, eventName);
    }

    // ───────────────────── VISUAL DEBUG ─────────────────────

    /**
     * Temporarily highlights an element with a red border (debug purposes).
     *
     * @param selector element locator
     */
    protected void jsHighlightElement(By selector) {
        logger.info("Highlighting element {}", selector);
        WebElement element = findElementPresent(selector);
        ((JavascriptExecutor) driver).executeScript(
                "var el=arguments[0]; var orig=el.style.border;"
                        + "el.style.border='3px solid red';"
                        + "setTimeout(function(){el.style.border=orig;}, 2000);",
                element);
    }

    // ───────────────────── PAGE STATE ─────────────────────

    /**
     * Checks whether the page has fully loaded (document.readyState === 'complete').
     *
     * @return true if loaded
     */
    protected boolean jsIsPageLoaded() {
        return "complete".equals(
                ((JavascriptExecutor) driver).executeScript("return document.readyState"));
    }

    /**
     * Returns the current vertical scroll position.
     *
     * @return scroll Y in pixels
     */
    protected long jsGetScrollY() {
        return (long) ((JavascriptExecutor) driver).executeScript("return window.pageYOffset;");
    }

    /**
     * Returns the total page height.
     *
     * @return page height in pixels
     */
    protected long jsGetPageHeight() {
        return (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight;");
    }
}
