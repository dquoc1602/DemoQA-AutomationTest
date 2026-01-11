package core;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;
import util.Helper;
import static util.Constants.*;
import java.util.Objects;

/**
 * TestSettings manages centralized configuration from .env file, system properties, and TestData.json.
 * Priority order: System Properties > .env file > Hardcoded defaults
 *
 * Usage:
 * - mvn clean test -Denv=GURU -Dbrowser=chrome -DhubType=NONE
 */
public class TestSettings {
    private static final Dotenv DOTENV = Dotenv.configure().ignoreIfMissing().load();

    //ENVIRONMENT SETTINGS
    /** Test environment (DEMOQA, GURU, APPLITOOLS, etc.) - Usage: mvn clean test -Denv=DEMOQA */
    public static final String TEST_ENV = System.getProperty("env", DOTENV.get("TEST_ENV", "DEMOQA"));

    /** Environment configuration loaded from TestData.json */
    public static final JSONObject ENV_CONFIG = Objects.requireNonNull(Helper.loadJsonFile(JSON_DATA_PATH)).getJSONObject(TEST_ENV);

    /** Base URL for the test environment */
    public static final String BASE_URL = ENV_CONFIG.getString("base_url");

    // BROWSER SETTINGS
    /** Browser type (chrome, firefox, edge) - Usage: mvn clean test -Dbrowser=chrome */
    public static final String BROWSER_TYPE = System.getProperty("browser", DOTENV.get("BROWSER","chrome"));

    /** Screen resolution for browser window */
    public static final String SCREEN_RESOLUTION = System.getProperty("resolution", DOTENV.get("SCREEN_RESOLUTION", "1920,1080"));

    /** Headless mode flag - Usage: mvn clean test -Dheadless=true */
    public static final boolean HEADLESS = Boolean.parseBoolean(System.getProperty("headless", DOTENV.get("HEADLESS", "false")));

    // WAIT SETTINGS
    /** Element visibility wait timeout in seconds */
    public static final int WAIT_ELEMENT = 5;

    /** Implicit wait timeout in seconds */
    public static final int IMPLICIT_WAIT = 2;

    /** Page load timeout in seconds */
    public static final int PAGE_LOAD_TIMEOUT = 15;

    // HUB SETTINGS
    /** Hub type (NONE, GRID) - Usage: mvn clean test -DhubType=GRID */
    public static final String HUB_TYPE = System.getProperty("hubType", DOTENV.get("HUB_TYPE","NONE"));

    /** Selenium Grid hub URL */
    public static final String GRID_HUB_URL = "http://localhost:4444";
}
