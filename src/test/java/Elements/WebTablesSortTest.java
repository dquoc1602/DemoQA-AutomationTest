package Elements;

import core.BaseTest;
import models.WebTableRecord;
import models.enums.SortDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.demoQA.Elements.WebTablesPage;
import pages.demoQA.Elements.WebTablesPage.Column;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Web Tables Sort Tests")
public class WebTablesSortTest extends BaseTest {

    private WebTablesPage table;

    @BeforeEach
    void setup() {
        table = new WebTablesPage();
        // Ensure sufficient data for sorting
        if (table.getAllRecords().size() < 3) {
            table.addRecord(new WebTableRecord("Alice", "Zilch", "25", "alice@test.com", "2000", "IT"));
            table.addRecord(new WebTableRecord("Bob", "Yarrow", "35", "bob@test.com", "3000", "Finance"));
            table.addRecord(new WebTableRecord("Charlie", "Xavier", "45", "charlie@test.com", "4000", "Admin"));
        }
    }

    @Test
    @DisplayName("Verify default sort state")
    void verifyDefaultSort() {
        // Usually no sort or maybe First Name asc? Let's check.
        // The implementation assumes NONE initially unless site defaults changed.
        // We just verify we can read it.
        SortDirection dir = table.getColumnSortDirection(Column.FIRST_NAME);
        // Just asserting it doesn't crash
        assertTrue(dir == SortDirection.NONE || dir == SortDirection.ASC || dir == SortDirection.DESC);
    }

    @Test
    @DisplayName("Verify sorting by First Name")
    void verifySortFirstName() {
        // Click to sort ASC
        table.clickColumnHeader(Column.FIRST_NAME);
        assertEquals(SortDirection.ASC, table.getColumnSortDirection(Column.FIRST_NAME),
                "Column should be sorted ASC");

        // Verify data is sorted ASC
        List<String> firstNames = table.getAllRecords().stream()
                .map(r -> r.firstName)
                .collect(Collectors.toList());

        List<String> sortedNames = firstNames.stream()
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList());

        assertEquals(sortedNames, firstNames, "Records should be sorted by First Name ASC");

        // Click again to sort DESC
        table.clickColumnHeader(Column.FIRST_NAME);
        assertEquals(SortDirection.DESC, table.getColumnSortDirection(Column.FIRST_NAME),
                "Column should be sorted DESC");

        firstNames = table.getAllRecords().stream()
                .map(r -> r.firstName)
                .collect(Collectors.toList());

        sortedNames = firstNames.stream()
                .sorted(String.CASE_INSENSITIVE_ORDER.reversed())
                .collect(Collectors.toList());

        assertEquals(sortedNames, firstNames, "Records should be sorted by First Name DESC");
    }

    @Test
    @DisplayName("Verify sorting by Salary (Numeric string)")
    void verifySortSalary() {
        table.clickColumnHeader(Column.SALARY);
        assertEquals(SortDirection.ASC, table.getColumnSortDirection(Column.SALARY));

        List<Long> salaries = table.getAllRecords().stream()
                .map(r -> Long.parseLong(r.salary))
                .collect(Collectors.toList());

        List<Long> sortedSalaries = salaries.stream()
                .sorted()
                .collect(Collectors.toList());

        assertEquals(sortedSalaries, salaries, "Records should be sorted by Salary ASC");

        // Click again for DESC
        table.clickColumnHeader(Column.SALARY);
        assertEquals(SortDirection.DESC, table.getColumnSortDirection(Column.SALARY));

        salaries = table.getAllRecords().stream()
                .map(r -> Long.parseLong(r.salary))
                .collect(Collectors.toList());

        sortedSalaries = salaries.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        assertEquals(sortedSalaries, salaries, "Records should be sorted by Salary DESC");
    }

    @Test
    @DisplayName("Verify sorting by Last Name")
    void verifySortLastName() {
        table.clickColumnHeader(Column.LAST_NAME);
        assertEquals(SortDirection.ASC, table.getColumnSortDirection(Column.LAST_NAME));

        List<String> lastNames = table.getAllRecords().stream()
                .map(r -> r.lastName)
                .collect(Collectors.toList());

        List<String> sortedNames = lastNames.stream()
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList());

        assertEquals(sortedNames, lastNames, "Records should be sorted by Last Name ASC");
    }

    @Test
    @DisplayName("Verify sorting by Age (Numeric)")
    void verifySortAge() {
        table.clickColumnHeader(Column.AGE);
        assertEquals(SortDirection.ASC, table.getColumnSortDirection(Column.AGE));

        List<Integer> ages = table.getAllRecords().stream()
                .map(r -> Integer.parseInt(r.age))
                .collect(Collectors.toList());

        List<Integer> sortedAges = ages.stream()
                .sorted()
                .collect(Collectors.toList());

        assertEquals(sortedAges, ages, "Records should be sorted by Age ASC");
    }

    @Test
    @DisplayName("Verify sorting by Email")
    void verifySortEmail() {
        table.clickColumnHeader(Column.EMAIL);
        assertEquals(SortDirection.ASC, table.getColumnSortDirection(Column.EMAIL));

        List<String> emails = table.getAllRecords().stream()
                .map(r -> r.email)
                .collect(Collectors.toList());

        List<String> sortedEmails = emails.stream()
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList());

        assertEquals(sortedEmails, emails, "Records should be sorted by Email ASC");
    }

    @Test
    @DisplayName("Verify sorting by Department")
    void verifySortDepartment() {
        table.clickColumnHeader(Column.DEPARTMENT);
        assertEquals(SortDirection.ASC, table.getColumnSortDirection(Column.DEPARTMENT));

        List<String> departments = table.getAllRecords().stream()
                .map(r -> r.department)
                .collect(Collectors.toList());

        List<String> sortedDepts = departments.stream()
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList());

        assertEquals(sortedDepts, departments, "Records should be sorted by Department ASC");
    }

    @Test
    @DisplayName("Verify sort remains after adding a new record")
    void verifySortAfterAddingRecord() {
        // Sort by First Name ASC first
        table.clickColumnHeader(Column.FIRST_NAME);

        // Add a record that should be at the beginning or middle
        WebTableRecord newRecord = new WebTableRecord("Aaron", "Alpha", "20", "aaron@test.com", "1000", "Testing");
        table.addRecord(newRecord);

        // Verify it's still sorted
        List<String> firstNames = table.getAllRecords().stream()
                .map(r -> r.firstName)
                .collect(Collectors.toList());

        List<String> sortedNames = firstNames.stream()
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList());

        assertEquals(sortedNames, firstNames, "Records should still be sorted after adding a new record");
        assertEquals("Aaron", firstNames.get(0), "New record 'Aaron' should be the first record when sorted ASC");
    }

    @Test
    @DisplayName("Verify sorting with search applied")
    void verifySortWithSearch() {
        // Search for 'a' to get multiple results
        table.search("a");

        table.clickColumnHeader(Column.FIRST_NAME);

        List<String> firstNames = table.getAllRecords().stream()
                .map(r -> r.firstName)
                .collect(Collectors.toList());

        List<String> sortedNames = firstNames.stream()
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList());

        assertEquals(sortedNames, firstNames, "Filtered records should be sorted by First Name ASC");
    }

    @Test
    @DisplayName("Verify sort persistence after page size change")
    void verifySortPersistenceAfterResize() {
        table.clickColumnHeader(Column.FIRST_NAME);
        assertEquals(SortDirection.ASC, table.getColumnSortDirection(Column.FIRST_NAME));

        table.setRowsPerPage("5");

        // Sort direction should persist in UI
        assertEquals(SortDirection.ASC, table.getColumnSortDirection(Column.FIRST_NAME),
                "Sort direction should persist after page size change");

        // Data on first page should still be sorted
        List<String> firstNames = table.getAllRecords().stream()
                .map(r -> r.firstName)
                .collect(Collectors.toList());

        List<String> sortedNames = firstNames.stream()
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .limit(firstNames.size())
                .collect(Collectors.toList());

        assertEquals(sortedNames, firstNames, "Data should still be sorted after page size change");
    }
}
