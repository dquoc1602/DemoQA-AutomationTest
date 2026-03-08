package core.base;

import org.openqa.selenium.JavascriptExecutor;

/**
 * Browser storage utilities: localStorage and sessionStorage.
 * Future-proof — useful for SPA testing, token management, and state debugging.
 * Reusable across web, app, and mobile projects.
 */
public class StorageHelper extends TableHelper {

    // ═══════════════════ LOCAL STORAGE ═══════════════════

    /**
     * Sets a key-value pair in localStorage.
     *
     * @param key   storage key
     * @param value storage value
     */
    protected void setLocalStorageItem(String key, String value) {
        logger.info("Setting localStorage item: {}={}", key, value);
        ((JavascriptExecutor) driver).executeScript(
                "window.localStorage.setItem(arguments[0], arguments[1]);", key, value);
    }

    /**
     * Gets a value from localStorage.
     *
     * @param key storage key
     * @return value, or null if key doesn't exist
     */
    protected String getLocalStorageItem(String key) {
        return (String) ((JavascriptExecutor) driver).executeScript(
                "return window.localStorage.getItem(arguments[0]);", key);
    }

    /**
     * Removes a key from localStorage.
     *
     * @param key storage key
     */
    protected void removeLocalStorageItem(String key) {
        logger.info("Removing localStorage item: {}", key);
        ((JavascriptExecutor) driver).executeScript(
                "window.localStorage.removeItem(arguments[0]);", key);
    }

    /**
     * Clears all localStorage data.
     */
    protected void clearLocalStorage() {
        logger.info("Clearing localStorage");
        ((JavascriptExecutor) driver).executeScript("window.localStorage.clear();");
    }

    /**
     * Returns the number of items in localStorage.
     *
     * @return item count
     */
    protected long getLocalStorageLength() {
        return (long) ((JavascriptExecutor) driver).executeScript("return window.localStorage.length;");
    }

    /**
     * Checks whether a key exists in localStorage.
     *
     * @param key storage key
     * @return true if the key exists
     */
    protected boolean isLocalStorageKeyPresent(String key) {
        return getLocalStorageItem(key) != null;
    }

    // ═══════════════════ SESSION STORAGE ═══════════════════

    /**
     * Sets a key-value pair in sessionStorage.
     *
     * @param key   storage key
     * @param value storage value
     */
    protected void setSessionStorageItem(String key, String value) {
        logger.info("Setting sessionStorage item: {}={}", key, value);
        ((JavascriptExecutor) driver).executeScript(
                "window.sessionStorage.setItem(arguments[0], arguments[1]);", key, value);
    }

    /**
     * Gets a value from sessionStorage.
     *
     * @param key storage key
     * @return value, or null if key doesn't exist
     */
    protected String getSessionStorageItem(String key) {
        return (String) ((JavascriptExecutor) driver).executeScript(
                "return window.sessionStorage.getItem(arguments[0]);", key);
    }

    /**
     * Removes a key from sessionStorage.
     *
     * @param key storage key
     */
    protected void removeSessionStorageItem(String key) {
        logger.info("Removing sessionStorage item: {}", key);
        ((JavascriptExecutor) driver).executeScript(
                "window.sessionStorage.removeItem(arguments[0]);", key);
    }

    /**
     * Clears all sessionStorage data.
     */
    protected void clearSessionStorage() {
        logger.info("Clearing sessionStorage");
        ((JavascriptExecutor) driver).executeScript("window.sessionStorage.clear();");
    }

    /**
     * Returns the number of items in sessionStorage.
     *
     * @return item count
     */
    protected long getSessionStorageLength() {
        return (long) ((JavascriptExecutor) driver).executeScript("return window.sessionStorage.length;");
    }

    /**
     * Checks whether a key exists in sessionStorage.
     *
     * @param key storage key
     * @return true if the key exists
     */
    protected boolean isSessionStorageKeyPresent(String key) {
        return getSessionStorageItem(key) != null;
    }

    // ═══════════════════ CLEAR ALL ═══════════════════

    /**
     * Clears both localStorage and sessionStorage.
     */
    protected void clearAllBrowserStorage() {
        logger.info("Clearing all browser storage");
        clearLocalStorage();
        clearSessionStorage();
    }
}
