package pages.demoQA.Elements;

import core.Basepage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.*;
import java.util.stream.Collectors;

import static util.Constants.DEMOQA_WED_TABLES_URL;

/**
 * Lead / Principal-ready Page Object for Web Tables (ReactTable)
 * - Full state logging
 * - Deterministic data access
 * - ReactTable-safe row handling
 */
public class WebTablesPage extends Basepage {

    // ================= SELECTORS =================

    private static final class Selectors {

        static final By TABLE_ROOT = By.cssSelector(".ReactTable");
        static final By LOADING = By.cssSelector(".-loading");

        static final By ADD_BUTTON = By.id("addNewRecordButton");
        static final By SEARCH_BOX = By.id("searchBox");

        static final By HEADER_ROW = By.cssSelector(".rt-thead .rt-tr");
        static final By HEADER_TEXT = By.cssSelector(".rt-resizable-header-content");

        static final By ROW_GROUP = By.cssSelector(".rt-tbody .rt-tr-group");
        static final By DATA_ROW = By.cssSelector(".rt-tr:not(.rt-padRow)");
        static final By CELL = By.cssSelector(".rt-td");

        static final String EDIT_BUTTON_BY_INDEX = "[id='edit-record-%s']";
        static final String DELETE_BUTTON_BY_INDEX = "[id='delete-record-%s']";

        static final By PAGE_SIZE_SELECT = By.cssSelector("select[aria-label='rows per page']");
        static final By PAGE_INPUT = By.cssSelector("input[aria-label='jump to page']");

        // Modal form
        static final By FIRST_NAME = By.id("firstName");
        static final By LAST_NAME = By.id("lastName");
        static final By EMAIL = By.id("userEmail");
        static final By AGE = By.id("age");
        static final By SALARY = By.id("salary");
        static final By DEPARTMENT = By.id("department");
        static final By SUBMIT = By.id("submit");

        private Selectors() {}
    }

    // ================= DOMAIN MODEL =================

    public static class WebTableRecord {
        public final String firstName;
        public final String lastName;
        public final String age;
        public final String email;
        public final String salary;
        public final String department;

        public WebTableRecord(String firstName, String lastName,
                              String age, String email,
                              String salary, String department) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.email = email;
            this.salary = salary;
            this.department = department;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof WebTableRecord)) return false;
            WebTableRecord r = (WebTableRecord) o;
            return Objects.equals(email, r.email)
                    && Objects.equals(firstName, r.firstName)
                    && Objects.equals(lastName, r.lastName)
                    && Objects.equals(age, r.age)
                    && Objects.equals(salary, r.salary)
                    && Objects.equals(department, r.department);
        }

        @Override
        public int hashCode() {
            return Objects.hash(email);
        }

        @Override
        public String toString() {
            return String.format(
                    "[%s %s | age=%s | email=%s | salary=%s | dept=%s]",
                    firstName, lastName, age, email, salary, department
            );
        }
    }

    public enum Column {
        FIRST_NAME(0),
        LAST_NAME(1),
        AGE(2),
        EMAIL(3),
        SALARY(4),
        DEPARTMENT(5);

        public final int index;

        Column(int index) {
            this.index = index;
        }
    }

    // ================= CONSTRUCTOR =================

    public WebTablesPage() {
        super();
        logger.info("Opening Web Tables page: {}", DEMOQA_WED_TABLES_URL);
        openSite(DEMOQA_WED_TABLES_URL);
        waitForTableReady();
    }

    // ================= STABILIZATION =================

    private void waitForTableReady() {
        logger.debug("Waiting for table to be ready");
        waitForElementInvisible(Selectors.LOADING);
        findElement(Selectors.TABLE_ROOT);
        logger.debug("Table ready");
    }

    private void waitForTableStable() {
        logger.trace("Waiting for table stabilization");
        waitForCondition(() -> {
            List<WebElement> rows = driver.findElements(Selectors.DATA_ROW);
            boolean stable = rows.stream().anyMatch(WebElement::isDisplayed);
            logger.trace("Table stable: {}", stable);
            return stable;
        });
    }

    // ================= READ =================

    public List<WebTableRecord> getAllRecords() {
        logger.info("Fetching all records from table");
        waitForTableReady();

        List<WebElement> rows = getAllDataRows();
        List<Integer> columnMap = resolveColumnIndexes();

        List<WebTableRecord> records = new ArrayList<>();

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(Selectors.CELL);

            WebTableRecord record = new WebTableRecord(
                    cells.get(columnMap.get(0)).getText().trim(),
                    cells.get(columnMap.get(1)).getText().trim(),
                    cells.get(columnMap.get(2)).getText().trim(),
                    cells.get(columnMap.get(3)).getText().trim(),
                    cells.get(columnMap.get(4)).getText().trim(),
                    cells.get(columnMap.get(5)).getText().trim()
            );

            logger.trace("Parsed record: {}", record);
            records.add(record);
        }

        logger.info("Total parsed records: {}", records.size());
        return records;
    }


    private List<WebElement> getAllDataRows() {
        List<WebElement> rows = driver.findElements(Selectors.DATA_ROW);

        List<WebElement> realRows = rows.stream()
                .filter(this::isRealDataRow)
                .collect(Collectors.toList());

        logger.debug("Filtered real data rows: {}", realRows.size());
        return realRows;
    }


    private String getCellText(WebElement row, Column column) {
        List<WebElement> cells = row.findElements(Selectors.CELL);

        if (cells.size() <= column.index) {
            logger.error("Column index {} out of bounds, cell size={}", column.index, cells.size());
            throw new IllegalStateException("Column index out of bounds: " + column);
        }

        String value = cells.get(column.index).getText().trim();
        logger.trace("Cell [{}] value='{}'", column, value);
        return value;
    }

    public int getVisibleDataRowCount() {
        waitForTableStable();

        int count = getAllDataRows().size();
        logger.info("Visible data row count: {}", count);
        return count;
    }


    public Optional<WebElement> findRecordByEmail(String email) {
        logger.debug("Searching record by email: {}", email);
        waitForTableStable();

        Optional<WebElement> result = getAllDataRows().stream()
                .filter(row -> email.equals(getCellText(row, Column.EMAIL)))
                .findFirst();

        logger.debug("Record with email '{}' found: {}", email, result.isPresent());
        return result;
    }


    public boolean recordExists(WebTableRecord record) {
        boolean exists = getAllRecords().contains(record);
        logger.info("Record exists [{}]: {}", record.email, exists);
        return exists;
    }

    // ================= SEARCH =================

    public WebTablesPage search(String keyword) {
        logger.info("Searching keyword: '{}'", keyword);
        enterText(Selectors.SEARCH_BOX, keyword);
        waitForTableReady();
        return this;
    }

    public WebTablesPage clearSearch() {
        logger.info("Clearing search box");
        findElement(Selectors.SEARCH_BOX).clear();
        waitForTableReady();
        return this;
    }

    // ================= ADD =================

    public WebTablesPage addRecord(WebTableRecord record) {
        logger.info("Adding record: {}", record);
        clickButton(Selectors.ADD_BUTTON);

        enterText(Selectors.FIRST_NAME, record.firstName);
        enterText(Selectors.LAST_NAME, record.lastName);
        enterText(Selectors.EMAIL, record.email);
        enterText(Selectors.AGE, record.age);
        enterText(Selectors.SALARY, record.salary);
        enterText(Selectors.DEPARTMENT, record.department);

        clickButton(Selectors.SUBMIT);
        waitForTableReady();
        return this;
    }

    // ================= EDIT =================

    public WebTablesPage editRecordByEmail(String email, WebTableRecord updated) {
        logger.info("Editing record by email: {}", email);

        int rowIndex = resolveRowIndexByEmail(email);
        logger.debug("Resolved row index for edit: {}", rowIndex);

        clickWebElement(findElement(By.cssSelector(
                String.format(Selectors.EDIT_BUTTON_BY_INDEX, rowIndex))));

        replaceText(Selectors.FIRST_NAME, updated.firstName);
        replaceText(Selectors.LAST_NAME, updated.lastName);
        replaceText(Selectors.EMAIL, updated.email);
        replaceText(Selectors.AGE, updated.age);
        replaceText(Selectors.SALARY, updated.salary);
        replaceText(Selectors.DEPARTMENT, updated.department);

        clickButton(Selectors.SUBMIT);
        waitForTableReady();
        return this;
    }

    // ================= DELETE =================

    public WebTablesPage deleteRecordByEmail(String email) {
        logger.info("Deleting record by email: {}", email);

        int rowIndex = resolveRowIndexByEmail(email);
        logger.debug("Resolved row index for delete: {}", rowIndex);

        clickWebElement(findElement(By.cssSelector(
                String.format(Selectors.DELETE_BUTTON_BY_INDEX, rowIndex))));
        waitForTableReady();
        return this;
    }

    /// Delete index theo index sau khi duoc add new vao Table
    public WebTablesPage deleteRecordByEmailSafe(String email) {
        logger.info("Deleting record SAFELY by email: {}", email);

        WebElement row = findRecordByEmail(email)
                .orElseThrow(() -> new IllegalStateException(
                        "Cannot delete – record not found: " + email));

        WebElement deleteBtn = row.findElement(
                By.cssSelector("span[title='Delete']")
        );

        logger.debug("Clicking delete button inside matched row");
        clickWebElement(deleteBtn);

        waitForTableReady();
        return this;
    }


    // ================= PAGINATION =================

    public WebTablesPage setRowsPerPage(String size) {
        logger.info("Setting rows per page to {}", size);
        WebElement select = findElement(Selectors.PAGE_SIZE_SELECT);
        select.sendKeys(size);
        waitForTableReady();
        return this;
    }

    public WebTablesPage goToPage(int page) {
        logger.info("Going to page {}", page);
        WebElement input = findElement(Selectors.PAGE_INPUT);
        input.clear();
        input.sendKeys(String.valueOf(page));
        input.sendKeys("\n");
        waitForTableReady();
        return this;
    }

    // ================= VERIFY =================

    public WebTablesPage verifyRecordExists(WebTableRecord record) {
        waitForTableStable();
        boolean found = findRecordByEmail(record.email).isPresent();

        logger.info("Verify record exists [{}]: {}", record.email, found);
        verifyTrue(found, "Expected record not found: " + record.email);
        return this;
    }

    public WebTablesPage verifyRecordNotExists(String email) {
        boolean absent = findRecordByEmail(email).isEmpty();
        logger.info("Verify record not exists [{}]: {}", email, absent);
        verifyTrue(absent, "Record unexpectedly exists: " + email);
        return this;
    }

    // ================= INTERNAL HELPERS =================

    private int resolveRowIndexByEmail(String email) {
        logger.debug("Resolving row index by email: {}", email);
        List<WebTableRecord> records = getAllRecords();

        for (int i = 0; i < records.size(); i++) {
            logger.trace("Checking row {} email={}", i + 1, records.get(i).email);
            if (records.get(i).email.equalsIgnoreCase(email)) {
                return i + 1;
            }
        }
        logger.error("Record not found for email: {}", email);
        throw new IllegalStateException("Record not found for email: " + email);
    }

    private List<Integer> resolveColumnIndexes() {
        logger.debug("Resolving column indexes from header");

        List<WebElement> headers = findElement(Selectors.HEADER_ROW)
                .findElements(Selectors.HEADER_TEXT);

        Map<String, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < headers.size(); i++) {
            String text = headers.get(i).getText().trim().toLowerCase();
            indexMap.put(text, i);
            logger.trace("Header '{}' at index {}", text, i);
        }

        return List.of(
                indexMap.get("first name"),
                indexMap.get("last name"),
                indexMap.get("age"),
                indexMap.get("email"),
                indexMap.get("salary"),
                indexMap.get("department")
        );
    }

    private boolean isRealDataRow(WebElement row) {
        List<WebElement> cells = row.findElements(Selectors.CELL);

        if (cells.size() <= Column.EMAIL.index) {
            logger.trace("Row skipped: insufficient cell count={}", cells.size());
            return false;
        }

        boolean hasData = cells.stream()
                .anyMatch(cell -> !cell.getText().trim().isEmpty());

        logger.trace("Row real data: {}", hasData);
        return hasData;
    }

}
