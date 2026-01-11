package core;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import util.Helper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Level;

import static core.TestSettings.GRID_HUB_URL;

/**
 * DriverManager handles WebDriver lifecycle management following POM best practices.
 * Uses ThreadLocal to ensure thread-safe WebDriver instances for parallel test execution.
 * Implements browser factory pattern for Chrome, Firefox, and Edge browsers.
 */
public class DriverManager extends Helper {
    private boolean isRemote = false;
    private URL hubUrl;
    private static final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    /**
     * Parameterized constructor - allows custom browser type
     * @param browserType Browser to initialize (chrome, firefox, edge)
     */
    public DriverManager(String browserType){
        super();

    }

    public DriverManager() throws MalformedURLException {
        super();
        if(Objects.equals(TestSettings.HUB_TYPE, "NONE")){
            initializeDriver(TestSettings.BROWSER_TYPE);
        }else {
            if (Objects.equals(TestSettings.HUB_TYPE, "GRID")){
                isRemote = true;
                this.hubUrl = java.net.URI.create(GRID_HUB_URL).toURL();
                initializeDriver(TestSettings.BROWSER_TYPE);
            }
        }
    }

    /**
     * Initializes WebDriver instance based on browser type using factory pattern
     * @param browserType Browser to initialize (chrome, firefox, edge)
     */
    private void initializeDriver(String browserType) {
        logger.info("Initializing {} browser", browserType);

        System.setProperty("webdriver.chrome.silentOutput", "true");
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);

        if(webDriver.get() != null){
            logger.warn("WebDriver already initialized for this thread");
            return;
        }

        try {
            WebDriver driver = createDriver(browserType.toLowerCase());
            // driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(TestSettings.IMPLICIT_WAIT));

            WebDriver decoratedDriver = new EventFiringDecorator<WebDriver>(new AutoScrollListener()).decorate(driver);
            webDriver.set(decoratedDriver);
            logger.info("WebDriver initialized successfully for browser: {}", browserType);
        } catch (Exception e) {
            logger.error("Failed to initialize {} browser", browserType, e);
            throw new RuntimeException("Failed to initialize WebDriver for " + browserType, e);
        }

    }

    /**
     * Factory method to create WebDriver instance based on browser type
     * @param browserType Browser type (chrome, firefox, edge)
     * @return WebDriver instance
     * @throws MalformedURLException if remote hub URL is malformed
     */
    private WebDriver createDriver(String browserType) throws MalformedURLException{
        return switch (browserType) {
            case "chrome" -> createChromeDriver();
            case "firefox" -> createFirefoxDriver();
            case "edge" -> createEdgeDriver();
            default -> {
                logger.error("Unsuppported browser type: {}", browserType);
                throw new IllegalArgumentException("Unsupported browser type: " + browserType +
                        ". Supported types: chrome, firefox, edge");
            }
        };
    }

    /**
     * Creates and configures Chrome WebDriver
     * @return Configured ChromeDriver instance
     * @throws MalformedURLException if remote hub URL is malformed (for RemoteWebDriver)
     */
    private WebDriver createChromeDriver() throws MalformedURLException {
        if(!isRemote) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments(String.format("--window-size=%s", TestSettings.SCREEN_RESOLUTION));
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");

            if (TestSettings.HEADLESS) {
                options.addArguments("--headless=new");
                logger.debug("Chrome browser initialized in headless mode");
            }

            logger.debug("Chrome browser initialized with options: {}", options.asMap());
            return new ChromeDriver(options);
        }else {
            // Remote WebDriver initialization can be implemented here
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setPlatform(Platform.WINDOWS);
            capabilities.setBrowserName("chrome");
            // Use RemoteWebDriver to connect to the hub
            return new RemoteWebDriver(this.hubUrl, capabilities);
        }
    }

    /**
     * Creates and configures Firefox WebDriver
     * @return Configured FirefoxDriver instance
     */
    private WebDriver createFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();

        if (TestSettings.HEADLESS) {
            options.addArguments("--headless");
            logger.debug("Firefox browser initialized in headless mode");
        }

        logger.debug("Firefox browser initialized");
        return new FirefoxDriver(options);
    }

    /**
     * Creates and configures Edge WebDriver
     * @return Configured EdgeDriver instance
     */
    private WebDriver createEdgeDriver() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");

        if (TestSettings.HEADLESS) {
            options.addArguments("--headless");
            logger.debug("Edge browser initialized in headless mode");
        }

        logger.debug("Edge browser initialized");
        return new EdgeDriver(options);
    }

    /**
     * Gets the WebDriver instance for the current thread
     * @return WebDriver instance for current thread
     * @throws IllegalStateException if WebDriver is not initialized
     */
    public static WebDriver getDriver() {
        WebDriver driver = webDriver.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver not initialized. Call DriverManager constructor first.");
        }
        return driver;
    }

    /**
     * Sets WebDriver instance for current thread (use with caution)
     * @param driver WebDriver instance to set
     */
    public static void setWebDriver(WebDriver driver) {
        webDriver.set(driver);
    }

    /**
     * Removes WebDriver instance from ThreadLocal for current thread
     * Should be called after quit() to prevent memory leaks
     */
    private static void removeDriver() {
        webDriver.remove();
    }

    /**
     * Navigates to specified URL
     * @param url Target URL to navigate to
     */
    public void navigateTo(String url) {
        logger.info("Navigating to URL: {}", url);
        try {
            getDriver().get(url);
            logger.debug("Navigation to {} successful", url);
        } catch (Exception e) {
            logger.error("Failed to navigate to URL: {}", url, e);
            throw new RuntimeException("Navigation failed to: " + url, e);
        }
    }

    /**
     * Gets the title of the current page
     * @return Page title
     */
    public String getTitle() {
        String title = getDriver().getTitle();
        logger.debug("Current page title: {}", title);
        return title;
    }

    /**
     * Gets the current URL
     * @return Current URL
     */
    public String getCurrentUrl() {
        String url = getDriver().getCurrentUrl();
        logger.debug("Current URL: {}", url);
        return url;
    }

    /**
     * Quits the WebDriver instance and closes all associated windows
     * Also removes WebDriver from ThreadLocal to prevent memory leaks
     */
    public void quit() {
        WebDriver driver = webDriver.get();

        if (driver != null) {
            logger.info("Quitting WebDriver");
            try {
                driver.quit();
                removeDriver();
                logger.debug("WebDriver quit successfully and removed from ThreadLocal");
            } catch (Exception e) {
                logger.error("Error while quitting WebDriver", e);
                // Still try to remove from ThreadLocal even if quit fails
                removeDriver();
            }
        } else {
            logger.warn("Attempted to quit null WebDriver - WebDriver may not have been initialized");
        }
    }
}
