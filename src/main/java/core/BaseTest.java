package core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import util.Helper;

import java.net.MalformedURLException;

public class BaseTest extends Helper {
    protected DriverManager driverManager;

    @BeforeEach
    public void setup(TestInfo testInfo) throws MalformedURLException {
        logger.info("========================================");
        logger.info("Starting test: {}", testInfo.getDisplayName());
        logger.info("Test class: {}", testInfo.getTestClass().orElse(null));
        logger.info("Environment: {}", TestSettings.TEST_ENV);
        logger.info("Browser: {}", TestSettings.BROWSER_TYPE);
        logger.info("========================================");

        try {
            driverManager = new DriverManager();
            logger.info("WebDriver initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver", e);
            throw e;
        }
    }

    /**
     * Teardown executed after each test method.
     * Quits WebDriver and logs test completion.
     *
     * @param testInfo JUnit 5 test metadata
     */
    @AfterEach
    public void teardown(TestInfo testInfo) {
        try {
            if (driverManager != null) {
                driverManager.quit();
                logger.info("WebDriver quit successfully");
            }
        } catch (Exception e) {
            logger.error("Error during test teardown", e);
        }

        logger.info("Test completed: {}", testInfo.getDisplayName());
        logger.info("========================================\n");
    }
}
