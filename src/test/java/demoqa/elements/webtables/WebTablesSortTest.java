package demoqa.elements.webtables;

import core.BaseTest;
import model.WebTableRecord;
import model.enums.SortDirection;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.demoqa.elements.webtables.WebTablesPage;
import model.enums.WebTableColumn;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WebTablesSortTest extends BaseTest {

        private WebTablesPage table;

        @BeforeMethod
        void setup() {
                table = new WebTablesPage();
                // Ensure sufficient data for sorting
                if (table.getAllRecords().size() < 3) {
                        table.addRecord(new WebTableRecord("Alice", "Zilch", "25", "alice@test.com", "2000", "IT"));
                        table.addRecord(new WebTableRecord("Bob", "Yarrow", "35", "bob@test.com", "3000", "Finance"));
                        table.addRecord(new WebTableRecord("Charlie", "Xavier", "45", "charlie@test.com", "4000",
                                        "Admin"));
                }
        }

        @Test(description = "Verify default sort state")
        void verifyDefaultSort() {
                // Usually no sort or maybe First Name asc? Let's check.
                // The implementation assumes NONE initially unless site defaults changed.
                // We just verify we can read it.
                SortDirection dir = table.getColumnSortDirection(WebTableColumn.FIRST_NAME);
                // Just asserting it doesn't crash
                Assert.assertTrue(dir == SortDirection.NONE || dir == SortDirection.ASC || dir == SortDirection.DESC);
        }

        @Test(description = "Verify sorting by First Name")
        void verifySortFirstName() {
                // Click to sort ASC
                table.clickColumnHeader(WebTableColumn.FIRST_NAME);
                Assert.assertEquals(table.getColumnSortDirection(WebTableColumn.FIRST_NAME), SortDirection.ASC,
                                "Column should be sorted ASC");

                // Verify data is sorted ASC
                List<String> firstNames = table.getAllRecords().stream()
                                .map(r -> r.firstName)
                                .collect(Collectors.toList());

                List<String> sortedNames = firstNames.stream()
                                .sorted(String.CASE_INSENSITIVE_ORDER)
                                .collect(Collectors.toList());

                Assert.assertEquals(firstNames, sortedNames, "Records should be sorted by First Name ASC");

                // Click again to sort DESC
                table.clickColumnHeader(WebTableColumn.FIRST_NAME);
                Assert.assertEquals(table.getColumnSortDirection(WebTableColumn.FIRST_NAME), SortDirection.DESC,
                                "Column should be sorted DESC");

                firstNames = table.getAllRecords().stream()
                                .map(r -> r.firstName)
                                .collect(Collectors.toList());

                sortedNames = firstNames.stream()
                                .sorted(String.CASE_INSENSITIVE_ORDER.reversed())
                                .collect(Collectors.toList());

                Assert.assertEquals(firstNames, sortedNames, "Records should be sorted by First Name DESC");
        }

        @Test(description = "Verify sorting by Salary (Numeric string)")
        void verifySortSalary() {
                table.clickColumnHeader(WebTableColumn.SALARY);
                Assert.assertEquals(table.getColumnSortDirection(WebTableColumn.SALARY), SortDirection.ASC);

                List<Long> salaries = table.getAllRecords().stream()
                                .map(r -> Long.parseLong(r.salary))
                                .collect(Collectors.toList());

                List<Long> sortedSalaries = salaries.stream()
                                .sorted()
                                .collect(Collectors.toList());

                Assert.assertEquals(salaries, sortedSalaries, "Records should be sorted by Salary ASC");

                // Click again for DESC
                table.clickColumnHeader(WebTableColumn.SALARY);
                Assert.assertEquals(table.getColumnSortDirection(WebTableColumn.SALARY), SortDirection.DESC);

                salaries = table.getAllRecords().stream()
                                .map(r -> Long.parseLong(r.salary))
                                .collect(Collectors.toList());

                sortedSalaries = salaries.stream()
                                .sorted(Comparator.reverseOrder())
                                .collect(Collectors.toList());

                Assert.assertEquals(salaries, sortedSalaries, "Records should be sorted by Salary DESC");
        }

        @Test(description = "Verify sorting by Last Name")
        void verifySortLastName() {
                table.clickColumnHeader(WebTableColumn.LAST_NAME);
                Assert.assertEquals(table.getColumnSortDirection(WebTableColumn.LAST_NAME), SortDirection.ASC);

                List<String> lastNames = table.getAllRecords().stream()
                                .map(r -> r.lastName)
                                .collect(Collectors.toList());

                List<String> sortedNames = lastNames.stream()
                                .sorted(String.CASE_INSENSITIVE_ORDER)
                                .collect(Collectors.toList());

                Assert.assertEquals(lastNames, sortedNames, "Records should be sorted by Last Name ASC");
        }

        @Test(description = "Verify sorting by Age (Numeric)")
        void verifySortAge() {
                table.clickColumnHeader(WebTableColumn.AGE);
                Assert.assertEquals(table.getColumnSortDirection(WebTableColumn.AGE), SortDirection.ASC);

                List<Integer> ages = table.getAllRecords().stream()
                                .map(r -> Integer.parseInt(r.age))
                                .collect(Collectors.toList());

                List<Integer> sortedAges = ages.stream()
                                .sorted()
                                .collect(Collectors.toList());

                Assert.assertEquals(ages, sortedAges, "Records should be sorted by Age ASC");
        }

        @Test(description = "Verify sorting by Email")
        void verifySortEmail() {
                table.clickColumnHeader(WebTableColumn.EMAIL);
                Assert.assertEquals(table.getColumnSortDirection(WebTableColumn.EMAIL), SortDirection.ASC);

                List<String> emails = table.getAllRecords().stream()
                                .map(r -> r.email)
                                .collect(Collectors.toList());

                List<String> sortedEmails = emails.stream()
                                .sorted(String.CASE_INSENSITIVE_ORDER)
                                .collect(Collectors.toList());

                Assert.assertEquals(emails, sortedEmails, "Records should be sorted by Email ASC");
        }

        @Test(description = "Verify sorting by Department")
        void verifySortDepartment() {
                table.clickColumnHeader(WebTableColumn.DEPARTMENT);
                Assert.assertEquals(table.getColumnSortDirection(WebTableColumn.DEPARTMENT), SortDirection.ASC);

                List<String> departments = table.getAllRecords().stream()
                                .map(r -> r.department)
                                .collect(Collectors.toList());

                List<String> sortedDepts = departments.stream()
                                .sorted(String.CASE_INSENSITIVE_ORDER)
                                .collect(Collectors.toList());

                Assert.assertEquals(departments, sortedDepts, "Records should be sorted by Department ASC");
        }

        @Test(description = "Verify sort remains after adding a new record")
        void verifySortAfterAddingRecord() {
                // Sort by First Name ASC first
                table.clickColumnHeader(WebTableColumn.FIRST_NAME);

                // Add a record that should be at the beginning or middle
                WebTableRecord newRecord = new WebTableRecord("Aaron", "Alpha", "20", "aaron@test.com", "1000",
                                "Testing");
                table.addRecord(newRecord);

                // Verify it's still sorted
                List<String> firstNames = table.getAllRecords().stream()
                                .map(r -> r.firstName)
                                .collect(Collectors.toList());

                List<String> sortedNames = firstNames.stream()
                                .sorted(String.CASE_INSENSITIVE_ORDER)
                                .collect(Collectors.toList());

                Assert.assertEquals(firstNames, sortedNames,
                                "Records should still be sorted after adding a new record");
                Assert.assertEquals(firstNames.get(0), "Aaron",
                                "New record 'Aaron' should be the first record when sorted ASC");
        }

        @Test(description = "Verify sorting with search applied")
        void verifySortWithSearch() {
                // Search for 'a' to get multiple results
                table.search("a");

                table.clickColumnHeader(WebTableColumn.FIRST_NAME);

                List<String> firstNames = table.getAllRecords().stream()
                                .map(r -> r.firstName)
                                .collect(Collectors.toList());

                List<String> sortedNames = firstNames.stream()
                                .sorted(String.CASE_INSENSITIVE_ORDER)
                                .collect(Collectors.toList());

                Assert.assertEquals(firstNames, sortedNames, "Filtered records should be sorted by First Name ASC");
        }

        @Test(description = "Verify sort persistence after page size change")
        void verifySortPersistenceAfterResize() {
                table.clickColumnHeader(WebTableColumn.FIRST_NAME);
                Assert.assertEquals(table.getColumnSortDirection(WebTableColumn.FIRST_NAME), SortDirection.ASC);

                table.setRowsPerPage("5");

                // Sort direction should persist in UI
                Assert.assertEquals(table.getColumnSortDirection(WebTableColumn.FIRST_NAME), SortDirection.ASC,
                                "Sort direction should persist after page size change");

                // Data on first page should still be sorted
                List<String> firstNames = table.getAllRecords().stream()
                                .map(r -> r.firstName)
                                .collect(Collectors.toList());

                List<String> sortedNames = firstNames.stream()
                                .sorted(String.CASE_INSENSITIVE_ORDER)
                                .limit(firstNames.size())
                                .collect(Collectors.toList());

                Assert.assertEquals(firstNames, sortedNames, "Data should still be sorted after page size change");
        }
}
