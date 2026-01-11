package util;

import java.nio.file.Paths;
import java.time.Duration;

public final class Constants {
    /// Project Path Constants
    public static final String PROJECT_ROOT_DIR = System.getProperty("user.dir");
    public static final String PROJECT_ROOT_PATH = Paths.get(PROJECT_ROOT_DIR).toString();
    public static final String RESOURCES_PATH = Paths.get(PROJECT_ROOT_PATH, "src", "main", "resources").toString();
    public static final String JSON_DATA_PATH = Paths.get(RESOURCES_PATH, "TestData.json").toString();

    /// XML Helper Constants
    public static final String CONFIG_FILE_PATH = Paths.get(RESOURCES_PATH, "config.xml").toString();
    public static final String BOOK_FILE_PATH = Paths.get(RESOURCES_PATH, "book.xml").toString();

    /// CSV Helper Constants
    public static final String ADDRESSES_FILE_PATH = Paths.get(RESOURCES_PATH, "addresses.csv").toString();
    public static final String CSV_DELIMITER = ",";

    /// CSV Node Path Constants
    public static final String CHECKBOX_NODE_PATH_CSV_PATH = Paths.get(RESOURCES_PATH, "checkbox_paths.csv").toString();

    /// Log Path Constants
    public static final String LOG_DIR = Paths.get(PROJECT_ROOT_PATH, "target", "logs").toString();
    public static final String AUTOMATION_LOG = Paths.get(LOG_DIR, "automation.log").toString();
    public static final String ERROR_LOG = Paths.get(LOG_DIR, "errors.log").toString();
    public static final String TEST_LOG = Paths.get(LOG_DIR, "test-execution.log").toString();

    /// Tree Constants
    public static final Duration TREE_STABILIZATION_TIMEOUT = Duration.ofSeconds(3);
    public static final Duration TREE_POLLING_INTERVAL = Duration.ofMillis(100);


    /// DemoQA Site Constants
    public static final String DEMOQA_BASE_URL = "https://demoqa.com";

    //Elements
    public static final String DEMOQA_TEXTBOX_URL = DEMOQA_BASE_URL + "/text-box";
    public static final String DEMOQA_CHECKBOX_URL = DEMOQA_BASE_URL + "/checkbox";
    public static final String DEMOQA_RADIO_BUTTON_URL = DEMOQA_BASE_URL + "/radio-button";
    public static final String DEMOQA_WED_TABLES_URL = DEMOQA_BASE_URL + "/webtables";
    public static final String DEMOQA_BUTTONS_URL = DEMOQA_BASE_URL + "/buttons";
    public static final String DEMOQA_LINKS_URL = DEMOQA_BASE_URL + "/links";
    public static final String DEMOQA_BROKEN_URL = DEMOQA_BASE_URL + "/broken";
    public static final String DEMOQA_UPLOAD_DOWNLOAD_URL = DEMOQA_BASE_URL + "/upload-download";
    public static final String DEMOQA_DYNAMIC_PROPERTIES_URL = DEMOQA_BASE_URL + "/dynamic-properties";

    //Form
    public static final String DEMOQA_FORM_URL = DEMOQA_BASE_URL + "/automation-practice-form";

    //Alert, Frame & Window
    public static final String DEMOQA_BROWSER_WINDOW_URL = DEMOQA_BASE_URL + "/browser-windows";
    public static final String DEMOQA_ALERTS_URL = DEMOQA_BASE_URL + "/alerts";
    public static final String DEMOQA_FRAMES_URL = DEMOQA_BASE_URL + "/frames";
    public static final String DEMOQA_NESTED_FRAMES_URL = DEMOQA_BASE_URL + "/nestedframes";
    public static final String DEMOQA_MODAL_DIALOGS_URL = DEMOQA_BASE_URL + "/modal-dialogs";

    //Widgets
    public static final String DEMOQA_ACCORDION_URL = DEMOQA_BASE_URL + "/accordian";
    public static final String DEMOQA_AUTO_COMPLETE_URL = DEMOQA_BASE_URL + "/auto-complete";
    public static final String DEMOQA_DATE_PICKER_URL = DEMOQA_BASE_URL + "/date-picker";
    public static final String DEMOQA_SLIDER_URL = DEMOQA_BASE_URL + "/slider";
    public static final String DEMOQA_PROGRESS_BAR_URL = DEMOQA_BASE_URL + "/progress-bar";
    public static final String DEMOQA_TABS_URL = DEMOQA_BASE_URL + "/tabs";
    public static final String DEMOQA_TOOL_TIPS_URL = DEMOQA_BASE_URL + "/tool-tips";
    public static final String DEMOQA_MENU_URL = DEMOQA_BASE_URL + "/menu";
    public static final String DEMOQA_SELECT_MENU_URL = DEMOQA_BASE_URL + "/select-menu";

    //Interactions
    public static final String DEMOQA_SORTABLE_URL = DEMOQA_BASE_URL + "/sortable";
    public static final String DEMOQA_SELECTABLE_URL = DEMOQA_BASE_URL + "/selectable";
    public static final String DEMOQA_RESIZABLE_URL = DEMOQA_BASE_URL + "/resizable";
    public static final String DEMOQA_DROPPABLE_URL = DEMOQA_BASE_URL + "/droppable";
    public static final String DEMOQA_DRAGGABLE_URL = DEMOQA_BASE_URL + "/dragabble";

    //Book Store Application
    public static final String DEMOQA_LOGIN_URL = DEMOQA_BASE_URL + "/login";
    public static final String DEMOQA_BOOKS_URL = DEMOQA_BASE_URL + "/books";
    public static final String DEMOQA_PROFILE_URL = DEMOQA_BASE_URL + "/profile";

}
