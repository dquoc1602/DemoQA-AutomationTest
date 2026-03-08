package demoqa.elements.webtables;

import core.BaseTest;
import model.WebTableRecord;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.demoqa.elements.webtables.WebTablesPage;

public class WebTablesPaginationTest extends BaseTest {

    private WebTablesPage table;

    @BeforeMethod
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

    @Test(description = "Verify default pagination state")
    void verifyDefaultPagination() {
        // Default rows per page is 10. Total 13 records. Should be 2 pages.
        Assert.assertEquals(table.getCurrentPage(), 1, "Should start on Page 1");
        Assert.assertEquals(table.getTotalPages(), 2, "Should have 2 pages total");

        Assert.assertFalse(table.isPreviousButtonEnabled(), "Previous button should be disabled on first page");
        Assert.assertTrue(table.isNextButtonEnabled(), "Next button should be enabled on first page");
    }

    @Test(description = "Verify navigation buttons")
    void verifyNavigationButtons() {
        table.clickNext();
        Assert.assertEquals(table.getCurrentPage(), 2, "Should be on Page 2");

        // On last page
        Assert.assertFalse(table.isNextButtonEnabled(), "Next button should be disabled on last page");
        Assert.assertTrue(table.isPreviousButtonEnabled(), "Previous button should be enabled on last page");

        table.clickPrevious();
        Assert.assertEquals(table.getCurrentPage(), 1, "Should return to Page 1");
    }

    @Test(description = "Verify changing page size")
    void verifyPageSizeChange() {
        // Change to 5 rows per page. 13 records / 5 = 2.6 -> 3 pages.
        table.setRowsPerPage("5");

        Assert.assertEquals(table.getTotalPages(), 3, "Should have 3 pages when size is 5");
        Assert.assertEquals(table.getCurrentPage(), 1, "Should stay on Page 1 (or reset to 1)");
    }

    @Test(description = "Verify jumping to page")
    void verifyJumpToPage() {
        table.goToPage(2);
        Assert.assertEquals(table.getCurrentPage(), 2, "Should have jumped to Page 2");

        // Ensure data from page 2 is likely visible (logic check)
        Assert.assertTrue(table.isPreviousButtonEnabled(), "Previous button should be active on Page 2");
    }

    @Test(description = "Verify jumping to invalid page handles gracefully")
    void verifyInvalidJump() {
        int total = table.getTotalPages();
        table.goToPage(total + 5);

        // Behavior depends on app, usually stays on last page or current page
        // Assuming it clamps to max page or stays put.
        // Let's verify we are at least on a valid page <= total
        Assert.assertTrue(table.getCurrentPage() <= table.getTotalPages());
    }
}
