package Elements;

import core.BaseTest;
import models.WebTableRecord;
import org.junit.jupiter.api.*;
import pages.demoQA.Elements.WebTablesPage;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Web Tables Test")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class WebTablesTest extends BaseTest {

        private WebTablesPage table;

        private static final WebTableRecord NEW_RECORD = new WebTableRecord(
                        "John",
                        "Doe",
                        "30",
                        "john.doe@test.com",
                        "5000",
                        "Engineering");

        private static final WebTableRecord UPDATED_RECORD = new WebTableRecord(
                        "John",
                        "Doe",
                        "31",
                        "john.doe@test.com",
                        "6000",
                        "Platform");

        @BeforeEach
        void setup() {
                table = new WebTablesPage();
        }

        @Test
        @DisplayName("01 – Default records should be present")
        void shouldLoadDefaultRecords() {
                assertFalse(table.getAllRecords().isEmpty(),
                                "Expected default records to be present");
        }

        @Test
        @DisplayName("02 – Add new record")
        void shouldAddNewRecord() {
                table.addRecord(NEW_RECORD);
                assertTrue(table.recordExists(NEW_RECORD), "Record should exist after add");
        }

        @Test
        @DisplayName("03 – Search by email")
        void shouldSearchByEmail() {
                table.addRecord(NEW_RECORD);
                table.search(NEW_RECORD.email);

                assertTrue(table.recordExists(NEW_RECORD), "Record should exist in search results");

                table.clearSearch();
        }

        @Test
        @DisplayName("04 – Edit existing record")
        void shouldEditExistingRecord() {
                table.addRecord(NEW_RECORD);
                table.editRecordByEmail(NEW_RECORD.email, UPDATED_RECORD);

                assertTrue(table.recordExists(UPDATED_RECORD), "Updated record should exist");
                assertFalse(table.recordExistsByEmail(NEW_RECORD.email), "Old record email should not exist");
        }

        @Test
        @DisplayName("05 – Delete record")
        void shouldDeleteRecord() {
                table.addRecord(NEW_RECORD);
                table.deleteRecordByEmailSafe(NEW_RECORD.email);

                assertFalse(table.recordExistsByEmail(NEW_RECORD.email), "Record should be deleted");
        }

        @Test
        void shouldChangeRowsPerPage() {
                table.setRowsPerPage("5");
                int visibleDataRows = table.getVisibleDataRowCount();

                assertTrue(visibleDataRows <= 5,
                                "Expected visible records <= page size");
        }

        @Test
        @DisplayName("07 – Email uniquely identifies record")
        void emailShouldUniquelyIdentifyRecord() {
                table.addRecord(NEW_RECORD);
                assertTrue(table.findRecordByEmail(NEW_RECORD.email).isPresent(),
                                "Expected record to be found by email");
        }

        @Test
        @DisplayName("08 – Delete non-existing record fails deterministically")
        void shouldFailDeletingNonExistingRecord() {
                IllegalStateException ex = assertThrows(
                                IllegalStateException.class,
                                () -> table.deleteRecordByEmailSafe("notfound@test.com"));
                assertTrue(ex.getMessage().contains("Record not found"),
                                "Exception message mismatch");
        }

        @Test
        @DisplayName("09 – Full CRUD workflow")
        void shouldHandleFullWorkflow() {
                table.addRecord(NEW_RECORD);
                assertTrue(table.recordExists(NEW_RECORD));

                table.editRecordByEmail(NEW_RECORD.email, UPDATED_RECORD);
                assertTrue(table.recordExists(UPDATED_RECORD));

                table.search(UPDATED_RECORD.department);
                assertTrue(table.recordExists(UPDATED_RECORD));

                table.clearSearch();
                table.deleteRecordByEmailSafe(UPDATED_RECORD.email);
                assertFalse(table.recordExistsByEmail(UPDATED_RECORD.email));
        }
}
