package core.base;

import org.openqa.selenium.JavascriptExecutor;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
// import java.net.URL;
// import java.util.Map;

/**
 * Network and HTTP utilities for API validation, link checking, and performance monitoring.
 * Future-proof — useful for broken link tests, network condition simulation, and response validation.
 * Reusable across web, app, and mobile projects.
 */
public class NetworkHelper extends StorageHelper {

    // ═══════════════════ HTTP STATUS ═══════════════════

    /**
     * Sends an HTTP GET request and returns the response code.
     * Useful for broken-link tests and API health checks.
     *
     * @param url the URL to check
     * @return HTTP status code (200, 404, 500, etc.), or -1 if connection fails
     */
    protected int getHttpResponseCode(String url) {
        logger.info("Checking HTTP response code for: {}", url);
        try {
            HttpURLConnection connection = (HttpURLConnection) URI.create(url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10_000);
            connection.setReadTimeout(10_000);
            connection.setInstanceFollowRedirects(true);
            connection.connect();
            int code = connection.getResponseCode();
            logger.info("HTTP {} for URL: {}", code, url);
            connection.disconnect();
            return code;
        } catch (IOException e) {
            logger.error("Failed to connect to URL: {}", url, e);
            return -1;
        }
    }

    /**
     * Checks whether a URL returns a successful HTTP response (2xx).
     *
     * @param url the URL to check
     * @return true if status code is 2xx
     */
    protected boolean isUrlReachable(String url) {
        int code = getHttpResponseCode(url);
        return code >= 200 && code < 300;
    }

    /**
     * Checks whether a URL returns HTTP 200 OK.
     *
     * @param url the URL to check
     * @return true if status is 200
     */
    protected boolean isUrlOk(String url) {
        return getHttpResponseCode(url) == 200;
    }

    /**
     * Checks if a URL is a broken link (4xx or 5xx response, or unreachable).
     *
     * @param url the URL to check
     * @return true if status code >= 400 or connection fails
     */
    protected boolean isBrokenLink(String url) {
        int code = getHttpResponseCode(url);
        return code < 0 || code >= 400;
    }

    // ═══════════════════ PERFORMANCE TIMING ═══════════════════

    /**
     * Returns the page load time in milliseconds using the Performance API.
     *
     * @return page load duration in ms
     */
    protected long getPageLoadTimeMs() {
        try {
            Object result = ((JavascriptExecutor) driver).executeScript(
                    "var perf = window.performance.timing;"
                            + "return perf.loadEventEnd - perf.navigationStart;");
            return ((Number) result).longValue();
        } catch (Exception e) {
            logger.warn("Could not measure page load time", e);
            return -1;
        }
    }

    /**
     * Returns the DOM content loaded time in milliseconds.
     *
     * @return DOM content loaded duration in ms
     */
    protected long getDomContentLoadedTimeMs() {
        try {
            Object result = ((JavascriptExecutor) driver).executeScript(
                    "var perf = window.performance.timing;"
                            + "return perf.domContentLoadedEventEnd - perf.navigationStart;");
            return ((Number) result).longValue();
        } catch (Exception e) {
            logger.warn("Could not measure DOM content loaded time", e);
            return -1;
        }
    }

    /**
     * Returns the server response time in milliseconds (TTFB - Time To First Byte).
     *
     * @return TTFB in ms
     */
    protected long getServerResponseTimeMs() {
        try {
            Object result = ((JavascriptExecutor) driver).executeScript(
                    "var perf = window.performance.timing;"
                            + "return perf.responseStart - perf.requestStart;");
            return ((Number) result).longValue();
        } catch (Exception e) {
            logger.warn("Could not measure server response time", e);
            return -1;
        }
    }

    // ═══════════════════ CONSOLE LOGS ═══════════════════

    /**
     * Returns the number of JavaScript errors in the browser console.
     * Note: requires the browser to have logging capabilities enabled.
     *
     * @return count of SEVERE-level log entries
     */
    protected long getJsErrorCount() {
        try {
            return driver.manage().logs().get("browser").getAll().stream()
                    .filter(entry -> entry.getLevel().getName().equals("SEVERE"))
                    .count();
        } catch (Exception e) {
            logger.warn("Could not retrieve browser console logs", e);
            return -1;
        }
    }
}
