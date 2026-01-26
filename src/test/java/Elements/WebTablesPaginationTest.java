package Elements;

import core.BaseTest;
import models.WebTableRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.demoQA.Elements.WebTablesPage;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Web Tables Pagination Tests")
public class WebTablesPaginationTest extends BaseTest {

    private WebTablesPage table;

    @BeforeEach
    void setup() {
        table = new WebTablesPage();
        // Add 10 records as requested
        for (int i = 1; i <= 10; i++) {
            WebTableRecord record = new WebTableRecord(
                    "User" + i,
                    "Test" + i,
                    String.valueOf(20 + i),
                    "user" + i + "@test.com",
                    String.valueOf(1000 * i),
                    "Department" + i);
            table.addRecord(record);
        }
        // Total records = 3 (default) + 10 (added) = 13 records
    }

    @Test
    @DisplayName("Verify default pagination state")
    void verifyDefaultPagination() {
        // Default rows per page is 10. Total 13 records. Should be 2 pages.
        assertEquals(1, table.getCurrentPage(), "Should start on Page 1");
        assertEquals(2, table.getTotalPages(), "Should have 2 pages total");

        assertFalse(table.isPreviousButtonEnabled(), "Previous button should be disabled on first page");
        assertTrue(table.isNextButtonEnabled(), "Next button should be enabled on first page");
    }

    @Test
    @DisplayName("Verify navigation buttons")
    void verifyNavigationButtons() {
        table.clickNext();
        assertEquals(2, table.getCurrentPage(), "Should be on Page 2");

        // On last page
        assertFalse(table.isNextButtonEnabled(), "Next button should be disabled on last page");
        assertTrue(table.isPreviousButtonEnabled(), "Previous button should be enabled on last page");

        table.clickPrevious();
        assertEquals(1, table.getCurrentPage(), "Should return to Page 1");
    }

    @Test
    @DisplayName("Verify changing page size")
    void verifyPageSizeChange() {
        // Change to 5 rows per page. 13 records / 5 = 2.6 -> 3 pages.
        table.setRowsPerPage("5");

        assertEquals(3, table.getTotalPages(), "Should have 3 pages when size is 5");
        assertEquals(1, table.getCurrentPage(), "Should stay on Page 1 (or reset to 1)");
    }

    @Test
    @DisplayName("Verify jumping to page")
    void verifyJumpToPage() {
        table.goToPage(2);
        assertEquals(2, table.getCurrentPage(), "Should have jumped to Page 2");

        // Ensure data from page 2 is likely visible (logic check)
        assertTrue(table.isPreviousButtonEnabled(), "Previous button should be active on Page 2");
    }

    @Test
    @DisplayName("Verify jumping to invalid page handles gracefully")
    void verifyInvalidJump() {
        int total = table.getTotalPages();
        table.goToPage(total + 5);

        // Behavior depends on app, usually stays on last page or current page
        // Assuming it clamps to max page or stays put.
        // Let's verify we are at least on a valid page <= total
        assertTrue(table.getCurrentPage() <= table.getTotalPages());
    }
}
