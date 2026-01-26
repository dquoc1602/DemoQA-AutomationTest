package pages.demoQA.Elements;

import core.Basepage;
import locators.demoQA.elements.WebTablesLocators;
import models.WebTableRecord;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.*;
import java.util.stream.Collectors;

import static util.Constants.DEMOQA_WED_TABLES_URL;

public class WebTablesPage extends Basepage {

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

    public WebTablesPage() {
        super();
        logger.info("Opening Web Tables page: {}", DEMOQA_WED_TABLES_URL);
        openSite(DEMOQA_WED_TABLES_URL);
        waitForTableReady();
    }

    private void waitForTableReady() {
        logger.debug("Waiting for table to be ready");
        waitForElementInvisible(WebTablesLocators.LOADING);
        findElement(WebTablesLocators.TABLE_ROOT);
        logger.debug("Table ready");
    }

    private void waitForTableStable() {
        logger.trace("Waiting for table stabilization");
        waitForCondition(() -> {
            List<WebElement> rows = findElements(WebTablesLocators.DATA_ROW);
            boolean stable = rows.stream().anyMatch(WebElement::isDisplayed);
            logger.trace("Table stable: {}", stable);
            return stable;
        });
    }

    public List<WebTableRecord> getAllRecords() {
        logger.info("Fetching all records from table");
        waitForTableReady();

        List<WebElement> rows = getAllDataRows();
        List<Integer> columnMap = resolveColumnIndexes();

        List<WebTableRecord> records = new ArrayList<>();

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(WebTablesLocators.CELL);

            WebTableRecord record = new WebTableRecord(
                    cells.get(columnMap.get(0)).getText().trim(),
                    cells.get(columnMap.get(1)).getText().trim(),
                    cells.get(columnMap.get(2)).getText().trim(),
                    cells.get(columnMap.get(3)).getText().trim(),
                    cells.get(columnMap.get(4)).getText().trim(),
                    cells.get(columnMap.get(5)).getText().trim());

            logger.trace("Parsed record: {}", record);
            records.add(record);
        }

        logger.info("Total parsed records: {}", records.size());
        return records;
    }

    private List<WebElement> getAllDataRows() {
        List<WebElement> rows = findElements(WebTablesLocators.DATA_ROW);

        List<WebElement> realRows = rows.stream()
                .filter(this::isRealDataRow)
                .collect(Collectors.toList());

        logger.debug("Filtered real data rows: {}", realRows.size());
        return realRows;
    }

    private String getCellText(WebElement row, Column column) {
        List<WebElement> cells = row.findElements(WebTablesLocators.CELL);

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

    public boolean recordExistsByEmail(String email) {
        boolean found = findRecordByEmail(email).isPresent();
        logger.info("Record exists by email [{}]: {}", email, found);
        return found;
    }

    public WebTablesPage search(String keyword) {
        logger.info("Searching keyword: '{}'", keyword);
        enterText(WebTablesLocators.SEARCH_BOX, keyword);
        waitForTableReady();
        return this;
    }

    public WebTablesPage clearSearch() {
        logger.info("Clearing search box");
        findElement(WebTablesLocators.SEARCH_BOX).clear();
        waitForTableReady();
        return this;
    }

    public WebTablesPage addRecord(WebTableRecord record) {
        logger.info("Adding record: {}", record);
        clickButton(WebTablesLocators.ADD_BUTTON);

        enterText(WebTablesLocators.FIRST_NAME, record.firstName);
        enterText(WebTablesLocators.LAST_NAME, record.lastName);
        enterText(WebTablesLocators.EMAIL, record.email);
        enterText(WebTablesLocators.AGE, record.age);
        enterText(WebTablesLocators.SALARY, record.salary);
        enterText(WebTablesLocators.DEPARTMENT, record.department);

        clickButton(WebTablesLocators.SUBMIT);
        waitForTableReady();
        return this;
    }

    public WebTablesPage editRecordByEmail(String email, WebTableRecord updated) {
        logger.info("Editing record by email: {}", email);

        int rowIndex = resolveRowIndexByEmail(email);
        logger.debug("Resolved row index for edit: {}", rowIndex);

        clickWebElement(findElement(By.cssSelector(
                String.format(WebTablesLocators.EDIT_BUTTON_BY_INDEX, rowIndex))));

        replaceText(WebTablesLocators.FIRST_NAME, updated.firstName);
        replaceText(WebTablesLocators.LAST_NAME, updated.lastName);
        replaceText(WebTablesLocators.EMAIL, updated.email);
        replaceText(WebTablesLocators.AGE, updated.age);
        replaceText(WebTablesLocators.SALARY, updated.salary);
        replaceText(WebTablesLocators.DEPARTMENT, updated.department);

        clickButton(WebTablesLocators.SUBMIT);
        waitForTableReady();
        return this;
    }

    public WebTablesPage deleteRecordByEmail(String email) {
        logger.info("Deleting record by email: {}", email);

        int rowIndex = resolveRowIndexByEmail(email);
        logger.debug("Resolved row index for delete: {}", rowIndex);

        clickWebElement(findElement(By.cssSelector(
                String.format(WebTablesLocators.DELETE_BUTTON_BY_INDEX, rowIndex))));
        waitForTableReady();
        return this;
    }

    public WebTablesPage deleteRecordByEmailSafe(String email) {
        logger.info("Deleting record SAFELY by email: {}", email);

        WebElement row = findRecordByEmail(email)
                .orElseThrow(() -> new IllegalStateException(
                        "Cannot delete – record not found: " + email));

        WebElement deleteBtn = row.findElement(
                By.cssSelector("span[title='Delete']"));

        logger.debug("Clicking delete button inside matched row");
        clickWebElement(deleteBtn);

        waitForTableReady();
        return this;
    }

    public WebTablesPage setRowsPerPage(String size) {
        logger.info("Setting rows per page to {}", size);
        WebElement selectElement = findElement(WebTablesLocators.PAGE_SIZE_SELECT);
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(selectElement);
        // The values are likely "5", "10", etc. Let's try selectByValue first, then
        // readable text.
        // Based on typical HTML: <option value="5">5 rows</option>
        try {
            select.selectByValue(size);
        } catch (Exception e) {
            logger.warn("Failed to select by value {}, trying visible text", size);
            select.selectByVisibleText(size + " rows");
        }
        waitForTableReady();
        return this;
    }

    public WebTablesPage goToPage(int page) {
        logger.info("Going to page {}", page);
        WebElement input = findElement(WebTablesLocators.PAGE_INPUT);
        input.clear();
        input.sendKeys(String.valueOf(page));
        input.sendKeys("\n");
        waitForTableReady();
        return this;
    }

    public WebTablesPage clickNext() {
        logger.info("Clicking Next page button");
        clickButton(WebTablesLocators.NEXT_BUTTON);
        waitForTableReady();
        return this;
    }

    public WebTablesPage clickPrevious() {
        logger.info("Clicking Previous page button");
        clickButton(WebTablesLocators.PREVIOUS_BUTTON);
        waitForTableReady();
        return this;
    }

    public boolean isNextButtonEnabled() {
        return findElement(WebTablesLocators.NEXT_BUTTON).isEnabled();
    }

    public boolean isPreviousButtonEnabled() {
        return findElement(WebTablesLocators.PREVIOUS_BUTTON).isEnabled();
    }

    public int getTotalPages() {
        String text = findElement(WebTablesLocators.TOTAL_PAGES).getText();
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            logger.error("Failed to parse total pages: {}", text);
            return 0;
        }
    }

    public int getCurrentPage() {
        String val = getElementValue(WebTablesLocators.PAGE_INPUT);
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            logger.error("Failed to parse current page: {}", val);
            return 0;
        }
    }

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

        List<WebElement> headers = findElement(WebTablesLocators.HEADER_ROW)
                .findElements(WebTablesLocators.HEADER_TEXT);

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
                indexMap.get("department"));
    }

    private boolean isRealDataRow(WebElement row) {
        List<WebElement> cells = row.findElements(WebTablesLocators.CELL);

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
