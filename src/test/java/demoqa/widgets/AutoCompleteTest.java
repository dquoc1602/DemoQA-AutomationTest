package demoqa.widgets;

import core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.demoqa.widgets.AutoCompletePage;

import java.util.List;

public class AutoCompleteTest extends BaseTest {

    private AutoCompletePage page;

    @BeforeMethod
    void setup() {
        page = new AutoCompletePage();
    }

    @Test(description = "Verify multiple colors autocomplete and selection")
    void testMultipleAutoComplete() {
        String[] colors = { "Red", "Green", "Blue" };
        page.typeAndSelectMultiple(colors);

        List<String> selectedColors = page.getMultipleValues();
        Assert.assertEquals(selectedColors.size(), 3, "Should have 3 colors selected");
        for (String color : colors) {
            Assert.assertTrue(selectedColors.contains(color), "Expected " + color + " to be selected");
        }
    }

    @Test(description = "Verify single color autocomplete and selection")
    void testSingleAutoComplete() {
        String color = "Magenta";
        page.typeAndSelectSingle(color);

        String selectedColor = page.getSingleValue();
        Assert.assertEquals(selectedColor, color, "Single color selected mismatch");
    }

    @Test(description = "Verify removing specific colors from multiple selection")
    void testRemoveColors() {
        page.typeAndSelectMultiple("Red", "Green", "Blue");
        page.removeMultipleValue("Green");

        List<String> selectedColors = page.getMultipleValues();
        Assert.assertEquals(selectedColors.size(), 2, "Should have 2 colors left");
        Assert.assertFalse(selectedColors.contains("Green"), "Green should have been removed");
        Assert.assertTrue(selectedColors.contains("Red"), "Red should still be there");
        Assert.assertTrue(selectedColors.contains("Blue"), "Blue should still be there");
    }

    @Test(description = "Verify clearing all colors in multiple selection")
    void testClearAllColors() {
        page.typeAndSelectMultiple("Red", "Green", "Blue");
        page.clearAllMultiple();

        List<String> selectedColors = page.getMultipleValues();
        Assert.assertTrue(selectedColors.isEmpty(), "All selected colors should be cleared");
    }
}
