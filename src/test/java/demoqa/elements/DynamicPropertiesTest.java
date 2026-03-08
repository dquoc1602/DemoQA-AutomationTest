package demoqa.elements;

import core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.demoqa.elements.DynamicPropertiesPage;

public class DynamicPropertiesTest extends BaseTest {

    private DynamicPropertiesPage page;

    @BeforeMethod
    void setup() {
        page = new DynamicPropertiesPage();
    }

    @Test(description = "Verify button becomes enabled after 5 seconds")
    void testEnableAfter() {
        // Initial state
        Assert.assertFalse(page.isEnableButtonEnabled(), "Button should be initially disabled");

        // Wait and verify
        page.waitForEnableButton();
        Assert.assertTrue(page.isEnableButtonEnabled(), "Button should be enabled after 5 seconds");
    }

    @Test(description = "Verify button changes color (class) after 5 seconds")
    void testColorChange() {
        String initialClass = page.getColorButtonClass();
        Assert.assertFalse(initialClass.contains("text-danger"), "Button should not have 'text-danger' initially");

        // Wait for color change (DemoQA usually turns it red/text-danger after 5s)
        page.waitForColorChange("text-danger");
        Assert.assertTrue(page.getColorButtonClass().contains("text-danger"),
                "Button should have 'text-danger' class after delay");
    }

    @Test(description = "Verify button becomes visible after 5 seconds")
    void testVisibleAfter() {
        // Initial state - it should not be in the DOM or at least not visible
        Assert.assertFalse(page.isVisibleButtonDisplayed(), "Button should not be visible initially");

        // Wait and verify
        page.waitForVisibleButton();
        Assert.assertTrue(page.isVisibleButtonDisplayed(), "Button should be visible after 5 seconds");
    }
}
