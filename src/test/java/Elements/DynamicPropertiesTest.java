package Elements;

import core.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.demoQA.Elements.DynamicPropertiesPage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Dynamic Properties Page Tests")
public class DynamicPropertiesTest extends BaseTest {

    private DynamicPropertiesPage page;

    @BeforeEach
    void setup() {
        page = new DynamicPropertiesPage();
    }

    @Test
    @DisplayName("Verify button becomes enabled after 5 seconds")
    void testEnableAfter() {
        // Initial state
        assertFalse(page.isEnableButtonEnabled(), "Button should be initially disabled");

        // Wait and verify
        page.waitForEnableButton();
        assertTrue(page.isEnableButtonEnabled(), "Button should be enabled after 5 seconds");
    }

    @Test
    @DisplayName("Verify button changes color (class) after 5 seconds")
    void testColorChange() {
        String initialClass = page.getColorButtonClass();
        assertFalse(initialClass.contains("text-danger"), "Button should not have 'text-danger' initially");

        // Wait for color change (DemoQA usually turns it red/text-danger after 5s)
        page.waitForColorChange("text-danger");
        assertTrue(page.getColorButtonClass().contains("text-danger"),
                "Button should have 'text-danger' class after delay");
    }

    @Test
    @DisplayName("Verify button becomes visible after 5 seconds")
    void testVisibleAfter() {
        // Initial state - it should not be in the DOM or at least not visible
        assertFalse(page.isVisibleButtonDisplayed(), "Button should not be visible initially");

        // Wait and verify
        page.waitForVisibleButton();
        assertTrue(page.isVisibleButtonDisplayed(), "Button should be visible after 5 seconds");
    }
}
