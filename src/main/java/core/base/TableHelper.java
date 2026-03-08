package core.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility methods for reading and interacting with standard HTML {@code <table>} elements.
 * Assumes the conventional structure: {@code <table> → <thead>/<tbody> → <tr> → <th>/<td>}.
 * Reusable across web, app, and mobile projects.
 */
public class TableHelper extends DropdownHelper {

    // ───────────────────── ROW / COLUMN COUNT ─────────────────────

    /**
     * Returns the number of {@code <tr>} elements inside the table's {@code <tbody>}.
     *
     * @param tableSelector table locator
     * @return row count
     */
    protected int getTableRowCount(By tableSelector) {
        WebElement table = findElement(tableSelector);
        return table.findElements(By.cssSelector("tbody tr")).size();
    }

    /**
     * Returns the number of columns based on the first header row ({@code <th>}).
     *
     * @param tableSelector table locator
     * @return column count
     */
    protected int getTableColumnCount(By tableSelector) {
        WebElement table = findElement(tableSelector);
        return table.findElements(By.cssSelector("thead th")).size();
    }

    // ───────────────────── CELL TEXT ─────────────────────

    /**
     * Returns the text content of a specific cell.
     *
     * @param tableSelector table locator
     * @param row           1-based row index (inside tbody)
     * @param col           1-based column index
     * @return cell text
     */
    protected String getTableCellText(By tableSelector, int row, int col) {
        WebElement table = findElement(tableSelector);
        String xpath = String.format(".//tbody/tr[%d]/td[%d]", row, col);
        return table.findElement(By.xpath(xpath)).getText();
    }

    // ───────────────────── ROW / COLUMN TEXT LISTS ─────────────────────

    /**
     * Returns all cell texts in a given row.
     *
     * @param tableSelector table locator
     * @param row           1-based row index
     * @return list of cell texts
     */
    protected List<String> getTableRowTexts(By tableSelector, int row) {
        WebElement table = findElement(tableSelector);
        String xpath = String.format(".//tbody/tr[%d]/td", row);
        return table.findElements(By.xpath(xpath)).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns all cell texts in a given column.
     *
     * @param tableSelector table locator
     * @param col           1-based column index
     * @return list of cell texts
     */
    protected List<String> getTableColumnTexts(By tableSelector, int col) {
        WebElement table = findElement(tableSelector);
        String xpath = String.format(".//tbody/tr/td[%d]", col);
        return table.findElements(By.xpath(xpath)).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    // ───────────────────── HEADERS ─────────────────────

    /**
     * Returns all header texts ({@code <th>}).
     *
     * @param tableSelector table locator
     * @return list of header texts
     */
    protected List<String> getTableHeaders(By tableSelector) {
        WebElement table = findElement(tableSelector);
        return table.findElements(By.cssSelector("thead th")).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    // ───────────────────── FULL TABLE DATA ─────────────────────

    /**
     * Reads the entire table into a list of maps (header → cell value).
     * Each map represents one row.
     *
     * @param tableSelector table locator
     * @return list of row data maps
     */
    protected List<Map<String, String>> getTableAllData(By tableSelector) {
        WebElement table = findElement(tableSelector);
        List<String> headers = table.findElements(By.cssSelector("thead th")).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        List<Map<String, String>> data = new ArrayList<>();
        List<WebElement> rows = table.findElements(By.cssSelector("tbody tr"));

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            Map<String, String> rowMap = new LinkedHashMap<>();
            for (int i = 0; i < Math.min(headers.size(), cells.size()); i++) {
                rowMap.put(headers.get(i), cells.get(i).getText());
            }
            data.add(rowMap);
        }
        return data;
    }

    // ───────────────────── SEARCH / FIND ─────────────────────

    /**
     * Checks whether the table body has no rows.
     *
     * @param tableSelector table locator
     * @return true if no tbody rows
     */
    protected boolean isTableEmpty(By tableSelector) {
        return getTableRowCount(tableSelector) == 0;
    }

    /**
     * Finds the 1-based row index whose column matches the expected text.
     * Returns -1 if no matching row is found.
     *
     * @param tableSelector table locator
     * @param col           1-based column to search
     * @param text          expected cell text
     * @return 1-based row index, or -1
     */
    protected int findRowByColumnText(By tableSelector, int col, String text) {
        List<String> columnTexts = getTableColumnTexts(tableSelector, col);
        for (int i = 0; i < columnTexts.size(); i++) {
            if (columnTexts.get(i).equals(text)) {
                return i + 1; // 1-based
            }
        }
        return -1;
    }

    /**
     * Clicks on a specific cell in the table.
     *
     * @param tableSelector table locator
     * @param row           1-based row index
     * @param col           1-based column index
     */
    protected void clickTableCell(By tableSelector, int row, int col) {
        logger.info("Clicking table cell at row {} col {}", row, col);
        WebElement table = findElement(tableSelector);
        String xpath = String.format(".//tbody/tr[%d]/td[%d]", row, col);
        table.findElement(By.xpath(xpath)).click();
    }
}
