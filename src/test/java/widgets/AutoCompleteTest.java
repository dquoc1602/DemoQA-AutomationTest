package widgets;

import core.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.demoQA.widgets.AutoCompletePage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Auto Complete Tests")
public class AutoCompleteTest extends BaseTest {

    private AutoCompletePage page;

    @BeforeEach
    void setup() {
        page = new AutoCompletePage();
    }

    @Test
    @DisplayName("Verify multiple colors autocomplete and selection")
    void testMultipleAutoComplete() {
        String[] colors = { "Red", "Green", "Blue" };
        page.typeAndSelectMultiple(colors);

        List<String> selectedColors = page.getMultipleValues();
        assertEquals(3, selectedColors.size(), "Should have 3 colors selected");
        for (String color : colors) {
            assertTrue(selectedColors.contains(color), "Expected " + color + " to be selected");
        }
    }

    @Test
    @DisplayName("Verify single color autocomplete and selection")
    void testSingleAutoComplete() {
        String color = "Magenta";
        page.typeAndSelectSingle(color);

        String selectedColor = page.getSingleValue();
        assertEquals(color, selectedColor, "Single color selected mismatch");
    }

    @Test
    @DisplayName("Verify removing specific colors from multiple selection")
    void testRemoveColors() {
        page.typeAndSelectMultiple("Red", "Green", "Blue");
        page.removeMultipleValue("Green");

        List<String> selectedColors = page.getMultipleValues();
        assertEquals(2, selectedColors.size(), "Should have 2 colors left");
        assertFalse(selectedColors.contains("Green"), "Green should have been removed");
        assertTrue(selectedColors.contains("Red"), "Red should still be there");
        assertTrue(selectedColors.contains("Blue"), "Blue should still be there");
    }

    @Test
    @DisplayName("Verify clearing all colors in multiple selection")
    void testClearAllColors() {
        page.typeAndSelectMultiple("Red", "Green", "Blue");
        page.clearAllMultiple();

        List<String> selectedColors = page.getMultipleValues();
        assertTrue(selectedColors.isEmpty(), "All selected colors should be cleared");
    }
}
