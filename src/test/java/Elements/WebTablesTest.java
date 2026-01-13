package Elements;

import core.BaseTest;
import org.junit.jupiter.api.*;
import pages.demoQA.Elements.WebTablesPage;
import pages.demoQA.Elements.WebTablesPage.WebTableRecord;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Lead / Principal-ready UI Test Suite for Web Tables
 *
 * Design goals:
 * - Business-readable scenarios
 * - Deterministic assertions
 * - No UI-structure coupling
 * - Full CRUD coverage
 * - Search & pagination safe
 */
@DisplayName("Web Tables Test")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class WebTablesTest extends BaseTest {

    private WebTablesPage table;

    // ===================== TEST DATA =====================

    private static final WebTableRecord NEW_RECORD =
            new WebTableRecord(
                    "John",
                    "Doe",
                    "30",
                    "john.doe@test.com",
                    "5000",
                    "Engineering"
            );

    private static final WebTableRecord UPDATED_RECORD =
            new WebTableRecord(
                    "John",
                    "Doe",
                    "31",
                    "john.doe@test.com",
                    "6000",
                    "Platform"
            );

    // ===================== SETUP =====================

    @BeforeEach
    void setup() {
        table = new WebTablesPage();
    }

    // ===================== READ =====================

    @Test
    @DisplayName("01 – Default records should be present")
    void shouldLoadDefaultRecords() {
        assertFalse(table.getAllRecords().isEmpty(),
                "Expected default records to be present");
    }

    // ===================== ADD =====================

    @Test
    @DisplayName("02 – Add new record")
    void shouldAddNewRecord() {
        table.addRecord(NEW_RECORD)
                .verifyRecordExists(NEW_RECORD);
    }

    // ===================== SEARCH =====================

    @Test
    @DisplayName("03 – Search by email")
    void shouldSearchByEmail() {
        table.addRecord(NEW_RECORD)
                .search(NEW_RECORD.email)
                .verifyRecordExists(NEW_RECORD)
                .clearSearch();
    }

    // ===================== EDIT =====================

    @Test
    @DisplayName("04 – Edit existing record")
    void shouldEditExistingRecord() {
        table.addRecord(NEW_RECORD)
                .editRecordByEmail(NEW_RECORD.email, UPDATED_RECORD)
                .verifyRecordExists(UPDATED_RECORD);
    }

    // ===================== DELETE =====================

    @Test
    @DisplayName("05 – Delete record")
    void shouldDeleteRecord() {
        table.addRecord(NEW_RECORD)
                .deleteRecordByEmailSafe(NEW_RECORD.email)
                .verifyRecordNotExists(NEW_RECORD.email);
    }

    // ===================== PAGINATION =====================

    @Test
    void shouldChangeRowsPerPage() {
        table.setRowsPerPage("5");

        int visibleDataRows = table.getVisibleDataRowCount();

        assertTrue(visibleDataRows <= 5,
                "Expected visible records <= page size");
    }


    // ===================== IDENTITY =====================

    @Test
    @DisplayName("07 – Email uniquely identifies record")
    void emailShouldUniquelyIdentifyRecord() {
        table.addRecord(NEW_RECORD);
        assertTrue(
                table.findRecordByEmail(NEW_RECORD.email).isPresent(),
                "Expected record to be found by email"
        );
    }

    // ===================== NEGATIVE =====================

    @Test
    @DisplayName("08 – Delete non-existing record fails deterministically")
    void shouldFailDeletingNonExistingRecord() {
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> table.deleteRecordByEmailSafe("notfound@test.com")
        );
        assertTrue(ex.getMessage().contains("Record with email 'notfound@test.com' found: false"));
    }

    // ===================== END-TO-END =====================

    @Test
    @DisplayName("09 – Full CRUD workflow")
    void shouldHandleFullWorkflow() {
        table.addRecord(NEW_RECORD)
                .verifyRecordExists(NEW_RECORD)
                .editRecordByEmail(NEW_RECORD.email, UPDATED_RECORD)
                .verifyRecordExists(UPDATED_RECORD)
                .search(UPDATED_RECORD.department)
                .verifyRecordExists(UPDATED_RECORD)
                .clearSearch()
                .deleteRecordByEmailSafe(UPDATED_RECORD.email)
                .verifyRecordNotExists(UPDATED_RECORD.email);
    }
}
