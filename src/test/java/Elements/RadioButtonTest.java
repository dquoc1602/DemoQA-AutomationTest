package Elements;

import core.BaseTest;
import org.junit.jupiter.api.*;
import pages.demoQA.Elements.RadioButtonPage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Lead / Principal-ready UI Test Suite for Radio Button Page
 *
 * Design goals:
 * - Business-readable tests
 * - Deterministic behavior
 * - Result-text as source of truth
 * - Explicit disabled handling
 * - Zero duplication
 */
@DisplayName("Radio Button Test")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class RadioButtonTest extends BaseTest {

    private RadioButtonPage radio;

    // ===================== TEST DATA =====================

    private static final String YES = "Yes";
    private static final String IMPRESSIVE = "Impressive";
    private static final String NO = "No";

    // ===================== SETUP =====================

    @BeforeEach
    void setup() {
        radio = new RadioButtonPage();
    }

    // ===================== BASIC BEHAVIOR =====================

    @Test
    @DisplayName("01 – Select Yes radio")
    void shouldSelectYes() {
        radio.selectYes()
                .verifySelected(YES);
    }

    @Test
    @DisplayName("02 – Select Impressive radio")
    void shouldSelectImpressive() {
        radio.selectImpressive()
                .verifySelected(IMPRESSIVE);
    }

    // ===================== IDENTITY & IDP =====================

    @Test
    @DisplayName("03 – Selecting same radio is idempotent")
    void shouldBeIdempotent() {
        radio.selectYes()
                .selectYes()
                .verifySelected(YES);
    }

    // ===================== MUTUAL EXCLUSION =====================

    @Test
    @DisplayName("04 – Selecting another radio unselects previous")
    void shouldUnselectPreviousWhenNewSelected() {
        radio.selectYes()
                .selectImpressive()
                .verifySelected(IMPRESSIVE)
                .verifyNotSelected(YES);
    }

    // ===================== DISABLED BEHAVIOR =====================

    @Test
    @DisplayName("05 – Disabled radio cannot be selected")
    void shouldNotSelectDisabledRadio() {
        radio.selectYes()
                .attemptSelectNoAndVerifyUnchanged(YES)
                .verifyDisabled(NO);
    }

    // ===================== STATE VERIFICATION =====================

    @Test
    @DisplayName("06 – Only one radio can be selected")
    void shouldAllowOnlyOneSelection() {
        radio.selectYes()
                .verifyExactlyOneSelected()
                .selectImpressive()
                .verifyExactlyOneSelected();
    }

    @Test
    @DisplayName("07 – No selection initially")
    void shouldHaveNoInitialSelection() {
        radio.verifyNoSelection();
    }

    // ===================== NEGATIVE =====================

    @Test
    @DisplayName("08 – Invalid label fails deterministically")
    void shouldFailOnInvalidLabel() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> radio.selectByLabel("INVALID")
        );
        assertTrue(ex.getMessage().contains("Unknown radio label"));
    }

    // ===================== END-TO-END =====================

    @Test
    @DisplayName("09 – Full realistic workflow")
    void shouldHandleFullWorkflow() {
        radio.verifyNoSelection()
                .selectYes()
                .verifySelected(YES)
                .selectImpressive()
                .verifySelected(IMPRESSIVE)
                .attemptSelectNoAndVerifyUnchanged(IMPRESSIVE)
                .verifySelected(IMPRESSIVE);
    }
}
