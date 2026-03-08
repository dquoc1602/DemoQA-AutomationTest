package demoqa.elements;

import core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.demoqa.elements.RadioButtonPage;

public class RadioButtonTest extends BaseTest {

    private RadioButtonPage radio;

    private static final String YES = "Yes";
    private static final String IMPRESSIVE = "Impressive";
    private static final String NO = "No";

    @BeforeMethod
    void setup() {
        radio = new RadioButtonPage();
    }

    @Test(priority = 1, description = "01 – Select Yes radio")
    void shouldSelectYes() {
        radio.selectYes();
        Assert.assertTrue(radio.isSelected(YES), "Expected YES to be selected");
        Assert.assertEquals(radio.getResultText(), YES, "Result text mismatch");
    }

    @Test(priority = 2, description = "02 – Select Impressive radio")
    void shouldSelectImpressive() {
        radio.selectImpressive();
        Assert.assertTrue(radio.isSelected(IMPRESSIVE), "Expected IMPRESSIVE to be selected");
    }

    @Test(priority = 3, description = "03 – Selecting same radio is idempotent")
    void shouldBeIdempotent() {
        radio.selectYes()
                .selectYes();
        Assert.assertTrue(radio.isSelected(YES));
    }

    @Test(priority = 4, description = "04 – Selecting another radio unselects previous")
    void shouldUnselectPreviousWhenNewSelected() {
        radio.selectYes()
                .selectImpressive();

        Assert.assertTrue(radio.isSelected(IMPRESSIVE), "Impressive should be selected");
        Assert.assertFalse(radio.isSelected(YES), "Yes should NOT be selected");
    }

    @Test(priority = 5, description = "05 – Disabled radio cannot be selected")
    void shouldNotSelectDisabledRadio() {
        radio.selectYes();
        radio.attemptSelectNo(YES);

        Assert.assertTrue(radio.isDisabled(NO), "Expected NO to be disabled");
        Assert.assertTrue(radio.isSelected(YES), "Expected YES to remain selected");
        Assert.assertEquals(radio.getResultText(), YES, "Result text should still be YES");
    }

    @Test(priority = 6, description = "06 – Only one radio can be selected")
    void shouldAllowOnlyOneSelection() {
        radio.selectYes();
        Assert.assertEquals(radio.getSelectedCount(), 1, "Expected exactly 1 selected");

        radio.selectImpressive();
        Assert.assertEquals(radio.getSelectedCount(), 1, "Expected exactly 1 selected");
    }

    @Test(priority = 7, description = "07 – No selection initially")
    void shouldHaveNoInitialSelection() {
        Assert.assertEquals(radio.getSelectedCount(), 0, "Expected NO selection initially");
    }

    @Test(priority = 8, description = "08 – Invalid label fails deterministically")
    void shouldFailOnInvalidLabel() {
        IllegalArgumentException ex = Assert.expectThrows(
                IllegalArgumentException.class,
                () -> radio.selectByLabel("INVALID"));
        Assert.assertTrue(ex.getMessage().contains("Unknown radio label"));
    }

    @Test(priority = 9, description = "09 – Full realistic workflow")
    void shouldHandleFullWorkflow() {
        Assert.assertEquals(radio.getSelectedCount(), 0);

        radio.selectYes();
        Assert.assertTrue(radio.isSelected(YES));

        radio.selectImpressive();
        Assert.assertTrue(radio.isSelected(IMPRESSIVE));

        radio.attemptSelectNo(IMPRESSIVE);
        Assert.assertTrue(radio.isSelected(IMPRESSIVE));
    }
}
