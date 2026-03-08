package core;

import core.base.NetworkHelper;

/**
 * Base page object providing comprehensive test automation capabilities.
 * All methods are inherited via the component chain in {@code core.base.*}.
 *
 * <p>Inheritance chain:</p>
 * <pre>
 * Helper
 *   └── ElementFinder       (find / check / count elements)
 *       └── WaitHelper      (explicit waits, page load, AJAX, staleness)
 *           └── ElementInteraction  (click, type, hover, drag, keyboard, upload)
 *               └── JavaScriptHelper  (JS execution, scroll, highlight, attributes)
 *                   └── WindowManager  (alerts, windows, frames, cookies, screenshots)
 *                       └── WebElementInteraction  (direct WebElement operations)
 *                           └── DropdownHelper  (select/deselect dropdowns)
 *                               └── TableHelper  (HTML table utilities)
 *                                   └── StorageHelper  (localStorage / sessionStorage)
 *                                       └── NetworkHelper  (HTTP checks, performance timing)
 *                                           └── Basepage  (navigation, dynamic XPath)
 * </pre>
 *
 * <p>Page classes extend {@code Basepage} to inherit ALL capabilities.</p>
 */
public class BasePage extends NetworkHelper {

    public BasePage() {
        // driver is initialized in ElementFinder via DriverManager.getDriver()
    }

    // ═══════════════════ NAVIGATION ═══════════════════

    /**
     * Opens the base URL configured in TestSettings.
     */
    public void openSite() {
        logger.info("Navigating to URL: {}", TestSettings.BASE_URL);
        this.driver.get(TestSettings.BASE_URL);
        logger.info("Navigation to URL: {} completed", TestSettings.BASE_URL);
    }

    /**
     * Opens a specific URL.
     *
     * @param url the URL to navigate to
     */
    public void openSite(String url) {
        logger.info("Navigating to URL: {}", url);
        this.driver.get(url);
        logger.info("Navigation to URL: {} completed", url);
    }

    // ═══════════════════ UTILITY ═══════════════════

    /**
     * Creates a dynamic XPath {@link org.openqa.selenium.By} from a template.
     * Reduces boilerplate in Page classes for parameterised locators.
     *
     * @param xpathTemplate a format string with {@code %s} placeholders
     * @param args           values to substitute
     * @return By.xpath with substituted values
     */
    protected org.openqa.selenium.By getDynamicXpath(String xpathTemplate, String... args) {
        String xpath = String.format(xpathTemplate, (Object[]) args);
        return org.openqa.selenium.By.xpath(xpath);
    }
}
