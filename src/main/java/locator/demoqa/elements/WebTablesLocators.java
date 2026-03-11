package locator.demoqa.elements;

import org.openqa.selenium.By;

public final class WebTablesLocators {

    public static final By TABLE_ROOT = By.cssSelector(".web-tables-wrapper table");
    public static final By LOADING = By.cssSelector(".web-tables-wrapper .-loading"); // Not sure if this still exists, keeping it for now, might need check

    public static final By ADD_BUTTON = By.id("addNewRecordButton");
    public static final By SEARCH_BOX = By.id("searchBox");

    public static final By HEADER_ROW = By.cssSelector("thead tr");
    public static final By HEADER_CELL = By.cssSelector("thead th");
    public static final By HEADER_TEXT = By.cssSelector("thead th");

    public static final By ROW_GROUP = By.cssSelector("tbody");
    public static final By DATA_ROW = By.cssSelector("tbody tr");
    public static final By CELL = By.cssSelector("td");

    public static final String EDIT_BUTTON_BY_INDEX = "[id='edit-record-%s']";
    public static final String DELETE_BUTTON_BY_INDEX = "[id='delete-record-%s']";

    public static final By PAGE_SIZE_SELECT = By.cssSelector("select");
    public static final By PAGE_INPUT = By.cssSelector("input[aria-label='jump to page']"); // Need to check if this still exists, no jump found in dump
    public static final By PREVIOUS_BUTTON = By.xpath("//button[text()='Previous']");
    public static final By NEXT_BUTTON = By.xpath("//button[text()='Next']");
    public static final By TOTAL_PAGES = By.xpath("//div[contains(@class,'pagination')]//strong");

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
