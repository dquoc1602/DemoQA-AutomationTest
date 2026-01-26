package Elements;

import core.BaseTest;
import org.junit.jupiter.api.*;
import pages.demoQA.Elements.RadioButtonPage;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Radio Button Test")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class RadioButtonTest extends BaseTest {

    private RadioButtonPage radio;

    private static final String YES = "Yes";
    private static final String IMPRESSIVE = "Impressive";
    private static final String NO = "No";

    @BeforeEach
    void setup() {
        radio = new RadioButtonPage();
    }

    @Test
    @DisplayName("01 – Select Yes radio")
    void shouldSelectYes() {
        radio.selectYes();
        assertTrue(radio.isSelected(YES), "Expected YES to be selected");
        assertEquals(YES, radio.getResultText(), "Result text mismatch");
    }

    @Test
    @DisplayName("02 – Select Impressive radio")
    void shouldSelectImpressive() {
        radio.selectImpressive();
        assertTrue(radio.isSelected(IMPRESSIVE), "Expected IMPRESSIVE to be selected");
    }

    @Test
    @DisplayName("03 – Selecting same radio is idempotent")
    void shouldBeIdempotent() {
        radio.selectYes()
                .selectYes();
        assertTrue(radio.isSelected(YES));
    }

    @Test
    @DisplayName("04 – Selecting another radio unselects previous")
    void shouldUnselectPreviousWhenNewSelected() {
        radio.selectYes()
                .selectImpressive();

        assertTrue(radio.isSelected(IMPRESSIVE), "Impressive should be selected");
        assertFalse(radio.isSelected(YES), "Yes should NOT be selected");
    }

    @Test
    @DisplayName("05 – Disabled radio cannot be selected")
    void shouldNotSelectDisabledRadio() {
        radio.selectYes();
        radio.attemptSelectNo(YES);

        assertTrue(radio.isDisabled(NO), "Expected NO to be disabled");
        assertTrue(radio.isSelected(YES), "Expected YES to remain selected");
        assertEquals(YES, radio.getResultText(), "Result text should still be YES");
    }

    @Test
    @DisplayName("06 – Only one radio can be selected")
    void shouldAllowOnlyOneSelection() {
        radio.selectYes();
        assertEquals(1, radio.getSelectedCount(), "Expected exactly 1 selected");

        radio.selectImpressive();
        assertEquals(1, radio.getSelectedCount(), "Expected exactly 1 selected");
    }

    @Test
    @DisplayName("07 – No selection initially")
    void shouldHaveNoInitialSelection() {
        assertEquals(0, radio.getSelectedCount(), "Expected NO selection initially");
    }

    @Test
    @DisplayName("08 – Invalid label fails deterministically")
    void shouldFailOnInvalidLabel() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> radio.selectByLabel("INVALID"));
        assertTrue(ex.getMessage().contains("Unknown radio label"));
    }

    @Test
    @DisplayName("09 – Full realistic workflow")
    void shouldHandleFullWorkflow() {
        assertEquals(0, radio.getSelectedCount());

        radio.selectYes();
        assertTrue(radio.isSelected(YES));

        radio.selectImpressive();
        assertTrue(radio.isSelected(IMPRESSIVE));

        radio.attemptSelectNo(IMPRESSIVE);
        assertTrue(radio.isSelected(IMPRESSIVE));
    }
}
