package core;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.ITestResult;
import java.lang.reflect.Method;
import common.Helper;

import java.net.MalformedURLException;

public class BaseTest extends Helper {
    protected DriverManager driverManager;

    @BeforeMethod
    public void setup(Method method) throws MalformedURLException {
        logger.info("========================================");
        logger.info("Starting test: {}", method.getName());
        logger.info("Test class: {}", method.getDeclaringClass());
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
     * @param result TestNG test result
     */
    @AfterMethod
    public void teardown(ITestResult result) {
        try {
            if (driverManager != null) {
                driverManager.quit();
                logger.info("WebDriver quit successfully");
            }
        } catch (Exception e) {
            logger.error("Error during test teardown", e);
        }

        logger.info("Test completed: {}", result.getName());
        logger.info("========================================\n");
    }
}
