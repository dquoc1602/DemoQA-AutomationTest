package locators.demoQA.elements;

import org.openqa.selenium.By;

public final class WebTablesLocators {

    public static final By TABLE_ROOT = By.cssSelector(".ReactTable");
    public static final By LOADING = By.cssSelector(".ReactTable .-loading");

    public static final By ADD_BUTTON = By.id("addNewRecordButton");
    public static final By SEARCH_BOX = By.id("searchBox");

    public static final By HEADER_ROW = By.cssSelector(".rt-thead .rt-tr");
    public static final By HEADER_CELL = By.cssSelector(".rt-th");
    public static final By HEADER_TEXT = By.cssSelector(".rt-resizable-header-content");

    public static final By ROW_GROUP = By.cssSelector(".rt-tbody .rt-tr-group");
    public static final By DATA_ROW = By.cssSelector(".rt-tr:not(.rt-padRow)");
    public static final By CELL = By.cssSelector(".rt-td");

    public static final String EDIT_BUTTON_BY_INDEX = "[id='edit-record-%s']";
    public static final String DELETE_BUTTON_BY_INDEX = "[id='delete-record-%s']";

    public static final By PAGE_SIZE_SELECT = By.cssSelector("select[aria-label='rows per page']");
    public static final By PAGE_INPUT = By.cssSelector("input[aria-label='jump to page']");
    public static final By PREVIOUS_BUTTON = By.cssSelector("div.-previous > button");
    public static final By NEXT_BUTTON = By.cssSelector("div.-next > button");
    public static final By TOTAL_PAGES = By.cssSelector("span.-totalPages");

    // Modal form
    public static final By FIRST_NAME = By.id("firstName");
    public static final By LAST_NAME = By.id("lastName");
    public static final By EMAIL = By.id("userEmail");
    public static final By AGE = By.id("age");
    public static final By SALARY = By.id("salary");
    public static final By DEPARTMENT = By.id("department");
    public static final By SUBMIT = By.id("submit");

    private WebTablesLocators() {
    }
}
